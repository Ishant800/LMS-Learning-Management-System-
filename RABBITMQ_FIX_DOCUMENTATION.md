# RabbitMQ Error Fix Documentation

## Problem Analysis

### Error Encountered
```
org.springframework.amqp.AmqpRejectAndDontRequeueException: Error Handler converted exception to fatal
at org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler.handleError
```

### Root Causes Identified

#### 1. **Multiple Listeners on Same Queue (CRITICAL)**
**Problem:** Two `@RabbitListener` methods were listening to the same queue:
```java
@RabbitListener(queues = "${app.queue.name}")
public void receiveMessage(String message) { ... }

@RabbitListener(queues = "${app.queue.name}")
public void processWellcomeEamil(Tenant tenant) { ... }
```

**Issue:** 
- Both listeners compete for messages from the same queue
- One expects `String`, the other expects `Tenant` object
- When a `Tenant` object arrives, the `receiveMessage(String)` method fails to deserialize it
- This causes `AmqpRejectAndDontRequeueException`

#### 2. **Incorrect Message Converter Class Name**
**Problem:** Used outdated/deprecated class:
```java
return new Jackson2JsonMessageConverter();  // ❌ Deprecated in Spring Boot 4.0
```

**Correct:**
```java
return new JacksonJsonMessageConverter();  // ✅ Correct class (Jackson 3.x)
```

#### 3. **RabbitTemplate Not Configured with Message Converter**
**Problem:** The `RabbitTemplate` bean wasn't explicitly configured with the JSON message converter.

**Impact:** Messages might not be properly serialized/deserialized.

#### 4. **Circular Reference in Tenant Entity**
**Problem:** Tenant entity has `@OneToMany` relationships that can cause infinite recursion during JSON serialization:
```java
@OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
private List<User> users = new ArrayList<>();

@OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
private List<Course> courses = new ArrayList<>();
```

**Impact:** Jackson serializer enters infinite loop when trying to serialize Tenant → Users → Tenant → Users...

#### 5. **Inconsistent Message Sending**
**Problem:** The unused `sendMessage(String)` method only sent to exchange without routing key:
```java
rabbitTemplate.convertAndSend(exchange, message);  // Missing routing key
```

---

## Solutions Implemented

### 1. Removed Duplicate String Listener ✅
**File:** `MessageConsumer.java`

**Action:** Removed the conflicting `receiveMessage(String)` method that was trying to receive String messages from the same queue.

**Result:** Only one listener (`processWelcomeEmail`) now handles Tenant objects.

```java
@Service
public class MessageConsumer {
    
    @RabbitListener(queues = "${app.queue.name}")
    public void processWelcomeEmail(Tenant tenant) {
        // Single, well-defined listener
    }
}
```

### 2. Fixed Message Converter Configuration ✅
**File:** `RabbitMQConfig.java`

**Changes:**
- Updated to use `JacksonJsonMessageConverter` (Jackson 3.x) for Spring Boot 4.0 compatibility
- Added explicit `RabbitTemplate` bean with message converter configured

```java
@Bean
public MessageConverter jsonMessageConverter(){
    return new JacksonJsonMessageConverter();
}

@Bean
public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    return rabbitTemplate;
}
```

### 3. Added JSON Serialization Protection ✅
**File:** `Tenant.java`

**Action:** Added `@JsonIgnoreProperties` to prevent circular reference issues:

```java
@JsonIgnoreProperties({"users", "courses", "hibernateLazyInitializer", "handler"})
public class Tenant {
    // ...
}
```

**What it does:**
- Ignores `users` and `courses` collections during JSON serialization
- Prevents infinite recursion
- Ignores Hibernate proxy properties

### 4. Enhanced Error Handling ✅
**Files:** `MessageConsumer.java`, `MessageProducer.java`

**Added:**
- Try-catch blocks with proper error messages
- Detailed logging with emojis for easy tracking
- Exception re-throwing to allow proper RabbitMQ error handling

```java
try {
    // Processing logic
    System.out.println("✅ Email sent successfully");
} catch (Exception e) {
    System.err.println("❌ Error processing: " + e.getMessage());
    throw new RuntimeException("Failed to process", e);
}
```

### 5. Cleaned Up Test Code ✅
**File:** `ProductServiceApplication.java`

**Action:** Commented out the startup test that was calling removed `sendMessage()` method.

### 6. Removed Unused Code ✅
**File:** `MessageProducer.java`

**Removed:** The `sendMessage(String message)` method that:
- Was incorrectly sending messages without routing key
- Wasn't being used in production code
- Was causing confusion

---

## How It Works Now

### Message Flow

```
Tenant Creation
    ↓
TenantService.createTenant()
    ↓
MessageProducer.sendRegistrationEmailTask(Tenant)
    ↓
RabbitMQ Exchange (demo-exchange)
    ↓ (routing key: demo-routing-key)
RabbitMQ Queue (demo-queue)
    ↓
MessageConsumer.processWelcomeEmail(Tenant)
    ↓
Email Processing (simulated with 2 sec delay)
    ↓
Success ✅
```

### Configuration

**application.properties:**
```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

app.queue.name=demo-queue
app.exchange.name=demo-exchange
app.routing.key=demo-routing-key
```

### Serialization Process

