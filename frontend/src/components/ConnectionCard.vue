<template>
  <section class="card panel-card">
    <h2>Connection</h2>
    <p class="muted">Check if backend service is active.</p>
    <button class="btn btn-full" @click="$emit('check-status')">Check backend status</button>
  </section>
</template>
