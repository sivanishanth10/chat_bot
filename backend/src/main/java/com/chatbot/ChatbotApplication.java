package com.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class for the AI Chatbot
 * This application provides a REST API backend for the chatbot frontend
 * and integrates with Google's Gemini AI API for intelligent responses.
 * 
 * @author Chatbot Team
 * @version 1.0.0
 */
@SpringBootApplication
public class ChatbotApplication {

    /**
     * Main method to start the Spring Boot application
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ChatbotApplication.class, args);
        System.out.println("🚀 Chatbot Backend started successfully!");
        System.out.println("📱 Frontend can be accessed at: http://localhost:3000");
        System.out.println("🔌 Backend API available at: http://localhost:8080/api");
    }
}
