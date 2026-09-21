# RabbitMQ Architecture & Message Flow

## Current Architecture (FIXED) ✅

```
┌─────────────────────────────────────────────────────────────────┐
│                      TENANT CREATION FLOW                        │
└─────────────────────────────────────────────────────────────────┘

1. HTTP Request
   │
   ├─► POST /tenant/create
   │   Body: Tenant JSON
   │
   ▼
┌──────────────────────┐
│  TenantController    │
│  /tenant/create      │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│   TenantService      │
│   createTenant()     │
└──────────┬───────────┘
           │
           ├─► Save to Database (MySQL)
           │
           ├─► Call MessageProducer
           │
           ▼
┌────────────────────────────────────────────────────────────┐
│  MessageProducer.sendRegistrationEmailTask(Tenant tenant)  │
│                                                            │
│  1. Tenant object → JacksonJsonMessageConverter           │
│  2. Serialize to JSON                                     │
│  3. Send: exchange + routingKey + tenant                  │
└──────────────────────┬─────────────────────────────────────┘
                       │
                       ▼
           ┌───────────────────────┐
           │   RabbitMQ Exchange   │
           │   "demo-exchange"     │
           │   (TopicExchange)     │
           └───────────┬───────────┘
                       │
                       │ Routing Key: "demo-routing-key"
                       │
                       ▼
           ┌───────────────────────┐
           │   RabbitMQ Queue      │
           │   "demo-queue"        │
           │   (Durable)           │
           └───────────┬───────────┘
                       │
                       │ @RabbitListener
                       │
                       ▼
┌──────────────────────────────────────────────────────────┐
│  MessageConsumer.processWelcomeEmail(Tenant tenant)      │
│                                                          │
│  1. Receive JSON from queue                             │
│  2. JacksonJsonMessageConverter → Tenant object         │
│  3. Process: Send welcome email (simulated)             │
│  4. Thread.sleep(2000) - simulate email sending         │
│  5. Log success ✅                                       │
└──────────────────────────────────────────────────────────┘
```

---

## Configuration Components

### 1. Exchange Configuration
```java
@Bean
public TopicExchange exchange() {
    return new TopicExchange("demo-exchange");
}
```
- **Type:** TopicExchange
- **Name:** demo-exchange
- **Purpose:** Routes messages based on routing key patterns

### 2. Queue Configuration
```java
@Bean
public Queue queue() {
    return new Queue("demo-queue", true);
}
```
- **Name:** demo-queue
- **Durable:** true (survives RabbitMQ restart)
- **Purpose:** Stores messages until consumed

### 3. Binding Configuration
```java
@Bean
public Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue)
            .to(exchange)
            .with("demo-routing-key");
}
```
- **Routing Key:** demo-routing-key
- **Purpose:** Connect exchange to queue

### 4. Message Converter
```java
@Bean
public MessageConverter jsonMessageConverter() {
    return new JacksonJsonMessageConverter();
}
```
- **Type:** JacksonJsonMessageConverter (Jackson 3.x)
- **Purpose:** Serialize/deserialize Java objects ↔ JSON
- **Spring Boot 4.0 Compatible:** ✅

### 5. RabbitTemplate
```java
@Bean
public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(jsonMessageConverter());
    return template;
}
```
- **Purpose:** Send messages to RabbitMQ
- **Configured with:** JSON message converter

---

## Message Structure

### Tenant Object Serialization

**Before Sending (Java Object):**
```java
Tenant tenant = new Tenant();
tenant.setId("tenant123");
tenant.setTenantName("Test Company");
tenant.setAdminEmail("admin@test.com");
// ... other fields
```

**In RabbitMQ Queue (JSON):**
```json
{
  "id": "tenant123",
  "tenantName": "Test Company",
  "orgName": "Test Organization",
  "adminEmail": "admin@test.com",
  "adminPassword": "encrypted",
  "address": "123 Main St",
  "contactEmail": "contact@test.com",
  "contactPhone": "1234567890",
  "active": true,
  "subscriptionPlan": "PREMIUM",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

**Note:** `users` and `courses` fields are **NOT** included due to `@JsonIgnoreProperties`

**After Receiving (Java Object):**
```java
// Deserialized back to Tenant object
Tenant tenant = /* from JSON */
tenant.getTenantName(); // "Test Company"
tenant.getAdminEmail(); // "admin@test.com"
```

---

## Problem vs Solution

### ❌ BEFORE (BROKEN)

```
┌─────────────────────┐
│   RabbitMQ Queue    │
│   "demo-queue"      │
└──────┬──────────────┘
       │
       ├──► Listener 1: receiveMessage(String message)
       │    Expects: String
       │    Gets: Tenant JSON
       │    Result: ❌ DESERIALIZATION ERROR
       │
       └──► Listener 2: processWelcomeEmail(Tenant tenant)
            Expects: Tenant
            Gets: Tenant JSON
            Result: ✅ Would work, but Listener 1 fails first
            