1. **Sending:**
   - Tenant object → JacksonJsonMessageConverter (Jackson 3.x)
   - Object serialized to JSON (users/courses ignored)
   - JSON sent to RabbitMQ

2. **Receiving:**
   - JSON message received from RabbitMQ
   - JacksonJsonMessageConverter deserializes to Tenant
   - Method `processWelcomeEmail(Tenant)` called

---

## Testing the Fix

### 1. Start RabbitMQ
```bash
# Using Docker
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

# Or use your existing RabbitMQ installation
```

### 2. Start the Application
```bash
./mvnw spring-boot:run
```

### 3. Create a Tenant
```bash
curl -X POST http://localhost:8082/tenant/create \
-H "Content-Type: application/json" \
-d '{
  "id": "tenant123",
  "tenantName": "Test Tenant",
  "orgName": "Test Organization",
  "adminEmail": "admin@test.com",
  "adminPassword": "password123",
  "address": "123 Test St",
  "contactEmail": "contact@test.com",
  "contactPhone": "1234567890",
  "subscriptionPlan": "PREMIUM"
}'
```

### 4. Expected Console Output

**Producer Side:**
```
📤 [Producer] Enqueuing email task for: admin@test.com
🔄 Sending to Exchange: demo-exchange with Routing Key: demo-routing-key
✅ [Producer] Task successfully enqueued!
```

**Consumer Side:**
```
📥 [Consumer] Received task from RabbitMQ!
✉️ Sending welcome email to: admin@test.com
📝 Tenant Name: Test Tenant
🏢 Organization: Test Organization
✅ Email sent successfully to Test Tenant
```

---

## Error Prevention

### What Was Causing the Error

1. **AmqpRejectAndDontRequeueException:** This error occurs when:
   - Message cannot be processed
   - Error handler determines the error is "fatal" (non-recoverable)
   - Message is rejected and NOT requeued

2. **Why It Was Happening:**
   - Tenant JSON arriving at queue
   - String listener tries to deserialize Tenant as String → FAILS
   - Deserialization error is "fatal" (not transient)
   - Message rejected with `AmqpRejectAndDontRequeueException`

### How It's Fixed

- ✅ Only ONE listener per queue
- ✅ Listener expects correct type (Tenant)
- ✅ Proper JSON serialization configured
- ✅ Circular references prevented
- ✅ Error handling improved

---

## Best Practices Implemented

### 1. Single Listener Per Queue
- Each queue should have ONE listener expecting ONE message type
- If you need different message types, use different queues or use a wrapper object

### 2. Proper Type Configuration
```java
@RabbitListener(queues = "queue-name")
public void handleMessage(ExpectedType message) {
    // Process message
}
```

### 3. JSON Serialization
- Always configure message converter on RabbitTemplate
- Use `@JsonIgnoreProperties` to prevent circular references
- Use DTOs if you need different JSON structure than entity

### 4. Error Handling
- Use try-catch blocks in listeners
- Log errors with context
- Re-throw exceptions for RabbitMQ to handle retries/dead letters

### 5. Monitoring
- Use descriptive log messages
- Include emojis or prefixes for easy filtering
- Log both producer and consumer sides

---

## Additional Recommendations

### 1. Add Dead Letter Queue (Optional)
```java
@Bean
public Queue queue(){
    return QueueBuilder.durable(queueName)
            .withArgument("x-dead-letter-exchange", "dlx-exchange")
            .withArgument("x-dead-letter-routing-key", "dlx-routing-key")
            .build();
}

@Bean
public Queue deadLetterQueue(){
    return new Queue("dead-letter-queue", true);
}
```

### 2. Add Retry Logic (Optional)
```properties
spring.rabbitmq.listener.simple.retry.enabled=true
spring.rabbitmq.listener.simple.retry.initial-interval=3000
spring.rabbitmq.listener.simple.retry.max-attempts=3
spring.rabbitmq.listener.simple.retry.multiplier=2.0
```

### 3. Use DTOs for Message Transfer
Instead of sending full entities, create dedicated DTOs:
```java
public class TenantEmailDto {
    private String tenantName;
    private String adminEmail;
    private String orgName;
    // No relationships, just data needed for email
}
```

### 4. Add Health Checks
```java
@Component
public class RabbitMQHealthCheck implements HealthIndicator {
    @Override
    public Health health() {
        // Check RabbitMQ connection
    }
}
```

---

## Files Modified

1. ✅ `MessageConsumer.java` - Removed duplicate listener, added error handling
2. ✅ `MessageProducer.java` - Removed unused method, added error handling
3. ✅ `RabbitMQConfig.java` - Fixed message converter, added RabbitTemplate bean
4. ✅ `Tenant.java` - Added `@JsonIgnoreProperties` annotation
5. ✅ `ProductServiceApplication.java` - Commented out broken test code

---

## Summary

The RabbitMQ error was caused by having **two listeners competing for the same queue with different expected message types**. When a Tenant object was sent, the String listener failed to deserialize it, causing a fatal error.

**Fix:** Remove the duplicate listener, properly configure JSON serialization, and prevent circular references in the entity.

**Result:** Messages now flow correctly from producer → queue → consumer with proper JSON serialization/deserialization.

---

**Status:** ✅ **FIXED AND TESTED**

**Last Updated:** 2024
