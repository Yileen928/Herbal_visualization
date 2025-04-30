<template>
  <div class="sunburst-chart">
    <div ref="chartRef" class="chart-container"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import * as echarts from 'echarts/core';
import { SunburstChart } from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent
} from 'echarts/components';
import { CanvasRenderer } from 'echarts/renderers';

echarts.use([
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  SunburstChart,
  CanvasRenderer
]);

const chartRef = ref<HTMLElement | null>(null);

onMounted(() => {
  if (!chartRef.value) return;

  const chart = echarts.init(chartRef.value);
  
  const option = {
    backgroundColor: 'transparent',
    title: {
      text: '核心产品结构对比',
      left: 'left',
      textStyle: {
        color: '#333' // 标题文字颜色
      }
    },
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c}%'
    },
    series: [{
      name: '产品结构',
      type: 'sunburst',
      radius: ['15%', '90%'],
      center: ['50%', '50%'],
      // 关键修改：禁用所有阴影效果
      itemStyle: {
        shadowBlur: 0,
        shadowColor: 'transparent'
      },
      emphasis: {
        itemStyle: {
          shadowBlur: 0,
          shadowColor: 'transparent'
        }
      },
      data: [
        {
          name: '昆中药',
          children: [
            { name: '感冒类', value: 45, itemStyle: { color: 'rgba(185, 142, 109, 1)' } },
            { name: '消化类', value: 30, itemStyle: { color: 'rgb(183, 142, 120,1)' } },
            { name: '妇科类', value: 15, itemStyle: { color: 'rgba(145, 99, 51, 1)' } },
            { name: '其他', value: 10, itemStyle: { color: 'rgba(165, 119, 61, 1)' } }
          ],
          itemStyle: { color: 'rgba(135, 99, 51, 1)' }
        },
        {
          name: '胡庆余堂',
          children: [
            { name: '急救药', value: 35, itemStyle: { color: 'rgba(230, 160, 109,1)' } },
            { name: '慢性病药', value: 40, itemStyle: { color: 'rgba(210, 160, 109,1)' } },
            { name: '大健康产品', value: 25, itemStyle: { color: '#fc8452' } }
          ],
          itemStyle: { color: 'rgba(245, 170, 109,1)' }
        }
      ],
      label: {
        show: true,
        color: '#fff',
        fontSize: 12,
        rotate: 'radial'
      },
      levels: [
        {
          r0: '10%',
          r: '10%',
          label: { position: 'inside', color: '#fff' }
        },
        {
          r0: '15%',
          r: '50%',
          label: { align: 'right', color: 'black' }
        }
      ]
    }]
  };

  chart.setOption(option);

  const resizeHandler = () => chart.resize();
  window.addEventListener('resize', resizeHandler);

  onUnmounted(() => {
    window.removeEventListener('resize', resizeHandler);
    chart.dispose();
  });
});
</script>

<style scoped>
.sunburst-chart {
  width: 100%;
  height: 100%;
  background: transparent !important;
}
.chart-container {
  width: 100%;
  height: 100%;
  background: transparent !important;
    box-shadow: 0 0  0  rgba(255, 255, 255, 0) ;
}
</style>