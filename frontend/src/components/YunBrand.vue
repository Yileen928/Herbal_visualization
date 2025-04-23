<template>
  <div
    class="chart-container"
    ref="chartContainer"
    style="width: 100%; height: 100%"
  ></div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from "vue";
import * as echarts from "echarts";

const chartContainer = ref<HTMLElement | null>(null);
let chartInstance: echarts.ECharts | null = null;

// 云南白药数据
const yunnanBaiyaoData = [
  { name: "气雾剂", year: 2020, sales: 12.5, marketShare: 89.2 },
  { name: "气雾剂", year: 2021, sales: 13.8, marketShare: 90.1 },
  { name: "气雾剂", year: 2022, sales: 15, marketShare: 90.5 },
  { name: "气雾剂", year: 2023, sales: 17.34, marketShare: 91 },
  { name: "气雾剂", year: 2024, sales: 21.0, marketShare: 91.8 },

  { name: "创可贴", year: 2020, sales: 2.8, marketShare: 63.7 },
  { name: "创可贴", year: 2021, sales: 3.2, marketShare: 65.1 },
  { name: "创可贴", year: 2022, sales: 3.12, marketShare: 66 },
  { name: "创可贴", year: 2023, sales: 4, marketShare: 66.5 },
  { name: "创可贴", year: 2024, sales: 5.8, marketShare: 72.4 },

  { name: "散剂", year: 2020, sales: 9.3, marketShare: 13.2 },
  { name: "散剂", year: 2021, sales: 10.1, marketShare: 13.8 },
  { name: "散剂", year: 2022, sales: 11.5, marketShare: 14.2 },
  { name: "散剂", year: 2023, sales: 12.8, marketShare: 14.5 },
  { name: "散剂", year: 2024, sales: 14.9, marketShare: 14.9 },

  { name: "牙膏", year: 2020, sales: 45, marketShare: 20.9 },
  { name: "牙膏", year: 2021, sales: 52.3, marketShare: 22.1 },
  { name: "牙膏", year: 2022, sales: 60.3, marketShare: 24.4 },
  { name: "牙膏", year: 2023, sales: 61, marketShare: 24.6 },
  { name: "牙膏", year: 2024, sales: 61.04, marketShare: 25.0 },
];

// 北京同仁堂数据
const beijingTongrentangData = [
  { name: "安宫牛黄丸", year: 2020, sales: 35, marketShare: 68 },
  { name: "安宫牛黄丸", year: 2021, sales: 42, marketShare: 67 },
  { name: "安宫牛黄丸", year: 2022, sales: 45, marketShare: 66 },
  { name: "安宫牛黄丸", year: 2023, sales: 49, marketShare: 65 },
  { name: "安宫牛黄丸", year: 2024, sales: 51.46, marketShare: 65 },

  { name: "六味地黄丸", year: 2020, sales: 12, marketShare: 28 },
  { name: "六味地黄丸", year: 2021, sales: 14, marketShare: 27 },
  { name: "六味地黄丸", year: 2022, sales: 15, marketShare: 26 },
  { name: "六味地黄丸", year: 2023, sales: 16, marketShare: 25 },
  { name: "六味地黄丸", year: 2024, sales: 17.34, marketShare: 24 },

  { name: "同仁牛黄清心丸", year: 2020, sales: 6, marketShare: 43 },
  { name: "同仁牛黄清心丸", year: 2021, sales: 7.5, marketShare: 42 },
  { name: "同仁牛黄清心丸", year: 2022, sales: 8.5, marketShare: 41 },
  { name: "同仁牛黄清心丸", year: 2023, sales: 9, marketShare: 40 },
  { name: "同仁牛黄清心丸", year: 2024, sales: 9.5, marketShare: 40 },

  { name: "国公酒", year: 2020, sales: 2.5, marketShare: 52 },
  { name: "国公酒", year: 2021, sales: 3, marketShare: 51 },
  { name: "国公酒", year: 2022, sales: 3.2, marketShare: 50 },
  { name: "国公酒", year: 2023, sales: 3.5, marketShare: 50 },
  { name: "国公酒", year: 2024, sales: 3.8, marketShare: 50 },
];

