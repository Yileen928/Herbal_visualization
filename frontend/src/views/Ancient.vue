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

<style scoped>
button {
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 20px;
}
</style>