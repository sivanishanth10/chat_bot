# IntelliJ IDEA Setup Guide

## How to Run Backend in IntelliJ IDEA

### Prerequisites
- IntelliJ IDEA (Community or Ultimate Edition)
- Java 24 (already configured)
- MySQL running on localhost:3306

### Step-by-Step Instructions

1. **Open the Backend Project**
   - Launch IntelliJ IDEA
   - Go to `File` → `Open`
   - Navigate to: `C:\Users\sanja\Downloads\chat-bot-main\chat_bot_main\backend`
   - Select the `backend` folder and click `OK`

2. **Wait for Maven Sync**
   - IntelliJ will automatically detect it's a Maven project
   - Wait for the Maven sync to complete (progress bar at bottom)
   - All dependencies will be downloaded automatically

3. **Verify Project Structure**
   - Ensure you see the Maven project structure in the Project Explorer
   - Main class: `src/main/java/com/chatbot/ChatbotApplication.java`
   - Configuration: `src/main/resources/application.properties`

4. **Run the Application**
   - **Method 1**: Right-click on `ChatbotApplication.java` → `Run 'ChatbotApplication'`
   - **Method 2**: Click the green play button next to the `main` method
   - **Method 3**: Use the Run Configuration (if already set up)

5. **Verify Backend is Running**
   - Look for these messages in the console:
   ```
   🚀 Chatbot Backend started successfully!
   📱 Frontend can be accessed at: http://localhost:3000
   🔌 Backend API available at: http://localhost:8080/api
   ☕ Running on Java 24
   ```

### Troubleshooting

#### Maven Issues
- If Maven sync fails, try: `File` → `Reload All Maven Projects`
- Or run: `./mvnw clean install` in the terminal

#### Database Connection Issues
- Ensure MySQL is running on port 3306
- Check database credentials in `application.properties`
- Verify database `chatbot_db` exists

#### Port Already in Use
- If port 8080 is busy, change it in `application.properties`:
  ```properties
  server.port=8081
  ```

#### CORS Issues
- The CORS configuration is already set up to allow:
  - `http://localhost:*`
  - `http://127.0.0.1:*`
  - `file://*` (for local HTML files)

### Development Tips

1. **Hot Reload**: The application includes Spring Boot DevTools for automatic restart on code changes

2. **Debug Mode**: Set breakpoints in your Java code and run in debug mode

3. **Database Viewing**: Use IntelliJ's Database tool to view MySQL data

4. **API Testing**: Use the built-in HTTP client or Postman to test endpoints

### Available Endpoints
- `POST /api/chat/send` - Send chat message
- `GET /api/chat/history/{sessionId}` - Get chat history
- `GET /api/chat/recent` - Get recent messages
- `GET /api/chat/stats/{sessionId}` - Get session statistics
- `DELETE /api/chat/history/{sessionId}` - Clear chat history
- `GET /api/chat/health` - Health check

### Next Steps
1. Start the backend (this guide)
2. Open the frontend in VS Code or browser
3. Test the chatbot functionality
