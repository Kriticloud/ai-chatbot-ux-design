<template>
  <main class="app">
    <AppHeader :status="statusText" />

    <section class="layout-grid">
      <aside class="left-column">
        <ConnectionCard @check-status="checkStatus" />
        <TrainCard :status-text="trainStatus" @train="trainBot" />
        <ReminderCard
          :reminders="reminders"
          @add-reminder="addReminder"
          @complete-reminder="completeReminder"
        />
      </aside>

      <ChatView :messages="messages" @send-message="sendMessage" />
    </section>
  </main>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import AppHeader from '../components/AppHeader.vue';
import ChatView from '../components/ChatView.vue';
import ConnectionCard from '../components/ConnectionCard.vue';
import ReminderCard from '../components/ReminderCard.vue';
import TrainCard from '../components/TrainCard.vue';
import { api } from '../services/api';

const statusText = ref('unknown');
const trainStatus = ref('No training submitted yet.');
const reminders = ref([]);
const messages = ref([]);

async function checkStatus() {
  statusText.value = 'checking...';
  try {
    const data = await api.getStatus();
    statusText.value = data.status;
  } catch (error) {
    statusText.value = 'backend unreachable';
  }
}

async function loadHistory() {
  try {
    const history = await api.getMessages();
    messages.value = history.length
      ? history
      : [{ role: 'assistant', content: 'Hello! I am here for companionship and daily support.' }];
  } catch (error) {
    messages.value = [{ role: 'assistant', content: 'Hello! I am here for companionship and daily support.' }];
  }
}

async function loadReminders() {
  try {
    reminders.value = await api.getReminders();
  } catch (error) {
    reminders.value = [];
  }
}

async function trainBot({ question, answer, reset }) {
  if (!question || !answer) return;
  trainStatus.value = 'Training...';
  try {
    const data = await api.train(question, answer);
    trainStatus.value = `Learned: "${data.learnedQuestion}"`;
    reset();
    messages.value.push({ role: 'assistant', content: 'Great! I learned a new response. Ask me now.' });
  } catch (error) {
    trainStatus.value = error.message;
  }
}

async function addReminder({ reset, ...reminder }) {
  if (!reminder.title || !reminder.reminderTime) return;
  try {
    await api.addReminder(reminder);
    reset();
    await loadReminders();
  } catch (error) {
    trainStatus.value = 'Could not add reminder.';
  }
}

async function completeReminder(reminder) {
  if (reminder.completed) return;
  try {
    await api.completeReminder(reminder);
    await loadReminders();
  } catch (error) {
    trainStatus.value = 'Could not update reminder.';
  }
}

async function sendMessage(content) {
  messages.value.push({ role: 'user', content });
  try {
    const data = await api.sendMessage(content);
    messages.value.push({ role: 'assistant', content: data.response });
  } catch (error) {
    messages.value.push({ role: 'assistant', content: 'I cannot reach the server right now. Please try again.' });
  }
}

onMounted(async () => {
  await Promise.all([loadHistory(), loadReminders()]);
});
</script>
