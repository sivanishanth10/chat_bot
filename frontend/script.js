/**
 * AI Chatbot Frontend JavaScript
 * Handles chat functionality, API communication, and UI interactions
 * 
 * @author Chatbot Team
 * @version 1.0.0
 */

class ChatbotApp {
    constructor() {
        // Configuration
        this.apiBaseUrl = 'http://localhost:8080/api';
        this.sessionId = this.generateSessionId();
        this.isTyping = false;
        
        // DOM Elements
        this.elements = this.initializeElements();
        
        // Event Listeners
        this.attachEventListeners();
        
        // Initialize the app
        this.initialize();
    }

    /**
     * Initialize DOM element references
     */
    initializeElements() {
        return {
            chatForm: document.getElementById('chatForm'),
            messageInput: document.getElementById('messageInput'),
            sendBtn: document.getElementById('sendBtn'),
            chatMessages: document.getElementById('chatMessages'),
            welcomeMessage: document.getElementById('welcomeMessage'),
            typingIndicator: document.getElementById('typingIndicator'),
            clearChatBtn: document.getElementById('clearChatBtn'),
            exportChatBtn: document.getElementById('exportChatBtn'),
            charCount: document.getElementById('charCount'),
            loadingOverlay: document.getElementById('loadingOverlay'),
            errorModal: document.getElementById('errorModal'),
            successToast: document.getElementById('successToast')
        };
    }

    /**
     * Attach event listeners to DOM elements
     */
    attachEventListeners() {
        // Chat form submission
        this.elements.chatForm.addEventListener('submit', (e) => this.handleFormSubmit(e));
        
        // Message input events
        this.elements.messageInput.addEventListener('input', (e) => this.handleInputChange(e));
        this.elements.messageInput.addEventListener('keydown', (e) => this.handleKeyDown(e));
        
        // Button events
        this.elements.clearChatBtn.addEventListener('click', () => this.clearChat());
        this.elements.exportChatBtn.addEventListener('click', () => this.exportChat());
        
        // Modal events
        document.getElementById('closeErrorModal').addEventListener('click', () => this.hideErrorModal());
        document.getElementById('closeModalBtn').addEventListener('click', () => this.hideErrorModal());
        document.getElementById('retryBtn').addEventListener('click', () => this.retryLastRequest());
        
        // Auto-resize textarea
        this.elements.messageInput.addEventListener('input', () => this.autoResizeTextarea());
    }

    /**
     * Initialize the application
     */
    initialize() {
        // Load chat history if available
        this.loadChatHistory();
        
        // Focus on input
        this.elements.messageInput.focus();
        
        console.log('🤖 AI Chatbot initialized successfully!');
        console.log(`📱 Session ID: ${this.sessionId}`);
    }

    /**
     * Handle form submission
     */
    async handleFormSubmit(e) {
        e.preventDefault();
        
        const message = this.elements.messageInput.value.trim();
        if (!message) return;
        
        try {
            // Disable form during submission
            this.setFormState(false);
            
            // Add user message to chat
            this.addMessage(message, 'user');
            
            // Clear input
            this.elements.messageInput.value = '';
            this.autoResizeTextarea();
            
            // Show typing indicator
            this.showTypingIndicator();
            
            // Send message to API
            const response = await this.sendMessageToAPI(message);
            
            // Hide typing indicator
            this.hideTypingIndicator();
            
            // Add AI response to chat
            if (response.status === 'SUCCESS') {
                this.addMessage(response.aiResponse, 'ai');
                this.hideWelcomeMessage();
            } else {
                throw new Error(response.message || 'Failed to get AI response');
            }
            
        } catch (error) {
            console.error('Error sending message:', error);
            this.hideTypingIndicator();
            this.showError('Failed to send message. Please try again.');
        } finally {
            // Re-enable form
            this.setFormState(true);
        }
    }

