<template>
  <section class="card panel-card">
    <h2>Reminders</h2>
    <p class="muted">Create and complete reminders.</p>
    <form class="stack-form" @submit.prevent="submitReminder">
      <input v-model="title" type="text" placeholder="e.g. Take afternoon medicine" required />
      <input v-model="reminderTime" type="datetime-local" required />
      <button class="btn btn-full" type="submit">Add reminder</button>
    </form>

    <ul class="reminder-list">
      <li v-for="reminder in reminders" :key="reminder.id" class="reminder-item">
        <div>
          <strong>{{ reminder.title }}</strong>
          <div class="muted">{{ formatTime(reminder.reminderTime) }}</div>
        </div>
        <button
          class="btn"
          :disabled="reminder.completed"
          @click="$emit('complete-reminder', reminder)"
        >
          {{ reminder.completed ? 'Completed' : 'Mark done' }}
        </button>
      </li>
      <li v-if="reminders.length === 0" class="muted">No reminders yet.</li>
    </ul>
  </section>
</template>

<script setup>
import { ref } from 'vue';

defineProps({
  reminders: {
    type: Array,
    required: true
  }
});

const emit = defineEmits(['add-reminder', 'complete-reminder']);

const title = ref('');
const reminderTime = ref('');

function submitReminder() {
  emit('add-reminder', {
    title: title.value.trim(),
    reminderTime: `${reminderTime.value}:00`,
    completed: false,
    reset: () => {
      title.value = '';
      reminderTime.value = '';
    }
  });
}

function formatTime(value) {
  return new Date(value).toLocaleString();
}
</script>
