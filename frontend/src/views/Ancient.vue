<template>
  <div class="Ancient">
    <div class="book-wrapper">
      <div id="flipbook">
        <div class="hard double"><img src="/images/首页.png" alt="封面首页" /></div>
        <div class="page"><img src="/images/兰茂.png" alt="兰茂图" /></div>
        <div class="page"><img src="/images/介绍.png" alt="介绍图"/></div>
        <div class="page">
          <div class="page-bg" style="background-image: url('/images/背景.png');">
            <img src="/images/三七.png" class="page-img" alt="三七图" @click="goToHerbal('三七')" />
          </div>
        </div>
        <div class="page">
          <div class="page-bg" style="background-image: url('/images/背景.png');">
            <img src="/images/滇重楼.png" class="page-img" alt="滇重楼图" @click="goToHerbal('滇重楼')" />
          </div>
        </div>
        <div class="page">
          <div class="page-bg" style="background-image: url('/images/背景.png');">
            <img src="/images/滇黄精.png" class="page-img" alt="滇黄精图" @click="goToHerbal('滇黄精')" />
          </div>
        </div>
        <div class="page">
          <div class="page-bg" style="background-image: url('/images/背景.png');">
            <img src="/images/滇龙胆草.png" class="page-img" alt="滇龙胆图" @click="goToHerbal('滇龙胆')" />
          </div>
        </div>
        <div class="hard double"><img src="/images/img_3.png" alt="滇黄精图"/></div>
      </div>

      <!-- 进度条组件 -->
      <div class="progress-container">
        <input
  type="range"
  min="1"
  :max="totalPages"
  v-model="currentPage"
  @input="handleSliderChange"
  class="progress-slider"
/>

        <span class="page-counter">{{ currentPage }} / {{ totalPages }}</span>
      </div>
    </div>
</template>

<script setup>
import { onBeforeMount, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const currentPage = ref(1)
const totalPages = 8
let flipbookInstance = null

const goToHerbal = (herbName) => {
  router.push({ path: '/herbal', query: { herbName } })
}

// 处理滑块实时输入
const handleSliderInput = (event) => {
  currentPage.value = parseInt(event.target.value)
}

const handleSliderChange = () => {
  if (flipbookInstance && flipbookInstance.data('turn')) {
    flipbookInstance.turn('page', currentPage.value)
  }
}


onBeforeMount(() => {
  const script = document.createElement('script')
  script.src = '/lib/turn.js'
  script.onload = () => {
    document.getElementById('flipbook')?.classList.add('turnjs-ready')
    if (typeof $.fn.turn === 'undefined') {
      console.error('turn.js still not available')
      return
    }

    flipbookInstance = $('#flipbook')
    flipbookInstance.turn({
      width: 1200,
      height: 800,
      autoCenter: true,
      display: 'double',
      elevation: 50,
      gradients: true,
      when: {
        turning: function (event, page) {
          currentPage.value = page
        }
      }
    })

    flipbookInstance.turn('page', currentPage.value)
  }

  script.onerror = () => {
    console.error('Error loading turn.js')
  }

  document.head.appendChild(script)
})
</script>

<style scoped>
@import './ancient.css';
</style>
