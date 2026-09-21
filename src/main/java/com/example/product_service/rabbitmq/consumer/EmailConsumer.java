package com.example.product_service.rabbitmq.consumer;

import com.example.product_service.EmailService.EmailSender;
import com.example.product_service.entity.Tenant;
import com.example.product_service.entity.User;
import com.example.product_service.rabbitmq.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final  EmailSender emailSender ;

    public EmailConsumer(EmailSender emailSender) {
        this.emailSender = emailSender;
    }


    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void sendEmail(User tenant) throws InterruptedException {
        System.out.println("email consumer recived: " );
        System.out.println("Processing email.....");
        Thread.sleep(10000);

        emailSender.sendNotificationToTenant(tenant.getEmail());
        System.out.println("email send sucessfully");
    }
}
