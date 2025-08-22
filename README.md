# AI Chatbot with Spring Boot Backend

A production-ready AI chatbot application built with Spring Boot backend, MySQL database, and a modern HTML/CSS/JavaScript frontend. The chatbot integrates with Google's Gemini AI API to provide intelligent responses.

## 🚀 Features

- **Intelligent AI Responses**: Powered by Google's Gemini 2.5 Pro model
- **Real-time Chat Interface**: Modern, responsive chat UI with typing indicators
- **Session Management**: Persistent chat sessions with unique identifiers
- **Chat History**: Store and retrieve conversation history from MySQL database
- **RESTful API**: Clean Spring Boot backend with proper architecture
- **CORS Enabled**: Frontend can communicate with backend seamlessly
- **Production Ready**: Includes error handling, validation, and logging
- **Responsive Design**: Works on desktop, tablet, and mobile devices

## 🏗️ Architecture

### Backend (Spring Boot)
- **Controller Layer**: REST endpoints for chat operations
- **Service Layer**: Business logic and Gemini API integration
- **Repository Layer**: Data access with Spring Data JPA
- **Model Layer**: Entity classes and DTOs
- **Configuration**: CORS, database, and application properties

### Frontend (HTML/CSS/JavaScript)
- **Modern UI**: Clean, responsive design with CSS Grid and Flexbox
- **Interactive Chat**: Real-time message handling and display
- **Local Storage**: Client-side chat history persistence
- **Error Handling**: User-friendly error messages and modals
- **Accessibility**: Keyboard navigation and screen reader support

### Database (MySQL)
- **Chat Messages**: Store user queries and AI responses
- **Session Tracking**: Link conversations to unique sessions
- **Performance**: Optimized indexes and efficient queries
- **Analytics**: Response time tracking and usage statistics

## 📋 Prerequisites

- **Java 17** or higher
- **MySQL 8.0** or higher
- **Maven 3.6** or higher
- **Modern web browser** (Chrome, Firefox, Safari, Edge)

## 🛠️ Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd chat-bot-main
```

### 2. Database Setup
1. **Start MySQL Server**
   ```bash
   # Windows
   net start mysql
   
   # macOS/Linux
   sudo systemctl start mysql
   ```

2. **Create Database and User**
   ```sql
   -- Connect to MySQL as root
   mysql -u root -p
   
   -- Create database
   CREATE DATABASE chatbot_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   
   -- Create user (optional, you can use root)
   CREATE USER 'chatbot_user'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON chatbot_db.* TO 'chatbot_user'@'localhost';
   FLUSH PRIVILEGES;
   
   -- Exit MySQL
   EXIT;
   ```

3. **Run Schema Script** (optional)
   ```bash
   mysql -u root -p chatbot_db < src/main/resources/schema.sql
   ```

### 3. Configure Application
1. **Update Database Configuration**
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

2. **Verify Gemini API Key**
   Ensure your Gemini API key is set in `application.properties`:
   ```properties
   gemini.api.key=your_gemini_api_key
   ```

### 4. Development Setup

#### **Backend (IntelliJ IDEA)**
1. **Open Project in IntelliJ IDEA**
   - Open IntelliJ IDEA
   - Select "Open" or "Import Project"
   - Navigate to your project folder and select it
   - Choose "Import project from external model" → "Maven"
   - Click "Next" and "Finish"

2. **Configure JDK**
   - Go to `File` → `Project Structure`
   - Under `Project Settings` → `Project`:
     - Set `Project SDK` to Java 17
     - Set `Project language level` to 17
   - Click "Apply" and "OK"

3. **Run the Application**
   - Go to `Run` → `Edit Configurations`
   - Click the "+" button and select "Spring Boot"
   - Configure:
     - **Name**: ChatbotApplication
     - **Main class**: `com.chatbot.ChatbotApplication`
     - **Module**: `chatbot-backend`
   - Click "Apply" and "OK"
   - Click the green play button or press `Shift+F10`

#### **Frontend (VS Code)**
1. **Open Project in VS Code**
   ```bash
   # Open workspace file
   code chatbot.code-workspace
   
   # Or open frontend folder directly
   code frontend/
   ```

2. **Install Recommended Extensions**
   VS Code will prompt you to install recommended extensions:
   - **Live Server** - For serving the frontend
   - **Prettier** - Code formatting
   - **Auto Rename Tag** - HTML tag renaming
   - **Path Intellisense** - File path autocomplete

3. **Start Frontend Development Server**
   - Right-click on `frontend/index.html`
   - Select "Open with Live Server"
   - Frontend will open at `http://localhost:3000`

