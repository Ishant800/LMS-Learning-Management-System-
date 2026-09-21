package com.example.product_service.rabbitmq;

import com.example.product_service.entity.Tenant;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {


    @RabbitListener(queues = "${app.queue.name}")
    public void processWelcomeEmail(Tenant tenant){
        try {
            System.out.println("📥 [Consumer] Received task from RabbitMQ!");
            System.out.println("✉️ Sending welcome email to: " + tenant.getAdminEmail());
            System.out.println("📝 Tenant Name: " + tenant.getTenantName());
            System.out.println("🏢 Organization: " + tenant.getOrgName());
            
            // Simulate email sending
            Thread.sleep(10000);
            
            System.out.println("✅ Email sent successfully to " + tenant.getTenantName());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ Email sending interrupted for " + tenant.getTenantName());
        } catch (Exception e) {
            System.err.println("❌ Error processing email task: " + e.getMessage());
            e.printStackTrace();
            // Re-throw to allow RabbitMQ error handler to process
            throw new RuntimeException("Failed to process welcome email for tenant: " + tenant.getTenantName(), e);
        }
    }

}
