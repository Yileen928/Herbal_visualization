<template>
  <div class="book-wrapper">
    <div id="flipbook">
      <!-- 封面 -->
      <div class="hard">
        <img src="/images/首页.png" alt="封面" />
      </div>

      <!-- 第一翻页的左页：内容页 (例如：画像) -->
      <div class="page">
        <img src="/images/兰茂.png" alt="Page 1" />
      </div>

      <!-- 第一翻页的右页：内容页 -->
      <div class="page">
        <img src="/images/03.png" alt="Page 2" />
      </div>

      <!-- 后续页面：从第二页开始，使用内容页 -->
      <div class="page">
        <img src="/images/01.jpeg" alt="Page 3" />
      </div>
      <!-- 假设最后一页为封底 -->
      <div class="hard">
        <img src="/images/03.png" alt="Back Cover" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'

onMounted(() => {
  const script = document.createElement('script')
  script.src = '/lib/turn.js'  // 动态加载 turn.js
  script.onload = () => {
    if (typeof $.fn.turn === 'undefined') {
      console.error('turn.js still not available')
      return
    }
    $('#flipbook').turn({
      width: 922,         // 总宽度 = 两页宽度之和
      height: 600,
      autoCenter: true,
      display: 'double',  // 双页显示
      elevation: 50,      // 翻页厚度
      gradients: true,
      when: {
        turning: function (event, page, view) {
          // 可以在翻页时做一些处理
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