package com.example.product_service.rabbitmq;

import com.example.product_service.entity.Tenant;
import com.example.product_service.entity.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;


    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendRegistrationEmail(User tenant){
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.EMAIL_ROUTING,
                    tenant);
            
            System.out.println("✅ [Producer] Task successfully enqueued!");
        } catch (Exception e) {
            System.err.println("❌ [Producer] Failed to send message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to send registration email task", e);
        }
    }

    public void sendNotification(Tenant tenant){
        try{
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.NOTIFICATION_ROUTING,
                    tenant
            );
        }catch(Exception e){
            System.err.println("❌ [Producer] Failed to send message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to send registration email task", e);
        }
    }

}
