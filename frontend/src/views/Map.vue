<template>
  <div class="map-container">
    <div id="china-map" ref="mapContainer"></div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import chinaJson from '../assets/China2.json'

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
      regions: [{
      name: '南海诸岛',
      itemStyle: {
        opacity: 0 // 完全透明
      },
      label: {
        show: false // 隐藏标签
      }
    }],
      roam: true,
      emphasis: {
        label: {
          show: true
        }
      },
      data: [
        {name: '北京市', value: 4},
        {name: '天津市', value: 4},
        {name: '上海市', value: 5},
        {name: '重庆市', value: 7},
        {name: '河北省', value: 21},
        {name: '山西省', value: 19},
        {name: '辽宁省', value: 18},
        {name: '吉林省', value: 11},
        {name: '黑龙江省', value: 6},
        {name: '江苏省', value: 26},
        {name: '浙江省', value: 37},
        {name: '安徽省', value: 29},
        {name: '福建省', value: 41},
        {name: '江西省', value: 35},
        {name: '山东省', value: 18},
        {name: '河南省', value: 26},
        {name: '广东省', value: 47},
        {name: '湖南省', value: 37},
        {name: '湖北省', value: 42},
        {name: '海南省', value: 19},
        {name: '四川省', value: 75},
        {name: '贵州省', value: 47},
        {name: '云南省', value: 156},
        {name: '陕西省', value: 33},
        {name: '甘肃省', value: 20},
        {name: '青海省', value: 5},
        {name: '台湾省', value: 16},
        {name: '内蒙古自治区', value: 10},
        {name: '广西壮族自治区', value: 62},
        {name: '西藏自治区', value: 18},
        {name: '宁夏回族自治区', value: 2},
        {name: '新疆维吾尔自治区', value: 4},
        {name: '香港特别行政区', value: 0},
        {name: '澳门特别行政区', value: 0}
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
