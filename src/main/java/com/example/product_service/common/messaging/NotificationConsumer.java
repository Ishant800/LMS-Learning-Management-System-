package com.example.product_service.common.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final JavaMailSender mailSender;

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void handleNotificationEvent(NotificationEvent event) throws InterruptedException {
        String subject = "";
        String body = "";

        //switch based on scenario type
        switch (event.type()){
            case TENANT_REGISTRATION -> {
                subject = "Welcome to the LMS Portal!";
                body = String.format("Hello %s, your organization %s is successfully set up.",
                        event.templateData().get("adminName"), event.templateData().get("orgName"));
            }

            case USER_REGISTRATION -> {
                subject = "Account Created Successfully";
                body = String.format("Your account has been mapped to %s. Welcome aboard!",
                        event.templateData().get("orgName"));
            }
            case ADMIN_USER_ALERT -> {
                subject = "New User Registration Alert";
                body = String.format("User %s just registered under your organization %s.",
                        event.templateData().get("registrantName"), event.templateData().get("orgName"));
            }
        }
        Thread.sleep(10000);
        sendEmail(event.recipientEmail(),subject,body);

    }

    private void sendEmail(String to, String subject,String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

    }

}
