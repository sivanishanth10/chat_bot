package com.chatbot.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class representing a chat message in the database
 * This class maps to the chat_messages table and stores all conversation data
 * 
 * @author Chatbot Team
 * @version 1.0.0
 */
@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_message", nullable = false, columnDefinition = "TEXT")
    private String userMessage;

    @Column(name = "ai_response", nullable = false, columnDefinition = "TEXT")
    private String aiResponse;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "session_id", length = 100)
    private String sessionId;

    @Column(name = "user_ip", length = 45)
    private String userIp;

    @Column(name = "response_time_ms")
    private Long responseTimeMs;

    // Default constructor
    public ChatMessage() {
        this.timestamp = LocalDateTime.now();
    }

    // Constructor with required fields
    public ChatMessage(String userMessage, String aiResponse, String sessionId) {
        this();
        this.userMessage = userMessage;
        this.aiResponse = aiResponse;
        this.sessionId = sessionId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getAiResponse() {
        return aiResponse;
    }

    public void setAiResponse(String aiResponse) {
        this.aiResponse = aiResponse;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public Long getResponseTimeMs() {
        return responseTimeMs;
    }

    public void setResponseTimeMs(Long responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", userMessage='" + userMessage + '\'' +
                ", aiResponse='" + aiResponse + '\'' +
                ", timestamp=" + timestamp +
                ", sessionId='" + sessionId + '\'' +
                ", userIp='" + userIp + '\'' +
                ", responseTimeMs=" + responseTimeMs +
                '}';
    }
}
