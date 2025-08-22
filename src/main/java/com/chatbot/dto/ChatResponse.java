package com.chatbot.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for chat responses to the frontend
 * Contains the AI response and metadata about the conversation
 * 
 * @author Chatbot Team
 * @version 1.0.0
 */
public class ChatResponse {

    private String aiResponse;
    private String sessionId;
    private LocalDateTime timestamp;
    private Long responseTimeMs;
    private String status;
    private String message;

    // Default constructor
    public ChatResponse() {
        this.timestamp = LocalDateTime.now();
        this.status = "SUCCESS";
    }

    // Constructor for successful response
    public ChatResponse(String aiResponse, String sessionId, Long responseTimeMs) {
        this();
        this.aiResponse = aiResponse;
        this.sessionId = sessionId;
        this.responseTimeMs = responseTimeMs;
    }

    // Constructor for error response
    public ChatResponse(String status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    // Getters and Setters
    public String getAiResponse() {
        return aiResponse;
    }

    public void setAiResponse(String aiResponse) {
        this.aiResponse = aiResponse;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getResponseTimeMs() {
        return responseTimeMs;
    }

    public void setResponseTimeMs(Long responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatResponse{" +
                "aiResponse='" + aiResponse + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", timestamp=" + timestamp +
                ", responseTimeMs=" + responseTimeMs +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
