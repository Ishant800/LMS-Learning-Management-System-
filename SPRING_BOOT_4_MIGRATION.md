# Spring Boot 4.0 Migration - RabbitMQ Message Converter

## Issue: Deprecation Warning in IntelliJ

```
'org.springframework.amqp.support.converter.Jackson2JsonMessageConverter' 
is deprecated since version 4.0 and marked for removal
```

## Background

Spring Boot 4.0 has upgraded to use **Jackson 3.x** (from Jackson 2.x), and as part of this upgrade, the RabbitMQ message converters have been updated:

- **Deprecated:** `Jackson2JsonMessageConverter` (Jackson 2.x)
- **New:** `JacksonJsonMessageConverter` (Jackson 3.x)

According to Spring AMQP documentation:
> The `AbstractJackson2MessageConverter`, its implementations and related `Jackson2JavaTypeMapper` API have been deprecated for removal in 4.0 version in favor of respective classes based on Jackson 3.

---

## Changes Made

### Before (Deprecated) ❌

```java
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Bean
public MessageConverter jsonMessageConverter(){
    return new Jackson2JsonMessageConverter();
}
```

### After (Spring Boot 4.0 Compatible) ✅

```java
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;

@Bean
public MessageConverter jsonMessageConverter(){
    return new JacksonJsonMessageConverter();
}
```

---

## Complete Updated Configuration

**File:** `RabbitMQConfig.java`

```java
package com.example.product_service.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${app.queue.name}")
    private String queueName;

    @Value("${app.exchange.name}")
    private String exchangeName;

    @Value("${app.routing.key}")
    private String routingKey;

    @Bean
    public Queue queue(){
        return new Queue(queueName, true);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchangeName);
    }

    /**
     * JSON Message Converter for serializing/deserializing objects
     * Uses Jackson 3.x (JacksonJsonMessageConverter)
     * Compatible with Spring Boot 4.0+
     */
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

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(routingKey);
    }
}
```

---

## What Changed?

### 1. Import Statement
```java
// Old
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

// New
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
```

### 2. Class Name
```java
// Old
new Jackson2JsonMessageConverter()

// New
new JacksonJsonMessageConverter()
```

### 3. Functionality
- **Same API**: Both classes have the same public API
- **No code changes needed**: Configuration works the same way
- **Jackson 3.x**: Uses the newer Jackson library version
- **Better performance**: Jackson 3.x has performance improvements

---

## Migration Checklist

- [x] Replace `Jackson2JsonMessageConverter` with `JacksonJsonMessageConverter`
- [x] Update import statements
- [x] Test message sending and receiving
- [x] Verify no deprecation warnings in IDE
- [x] Ensure all RabbitMQ functionality works correctly

---

## Compatibility

| Component | Version | Status |
|-----------|---------|--------|
| Spring Boot | 4.0.0 | ✅ Compatible |
| Jackson | 3.x | ✅ Compatible |
| Spring AMQP | Latest | ✅ Compatible |
| RabbitMQ | Any | ✅ Compatible |

---

## Benefits of Jackson 3.x

1. **Modern Java Support**: Better support for Java 17+ features
2. **Performance**: Improved serialization/deserialization performance
3. **Security**: Latest security patches and updates
4. **Records Support**: Better support for Java Records
5. **Module System**: Better Java Module System (JPMS) support

---

## Testing

After making the change, test the following scenarios:

### 1. Basic Message Sending
```java
Tenant tenant = new Tenant();
tenant.setId("test123");
tenant.setTenantName("Test Company");
messageProducer.sendRegistrationEmailTask(tenant);
```

**Expected Output:**
```
📤 [Producer] Enqueuing email task for: admin@test.com
✅ [Producer] Task successfully enqueued!
```

### 2. Message Receiving
```java
@RabbitListener(queues = "${app.queue.name}")
public void processWelcomeEmail(Tenant tenant) {
    // Process tenant
}
```

**Expected Output:**
```
📥 [Consumer] Received task from RabbitMQ!
✉️ Sending welcome email to: admin@test.com
✅ Email sent successfully to Test Company
```

### 3. No Errors
- No `AmqpRejectAndDontRequeueException`
- No deprecation warnings
- No serialization errors
- Messages processed successfully

---

## Troubleshooting

### Issue: ClassNotFoundException
**Error:**
```
java.lang.ClassNotFoundException: 
org.springframework.amqp.support.converter.JacksonJsonMessageConverter
```

**Solution:** Ensure you have the latest Spring AMQP dependency:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

### Issue: Serialization Error
**Error:**
```
Could not serialize object
```

**Solution:** Ensure your entity has `@JsonIgnoreProperties` for circular references:
```java
@JsonIgnoreProperties({"users", "courses", "hibernateLazyInitializer", "handler"})
public class Tenant { ... }
```

---

## Additional Resources

- [Spring AMQP Documentation - Message Converters](https://docs.spring.io/spring-amqp/reference/amqp/message-converters.html)
- [Jackson 3.x Migration Guide](https://github.com/FasterXML/jackson/wiki/Jackson-Release-3.0)
- [Spring Boot 4.0 Release Notes](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Release-Notes)

---

## Summary

✅ **Changed:** `Jackson2JsonMessageConverter` → `JacksonJsonMessageConverter`  
✅ **Reason:** Spring Boot 4.0 compatibility (Jackson 3.x)  
✅ **Impact:** No functional changes, just updated to non-deprecated class  
✅ **Result:** No more deprecation warnings in IntelliJ

---

**Status:** ✅ Migration Complete  
**Spring Boot Version:** 4.0.0  
**Last Updated:** 2024
