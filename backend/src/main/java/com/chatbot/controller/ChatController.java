package com.chatbot.controller;

import com.chatbot.dto.ChatRequest;
import com.chatbot.dto.ChatResponse;
import com.chatbot.model.ChatMessage;
import com.chatbot.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for handling chat-related HTTP requests
 * Provides endpoints for sending messages, retrieving chat history, and managing sessions
 * 
 * @author Chatbot Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000", "http://localhost:5500", "http://127.0.0.1:5500"})
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * Endpoint to send a chat message and receive AI response
     * 
     * @param chatRequest the chat request containing user message
     * @param request HTTP request object to extract client IP
     * @return ResponseEntity containing the chat response
     */
    @PostMapping("/send")
    public ResponseEntity<ChatResponse> sendMessage(@Valid @RequestBody ChatRequest chatRequest, 
                                                   HttpServletRequest request) {
        try {
            // Extract client IP address
            String clientIp = getClientIpAddress(request);
            
            // Process the chat request
            ChatResponse response = chatService.processChatRequest(chatRequest, clientIp);
            
            if ("ERROR".equals(response.getStatus())) {
                return ResponseEntity.badRequest().body(response);
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            ChatResponse errorResponse = new ChatResponse("ERROR", "Internal server error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * Endpoint to retrieve chat history for a specific session
     * 
     * @param sessionId the session identifier
     * @return ResponseEntity containing the chat history
     */
    @GetMapping("/history/{sessionId}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable String sessionId) {
        try {
            List<ChatMessage> history = chatService.getChatHistory(sessionId);
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Endpoint to retrieve recent chat messages across all sessions
     * 
     * @param limit maximum number of messages to return (default: 50)
     * @return ResponseEntity containing recent messages
     */
    @GetMapping("/recent")
    public ResponseEntity<List<ChatMessage>> getRecentMessages(@RequestParam(defaultValue = "50") int limit) {
        try {
            List<ChatMessage> recentMessages = chatService.getRecentMessages(limit);
            return ResponseEntity.ok(recentMessages);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Endpoint to get session statistics
     * 
     * @param sessionId the session identifier
     * @return ResponseEntity containing session statistics
     */
    @GetMapping("/stats/{sessionId}")
    public ResponseEntity<ChatService.ChatSessionStats> getSessionStats(@PathVariable String sessionId) {
        try {
            ChatService.ChatSessionStats stats = chatService.getSessionStats(sessionId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Endpoint to delete chat history for a specific session
     * 
     * @param sessionId the session identifier
     * @return ResponseEntity indicating success or failure
     */
    @DeleteMapping("/history/{sessionId}")
    public ResponseEntity<String> deleteSessionHistory(@PathVariable String sessionId) {
        try {
            chatService.deleteSessionHistory(sessionId);
            return ResponseEntity.ok("Session history deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to delete session history: " + e.getMessage());
        }
    }

    /**
     * Health check endpoint
     * 
     * @return ResponseEntity indicating the service is running
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Chatbot service is running!");
    }

    /**
     * Extracts the client IP address from the HTTP request
     * Handles various proxy headers that might be present
     * 
     * @param request the HTTP request
     * @return the client IP address
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
}