    /**
     * Handle input change events
     */
    handleInputChange(e) {
        const message = e.target.value;
        const charCount = message.length;
        
        // Update character count
        this.elements.charCount.textContent = `${charCount}/1000`;
        
        // Enable/disable send button
        this.elements.sendBtn.disabled = charCount === 0 || charCount > 1000;
        
        // Update character count color
        if (charCount > 900) {
            this.elements.charCount.style.color = '#ef4444';
        } else if (charCount > 800) {
            this.elements.charCount.style.color = '#f59e0b';
        } else {
            this.elements.charCount.style.color = '';
        }
    }

    /**
     * Handle keydown events in textarea
     */
    handleKeyDown(e) {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            if (!this.elements.sendBtn.disabled) {
                this.elements.chatForm.dispatchEvent(new Event('submit'));
            }
        }
    }

    /**
     * Auto-resize textarea based on content
     */
    autoResizeTextarea() {
        const textarea = this.elements.messageInput;
        textarea.style.height = 'auto';
        textarea.style.height = Math.min(textarea.scrollHeight, 200) + 'px';
    }

    /**
     * Send message to the backend API
     */
    async sendMessageToAPI(message) {
        const url = `${this.apiBaseUrl}/chat/send`;
        const payload = {
            userMessage: message,
            sessionId: this.sessionId
        };

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    }

    /**
     * Add a message to the chat
     */
    addMessage(text, sender) {
        const messageDiv = document.createElement('div');
        messageDiv.className = `message ${sender}`;
        
        const messageContent = document.createElement('div');
        messageContent.className = 'message-content';
        
        const messageText = document.createElement('div');
        messageText.className = 'message-text';
        messageText.textContent = text;
        
        const messageTime = document.createElement('span');
        messageTime.className = 'message-time';
        messageTime.textContent = this.formatTime(new Date());
        
        messageContent.appendChild(messageText);
        messageContent.appendChild(messageTime);
        messageDiv.appendChild(messageContent);
        
        this.elements.chatMessages.appendChild(messageDiv);
        
        // Scroll to bottom
        this.scrollToBottom();
        
        // Save to local storage
        this.saveMessageToHistory(text, sender);
    }

    /**
     * Show typing indicator
     */
    showTypingIndicator() {
        this.isTyping = true;
        this.elements.typingIndicator.style.display = 'flex';
        this.scrollToBottom();
    }

    /**
     * Hide typing indicator
     */
    hideTypingIndicator() {
        this.isTyping = false;
        this.elements.typingIndicator.style.display = 'none';
    }

    /**
     * Hide welcome message
     */
    hideWelcomeMessage() {
        if (this.elements.welcomeMessage) {
            this.elements.welcomeMessage.style.display = 'none';
        }
    }

    /**
     * Scroll chat to bottom
     */
    scrollToBottom() {
        setTimeout(() => {
            this.elements.chatMessages.scrollTop = this.elements.chatMessages.scrollHeight;
        }, 100);
    }

    /**
     * Set form state (enabled/disabled)
     */
    setFormState(enabled) {
        this.elements.messageInput.disabled = !enabled;
        this.elements.sendBtn.disabled = !enabled;
    }

    /**
     * Clear chat history
     */
    clearChat() {
        if (confirm('Are you sure you want to clear the chat history? This action cannot be undone.')) {
            // Clear DOM
            this.elements.chatMessages.innerHTML = '';
            
            // Show welcome message
            if (this.elements.welcomeMessage) {
                this.elements.welcomeMessage.style.display = 'block';
            }
            
            // Clear local storage
            localStorage.removeItem(`chat_history_${this.sessionId}`);
            
            // Generate new session ID
            this.sessionId = this.generateSessionId();
            
            this.showSuccess('Chat history cleared successfully!');
        }
    }

    /**
     * Export chat history
     */
    exportChat() {
        const messages = this.getChatHistory();
        if (messages.length === 0) {
            this.showError('No chat history to export.');
            return;
        }

        const exportData = {
            sessionId: this.sessionId,
            exportDate: new Date().toISOString(),
            messages: messages
        };

        const blob = new Blob([JSON.stringify(exportData, null, 2)], { type: 'application/json' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `chatbot-export-${this.sessionId}-${new Date().toISOString().split('T')[0]}.json`;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);

        this.showSuccess('Chat history exported successfully!');
    }

    /**
     * Load chat history from local storage
     */
    loadChatHistory() {
        const history = this.getChatHistory();
        if (history.length > 0) {
            history.forEach(msg => {
                this.addMessage(msg.text, msg.sender);
            });
            this.hideWelcomeMessage();
        }
    }

    /**
     * Save message to local storage
     */
    saveMessageToHistory(text, sender) {
        const history = this.getChatHistory();
        history.push({
            text: text,
            sender: sender,
            timestamp: new Date().toISOString()
        });
        
        // Keep only last 100 messages
        if (history.length > 100) {
            history.splice(0, history.length - 100);
        }
        
        localStorage.setItem(`chat_history_${this.sessionId}`, JSON.stringify(history));
    }

    /**
     * Get chat history from local storage
     */
    getChatHistory() {
        try {
            const history = localStorage.getItem(`chat_history_${this.sessionId}`);
            return history ? JSON.parse(history) : [];
        } catch (error) {
            console.error('Error loading chat history:', error);
            return [];
        }
    }

    /**
     * Show error modal
     */
    showError(message) {
        document.getElementById('errorMessage').textContent = message;
        this.elements.errorModal.style.display = 'flex';
    }

    /**
     * Hide error modal
     */
    hideErrorModal() {
        this.elements.errorModal.style.display = 'none';
    }

    /**
     * Show success toast
     */
    showSuccess(message) {
        const toast = this.elements.successToast;
        toast.querySelector('.toast-message').textContent = message;
        toast.style.display = 'flex';
        
        setTimeout(() => {
            toast.style.display = 'none';
        }, 3000);
    }

    /**
     * Retry last request
     */
    retryLastRequest() {
        this.hideErrorModal();
        // This could be enhanced to actually retry the last failed request
        this.showSuccess('Retry functionality coming soon!');
    }

    /**
     * Generate a unique session ID
     */
    generateSessionId() {
        return 'session_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9);
    }

    /**
     * Format time for display
     */
    formatTime(date) {
        return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    }

    /**
     * Show loading overlay
     */
    showLoading() {
        this.elements.loadingOverlay.style.display = 'flex';
    }

    /**
     * Hide loading overlay
     */
    hideLoading() {
        this.elements.loadingOverlay.style.display = 'none';
    }
}

