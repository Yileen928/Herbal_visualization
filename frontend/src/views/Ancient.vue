<template>
  <div class="ancient">
    <h1>古籍页面</h1>
    <button @click="goToHerbal">前往本草页面</button>
  </div>
</template>

<script>
export default {
  name: 'Ancient',
  methods: {
    goToHerbal() {
      this.$router.push('/herbal')
    }
  }
}
</script>

<style>
@import '../assets/styles/ancient.css';
</style>