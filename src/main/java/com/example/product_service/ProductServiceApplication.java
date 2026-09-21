package com.example.product_service;

import com.example.product_service.rabbitmq.MessageProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	/**
	 * Test RabbitMQ connection on application startup
	 * This bean is commented out by default to avoid test messages
	 * Uncomment if you want to test RabbitMQ connection on startup
	 */
	// @Bean
	// public CommandLineRunner testRabbitMQ(MessageProducer producer){
	// 	return args -> {
	// 		System.out.println("🧪 Testing RabbitMQ connection...");
	// 		// Test sending is now done through actual tenant creation
	// 	};
	// }

}
