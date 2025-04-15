<template>
  <div class="yunnan-map" ref="mapContainer">
    <!-- 路线切换按钮列表 -->
    <div class="route-list">
      <button class="route-button" @click="switchRoute('route1')">普洱-红河线</button>
      <button class="route-button" @click="switchRoute('route2')">大理-丽江线</button>
      <button class="route-button" @click="switchRoute('route3')">丽江-迪庆线</button>
    </div>
  </div>
</template>

<script setup lang="ts">
// 导入必要的依赖
import { onMounted, ref, onBeforeUnmount, watch } from 'vue'
import * as echarts from 'echarts'
import 'echarts-gl'
import yunnanMap from '../assets/yunnan.json'

// 地图容器的引用
const mapContainer = ref<HTMLElement>()
// echarts实例
let chart: any = null

// 定义路线点坐标数据
const routePoints = [
  { name: '普洱', coords: [100.97, 22.83] },
  { name: '大理', coords: [100.23, 25.60] },
  { name: '丽江', coords: [100.25, 26.86] },
  { name: '迪庆', coords: [99.70, 27.83] },
  { name: '保山', coords: [99.17, 25.12] },
  { name: '红河', coords: [103.38, 23.37] }
]

// 定义多条路线数据
const allRoutes = {
  route1: [
    { name: '普洱', coords: [100.97, 22.83] },
    { name: '保山', coords: [99.17, 25.12] },
    { name: '红河', coords: [103.38, 23.37] }
  ],
  route2: [
    { name: '大理', coords: [100.23, 25.60] },
    { name: '丽江', coords: [100.25, 26.86] }
  ],
  route3: [
    { name: '丽江', coords: [100.25, 26.86] },
    { name: '迪庆', coords: [99.70, 27.83] }
  ]
}

// 当前选中的路线
const currentRoute = ref('route1')

// 切换路线的方法
const switchRoute = (routeName: string) => {
  currentRoute.value = routeName
  updateMap()  // 更新地图
}

// 更新地图的方法
const updateMap = () => {
  if (!chart) return

  const lines: { coords: number[][] }[] = []

  // 生成所有路线数据
  Object.values(allRoutes).forEach(route => {
    for (let i = 0; i < route.length - 1; i++) {
      lines.push({
        coords: [
          [...route[i].coords, 0.2],
          [...route[i + 1].coords, 0.2]
        ]
      })
    }
  })

  const option = {
    backgroundColor: 'transparent',  // 透明背景
    geo3D: {
      center: [101.5, 25.5],   // 调整中心点位置
      zoom: 1.8, 
      map: 'yunnan',
      show: true,
      roam: false,         // 开启鼠标缩放和平移漫游
      regionHeight: 0.8,  // 地图区域的高度
      boxHeight: 4,       // 3D地图的整体高度
      itemStyle: {
        color: 'transparent',       // 地图区域颜色为透明
        borderColor: '#764F35',  // 边界线颜色
        borderWidth: 1.2,
        opacity: 0.8
      },
      emphasis: {
        itemStyle: {
          color: '#93AC8C',
          areaColor: '#A0C65E',
          opacity: 1
        }
      },
      viewControl: {
        distance: 60,     // 视角距离主体的距离
        alpha: 15,        // 视角绕 x 轴的角度
        beta: -30,        // 视角绕 y 轴的角度
      }
    },
    series: [{
      type: 'lines3D',
      coordinateSystem: 'geo3D',
      effect: {
        show: true,
        period: 1,
        constantSpeed: 30,
        trailWidth: 4,
        trailLength: 0.8,
        trailColor: '#748D60',
        trailOpacity: 0.8,
        symbol: 'circle',
        symbolSize: 4
      },
      lineStyle: {
        width: 1,
        color: '#F0E6AA',
        opacity: 0,
        curveness: 0.5,
        type: 'dotted',
        dashLength: 0.1,
        dashPattern: [50, 50]
      },
      data: lines
    },
    {
      type: 'scatter3D',
      coordinateSystem: 'geo3D',
      symbol: 'circle',
      symbolSize: 10,
      shading: 'color',
      itemStyle: {
        color: '#A73E28',
        opacity: 1,
        borderWidth: 1,
        borderColor: '#fff'
      },
      data: routePoints.map(point => ({
        name: point.name,
        value: [...point.coords, 0],
        itemStyle: {
          color: '#A73E28'
        }
      }))
    }]
  }

  chart.setOption(option)
}

// 删除不需要的代码：
// - 删除 currentRoute 相关代码
// - 删除 switchRoute 方法
// - 删除 props 和 watch
// - 删除模板中的按钮部分
onMounted(() => {
  if (mapContainer.value) {
    if (chart) chart.dispose()

    chart = echarts.init(mapContainer.value)
    echarts.registerMap('yunnan', yunnanMap as any)
    updateMap() // 初始化时调用更新地图
  }
})

onBeforeUnmount(() => {
  if (chart) {
    chart.dispose()
    chart = null
  }
})

// 添加 props 定义
const props = defineProps<{
  currentRoute: string
}>()

// 监听路线变化
watch(() => props.currentRoute, (newRoute) => {
  if (chart) {
    updateMap()
  }
}, { immediate: true })
</script>

<style scoped>
.route-button {
  width: 120px;
  height: 30px;
  margin: 5px;
  background-color: #A73E28;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.route-button:hover,
.route-button.active {
  background-color: #8B3121;
}

.route-list {
  position: absolute;
  top: 20px;
  left: 20px;
  z-index: 100;
  display: flex;
  flex-direction: column;
  gap: 10px;
  background-color: rgba(255, 255, 255, 0.1);
  padding: 10px;
  border-radius: 8px;
}

.yunnan-map {
  position: relative;
  width: 100%;
  height: 70vh;
  border-radius: 8px;
  margin-left: auto;
  border: 2pt solid #764F35;
}
</style>
