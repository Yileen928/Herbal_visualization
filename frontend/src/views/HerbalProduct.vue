<template>
  <div class="herbal-product">
    <div class="herbal-product-background">
      <h1>药品页面</h1>
      <button @click="goToHci">前往人机交互页面</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()
const goToHci = () => {
  router.push('/hci')
}
</script>