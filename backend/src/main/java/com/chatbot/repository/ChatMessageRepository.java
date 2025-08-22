package com.chatbot.repository;

import com.chatbot.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for ChatMessage entity
 * Provides methods for database operations on chat messages
 * Extends JpaRepository for basic CRUD operations
 * 
 * @author Chatbot Team
 * @version 1.0.0
 */
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * Find all chat messages by session ID
     * 
     * @param sessionId the session identifier
     * @return list of chat messages for the session
     */
    List<ChatMessage> findBySessionIdOrderByTimestampAsc(String sessionId);

    /**
     * Find chat messages by session ID within a time range
     * 
     * @param sessionId the session identifier
     * @param startTime start of time range
     * @param endTime end of time range
     * @return list of chat messages within the time range
     */
    List<ChatMessage> findBySessionIdAndTimestampBetweenOrderByTimestampAsc(
            String sessionId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Count total messages for a session
     * 
     * @param sessionId the session identifier
     * @return count of messages
     */
    long countBySessionId(String sessionId);

    /**
     * Find recent chat messages across all sessions
     * 
     * @param limit maximum number of messages to return
     * @return list of recent chat messages
     */
    @Query("SELECT cm FROM ChatMessage cm ORDER BY cm.timestamp DESC")
    List<ChatMessage> findRecentMessages(@Param("limit") int limit);

    /**
     * Find messages by user IP address
     * 
     * @param userIp the user's IP address
     * @return list of chat messages from the IP
     */
    List<ChatMessage> findByUserIpOrderByTimestampDesc(String userIp);

    /**
     * Delete all messages for a specific session
     * 
     * @param sessionId the session identifier
     */
    void deleteBySessionId(String sessionId);
}
