const API_BASE = window.API_BASE || 'http://localhost:8080';

async function parseResponse(response) {
  const data = await response.json().catch(() => ({}));
  if (!response.ok) {
    throw new Error(data.error || 'Request failed');
  }
  return data;
}

export const api = {
  async getStatus() {
    const res = await fetch(`${API_BASE}/api/status`);
    return parseResponse(res);
  },
  async getMessages() {
    const res = await fetch(`${API_BASE}/api/messages`);
    return parseResponse(res);
  },
  async sendMessage(message) {
    const res = await fetch(`${API_BASE}/api/chat`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ message })
    });
    return parseResponse(res);
  },
  async train(question, answer) {
    const res = await fetch(`${API_BASE}/api/train`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ question, answer })
    });
    return parseResponse(res);
  },
  async getReminders() {
    const res = await fetch(`${API_BASE}/api/reminders`);
    return parseResponse(res);
  },
  async addReminder(reminder) {
    const res = await fetch(`${API_BASE}/api/reminders`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(reminder)
    });
    return parseResponse(res);
  },
  async completeReminder(reminder) {
    const res = await fetch(`${API_BASE}/api/reminders/${reminder.id}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ ...reminder, completed: true })
    });
    return parseResponse(res);
  }
};
