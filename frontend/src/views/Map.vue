<template>
  <div class="map">
    <div class="map-background">
      <h1>地图页面</h1>
      <button @click="goToHerbalProduct">前往药品页面</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()
const goToHerbalProduct = () => {
  router.push('/herbal-product')
}
</script>