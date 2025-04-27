<template>
  <div ref="sankeyChart" class="sankey-chart"></div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

const sankeyChart = ref<HTMLElement | null>(null)

onMounted(() => {
  if (!sankeyChart.value) return
  
  const chart = echarts.init(sankeyChart.value)
  
  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c}'
    },
    series: [{
      type: 'sankey',
      layout: 'none',
      draggable: false,
      emphasis: {
        focus: 'adjacency'
      },
      nodeWidth: 0,
      nodeGap: 8,
      layoutIterations: 0,
      data: [
        // 时间轴节点
        { name: '昆中药起源(1381)', itemStyle: { color: 'rgba(135, 99, 51, 1)' }},
        { name: '胡庆余堂创立(1874)', itemStyle: { color: 'rgba(245, 170, 109,1)' }},
        { name: '现代(2024)', itemStyle: { color: 'rgba(222, 204, 184, 1)' }},
        
        // 技艺传承节点 - 胡庆余堂
        { name: '炮制规范(47项)', itemStyle: { color: 'rgba(245, 170, 109,0.8)' }},
        { name: '非遗保护(2006)', itemStyle: { color: 'rgba(245, 170, 109,0.9)' }},
        { name: '数字化传播\n(5亿)', itemStyle: { color: 'rgba(245, 170, 109,1)' }},
        
        // 技艺传承节点 - 昆中药
        { name: '师带徒(345对)', itemStyle: { color: 'rgba(135, 99, 51, 0.8)' }},
        { name: '非遗认证(2014)', itemStyle: { color: 'rgba(135, 99, 51, 0.9)' }},
        { name: '智能化生产\n(GMP)', itemStyle: { color: 'rgba(135, 99, 51, 1)' }},
        
        // 文化理念节点
        { name: '戒欺', itemStyle: { color: 'rgba(245, 170, 109,0.7)' }},
        { name: '真不二价', itemStyle: { color: 'rgba(245, 170, 109,1)' }},
        { name: '大药厚德', itemStyle: { color: 'rgba(135, 99, 51, 0.7)' }},
        { name: '尊重融合', itemStyle: { color: 'rgba(135, 99, 51, 1)' }}
      ],
      links: [
        // 时间轴流动
        { source: '昆中药起源(1381)', target: '现代(2024)', value: 10 },
        { source: '胡庆余堂创立(1874)', target: '现代(2024)', value: 8 },
        
        // 胡庆余堂技艺传承
        { source: '胡庆余堂创立(1874)', target: '炮制规范(47项)', value: 6 },
        { source: '炮制规范(47项)', target: '非遗保护(2006)', value: 5 },
        { source: '非遗保护(2006)', target: '数字化传播(5亿)', value: 4 },
        { source: '数字化传播(5亿)', target: '现代', value: 3 },
        
        // 昆中药技艺传承
        { source: '昆中药起源(1381)', target: '师带徒(345对)', value: 7 },
        { source: '师带徒(345对)', target: '非遗认证(2014)', value: 6 },
        { source: '非遗认证(2014)', target: '智能化生产(GMP)', value: 5 },
        { source: '智能化生产(GMP)', target: '现代', value: 4 },
        
        // 文化理念流动
        { source: '胡庆余堂创立(1874)', target: '戒欺', value: 5 },
        { source: '戒欺', target: '真不二价', value: 4 },
        { source: '真不二价', target: '现代', value: 3 },
        
        { source: '昆中药起源(1381)', target: '大药厚德', value: 5 },
        { source: '大药厚德', target: '尊重融合', value: 4 },
        { source: '尊重融合', target: '现代', value: 3 }
      ],
      label: {
        color: '#333',
        fontSize: 10,
        fontWeight: 'bold'
      },
      lineStyle: {
        color: 'gradient',
        curveness: 0.4,
        opacity: 0.6
      }
    }]
  }

  chart.setOption(option)
  
  window.addEventListener('resize', () => chart.resize())
})
</script>

<style scoped>
.sankey-chart {
  width: 110%;
  height: 100%;
}
</style>