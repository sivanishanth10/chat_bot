# 🔧 Chatbot Troubleshooting Guide

## Problem: "Failed to send message. Please try again."

### Step 1: Check Backend Status

1. **Verify Backend is Running**
   - Open IntelliJ IDEA
   - Check if `ChatbotApplication` is running
   - Look for these messages in the console:
   ```
   🚀 Chatbot Backend started successfully!
   🔌 Backend API available at: http://localhost:8080/api
   ```

2. **Test Backend Health**
   - Open: `http://localhost:8080/api/chat/health` in your browser
   - Should return: `{"status":"UP","message":"Chatbot backend is running"}`

### Step 2: Use the Test Page

1. **Open the Test Page**
   - Open `frontend/test-api.html` in your browser
   - This will automatically test the backend connection

2. **Run All Tests**
   - Click "Test Health Endpoint"
   - Click "Test CORS Configuration" 
   - Click "Send Test Message"

### Step 3: Check Common Issues

#### Issue 1: Backend Not Running
**Symptoms**: Health check fails, "Connection refused" errors
**Solution**: 
- Restart the backend in IntelliJ
- Right-click `ChatbotApplication.java` → `Run 'ChatbotApplication'`

#### Issue 2: Port Already in Use
**Symptoms**: Backend won't start, port 8080 busy
**Solution**:
- Change port in `application.properties`:
  ```properties
  server.port=8081
  ```
- Update frontend URL to match

#### Issue 3: Database Connection
**Symptoms**: Backend starts but API calls fail
**Solution**:
- Ensure MySQL is running on port 3306
- Check database credentials in `application.properties`
- Verify `chatbot_db` database exists

#### Issue 4: CORS Issues
**Symptoms**: "Failed to fetch" errors in browser console
**Solution**:
- The CORS configuration has been updated to allow all origins
- Restart the backend after CORS changes

### Step 4: Browser Debugging

1. **Open Developer Tools**
   - Press `F12` or right-click → `Inspect`
   - Go to `Console` tab

2. **Check for Errors**
   - Look for red error messages
   - Common errors:
     - `CORS policy: No 'Access-Control-Allow-Origin'`
     - `Failed to fetch`
     - `Connection refused`

3. **Check Network Tab**
   - Go to `Network` tab
   - Try sending a message
   - Look for failed requests to `/api/chat/send`

### Step 5: Quick Fixes

#### Fix 1: Restart Everything
```bash
# 1. Stop backend in IntelliJ
# 2. Restart backend
# 3. Clear browser cache (Ctrl+Shift+R)
# 4. Try again
```

#### Fix 2: Use Different Port
If port 8080 is busy:
1. Edit `backend/src/main/resources/application.properties`
2. Change: `server.port=8081`
3. Edit `frontend/script.js`
4. Change: `this.apiBaseUrl = 'http://localhost:8081/api';`

#### Fix 3: Disable CORS (Development Only)
The CORS configuration has been temporarily relaxed for testing.

### Step 6: Test the Fix

1. **Open the main chatbot**: `frontend/index.html`
2. **Try sending a message**
3. **Check if it works**

### Still Having Issues?

1. **Check the test page results** from `test-api.html`
2. **Share the error messages** from browser console
3. **Verify backend logs** in IntelliJ console

### Common Error Messages

| Error | Cause | Solution |
|-------|-------|----------|
| `Failed to fetch` | Backend not running | Start backend in IntelliJ |
| `CORS policy` | CORS configuration | Restart backend |
| `Connection refused` | Port busy | Change port or kill process |
| `Database connection` | MySQL not running | Start MySQL service |

### Emergency Fix

If nothing works, try this minimal setup:

1. **Backend**: Just run `ChatbotApplication` in IntelliJ
2. **Frontend**: Open `test-api.html` first to verify connection
3. **Database**: Ensure MySQL is running with `chatbot_db` database

The test page will help identify exactly what's wrong!
