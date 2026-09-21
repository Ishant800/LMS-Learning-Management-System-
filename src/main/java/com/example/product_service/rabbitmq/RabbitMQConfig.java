package com.example.product_service.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "app.exchange";

    public static final String EMAIL_QUEUE = "email.queue";
    public static final String NOTIFICATION_QUEUE = "notification.queue";

    public static final String EMAIL_ROUTING = "email.send";
    public static final String NOTIFICATION_ROUTING = "notification.send";



    // exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    //Queues

    //email queue
    @Bean
    public Queue emailQueue(){
        return new Queue(EMAIL_QUEUE,true);
    }

    //notification queue
    @Bean
    public Queue notificationQueue(){
        return new Queue(NOTIFICATION_QUEUE,true);
    }

    //bindings
    @Bean
    public Binding emailBinding(
            Queue emailQueue,
            TopicExchange exchange
    ){
        return BindingBuilder
                .bind(emailQueue)
                .to(exchange)
                .with(EMAIL_ROUTING);
    }

    @Bean
    public Binding notificationBinding(
            Queue notificationQueue,
            TopicExchange exchange
    ){
        return BindingBuilder
                .bind(notificationQueue)
                .to(exchange)
                .with(NOTIFICATION_ROUTING);
    }



    @Bean
    public MessageConverter jsonMessageConverter(){
        return new JacksonJsonMessageConverter();
    }


}
