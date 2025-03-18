<template>
  <div class="home">
    <h1>首页</h1>
    <button @click="goToAncient">前往古籍页面</button>
  </div>
</template>

<script>
export default {
  name: 'Home',
  methods: {
    goToAncient() {
      this.$router.push('/ancient')
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