const API_BASE = window.API_BASE || "http://localhost:8080";

const statusBtn = document.getElementById("statusBtn");
const statusText = document.getElementById("statusText");
const chatForm = document.getElementById("chatForm");
const messageInput = document.getElementById("messageInput");
const messages = document.getElementById("messages");

function appendMessage(text, role) {
  const el = document.createElement("div");
  el.className = `message ${role}`;
  el.textContent = text;
  messages.appendChild(el);
  messages.scrollTop = messages.scrollHeight;
}

statusBtn.addEventListener("click", async () => {
  statusText.textContent = "Status: checking...";
  try {
    const res = await fetch(`${API_BASE}/api/status`);
    const data = await res.json();
    statusText.textContent = `Status: ${data.status}`;
  } catch (error) {
    statusText.textContent = "Status: backend unreachable";
  }
});

chatForm.addEventListener("submit", async (event) => {
  event.preventDefault();
  const message = messageInput.value.trim();
  if (!message) return;

  appendMessage(message, "user");
  messageInput.value = "";

  try {
    const res = await fetch(`${API_BASE}/api/chat`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ message })
    });

    const data = await res.json();
    if (!res.ok) {
      appendMessage(data.error || "Request failed", "bot");
      return;
    }
    appendMessage(data.response, "bot");
  } catch (error) {
    appendMessage("I cannot reach the server right now. Please try again.", "bot");
  }
});

appendMessage("Hello! I am here for companionship and daily support.", "bot");
