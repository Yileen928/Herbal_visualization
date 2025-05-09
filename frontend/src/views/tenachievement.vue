<template>
    <div class="background-container">
        <div class="ten-achievement">
            <div class="button-hci" @click="goToHci"></div>
        </div>
    </div>
</template>
  
<script setup>
import { useRouter } from 'vue-router';  
const router = useRouter(); 

const goToHci = () => {
    router.push("/hci"); 
};
</script>
  
<style scoped>
@import './tenachievement.css';
</style>