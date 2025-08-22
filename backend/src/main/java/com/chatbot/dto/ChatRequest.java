package com.chatbot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for chat requests from the frontend
 * Contains validation annotations to ensure data integrity
 * 
 * @author Chatbot Team
 * @version 1.0.0
 */
public class ChatRequest {

    @NotBlank(message = "User message cannot be empty")
    @Size(max = 1000, message = "User message cannot exceed 1000 characters")
    private String userMessage;

    @Size(max = 100, message = "Session ID cannot exceed 100 characters")
    private String sessionId;

    // Default constructor
    public ChatRequest() {}

    // Constructor with required fields
    public ChatRequest(String userMessage, String sessionId) {
        this.userMessage = userMessage;
        this.sessionId = sessionId;
    }

    // Getters and Setters
    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "ChatRequest{" +
                "userMessage='" + userMessage + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