// 颜色映射
const colorMap: Record<string, string> = {
  云南白药: "rgba(73, 127, 192, 0.8)",
  北京同仁堂: "rgba(192, 127, 73, 0.8)",
};

// 形状映射
const symbolMap: Record<string, string> = {
  云南白药: "circle",
  北京同仁堂: "rect",
};

function initChart() {
  if (!chartContainer.value) return;

  // 销毁之前的实例
  if (chartInstance) {
    chartInstance.dispose();
  }

  chartInstance = echarts.init(chartContainer.value);

  // 处理数据
  const seriesData = [
    ...yunnanBaiyaoData.map((item) => ({
      name: item.name,
      value: [item.year, item.sales, item.marketShare],
      itemStyle: {
        color: colorMap["云南白药"],
      },
      symbol: symbolMap["云南白药"],
    })),
    ...beijingTongrentangData.map((item) => ({
      name: item.name,
      value: [item.year, item.sales, item.marketShare],
      itemStyle: {
        color: colorMap["北京同仁堂"],
      },
      symbol: symbolMap["北京同仁堂"],
    })),
  ];

  const option = {
    title: {
      text: "核心产品销售额与份额对比气泡图",
      left: "left",
      textStyle: {
        fontSize: 16,
        color: "#000",
        fontWeight: "bold",
      },
    },
    tooltip: {
      trigger: "item",
      formatter: (params: any) => {
        const data = params.data;
        return `
          <div style="font-weight:bold">${data.name}</div>
          <div>年份: ${data.value[0]}</div>
          <div>销售额: ${data.value[1]}亿元</div>
          <div>市场份额: ${data.value[2]}%</div>
        `;
      },
    },
    grid: {
      left: "5%",
      right: "7%",
      bottom: "3%",
      top: "20%",
      containLabel: true,
    },
    xAxis: {
      type: "value",
      name: "年份",
      nameLocation: "middle",
      nameGap: 30,
      min: 2019,
      max: 2024.5,
      axisLabel: {
        formatter: (value: number) => value.toFixed(0),
        color: "gray",
      },
      axisLine: {
        lineStyle: {
          color: "gray",
        },
      },
      axisTick: {
        lineStyle: {
          color: "rgba(222, 204, 184, 0.5)",
        },
      },
      splitLine: {
        lineStyle: {
          color: "rgba(222, 204, 184, 0.5)",
        },
      },
    },
    yAxis: {
      type: "value",
      name: "销售额（亿元）",
      min: 0,
      max: 70,
      interval: 5, // Y 轴刻度间隔为 5
      axisLabel: {
        color: "#000",
      },
      axisLine: {
        lineStyle: {
          color: "gray",
        },
      },
      axisTick: {
        lineStyle: {
          color: "rgba(222, 204, 184, 0.5)",
        },
      },
      splitLine: {
        lineStyle: {
          color: "rgba(222, 204, 184, 0.5)",
        },
      },
    },
    visualMap: {
      show: false,
      dimension: 2,
      min: 10,
      max: 100,
      inRange: {
        symbolSize: [10, 40],
      },
    },
    series: [
      {
        name: "市场份额",
        type: "scatter",
        symbolSize: (data: any) => {
          return Math.sqrt(data[2]) * 5;
        },
        data: seriesData,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowColor: "rgba(0, 0, 0, 0.5)",
          },
        },
      },
    ],
  };

  chartInstance.setOption(option);

  // 响应式调整
  const resizeObserver = new ResizeObserver(() => {
    chartInstance?.resize();
  });
  resizeObserver.observe(chartContainer.value);

  onBeforeUnmount(() => {
    resizeObserver.disconnect();
    if (chartInstance) {
      chartInstance.dispose();
    }
  });
}

onMounted(() => {
  initChart();
});
</script>

<style scoped>
.chart-container {
  width: 100%;
  height: 100%;
  background-color: transparent;
}
</style>
