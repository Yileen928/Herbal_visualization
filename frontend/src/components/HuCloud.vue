<template>
  <div class="doctor-skills-wordcloud">
    <div ref="wordcloudChart" style="width: 100%; height:100%;"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue'
import * as echarts from 'echarts';
import 'echarts-wordcloud';

const wordcloudChart = ref<HTMLElement | null>(null)
let chartInstance: echarts.ECharts | null = null

// 胡庆余堂数据（已按词频降序排序）
const hqytData = [
  { name: '杭州', value: 4978 },
  { name: '医生', value: 1257 },
  { name: '膏方', value: 1255 },
  { name: '玉竹', value: 933 },
  { name: '脾胃', value: 850 },
  { name: '价格', value: 838 },
  { name: '中医', value: 814 },
  { name: '地址', value: 692 },
  { name: '名医馆', value: 646 },
  { name: '中药', value: 610 },
  { name: '电话', value: 602 },
  { name: '功效', value: 587 },
  { name: '养生', value: 569 },
  { name: '上海', value: 568 },
  { name: '代理', value: 502 },
  { name: '专家', value: 500 },
  { name: '时间', value: 428 },
  { name: '药膳', value: 425 },
  { name: '国药', value: 409 },
  { name: '玉仁', value: 401 },
  { name: '胡雪岩', value: 380 },
  { name: '效果', value: 367 },
  { name: '小儿', value: 364 },
  { name: '阿胶', value: 345 },
  { name: '临平', value: 332 },
  { name: '图片', value: 326 },
  { name: '河坊街', value: 325 },
  { name: '产品', value: 325 },
  { name: '微商', value: 323 },
  { name: '燕窝', value: 320 }
].map((item, index) => ({
  ...item,
  // 前5项使用不透明色，其余使用半透明色
  textStyle: {
    color: index < 5 ? '#f5aa6d' : 'rgba(245, 170, 109, 0.5)'
  }
}))

onMounted(() => {
  if (wordcloudChart.value) {
    chartInstance = echarts.init(wordcloudChart.value)
    
    const option: echarts.EChartsOption = {
      title: {
        text: '胡庆余堂关键词热度',
        left: 'center',
        textStyle: {
          fontSize: 16,
          fontWeight: 'bold'

        }
      },
      tooltip: {
        show: true
      },
      series: [{
        type: 'wordCloud',
        shape: 'circle',
        left: 'center',
        top: 'center',
        width: '90%',
        height: '90%',
        sizeRange: [12, 60],
        rotationRange: [-45, 45],
        rotationStep: 15,
        gridSize: 8,
        drawOutOfBound: false,
        textStyle: {
          fontFamily: 'sans-serif',
          fontWeight: 'bold'
        },
          emphasis: {
            focus: "self",
          },
        data: hqytData
      }]
    }

    chartInstance.setOption(option)
    
    window.addEventListener('resize', resizeHandler)
  }
})

const resizeHandler = () => {
  if (chartInstance) {
    chartInstance.resize()
  }
}

onBeforeUnmount(() => {
  if (chartInstance) {
    chartInstance.dispose()
    window.removeEventListener('resize', resizeHandler)
  }
})
</script>

<style scoped>
.doctor-skills-wordcloud {
  width: 100%;
  height: 100%;
  border-radius: 8px;
  padding: 10px;
}
</style>