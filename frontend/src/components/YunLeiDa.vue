<template>
  <div class="doctor-skills-wordcloud">
    <div ref="chartRef" class="chart-container"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import * as echarts from 'echarts';

const chartRef = ref<HTMLElement | null>(null);
let chart: echarts.ECharts | null = null;

const initChart = () => {
  if (!chartRef.value) return;
  chart = echarts.init(chartRef.value);

  const option = {
    title: {
      text: '云南白药集团产品覆盖度',
      left: 'left',  
      textStyle: {
        color: '#333',
        fontSize: 14, // 调整字体大小
      },
    },
    tooltip: true, // 鼠标悬停提示
    legend: {
      data: ['产品覆盖度'],
      orient: 'vertical',
      left: 'right',
      textStyle: {
        fontSize: 12, // 调整字体大小
      },
    },
    radar: {
      name: {
        textStyle: {
          color: '#333',
          fontSize: 12, // 调整指示器文字大小
        },
      },
      indicator: [
        { name: '药品种类', max: 600 },
        { name: '健康品种类', max: 10 },
        { name: '中药资源种类', max: 15 },
        { name: '医药物流覆盖', max: 20 },
        { name: '市场覆盖范围', max: 10 },
      ],
      radius: '75%', // 调整雷达图的大小
    },
    series: [
      {
        name: '产品覆盖度',
        type: 'radar',
        data: [
          {
            value: [567, 5, 10, 16, 10],
            name: '产品覆盖度',
            areaStyle: {},
            symbol: 'circle',
            symbolSize: 6,
            lineStyle: {
              color: '#3890ff',
            },
            areaStyle: {
              color: 'rgba(56, 144, 255, 0.3)',
            },
            label: {
              show: true, // 显示标签
              formatter: function (params: any) {
                return params.value; // 显示数值
              },
              position: 'outside', // 标签位置
              color: '#333', // 标签字体颜色
              fontSize: 10, // 标签字体大小
            },
          },
        ],
      },
    ],
  };

  chart.setOption(option);
};

const handleResize = () => {
  chart?.resize();
};

onMounted(() => {
  initChart();
  window.addEventListener('resize', handleResize);
});

onUnmounted(() => {
  chart?.dispose();
  window.removeEventListener('resize', handleResize);
});
</script>

<style scoped>
.chart-container {
  margin-left: -5%;
  width: 100%;
  height: 300px;
  background-color: rgba(75, 192, 192, 0); /* 设置背景颜色为透明 */
}
</style>