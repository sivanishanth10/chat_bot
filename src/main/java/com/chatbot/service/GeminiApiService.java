package com.chatbot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Service class for integrating with Google's Gemini AI API
 * Handles HTTP requests to the Gemini API and processes responses
 * 
 * @author Chatbot Team
 * @version 1.0.0
 */
@Service
public class GeminiApiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    /**
     * Constructor initializes WebClient and ObjectMapper
     */
    public GeminiApiService() {
        this.webClient = WebClient.builder().build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Sends a user message to Gemini API and returns the AI response
     * 
     * @param userMessage the user's input message
     * @return the AI-generated response
     * @throws RuntimeException if API call fails
     */
    public String getAiResponse(String userMessage) {
        try {
            // Create the request payload for Gemini API
            ObjectNode requestBody = createGeminiRequest(userMessage);
            
            // Make the API call
            String response = webClient.post()
                    .uri(apiUrl + "?key=" + apiKey)
                    .bodyValue(requestBody.toString())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Parse and extract the response text
            return parseGeminiResponse(response);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to get AI response from Gemini API: " + e.getMessage(), e);
        }
    }

    /**
     * Creates the request payload for Gemini API
     * 
     * @param userMessage the user's message
     * @return ObjectNode containing the request structure
     */
    private ObjectNode createGeminiRequest(String userMessage) {
        ObjectNode requestBody = objectMapper.createObjectNode();
        ArrayNode contents = objectMapper.createArrayNode();
        ObjectNode content = objectMapper.createObjectNode();
        ArrayNode parts = objectMapper.createArrayNode();
        ObjectNode part = objectMapper.createObjectNode();
        
        part.put("text", userMessage);
        parts.add(part);
        content.set("parts", parts);
        contents.add(content);
        requestBody.set("contents", contents);
        
        // Add generation config for better responses
        ObjectNode generationConfig = objectMapper.createObjectNode();
        generationConfig.put("temperature", 0.7);
        generationConfig.put("topK", 40);
        generationConfig.put("topP", 0.95);
        generationConfig.put("maxOutputTokens", 1024);
        requestBody.set("generationConfig", generationConfig);
        
        return requestBody;
    }

    /**
     * Parses the Gemini API response to extract the AI-generated text
     * 
     * @param response the raw API response
     * @return the extracted AI response text
     * @throws RuntimeException if response parsing fails
     */
    private String parseGeminiResponse(String response) {
        try {
            JsonNode responseNode = objectMapper.readTree(response);
            
            // Navigate through the response structure
            JsonNode candidates = responseNode.get("candidates");
            if (candidates != null && candidates.isArray() && candidates.size() > 0) {
                JsonNode firstCandidate = candidates.get(0);
                JsonNode content = firstCandidate.get("content");
                if (content != null) {
                    JsonNode parts = content.get("parts");
                    if (parts != null && parts.isArray() && parts.size() > 0) {
                        JsonNode firstPart = parts.get(0);
                        String text = firstPart.get("text").asText();
                        return text != null ? text.trim() : "Sorry, I couldn't generate a response.";
                    }
                }
            }
            
            // If we can't parse the expected structure, return a fallback
            return "Sorry, I couldn't generate a response at this time.";
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Gemini API response: " + e.getMessage(), e);
        }
    }

    /**
     * Validates if the API key is configured
     * 
     * @return true if API key is set, false otherwise
     */
    public boolean isApiKeyConfigured() {
        return apiKey != null && !apiKey.trim().isEmpty();
    }

    /**
     * Gets the configured API URL
     * 
     * @return the Gemini API URL
     */
    public String getApiUrl() {
        return apiUrl;
    }
}
