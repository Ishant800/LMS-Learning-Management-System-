package com.example.product_service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendNotificationToTenant(String toEmail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ishantkarmacharya@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Organization Registration");
        String text = "Your Organisation Successfully Registered on CRUD24 "+
                "We are requested to update your business in CRUD24. " + "Thank you choosing us!";
        message.setText(text);

        javaMailSender.send(message);
    }

    public void sendTenantIdToUser(String toEmail,String TenantId){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ishantkarmacharya@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Request to provide tenantId");
        String text = toEmail + "users requested to create their profile in your organisation " +
                "You can share your business id with from your admin panel.";
        message.setText(text);
        javaMailSender.send(message);

    }

    public void wellcomeMessageToUser(String toEmail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ishantkarmacharya@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Wellcome Message.");
        message.setText("Wellcome Mr."+toEmail + "in CRUD24");
        javaMailSender.send(message);

    }

}
