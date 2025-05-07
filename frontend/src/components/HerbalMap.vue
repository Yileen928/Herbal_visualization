<template>
  <div class="yunnan-map" ref="mapContainer"></div>
</template>

<script setup lang="ts">
import { onMounted, ref, onBeforeUnmount } from 'vue'
import * as echarts from 'echarts'
import yunnanMap from '../assets/yunnan.json'

const mapContainer = ref<HTMLElement>()
let chart: any = null

const emit = defineEmits(['region-click'])

const option = {
  backgroundColor: 'transparent',
  series: [{
    type: 'map',
    map: 'yunnan',
    roam: true,
    zoom: 1,
    aspectScale: 1.5, 
    layoutSize: '75%', 
    center: [101, 25],
    label: {
      show: false,
    },
    itemStyle: {
      areaColor: '#F5F0E6',
      borderColor: '#8C7B6B',
      borderWidth: 1
    },
    emphasis: {
      label: {
        color: '#483D32',
        fontWeight: 'bold'
      },
      itemStyle: {
        areaColor: '#E3D7C3'
      }
    },
    select: {
      label: {
        color: '#3E2E1E',
        fontWeight: 'bold'
      },
      itemStyle: {
        areaColor: '#D2C1AD',
        borderColor: '#6B5C4D'
      }
    }
  }]
}

const handleMapClick = (params) => {
  const regionName = params.name
  emit('region-click', regionName)
  
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
    chart.on('click', handleMapClick)
  }
})

onBeforeUnmount(() => {
  if (chart) {
    chart.dispose()
    chart = null
  }
})

window.addEventListener('resize', () => {
  if (chart) chart.resize()
})
</script>

<style scoped>
.yunnan-map {
  width: 30vw;
  height: 30vh;
  margin: 0 auto;
}
</style>
