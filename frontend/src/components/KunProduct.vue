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

// 注册必要的组件
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
        title: {
      text: '核心产品结构对比',
      left: 'left',
    },
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c}%' // 鼠标悬停时显示名称和百分比
    },
    series: [
      {
        name: '产品结构',
        type: 'sunburst',
        // 控制旭日图大小的关键参数（半径范围）
        radius: ['15%', '90%'],  // 内、外半径
        // 控制旭日图位置的关键参数
        center: ['50%', '50%'],  // 水平居中，垂直偏下
        data: [
          {
            name: '昆中药',
            children: [
              {
                name: '感冒类',
                value: 45,
                itemStyle: { color: 'rgba(185, 142, 109, 1)' }
              },
              {
                name: '消化类',
                value: 30,
                itemStyle: { color: 'rgb(183, 142, 120,1)' }
              },
              {
                name: '妇科类',
                value: 15,
                itemStyle: { color: 'rgba(145, 99, 51, 1) ' }
              },
              {
                name: '其他',
                value: 10,
                itemStyle: { color: 'rgba(165, 119, 61, 1) ' }
              }
            ],
            itemStyle: {
              color: 'rgba(135, 99, 51, 1) '
            }
          },
          {
            name: '胡庆余堂',
            children: [
              {
                name: '急救药',
                value: 35,
                itemStyle: { color: 'rgba(230, 160, 109,1)' }
              },
              {
                name: '慢性病药',
                value: 40,
                itemStyle: { color: 'rgba(210, 160, 109,1)' }
              },
              {
                name: '大健康产品',
                value: 25,
                itemStyle: { color: '#fc8452' }
              }
            ],
            itemStyle: {
              color: 'rgba(245, 170, 109,1)'
            }
          }
        ],
        label: {
          show: true,
          color: '#fff',  // 统一标签颜色为灰色
          fontSize: 12,
          rotate: 'radial'
        },
        levels: [
          // 第一层：品牌名称
          {
            r0: '10%',
            r: '10%',
            label: {
              position: 'inside',
              color: '#fff'
            }
          },
          // 第二层：产品分类
          {
            r0: '15%',
            r: '50%',
            label: {
              align: 'right',
              color: 'black'
            }
          }
        ]
      }
    ]
  };

  chart.setOption(option);

  // 响应式调整
  const resizeHandler = () => chart.resize();
  window.addEventListener('resize', resizeHandler);

  // 组件卸载时清理
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
  display: flex;
  flex-direction: column;
  align-items: center;
}

/* 控制图表容器大小 */
.chart-container {
  width: 100%;
  height: 100%;
}

h3 {
  text-align: end;
  margin-bottom: 20px;
  color: #333;
}
</style>