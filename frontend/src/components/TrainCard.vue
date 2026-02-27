<template>
  <section class="card panel-card">
    <h2>Train chatbot</h2>
    <p class="muted">Teach custom Q&A for personalized responses.</p>
    <form class="stack-form" @submit.prevent="submit">
      <input v-model="question" type="text" placeholder="What should I ask?" required />
      <input v-model="answer" type="text" placeholder="What should the bot reply?" required />
      <button type="submit" class="btn btn-full">Train</button>
    </form>
    <p class="muted feedback-pill">{{ statusText }}</p>
  </section>
</template>

<script setup>
import { ref } from 'vue';

defineProps({
  statusText: {
    type: String,
    required: true
  }
});

const emit = defineEmits(['train']);

const question = ref('');
const answer = ref('');

function submit() {
  emit('train', { question: question.value.trim(), answer: answer.value.trim(), reset: resetFields });
}

function resetFields() {
  question.value = '';
  answer.value = '';
}
</script>