### 5. Test the Application
1. **Start Backend** (IntelliJ IDEA): Run `ChatbotApplication` configuration
2. **Start Frontend** (VS Code): Use Live Server extension
3. **Send a Test Message**: Type "Hello, how are you?" and press Enter
4. **Verify Response**: You should get an AI response from Gemini

## 🔧 Configuration

### Environment Variables
You can override configuration using environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/chatbot_db
export SPRING_DATASOURCE_USERNAME=your_username
export SPRING_DATASOURCE_PASSWORD=your_password
export GEMINI_API_KEY=your_api_key
```

### Application Properties
Key configuration options in `application.properties`:

```properties
# Server
server.port=8080
server.servlet.context-path=/api

# Database
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Gemini API
gemini.api.url=https://generativelanguage.googleapis.com/v1/models/gemini-2.5-pro:generateContent

# CORS
spring.web.cors.allowed-origins=http://localhost:3000,http://127.0.0.1:3000
```

## 📚 API Endpoints

### Chat Operations
- `POST /api/chat/send` - Send a message and get AI response
- `GET /api/chat/history/{sessionId}` - Get chat history for a session
- `GET /api/chat/recent` - Get recent messages across all sessions
- `GET /api/chat/stats/{sessionId}` - Get session statistics
- `DELETE /api/chat/history/{sessionId}` - Delete session history

### Health & Status
- `GET /api/chat/health` - Health check endpoint

### Request/Response Format

**Send Message Request:**
```json
{
  "userMessage": "Hello, how are you?",
  "sessionId": "session_123456789"
}
```

**Chat Response:**
```json
{
  "aiResponse": "Hello! I'm doing well, thank you for asking...",
  "sessionId": "session_123456789",
  "timestamp": "2024-01-15T10:30:00",
  "responseTimeMs": 1250,
  "status": "SUCCESS"
}
```

## 🎨 Frontend Customization

### Styling
- **CSS Variables**: Easy color and spacing customization in `styles.css`
- **Responsive Design**: Mobile-first approach with breakpoints
- **Dark Mode**: Automatic dark mode support based on system preferences
- **Accessibility**: High contrast mode and reduced motion support

### JavaScript
- **Modular Architecture**: Clean class-based structure
- **Event Handling**: Comprehensive event management
- **Local Storage**: Persistent chat history
- **Error Handling**: User-friendly error messages

## 🚀 Deployment

### Production Considerations
1. **Environment Variables**: Use environment variables for sensitive data
2. **Database**: Use production-grade MySQL with proper backups
3. **Security**: Enable HTTPS, implement rate limiting
4. **Monitoring**: Add application monitoring and logging
5. **Scaling**: Consider containerization with Docker

### Docker Deployment
```dockerfile
FROM openjdk:17-jre-slim
COPY target/chatbot-backend-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🧪 Testing

### Backend Testing
```bash
# Run unit tests
./mvnw test

# Run integration tests
./mvnw verify

# Generate test coverage report
./mvnw jacoco:report
```

### Frontend Testing
- Open browser developer tools
- Check console for any JavaScript errors
- Test responsive design on different screen sizes
- Verify accessibility features

## 📊 Monitoring & Logging

### Application Logs
- **Log Level**: Configurable via `application.properties`
- **Structured Logging**: JSON format for production environments
- **Performance Metrics**: Response time tracking and database query logging

### Health Checks
- **Database Connectivity**: Automatic health checks
- **API Status**: Health endpoint for monitoring
- **Gemini API**: Integration status monitoring

## 🔒 Security Features

- **Input Validation**: Request validation with Bean Validation
- **SQL Injection Protection**: JPA/Hibernate parameterized queries
- **CORS Configuration**: Controlled cross-origin access
- **Error Handling**: Secure error messages without information leakage

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Google Gemini AI**: For providing the AI capabilities
- **Spring Boot Team**: For the excellent framework
- **MySQL Community**: For the reliable database
- **Open Source Community**: For various libraries and tools

## 📞 Support

If you encounter any issues or have questions:

1. **Check the logs** for error messages
2. **Verify configuration** in `application.properties`
3. **Test database connectivity** manually
4. **Check Gemini API key** validity
5. **Open an issue** on the repository

## 🔄 Updates & Maintenance

### Regular Maintenance
- **Dependencies**: Update Spring Boot and other dependencies regularly
- **Security**: Monitor for security vulnerabilities
- **Performance**: Monitor response times and database performance
- **Backups**: Regular database backups

### Version History
- **v1.0.0**: Initial release with core functionality
- **Future**: Enhanced features, performance improvements, and security updates

---

**Happy Chatting! 🤖✨**