// Utility functions
const utils = {
    /**
     * Debounce function to limit API calls
     */
    debounce: function(func, wait) {
        let timeout;
        return function executedFunction(...args) {
            const later = () => {
                clearTimeout(timeout);
                func(...args);
            };
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
        };
    },

    /**
     * Format bytes to human readable format
     */
    formatBytes: function(bytes, decimals = 2) {
        if (bytes === 0) return '0 Bytes';
        const k = 1024;
        const dm = decimals < 0 ? 0 : decimals;
        const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
        const i = Math.floor(Math.log(bytes) / Math.log(k));
        return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
    },

    /**
     * Validate email format
     */
    isValidEmail: function(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }
};

// Initialize the app when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    try {
        window.chatbotApp = new ChatbotApp();
    } catch (error) {
        console.error('Failed to initialize chatbot app:', error);
        alert('Failed to initialize the chatbot. Please refresh the page.');
    }
});

// Handle page visibility changes
document.addEventListener('visibilitychange', () => {
    if (document.visibilityState === 'visible' && window.chatbotApp) {
        // Refresh chat when page becomes visible
        window.chatbotApp.scrollToBottom();
    }
});

// Handle beforeunload to save state
window.addEventListener('beforeunload', () => {
    if (window.chatbotApp) {
        // Save any pending state
        localStorage.setItem('chatbot_last_session', window.chatbotApp.sessionId);
    }
});

// Export for debugging
if (typeof module !== 'undefined' && module.exports) {
    module.exports = { ChatbotApp, utils };
}
