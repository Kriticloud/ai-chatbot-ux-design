const API_BASE = window.API_BASE || "http://localhost:8080";

const statusBtn = document.getElementById("statusBtn");
const statusText = document.getElementById("statusText");
const chatForm = document.getElementById("chatForm");
const messageInput = document.getElementById("messageInput");
const messages = document.getElementById("messages");
const trainForm = document.getElementById("trainForm");
const questionInput = document.getElementById("questionInput");
const answerInput = document.getElementById("answerInput");
const trainStatus = document.getElementById("trainStatus");
const reminderForm = document.getElementById("reminderForm");
const reminderTitle = document.getElementById("reminderTitle");
const reminderTime = document.getElementById("reminderTime");
const reminderList = document.getElementById("reminderList");

function appendMessage(text, role) {
  const el = document.createElement("div");
  el.className = `message ${role}`;
  el.textContent = text;
  messages.appendChild(el);
  messages.scrollTop = messages.scrollHeight;
}

function clearMessages() {
  messages.innerHTML = "";
}

async function loadHistory() {
  try {
    const res = await fetch(`${API_BASE}/api/messages`);
    if (!res.ok) return;
    const data = await res.json();
    clearMessages();
    data.forEach((msg) => appendMessage(msg.content, msg.role === "assistant" ? "bot" : "user"));
    if (data.length === 0) {
      appendMessage("Hello! I am here for companionship and daily support.", "bot");
    }
  } catch (error) {
    appendMessage("Hello! I am here for companionship and daily support.", "bot");
  }
}

function renderReminders(reminders) {
  reminderList.innerHTML = "";
  reminders.forEach((reminder) => {
    const li = document.createElement("li");
    li.className = "reminder-item";
    li.innerHTML = `
      <div>
        <strong>${reminder.title}</strong>
        <div class="muted">${new Date(reminder.reminderTime).toLocaleString()}</div>
      </div>
      <button class="btn" data-id="${reminder.id}" data-title="${reminder.title}" data-time="${reminder.reminderTime}" data-completed="${reminder.completed}" ${reminder.completed ? "disabled" : ""}>
        ${reminder.completed ? "Completed" : "Mark done"}
      </button>
    `;
    reminderList.appendChild(li);
  });

  reminderList.querySelectorAll("button").forEach((btn) => {
    btn.addEventListener("click", async () => {
      if (btn.dataset.completed === "true") return;
      await fetch(`${API_BASE}/api/reminders/${btn.dataset.id}`, {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          title: btn.dataset.title,
          reminderTime: btn.dataset.time,
          completed: true
        })
      });
      await loadReminders();
    });
  });
}

async function loadReminders() {
  try {
    const res = await fetch(`${API_BASE}/api/reminders`);
    if (!res.ok) return;
    const data = await res.json();
    renderReminders(data);
  } catch (error) {
    reminderList.innerHTML = '<li class="muted">Could not load reminders.</li>';
  }
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

trainForm.addEventListener("submit", async (event) => {
  event.preventDefault();
  const question = questionInput.value.trim();
  const answer = answerInput.value.trim();
  if (!question || !answer) return;

  trainStatus.textContent = "Training...";
  try {
    const res = await fetch(`${API_BASE}/api/train`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ question, answer })
    });
    const data = await res.json();
    if (!res.ok) {
      trainStatus.textContent = data.error || "Training failed";
      return;
    }
    trainStatus.textContent = `Learned: "${data.learnedQuestion}"`;
    questionInput.value = "";
    answerInput.value = "";
    appendMessage("Great! I learned a new response. Ask me now.", "bot");
  } catch (error) {
    trainStatus.textContent = "Training failed: backend unreachable";
  }
});

reminderForm.addEventListener("submit", async (event) => {
  event.preventDefault();
  const title = reminderTitle.value.trim();
  const reminderAt = reminderTime.value;
  if (!title || !reminderAt) return;

  await fetch(`${API_BASE}/api/reminders`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      title,
      reminderTime: `${reminderAt}:00`,
      completed: false
    })
  });

  reminderTitle.value = "";
  reminderTime.value = "";
  await loadReminders();
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

loadHistory();
loadReminders();
