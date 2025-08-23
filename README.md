# AI Chatbot - Powered by Google Gemini

A modern, intelligent chatbot application built with Spring Boot backend and vanilla JavaScript frontend, powered by Google's Gemini AI technology.

## 🚀 Features

- **Intelligent AI Responses**: Powered by Google's Gemini AI
- **Real-time Chat**: Instant responses with typing indicators
- **Chat History**: Persistent chat sessions with local storage
- **Export Functionality**: Export chat history as JSON
- **Modern UI**: Beautiful, responsive design
- **Session Management**: Unique session tracking for analytics
- **Error Handling**: Comprehensive error handling and user feedback

## 🛠️ Technology Stack

### Backend
- **Spring Boot 3.2.0**: Java-based web framework
- **Spring Data JPA**: Database persistence
- **MySQL**: Database
- **Google Gemini API**: AI integration
- **Maven**: Build tool

### Frontend
- **Vanilla JavaScript**: No framework dependencies
- **HTML5 & CSS3**: Modern web standards
- **Font Awesome**: Icons
- **Google Fonts**: Typography

## 📋 Prerequisites

- **Java 17+** (JDK 24.0.1 recommended)
- **Node.js 14+** (for frontend server)
- **MySQL 8.0+** (or compatible database)
- **Google Gemini API Key** (get from [Google AI Studio](https://makersuite.google.com/app/apikey))

## 🔧 Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd chat-bot-main
```

### 2. Backend Setup

#### Configure Database
1. Create a MySQL database:
```sql
CREATE DATABASE chatbot_db;
```

2. Update database configuration in `backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/chatbot_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

#### Configure Gemini API
1. Get your API key from [Google AI Studio](https://makersuite.google.com/app/apikey)
2. Add it to `backend/src/main/resources/application.properties`:
```properties
gemini.api.key=your_gemini_api_key_here
```

#### Run Backend
```bash
cd backend
./mvnw spring-boot:run
```

The backend will start on `http://localhost:8080/api`

### 3. Frontend Setup

#### Option 1: Using Node.js Server (Recommended)
```bash
cd frontend
npm start
```

The frontend will be available at `http://localhost:3000`

#### Option 2: Direct File Access
Simply open `frontend/index.html` in your browser. The CORS configuration has been updated to handle this scenario.

## 🚀 Running the Application

1. **Start the Backend**:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

2. **Start the Frontend** (if using Node.js server):
   ```bash
   cd frontend
   npm start
   ```

3. **Access the Application**:
   - Frontend: `http://localhost:3000` (if using Node.js server)
   - Frontend: Open `frontend/index.html` directly in browser
   - Backend API: `http://localhost:8080/api`

## 🔍 API Endpoints

- `POST /api/chat/send` - Send a message to the AI
- `GET /api/chat/history` - Get chat history (if implemented)
- `GET /api/health` - Health check endpoint

## 🐛 Troubleshooting

### CORS Issues
If you encounter CORS errors:

1. **For file:// URLs**: The CORS configuration has been updated to allow `null` origin
2. **For localhost**: Use the Node.js server (`npm start`) instead of opening files directly
3. **Check browser console**: Look for CORS-related error messages

### Database Connection Issues
1. Ensure MySQL is running
2. Verify database credentials in `application.properties`
3. Check if the database exists

### Gemini API Issues
1. Verify your API key is correct
2. Check API quota and limits
3. Ensure the API key has proper permissions

### Port Conflicts
- Backend: Change port in `application.properties` if 8080 is in use
- Frontend: Change `PORT` variable in `server.js` if 3000 is in use

## 📁 Project Structure

```
chat-bot-main/
├── backend/
│   ├── src/main/java/com/chatbot/
│   │   ├── ChatbotApplication.java
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── model/
│   │   ├── repository/
│   │   └── service/
│   ├── src/main/resources/
│   └── pom.xml
├── frontend/
│   ├── index.html
│   ├── script.js
│   ├── styles.css
│   ├── server.js
│   └── package.json
└── README.md
```

## 🔒 Security Considerations

- API keys should be stored in environment variables in production
- Implement proper authentication for production use
- Consider rate limiting for API endpoints
- Validate and sanitize user inputs

## 📝 Development

### Adding New Features
1. Backend: Add new endpoints in `ChatController`
2. Frontend: Extend the `ChatbotApp` class in `script.js`
3. Database: Add new entities in the `model` package

### Testing
- Backend: Use Spring Boot Test framework
- Frontend: Test manually or add unit tests
- API: Use tools like Postman or curl

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- Google Gemini AI for providing the AI capabilities
- Spring Boot team for the excellent framework
- Font Awesome for the beautiful icons
- The open-source community for inspiration and tools

---

**Happy Chatting! 🤖💬**
