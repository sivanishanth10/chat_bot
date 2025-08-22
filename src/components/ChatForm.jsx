import { useRef } from "react";

const ChatForm = ({ chatHistory,setChatHistory,generateBotResponse }) => {
  const inputRef = useRef();

  const handleFormSubmit = (e) => {
    e.preventDefault();
    const userMessage = inputRef.current.value.trim();
    if (!userMessage) return;
    inputRef.current.value = ""; // Optionally clear the input after submit
    setChatHistory((history) => [
      ...history,
      { role: "user", text: userMessage },
    ]);
    setTimeout(() => {
        setChatHistory((history) => [
          ...history,
          { role: "model", text:"thinking..." },
        ]);
        generateBotResponse([...chatHistory, { role: "user", text:` using the details provded above ,pleae address this query: ${userMessage}`}]);
    },600);
    
  };

  return (
    <form className="chat-form" onSubmit={handleFormSubmit}>
      <input
        type="text"
        placeholder="message.."
        className="message-input"
        required
        ref={inputRef}
      />
      <button type="submit" className="material-symbols-rounded">
        arrow_upward
      </button>
    </form>
  );
};

export default ChatForm;
