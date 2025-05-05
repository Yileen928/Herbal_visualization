<template>
  <div class="doctor-pharmacist-trend">
    <!-- 图表容器-->
    <div ref="chartContainer" style="width: 100%; height: 100%"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue'
import * as echarts from 'echarts'
import axios from 'axios'

// 图表DOM引用
const chartContainer = ref<HTMLElement | null>(null)
// ECharts实例
let chartInstance: echarts.ECharts | null = null

// 静态数据备用
const staticData = [
  { year: '2013', physician: 39.8, pharmacist: 11 },
  { year: '2014', physician: 41.9, pharmacist: 11.2 },
  { year: '2015', physician: 45.2, pharmacist: 11.4 },
  { year: '2016', physician: 48.2, pharmacist: 11.7 },
  { year: '2017', physician: 52.7, pharmacist: 12 },
  { year: '2018', physician: 57.5, pharmacist: 12.4 },
  { year: '2019', physician: 62.5, pharmacist: 12.7 },
  { year: '2020', physician: 68.3, pharmacist: 13.1 },
  { year: '2021', physician: 73.2, pharmacist: 13.7 },
  { year: '2022', physician: 76.4, pharmacist: 13.9 },
  { year: '2023', physician: 86.8, pharmacist: 16.1 }
]

/**
 * 从API获取数据
 */
const fetchApiData = async () => {
  try {
    const response = await axios.get('http://106.55.169.134:10010/yearOcc/all', {
      timeout: 5000 // 5秒超时
    })
    return response.data.map((item: any) => ({
      year: item.occupationYear,
      physician: parseFloat(item.physicianNameTotal),
      pharmacist: parseFloat(item.pharmacistsNameTotal)
    }))
  } catch (error) {
    console.error('API请求失败，使用静态数据:', error)
    return staticData
  }
}

/**
 * 初始化图表
 */
const initChart = async () => {
  if (!chartContainer.value) return

  // 获取数据（优先API，失败自动用静态数据）
  const data = await fetchApiData()

  // 提取数据用于图表
  const years = data.map((item) => item.year)
  const physicians = data.map((item) => item.physician)
  const pharmacists = data.map((item) => item.pharmacist)

  // 初始化ECharts实例
  chartInstance = echarts.init(chartContainer.value)

  // 图表配置（移除了title配置）
  const option = {
    tooltip: {
      trigger: 'axis',
      formatter: (params: any) => {
        const year = params[0].axisValue
        const physician = params[0].data
        const pharmacist = params[1].data
        return `
          <div style="font-weight:bold">${year}年</div>
          <div>执业医师: <span style="color:rgba(185, 142, 109, 1)">${physician}万人</span></div>
          <div>药师: <span style="color:rgba(226, 160, 109,1)">${pharmacist}万人</span></div>
        `
      }
    },
    legend: {
      data: ['执业医师', '药师'],
      textStyle: {
        color: '#666'
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '15%',
      containLabel: true
    },
    toolbox: {
      feature: {
        dataView: {
          title: '查看数据',
          readOnly: true,
          lang: ['数据视图', '关闭', '刷新'],
          buttonColor: '#666'
        },
        magicType: {
          title: {
            line: '切换为折线图',
            bar: '切换为柱状图'
          },
          type: ['line', 'bar'],
          iconStyle: {
            color: '#666'
          }
        },
        restore: {
          title: '还原',
          iconStyle: {
            color: '#666'
          }
        }
      },
      iconStyle: {
        borderColor: '#666'
      }
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: years,
      axisLabel: {
        rotate: 30,
        color: '#666'
      },
      axisLine: {
        lineStyle: {
          color: '#ddd'
        }
      }
    },
    yAxis: {
      type: 'value',
      name: '数量(万人)',
      nameTextStyle: {
        color: '#666'
      },
      axisLine: {
        show: true,
        lineStyle: {
          color: '#ddd'
        }
      },
      splitLine: {
        lineStyle: {
          color: '#f0f0f0'
        }
      }
    },
    series: [
      {
        name: '执业医师',
        type: 'line',
        smooth: true,
        symbol: 'circle',
        symbolSize: 8,
        data: physicians,
        itemStyle: {
          color: 'rgba(185, 142, 109, 1)'
        },
        lineStyle: {
          width: 3
        },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(185, 142, 109, 0.3)' },
            { offset: 1, color: 'rgba(185, 142, 109, 0.1)' }
          ])
        }
      },
      {
        name: '药师',
        type: 'line',
        smooth: true,
        symbol: 'circle',
        symbolSize: 8,
        data: pharmacists,
        itemStyle: {
          color: 'rgba(226, 160, 109,1)'
        },
        lineStyle: {
          width: 3
        },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(226, 160, 109, 0.3)' },
            { offset: 1, color: 'rgba(226, 160, 109, 0.1)' }
          ])
        }
      }
    ]
  }

  // 应用配置
  chartInstance.setOption(option)

  // 窗口大小改变时重新调整图表大小
  const resizeHandler = () => chartInstance?.resize()
  window.addEventListener('resize', resizeHandler)

  // 组件卸载时清理
  onBeforeUnmount(() => {
    window.removeEventListener('resize', resizeHandler)
    if (chartInstance) {
      chartInstance.dispose()
    }
  })
}

// 组件挂载时初始化图表
onMounted(() => {
  initChart()
})
</script>

<style scoped>
.doctor-pharmacist-trend {
  width: 100%;
  height: 100%;
}
</style>