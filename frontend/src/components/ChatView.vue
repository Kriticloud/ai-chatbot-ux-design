<template>
  <section class="card chat-card">
    <div class="chat-header">
      <h2>Conversation</h2>
      <span class="muted">Recent history available</span>
    </div>

    <div class="messages" ref="messagesContainer">
      <div v-for="(msg, index) in messages" :key="`${msg.role}-${index}`" :class="['message', msg.role === 'assistant' ? 'bot' : 'user']">
        {{ msg.content }}
      </div>
    </div>

    <form class="composer" @submit.prevent="submitMessage">
      <input v-model="message" type="text" placeholder="Type a message" required />
      <button type="submit" class="btn">Send</button>
    </form>
  </section>
</template>

<script setup>
import { nextTick, ref, watch } from 'vue';

const props = defineProps({
  messages: {
    type: Array,
    required: true
  }
});

const emit = defineEmits(['send-message']);

const message = ref('');
const messagesContainer = ref(null);

function submitMessage() {
  const content = message.value.trim();
  if (!content) return;
  emit('send-message', content);
  message.value = '';
}

watch(
  () => props.messages.length,
  async () => {
    await nextTick();
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight;
    }
  }
);
</script>
