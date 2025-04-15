<template>
  <div class="background-container" @click="changeBackground">
    <div class="time-background" 
         :style="{ backgroundImage: `url(${currentBg})` }"
         :class="{ 'fade-out': isChanging }">
    <div class="time-points1"></div>
    <div class="time-points2">
      <div class="time-con1"></div>
      <div class="time-con2">
        <div class="time-title">
          <button class="time-button1">
            公元前1800年
            </button>
        </div>
        <div class="time-line">
          </div>
          </div>
      <div class="time-con3">
        
      </div>
    </div>
    <div class="time-points3">
      <div class="time-con1"></div>
      <div class="time-con2">
        <div class="time-title">
          <button class="time-button2">
            ​公元前300年
            </button>
        </div>
        <div class="time-line2">
          </div>
          </div>
      <div class="time-con3">
        
      </div>
    </div>
    <div class="time-points4">
      <div class="time-con1"></div>
      <div class="time-con2">
        <div class="time-title">
          <button class="time-button3">
            公元前109年
            </button>
        </div>
        <div class="time-line3">
          </div>
          </div>
    </div>

    </div>
    <div class="time-background" 
         :style="{ backgroundImage: `url(${nextBg})` }"
         :class="{ 'fade-in': isChanging }">
    </div>
  </div>
</template>

<script>
// 修改图片导入方式
const qinBg = new URL('../assets/images/qinbackground.png', import.meta.url).href
const daliBg = new URL('../assets/images/dalibackground.png', import.meta.url).href
const hanBg = new URL('../assets/images/hanbackground.png', import.meta.url).href

export default {
  name: 'TimeHistory',
  data() {
    return {
      isChanging: false,
      backgrounds: [qinBg,hanBg,daliBg],
      currentBgIndex: 0,
      nextBgIndex: 1
    }
  },
  computed: {
    currentBg() {
      return this.backgrounds[this.currentBgIndex]
    },
    nextBg() {
      return this.backgrounds[this.nextBgIndex]
    }
  },
  methods: {
    changeBackground() {
      if (this.isChanging) return;
      this.isChanging = true;
      
      setTimeout(() => {
        this.currentBgIndex = this.nextBgIndex;
        this.nextBgIndex = (this.nextBgIndex + 1) % this.backgrounds.length;
        this.isChanging = false;
      }, 500);
    }
  }
}
</script>

<style scoped>
/* 2. 导入外部 CSS 文件 */
@import './timeHistory.css';
</style>