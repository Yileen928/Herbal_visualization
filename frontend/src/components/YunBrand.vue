<template>
  <div class="chart-container" ref="chartContainer"></div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';

const chartContainer = ref<HTMLDivElement | null>(null);

const productData = [
  { name: '气雾剂', year: 2020, sales: 12.5, marketShare: 89.2 },
  { name: '气雾剂', year: 2021, sales: 13.8, marketShare: 90.1 },
  { name: '气雾剂', year: 2022, sales: 15, marketShare: 90.5 },
  { name: '气雾剂', year: 2023, sales: 17.34, marketShare: 91 },
  { name: '气雾剂', year: 2024, sales: 21.0, marketShare: 91.8 },
  
  { name: '创可贴', year: 2020, sales: 2.8, marketShare: 63.7 },
  { name: '创可贴', year: 2021, sales: 3.2, marketShare: 65.1 },
  { name: '创可贴', year: 2022, sales: 3.12, marketShare: 66 },
  { name: '创可贴', year: 2023, sales: 4, marketShare: 66.5 },
  { name: '创可贴', year: 2024, sales: 5.8, marketShare: 72.4 },
  
  { name: '散剂', year: 2020, sales: 9.3, marketShare: 13.2 },
  { name: '散剂', year: 2021, sales: 10.1, marketShare: 13.8 },
  { name: '散剂', year: 2022, sales: 11.5, marketShare: 14.2 },
  { name: '散剂', year: 2023, sales: 12.8, marketShare: 14.5 },
  { name: '散剂', year: 2024, sales: 14.9, marketShare: 14.9 },

  { name: '牙膏', year: 2020, sales: 45, marketShare: 20.9 },
  { name: '牙膏', year: 2021, sales: 52.3, marketShare: 22.1 },
  { name: '牙膏', year: 2022, sales: 60.3, marketShare: 24.4 },
  { name: '牙膏', year: 2023, sales: 61, marketShare: 24.6 },
  { name: '牙膏', year: 2024, sales: 61.04, marketShare: 25.0 },
];

const colorMap = {
  '气雾剂': '#ff7f0e',
  '创可贴': '#1f77b4',
  '散剂': '#2ca02c',
  '牙膏': '#d62728',
};

onMounted(() => {
  console.log(chartContainer.value); // 确保 chartContainer 已正确绑定到 DOM 元素
  if (chartContainer.value) {
    const myChart = echarts.init(chartContainer.value);
    
    const option = {
      title: {
        text: '云南白药产品销售分析气泡图',
        left: 'center',
      },
      tooltip: {
        formatter: (params) => {
          console.log(params.data); // 调试：检查数据格式
          return `${params.data[0]}<br/>年份: ${params.data[1]}<br/>销售额: ${params.data[2] !== null ? params.data[2] + ' 亿元' : '未披露'}<br/>市场份额: ${params.data[3]}%`;
        },
      },
      xAxis: {
        type: 'value',
        name: '年份',
        min: 2020, // 根据数据调整范围
        max: 2024, // 根据数据调整范围
      },
      yAxis: {
        type: 'value',
        name: '销售额（亿元）',
        min: 0,
        max: 70,  // 根据数据调整范围
      },
      series: [ {
        name: '市场份额',
        type: 'scatter',
        symbolSize: (value) => {
          return value[3] * 10; // 调整气泡大小的因子
        },
        itemStyle: {
          color: (params) => colorMap[params.data[0]],
        },
        data: productData
          .filter(item => item.sales !== null && item.marketShare !== null) // 确保数据完整
          .map(item => [item.name, item.year, item.sales, item.marketShare]),
        emphasis: {
          focus: 'series',
        },
      } ],
    };

    myChart.setOption(option);
  }
});
</script>

<style scoped>
.chart-container {
  width: 100%;
  height: 350px;
  background-color: rgba(75, 192, 192, 0); /* 设置背景颜色为透明 */
}
</style>