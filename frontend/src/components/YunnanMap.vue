<template>
  <div class="yunnan-map" ref="mapContainer"></div>
</template>

<script setup lang="ts">
import { onMounted, ref, onBeforeUnmount} from 'vue'
import * as echarts from 'echarts'
import yunnanMap from '../assets/yunnan.json'

const mapContainer = ref<HTMLElement>()
let chart: any = null

// 定义事件
const emit = defineEmits(['regionClick'])

const option = {
  backgroundColor: 'transparent',
  geo: {
    map: 'yunnan',
    center: [101.8, 25.2],  // 调整中心点
    zoom: 1,  // 增加缩放比例
    aspectScale: 1,  // 调整地图长宽比
    layoutCenter: ['50%', '50%'],  // 布局居中
    layoutSize: '105%',    // 调整布局大小
    show: true,
    roam: false,
    selectedMode: 'single',  // 添加选择模式
    itemStyle: {
      areaColor: 'transparent',
      borderColor: '#ACA094',
      borderWidth: 1.5,
      shadowColor: 'rgba(0, 0, 0, 0.2)',
      shadowBlur: 10
    },
    label: {
      show: true,
      color: '#333',
      fontSize: 12,
      fontFamily: 'STKaiti',
      position: 'center'
    },
    emphasis: {
      label: {
        show: true,
        color: '#000',
        fontSize: 14,
        fontWeight: 'bold'
      },
      itemStyle: {
        areaColor: '#E1D5C7',
        borderWidth: 2,
        opacity: 0.8
      }
    },
    select: {  // 添加选中状态的样式
      label: {
        show: true,
        color: '#8B4513',
        fontSize: 14,
        fontWeight: 'bold'
      },
      itemStyle: {
        areaColor: '#E1D5C7',
        borderColor: '#ACA094',
        borderWidth: 2,
        shadowColor: 'rgba(139, 69, 19, 0.3)',
        shadowBlur: 15,
        opacity: 0.8
      }
    }
  },
  series: [{
    type: 'map',
    map: 'yunnan',
    geoIndex: 0,
    selectedMode: 'single',
    label: {
      show: true,
      color: '#333',
      fontSize: 12
    },
    data: []  // 数据将在点击时更新
  }]
}

// 在点击事件处理中添加选中效果
const handleMapClick = (params) => {
  const regionName = params.name
  emit('region-click', regionName)
  
  // 更新选中状态
  chart.setOption({
    series: [{
      data: [{
        name: regionName,
        selected: true
      }]
    }]
  })
}

onMounted(() => {
  if (mapContainer.value) {
    chart = echarts.init(mapContainer.value)
    echarts.registerMap('yunnan', yunnanMap as any)
    chart.setOption(option)
    
    // 注册点击事件
    chart.on('click', handleMapClick)
  }
})

onBeforeUnmount(() => {
  if (chart) {
    chart.dispose()
    chart = null
  }
})

// 添加窗口大小变化监听
window.addEventListener('resize', () => {
  if (chart) chart.resize()
})
</script>

<style scoped>
.yunnan-map {
  width: 100%;  
  height: 80vh; 
  margin: 0 auto; 
  border-radius: 8px;
}
</style>
