<template>
  <div class="background-container">
  </div>
    <div class="home">
      <div class="intro-animation" v-show="showIntro"></div>
      <div class="main-content" :class="{ 'fade-in': !showIntro }">
        <div class="left-container">
          <div class="homeleft">
            <img src="/images/首页左侧.png" alt="首页左侧" class="homeleftpic">
          </div>
        </div>
        <div class="text-container">
          <h1 class="title">
            <span class="vertical-text">药</span>
            <span class="vertical-text">灵</span>
            <span class="vertical-text">本</span>
            <span class="vertical-text">草</span>
          </h1>
          <h2 class="subtitle">
            <span class="vertical-text-small">云</span>
            <span class="vertical-text-small">南</span>
            <span class="vertical-text-small">中</span>
            <span class="vertical-text-small">医</span>
            <span class="vertical-text-small">药</span>
            <span class="vertical-text-small">文</span>
            <span class="vertical-text-small">化</span>
            <span class="vertical-text-small">遗</span>
            <span class="vertical-text-small">产</span>
            <span class="vertical-text-small">与</span>
            <span class="vertical-text-small">传</span>
            <span class="vertical-text-small">承</span>
            <span class="vertical-text-small">创</span>
            <span class="vertical-text-small">新</span>
            <span class="vertical-text-small">平</span>
            <span class="vertical-text-small">台</span>
          </h2>
          <div class="button-container">
            <img 
              src="/images/homebutton.png" 
              alt="跳转按钮" 
              class="challenge-button"
              @click="goAchievement"
            >
          </div>
        </div>
        <div class="right-container"></div>
        
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const showIntro = ref(true)

onMounted(() => {
  // GIF 播放完后淡出
  setTimeout(() => {
    showIntro.value = false
  }, 48000)
})

function goAchievement() {
  router.push('/achievement')
}
</script>

<style scoped>
@import './home.css';


</style>