import ChatbotIcon from "./components/ChatbotIcon";
import ChatForm from "./components/ChatForm";
import { useState } from "react";
import ChatMessage from "./components/ChatMessage";
import { companyinf } from "./companyinf";

const App = () => {
  const [chatHistory, setChatHistory] = useState([
    {
      hideInChat: true,
      role: "model",
      text: companyinf,
    },
  ]);
  const [isOpen, setIsOpen] = useState(false);

  const generateBotResponse = async (history) => {
    const updateHistory = (text) => {
      setChatHistory((prev) => [
        ...prev.filter((msg) => msg.text !== "thinking..."),
        { role: "model", text },
      ]);
    };

    //format chat history for api request
    history = history.map(({ role, text }) => ({ role, parts: [{ text }] }));
    const requestOptions = {
      method: "post",
      headers: { "content-type": "application/json" },
      body: JSON.stringify({ contents: history }),
    };
    try {
      const response = await fetch(
        import.meta.env.VITE_API_URL,
        requestOptions
      );
      const data = await response.json();
      if (!response.ok)
        throw new Error(data.error.message || "something went wrong");

      // Safely extract the bot's reply using optional chaining
      const apiResponseText =
        data.candidates?.[0]?.content?.parts?.[0]?.text
          ?.replace(/\*\*(.*?)\*\*/g, "$1")
          .trim() || "Sorry, I didn't get a response from the bot.";
      updateHistory(apiResponseText);

      console.log(data);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className="container" style={{ fontFamily: "Inter, sans-serif" }}>
      <button
        id="chatbot-toggle"
        style={{
          position: "fixed",
          bottom: "32px",
          right: "32px",
          zIndex: 1000,
          background: "#7c4dff",
          color: "#fff",
          border: "none",
          borderRadius: "50%",
          width: "56px",
          height: "56px",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          boxShadow: "0 4px 16px rgba(80,0,120,0.10)",
          cursor: "pointer",
          fontSize: "28px",
        }}
        aria-label={isOpen ? "Close chatbot" : "Open chatbot"}
        onClick={() => setIsOpen((open) => !open)}
      >
        <span className="material-symbols-rounded">
          {isOpen ? "close" : "mode_comment"}
        </span>
      </button>
      {isOpen && (
        <div className="chatbot-popup">
          {/* chatbot header */}
          <div className="chat-header">
            <div className="header-info">
              <ChatbotIcon />
              <h2 className="logo-text">Chatbot</h2>
            </div>
            <button
              className="material-symbols-rounded"
              style={{
                background: "none",
                border: "none",
                color: "#fff",
                fontSize: 24,
                cursor: "pointer",
              }}
              aria-label="Close chatbot"
              onClick={() => setIsOpen(false)}
            >
              close
            </button>
          </div>
          <div className="chat-body">
            <div className="message dot-message">
              <ChatbotIcon />
              <p className="message-text">
                Hey there <br /> How can I help you today?
              </p>
            </div>
            {chatHistory.map((chat, index) => (
              <ChatMessage key={index} chat={chat} />
            ))}
          </div>
          {/* chatbot footer */}
          <div className="chat-footer">
            <ChatForm
              chatHistory={chatHistory}
              setChatHistory={setChatHistory}
              generateBotResponse={generateBotResponse}
            />
          </div>
        </div>
      )}
    </div>
  );
};

export default App;
