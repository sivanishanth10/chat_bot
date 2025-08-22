package com.chatbot.service;

import com.chatbot.dto.ChatRequest;
import com.chatbot.dto.ChatResponse;
import com.chatbot.model.ChatMessage;
import com.chatbot.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service class for handling chat business logic
 * Coordinates between the repository, Gemini API service, and controllers
 * 
 * @author Chatbot Team
 * @version 1.0.0
 */
@Service
@Transactional
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final GeminiApiService geminiApiService;

    @Autowired
    public ChatService(ChatMessageRepository chatMessageRepository, GeminiApiService geminiApiService) {
        this.chatMessageRepository = chatMessageRepository;
        this.geminiApiService = geminiApiService;
    }

    /**
     * Processes a chat request and returns the AI response
     * 
     * @param chatRequest the incoming chat request
     * @param userIp the user's IP address
     * @return ChatResponse containing the AI response
     */
    public ChatResponse processChatRequest(ChatRequest chatRequest, String userIp) {
        long startTime = System.currentTimeMillis();
        
        try {
            // Generate session ID if not provided
            String sessionId = chatRequest.getSessionId();
            if (sessionId == null || sessionId.trim().isEmpty()) {
                sessionId = generateSessionId();
            }

            // Get AI response from Gemini API
            String aiResponse = geminiApiService.getAiResponse(chatRequest.getUserMessage());
            
            // Calculate response time
            long responseTime = System.currentTimeMillis() - startTime;
            
            // Save the conversation to database
            ChatMessage chatMessage = new ChatMessage(
                chatRequest.getUserMessage(), 
                aiResponse, 
                sessionId
            );
            chatMessage.setUserIp(userIp);
            chatMessage.setResponseTimeMs(responseTime);
            
            chatMessageRepository.save(chatMessage);
            
            // Return the response
            return new ChatResponse(aiResponse, sessionId, responseTime);
            
        } catch (Exception e) {
            // Log the error and return error response
            return new ChatResponse("ERROR", "Failed to process chat request: " + e.getMessage());
        }
    }

    /**
     * Retrieves chat history for a specific session
     * 
     * @param sessionId the session identifier
     * @return list of chat messages for the session
     */
    public List<ChatMessage> getChatHistory(String sessionId) {
        return chatMessageRepository.findBySessionIdOrderByTimestampAsc(sessionId);
    }

    /**
     * Retrieves recent chat messages across all sessions
     * 
     * @param limit maximum number of messages to return
     * @return list of recent chat messages
     */
    public List<ChatMessage> getRecentMessages(int limit) {
        return chatMessageRepository.findRecentMessages(limit);
    }

    /**
     * Retrieves chat history for a session within a time range
     * 
     * @param sessionId the session identifier
     * @param startTime start of time range
     * @param endTime end of time range
     * @return list of chat messages within the time range
     */
    public List<ChatMessage> getChatHistoryByTimeRange(String sessionId, LocalDateTime startTime, LocalDateTime endTime) {
        return chatMessageRepository.findBySessionIdAndTimestampBetweenOrderByTimestampAsc(sessionId, startTime, endTime);
    }

    /**
     * Deletes all messages for a specific session
     * 
     * @param sessionId the session identifier
     */
    public void deleteSessionHistory(String sessionId) {
        chatMessageRepository.deleteBySessionId(sessionId);
    }

    /**
     * Gets statistics for a session
     * 
     * @param sessionId the session identifier
     * @return ChatSessionStats containing session statistics
     */
    public ChatSessionStats getSessionStats(String sessionId) {
        long messageCount = chatMessageRepository.countBySessionId(sessionId);
        List<ChatMessage> messages = chatMessageRepository.findBySessionIdOrderByTimestampAsc(sessionId);
        
        if (messages.isEmpty()) {
            return new ChatSessionStats(sessionId, 0, null, null, 0L);
        }
        
        LocalDateTime firstMessage = messages.get(0).getTimestamp();
        LocalDateTime lastMessage = messages.get(messages.size() - 1).getTimestamp();
        long totalResponseTime = messages.stream()
                .mapToLong(msg -> msg.getResponseTimeMs() != null ? msg.getResponseTimeMs() : 0)
                .sum();
        
        return new ChatSessionStats(sessionId, messageCount, firstMessage, lastMessage, totalResponseTime);
    }

    /**
     * Generates a unique session ID
     * 
     * @return a unique session identifier
     */
    private String generateSessionId() {
        return "session_" + UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * Inner class for holding session statistics
     */
    public static class ChatSessionStats {
        private final String sessionId;
        private final long messageCount;
        private final LocalDateTime firstMessage;
        private final LocalDateTime lastMessage;
        private final long totalResponseTime;

        public ChatSessionStats(String sessionId, long messageCount, LocalDateTime firstMessage, 
                              LocalDateTime lastMessage, long totalResponseTime) {
            this.sessionId = sessionId;
            this.messageCount = messageCount;
            this.firstMessage = firstMessage;
            this.lastMessage = lastMessage;
            this.totalResponseTime = totalResponseTime;
        }

        // Getters
        public String getSessionId() { return sessionId; }
        public long getMessageCount() { return messageCount; }
        public LocalDateTime getFirstMessage() { return firstMessage; }
        public LocalDateTime getLastMessage() { return lastMessage; }
        public long getTotalResponseTime() { return totalResponseTime; }
        public double getAverageResponseTime() { 
            return messageCount > 0 ? (double) totalResponseTime / messageCount : 0.0; 
        }
    }
}
