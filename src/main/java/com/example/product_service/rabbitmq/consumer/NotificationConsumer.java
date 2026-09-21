package com.example.product_service.rabbitmq.consumer;

import com.example.product_service.EmailService.EmailSender;
import com.example.product_service.entity.Tenant;
import com.example.product_service.rabbitmq.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {
    private final EmailSender emailSender ;

    public NotificationConsumer(EmailSender emailSender) {
        this.emailSender = emailSender;
    }


    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void sendEmail(Tenant tenant) throws InterruptedException {
        System.out.println("notification consumer recived: " );
        System.out.println("Processing notification.....");
        Thread.sleep(20000);

        emailSender.sendNotificationToTenantAboutUser(tenant.getAdminEmail());
        System.out.println("email send sucessfully");
    }
}
