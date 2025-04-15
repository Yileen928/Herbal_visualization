<template>
  <div class="herbal-product">
    <div class="herbal-product-background">
      <div class="herbal-product-container1">
        <div class="herbal-product-chart1"></div>
        <div class="herbal-product-chart2">  </div>   
      </div>
      <div class="herbal-product-container2">
        <div class="herbal-product-chart3"></div>
        <div class="herbal-product-chart2"></div>
      </div>
      <div class="herbal-product-container3">
        <div class="herbal-product-chart1"></div>
        <div class="herbal-product-chart2"></div>
      </div>
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

<style scoped>
@import './HerbalProduct.css';
</style>