<template>
  <div class="background-container">
    <Vue3Lottie
      animation-link="/animations/home.json"
      :height="600"
      :width="1000"
      :loop="true"
      :auto-play="true"
    />
  </div>
</template>

<script setup>
import Vue3Lottie from 'vue3-lottie';
</script>

<style scoped>
@import './corona.css';
</style>
