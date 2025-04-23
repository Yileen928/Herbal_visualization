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
            <img src="/images/滇龙胆草.png" class="page-img" alt="滇龙胆图" @click="goToHerbal('滇龙胆草')" />
          </div>
        </div>
        <div class="hard double"><img src="/images/img_3.png" alt="滇黄精图"/></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goToHerbal = (herbName) => {
  router.push({ path: '/herbal', query: { herbName } })
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
    $('#flipbook').turn({
      width: 1200,
      height: 800,
      autoCenter: true,
      display: 'double',
      elevation: 50,
      gradients: true,
      when: {
        turning: function(event, page, view) {
          // 翻页处理
        }
      }
    })
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
