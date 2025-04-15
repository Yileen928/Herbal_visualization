<template>
  <div class="map-container">
    <div id="china-map" ref="mapContainer"></div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import chinaJson from '../assets/china.json'

const router = useRouter()
const mapContainer = ref(null)

const handleMapClick = (params) => {
  if (params.name === '云南省') {
    router.push('/herbal-product')
  }
}

onMounted(() => {
  const chart = echarts.init(mapContainer.value)
  echarts.registerMap('china', chinaJson)
  
  const option = {
    title: {
      text: '中国中药材地理分布',
      left: 'center',
      textStyle: {
        fontSize: 24,
        color: '#333'
      }
    },
    tooltip: {
      trigger: 'item',
      formatter: '{b}<br/>中药材数量：{c}种'
    },
    visualMap: {
      min: 0,
      max: 100,
      left: 'left',
      top: 'bottom',
      text: ['高', '低'],
      calculable: true,
      inRange: {
        color: ['#e0f7e0', '#1a6d1a']
      }
    },
    series: [{
      name: '中药材品种',
      type: 'map',
      map: 'china',
      roam: true,
      emphasis: {
        label: {
          show: true
        }
      },
      data: [
        {name: '云南省', value: 95},
        {name: '四川省', value: 88},
        {name: '贵州省', value: 82},
        {name: '广西壮族自治区', value: 78},
        {name: '湖南省', value: 75},
        {name: '陕西省', value: 70},
        {name: '甘肃省', value: 68}
      ]
    }]
  }
  
  chart.setOption(option)
  
  // 添加点击事件监听
  chart.on('click', handleMapClick)
  
  window.addEventListener('resize', () => {
    chart.resize()
  })
})
</script>

<style scoped>
@import './Map.css';
</style>