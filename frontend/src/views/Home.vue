<template>
  <!-- 主页面容器 -->
  <div class="home">
    <!-- 页面标题 -->
    <h1>首页</h1>
    <!-- 导航按钮，点击触发 goToAncient 方法 -->
    <button @click="goToAncient">前往古籍页面</button>
  </div>
</template>

<script>
export default {
  // 组件名称
  name: 'Home',
  
  // 组件方法
  methods: {
    // 页面跳转方法
    goToAncient() {
      // 使用 Vue Router 进行页面导航
      this.$router.push('/ancient')
    }
  }
}
</script>

<style>
/* 导入外部样式文件 */
@import '../assets/styles/home.css';
</style>