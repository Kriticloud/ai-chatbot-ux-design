const API_BASE = window.API_BASE || "http://localhost:8080";

const statusBtn = document.getElementById("statusBtn");
const statusText = document.getElementById("statusText");
const chatForm = document.getElementById("chatForm");
const messageInput = document.getElementById("messageInput");
const messages = document.getElementById("messages");
const loadRemindersBtn = document.getElementById("loadRemindersBtn");
const remindersList = document.getElementById("remindersList");

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

function buildReminderListItem(reminder) {
  const li = document.createElement("li");
  li.className = "reminder-item";

  const title = document.createElement("strong");
  title.textContent = reminder.title || "Untitled reminder";

  const time = document.createElement("span");
  time.textContent = reminder.remindAt
    ? ` — ${new Date(reminder.remindAt).toLocaleString()}`
    : " — time not set";

  li.append(title, time);
  return li;
}

function renderReminders(reminders) {
  if (!remindersList) {
    return;
  }

  remindersList.replaceChildren();
  reminders.forEach((reminder) => {
    remindersList.appendChild(buildReminderListItem(reminder));
  });
}

if (loadRemindersBtn) {
  loadRemindersBtn.addEventListener("click", async () => {
    loadRemindersBtn.disabled = true;
    loadRemindersBtn.textContent = "Loading...";

    try {
      const res = await fetch(`${API_BASE}/api/reminders`);
      const data = await res.json();

      if (!res.ok) {
        throw new Error(data.error || "Failed to load reminders");
      }

      renderReminders(data);
    } catch (error) {
      renderReminders([]);
      if (remindersList) {
        const errorItem = document.createElement("li");
        errorItem.className = "reminder-item error";
        errorItem.textContent = "Unable to load reminders right now.";
        remindersList.appendChild(errorItem);
      }
    } finally {
      loadRemindersBtn.disabled = false;
      loadRemindersBtn.textContent = "Load reminders";
    }
  });
}

appendMessage("Hello! I am here for companionship and daily support.", "bot");
