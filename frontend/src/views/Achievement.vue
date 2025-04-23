<template>
  <div class="achievement">
    <div class="achievement-background">
      
      <button @click="goToAncient">前往古籍页面</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()
const goToAncient = () => {
  router.push('/ancient')
}
</script>