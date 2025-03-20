<template>
  <div class="home">
    <div class="home-background">
      <h1>首页</h1>
      <button @click="goToAchievement">前往成果展示</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()
const goToAchievement = () => {
  router.push('/achievement')
}
</script>

<style>
@import './home.css';
</style>