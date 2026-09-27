package com.example.product_service.common.messaging;

import com.example.product_service.modules.tenant.dto.TenantEmailPayload;
import com.example.product_service.modules.tenant.entity.Tenant;
import com.example.product_service.modules.auth.entity.User;
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

    public void tenantRegister(String email,String orgName){
        try {
            TenantEmailPayload payload = new TenantEmailPayload(email,orgName);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.EMAIL_ROUTING,
                    payload
                    );


        } catch (Exception e) {
            System.err.println(" [Producer] Failed to send message: " + e.getMessage());
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
