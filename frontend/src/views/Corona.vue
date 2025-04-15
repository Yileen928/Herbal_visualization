<template>
  <div class="background-container">
    <div class="corona-background" @mousemove="handleMouseMove">
      <div class="light-effect" :style="lightPosition"></div>
      <div class="corona-bg"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Corona',
  data() {
    return {
      mouseX: 0,
      mouseY: 0
    }
  },
  computed: {
    lightPosition() {
      return {
        left: this.mouseX + 'px',
        top: this.mouseY + 'px'
      }
    }
  },
  methods: {
    handleMouseMove(e) {
      this.mouseX = e.clientX;
      this.mouseY = e.clientY;
    }
  }
}
</script>

<style scoped>
@import './corona.css';
</style>