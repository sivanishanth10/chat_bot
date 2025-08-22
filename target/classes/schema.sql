-- Chatbot Database Schema
-- This file contains the SQL schema for the chatbot application
-- Run this script to create the necessary database and tables

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS chatbot_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Use the chatbot database
USE chatbot_db;

-- Create chat_messages table
CREATE TABLE IF NOT EXISTS chat_messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_message TEXT NOT NULL COMMENT 'User input message',
    ai_response TEXT NOT NULL COMMENT 'AI generated response',
    timestamp DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'When the message was created',
    session_id VARCHAR(100) COMMENT 'Unique session identifier for grouping conversations',
    user_ip VARCHAR(45) COMMENT 'User IP address for analytics',
    response_time_ms BIGINT COMMENT 'Response time in milliseconds',
    
    -- Indexes for better performance
    INDEX idx_session_id (session_id),
    INDEX idx_timestamp (timestamp),
    INDEX idx_user_ip (user_ip),
    INDEX idx_session_timestamp (session_id, timestamp)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Stores chat conversations between users and AI';

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_chat_messages_session_id ON chat_messages(session_id);
CREATE INDEX IF NOT EXISTS idx_chat_messages_timestamp ON chat_messages(timestamp);
CREATE INDEX IF NOT EXISTS idx_chat_messages_user_ip ON chat_messages(user_ip);

-- Insert sample data for testing (optional)
INSERT INTO chat_messages (user_message, ai_response, session_id, user_ip, response_time_ms) VALUES
('Hello, how are you?', 'Hello! I am doing well, thank you for asking. I am an AI assistant designed to help you with various tasks and answer your questions. How can I assist you today?', 'sample_session_001', '127.0.0.1', 1250),
('What is artificial intelligence?', 'Artificial Intelligence (AI) is a branch of computer science that aims to create systems capable of performing tasks that typically require human intelligence. These tasks include learning, reasoning, problem-solving, perception, and language understanding. AI can be categorized into narrow AI (designed for specific tasks) and general AI (capable of performing any intellectual task that a human can do).', 'sample_session_001', '127.0.0.1', 2100),
('Tell me a joke', 'Here is a programming joke for you: Why do programmers prefer dark mode? Because light attracts bugs! 😄', 'sample_session_001', '127.0.0.1', 800);

-- Create a view for recent conversations
CREATE OR REPLACE VIEW recent_conversations AS
SELECT 
    session_id,
    COUNT(*) as message_count,
    MIN(timestamp) as first_message,
    MAX(timestamp) as last_message,
    AVG(response_time_ms) as avg_response_time
FROM chat_messages 
GROUP BY session_id 
ORDER BY last_message DESC;

-- Create a view for daily statistics
CREATE OR REPLACE VIEW daily_stats AS
SELECT 
    DATE(timestamp) as date,
    COUNT(*) as total_messages,
    COUNT(DISTINCT session_id) as unique_sessions,
    AVG(response_time_ms) as avg_response_time
FROM chat_messages 
GROUP BY DATE(timestamp) 
ORDER BY date DESC;

-- Grant permissions (adjust as needed for your environment)
-- GRANT ALL PRIVILEGES ON chatbot_db.* TO 'your_username'@'localhost';
-- FLUSH PRIVILEGES;
