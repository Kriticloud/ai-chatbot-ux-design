<template>
  <header class="app-header card">
    <div>
      <p class="eyebrow">Companion Care Assistant</p>
      <h1>Elderly Companion Chatbot</h1>
      <p class="subtitle">Friendly support, reminders, and daily conversation in one place.</p>
    </div>
    <div class="status-inline">Status: {{ status }}</div>
  </header>
</template>

<script setup>
defineProps({
  status: {
    type: String,
    required: true
  }
});
</script>
