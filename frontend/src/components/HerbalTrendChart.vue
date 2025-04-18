//中药产业趋势图 HerbalTrendChart.vue
<template>
  <div ref="chartRef" style="height: 350px"></div>
</template>

<script setup lang="ts">
import { onMounted, ref, onUnmounted } from "vue";
import * as echarts from "echarts";

const chartRef = ref<HTMLElement | null>(null);
let chartInstance: echarts.ECharts | null = null;
let timer: number | null = null;

const renderChart = () => {
  if (!chartRef.value) return;
  
  // 检查并销毁旧实例
  if (chartInstance) {
    chartInstance.dispose();
    chartInstance = null;
  }

  chartInstance = echarts.init(chartRef.value);

  const chartData = {
    years: ["2019", "2020", "2021", "2022", "2023"],
    totalValue: [388, 425, 445, 484, 535],
    totalVolume: [95, 115, 127, 135, 144],
    totalArea: [873, 901, 902, 904, 1031],
  };

  const option = {
    title: {
      text: "云南省中药产业现状",
      subtext: "中药总产值与总产量",
      left: "center",
    },
    tooltip: {
      trigger: "axis",
      backgroundColor: "rgba(0, 0, 0, 0.6)",
      borderColor: "transparent",
      formatter: (params: any) => {
        const html = params.map((item: any) => {
          return `<div class="tooltip-item">
            <span class="label">${item.marker}${item.seriesName}:</span>
            <span class="text">${item.value}</span>
          </div>`;
        });
        return `<div class="tooltip-box">${html.join("")}</div>`;
      },
    },
    legend: {
      data: ["总产值", "总产量", "总面积"],
      top: "15%",
    },
    xAxis: {
      type: "category",
      data: chartData.years,
      boundaryGap: false,
    },
    grid: {
      top: "30%",
      bottom: "8%",
    },
    yAxis: [
      {
        type: "value",
        name: "产值（亿元）",
        position: "left",
        axisLine: {
          show: false,
          lineStyle: {
            color: "gray",
          },
        },
      },
      {
        type: "value",
        name: "种植面积（万亩）",
        position: "right",
        axisLabel: {
          formatter: "{value}",
        },
        axisLine: {
          show: false,
          lineStyle: {
            color: "gray",
          },
        },
      },
    ],
    series: [
      {
        name: "总产值",
        type: "line",
        areaStyle: {
          color: "rgba(240, 226, 70, 0.32)",
        },
        data: chartData.totalValue,
        smooth: true,
        lineStyle: { color: "rgba(240, 226, 70, 0.96)" },
      },
      {
        name: "总产量",
        type: "line",
        areaStyle: {
          color: "rgba(211, 190, 106, 0.59)",
        },
        data: chartData.totalVolume,
        smooth: true,
        lineStyle: { color: "rgb(211, 190, 106)" },
      },
      {
        name: "总面积",
        type: "line",
        areaStyle: {
          color: "rgba(128, 104, 24, 0.48)",
        },
        data: chartData.totalArea,
        smooth: true,
        yAxisIndex: 1,
        lineStyle: { color: "rgba(128, 104, 24, 0.7)" },
      },
    ],
  };

  chartInstance.setOption(option);
  window.addEventListener("resize", () => chartInstance?.resize());

  // 自动轮播提示
  let currentIndex = 0;
  clearInterval(timer);
  timer = setInterval(() => {
    chartInstance?.dispatchAction({
      type: "showTip",
      seriesIndex: 0,
      dataIndex: currentIndex,
    });
    currentIndex = (currentIndex + 1) % chartData.years.length;
  }, 2000) as unknown as number;
};

onMounted(() => {
  renderChart();
});

onUnmounted(() => {
  if (timer) clearInterval(timer);
  if (chartInstance) {
    chartInstance.dispose();
    chartInstance = null;
  }
  // 清除DOM残留
  chartRef.value?.removeAttribute('_echarts_instance_');
});
</script>

<style>
.tooltip-box {
  padding: 0 6px;
}
.tooltip-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 16px;
}
.tooltip-item .label {
  color: rgba(255, 255, 255, 0.9);
}
.tooltip-item .text {
  color: white;
  font-weight: bold;
  margin-left: 24px;
}
</style>