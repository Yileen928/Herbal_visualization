<template>
  <div class="home">
    <div class="home-background">
      <div class="home-content">
       
        <button @click="goToAchievement">前往成果展示</button>
      </div>
      <div class="home-map">
        <YunnanMap :currentRoute="currentRoute" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import YunnanMap from '../components/YunnanMap.vue'

const router = useRouter()
const goToAchievement = () => {
  router.push('/achievement')
}

const routes = {
  route1: { name: '普洱-大理-迪庆线' },
  route2: { name: '普洱-保山-红河线' }
}

const currentRoute = ref('route1')

const switchRoute = (routeName: string) => {
  currentRoute.value = routeName
}
</script>

<style>
@import './home.css';
</style>