ERROR: AmqpRejectAndDontRequeueException
Reason: Listener 1 cannot convert Tenant JSON to String
Action: Message rejected, not requeued
```

### ✅ AFTER (FIXED)

```
┌─────────────────────┐
│   RabbitMQ Queue    │
│   "demo-queue"      │
└──────┬──────────────┘
       │
       └──► ONLY Listener: processWelcomeEmail(Tenant tenant)
            Expects: Tenant
            Gets: Tenant JSON
            Converter: JacksonJsonMessageConverter (Jackson 3.x)
            Result: ✅ SUCCESS
            
NO ERROR!
Message processed successfully ✅
```

---

## Error Handling Flow

### When Error Occurs in Listener

```java
@RabbitListener(queues = "${app.queue.name}")
public void processWelcomeEmail(Tenant tenant) {
    try {
        // Processing logic
    } catch (Exception e) {
        // Log error
        System.err.println("❌ Error: " + e.getMessage());
        
        // Re-throw to let RabbitMQ handle
        throw new RuntimeException("Failed to process", e);
    }
}
```

**Flow:**
1. Exception thrown in listener
2. RabbitMQ error handler catches it
3. Checks if error is recoverable
4. If NOT recoverable → `AmqpRejectAndDontRequeueException`
5. Message rejected (not requeued)

**Optional:** Add Dead Letter Queue for failed messages

---

## Testing Checklist

- [x] RabbitMQ running on localhost:5672
- [x] Application starts without errors
- [x] Can create tenant via API
- [x] Producer logs message sending
- [x] Consumer receives and processes message
- [x] No `AmqpRejectAndDontRequeueException` errors
- [x] Email processing completes successfully

---

## Monitoring & Debugging

### RabbitMQ Management Console
Access: http://localhost:15672
- Username: guest
- Password: guest

**Check:**
- Queues → demo-queue (should have 0 messages after processing)
- Exchanges → demo-exchange (should show bindings)
- Connections (application connected)

### Application Logs

**Producer:**
```
📤 [Producer] Enqueuing email task for: admin@test.com
🔄 Sending to Exchange: demo-exchange with Routing Key: demo-routing-key
✅ [Producer] Task successfully enqueued!
```

**Consumer:**
```
📥 [Consumer] Received task from RabbitMQ!
✉️ Sending welcome email to: admin@test.com
📝 Tenant Name: Test Company
🏢 Organization: Test Organization
✅ Email sent successfully to Test Company
```

---

## Common Issues & Solutions

### Issue 1: Connection Refused
**Symptom:** `Connection refused: localhost:5672`
**Solution:** Start RabbitMQ server

### Issue 2: Queue Not Found
**Symptom:** `Queue 'demo-queue' not found`
**Solution:** Check application.properties configuration

### Issue 3: Serialization Error
**Symptom:** `Cannot serialize object`
**Solution:** 
- Check `@JsonIgnoreProperties` on entity
- Ensure RabbitTemplate has message converter

### Issue 4: Multiple Listeners
**Symptom:** `AmqpRejectAndDontRequeueException`
**Solution:** 
- ONE listener per queue
- Consistent message types

---

## Future Enhancements

### 1. Dead Letter Queue
```java
@Bean
public Queue queue() {
    return QueueBuilder.durable(queueName)
            .withArgument("x-dead-letter-exchange", "dlx")
            .withArgument("x-dead-letter-routing-key", "dlx-key")
            .build();
}
```

### 2. Retry Mechanism
```properties
spring.rabbitmq.listener.simple.retry.enabled=true
spring.rabbitmq.listener.simple.retry.max-attempts=3
```

### 3. Message Priority
```java
@Bean
public Queue queue() {
    return QueueBuilder.durable(queueName)
            .maxPriority(10)
            .build();
}
```

### 4. TTL (Time To Live)
```java
@Bean
public Queue queue() {
    return QueueBuilder.durable(queueName)
            .ttl(300000) // 5 minutes
            .build();
}
```

---

**Status:** ✅ Architecture Documented and Working
**Last Updated:** 2024
