<template>
  <div class="Ancient">
    <div class="book-wrapper">
      <div id="flipbook">
      <div class="hard double"><img src="/images/首页.png" alt="封面首页" /></div>
      <div class="page"><img src="/images/兰茂.png" alt="兰茂图" /></div>
      <div class="page"><img src="/images/介绍.png" alt="介绍图"/></div>
      <div class="page">
        <div class="page-bg" style="background-image: url('/images/背景.png');">
          <img src="/images/三七.png" class="page-img" alt="三七图" @click="goToHerbal"/>
        </div>
      </div>
      <div class="page">
        <div class="page-bg" style="background-image: url('/images/背景.png');">
          <img src="/images/滇重楼.png" class="page-img" alt="滇重楼图" />
        </div>
      </div>
      <div class="page">
        <div class="page-bg" style="background-image: url('/images/背景.png');">
          <img src="/images/滇黄精.png" class="page-img" alt="滇黄精图" />
        </div>
      </div>
      <div class="page">
        <div class="page-bg" style="background-image: url('/images/背景.png');">
          <img src="/images/滇龙胆草.png" class="page-img" alt="滇龙胆图" />
        </div>
      </div>

      <div class="page">
        <div class="page-bg" style="background-image: url('/images/背景.png');">
          <img src="/images/云茯苓.png" class="page-img" alt="云茯苓图" />
        </div>
      </div>

      <div class="page">
        <div class="page-bg" style="background-image: url('/images/背景.png');">
          <img src="/images/天麻.png" class="page-img" alt="天麻图" />
        </div>
      </div>

      <div class="page">
        <div class="page-bg" style="background-image: url('/images/背景.png');">
          <img src="/images/砂仁.png" class="page-img" alt="砂仁图" />
        </div>
      </div>

      <div class="page">
        <div class="page-bg" style="background-image: url('/images/背景.png');">
          <img src="/images/云木香.png" class="page-img" alt="云木香图" />
        </div>
      </div>

      <div class="page">
        <div class="page-bg" style="background-image: url('/images/背景.png');">
          <img src="/images/铁皮石斛.png" class="page-img" alt="铁皮石斛图" />
        </div>
      </div>
      
      <div class="page">
        <div class="page-bg" style="background-image: url('/images/背景.png');">
          <img src="/images/灯盏花.png" class="page-img" alt="灯盏花图" />
        </div>
      </div>

      <div class="hard double"><img src="/images/背景.png" alt="结尾" /></div>
</div>

    </div>
  </div>
</template>

<script setup>

import { onBeforeMount, } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goToHerbal = () => {
  router.push('/herbal')
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