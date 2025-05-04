<template>
  <div class="background-container">
    <div class="corona"></div>
    <div class="corona-container">
      <video autoplay loop muted playsinline class="corona-video" crossorigin="anonymous" :src="coronaVideoSrc"></video>
      <button class="corona-button" @click="goAchievement"></button>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const coronaVideoSrc = computed(() => new URL('/home.webm', import.meta.url).href)

function goAchievement() {
  router.push('/achievement')
}
</script>

<style scoped>
@import './corona.css';
</style>