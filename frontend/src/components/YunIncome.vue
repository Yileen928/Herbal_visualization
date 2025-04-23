<template>
  <div class="IncomeComparison" style="width: 90%; height: 100%; margin: auto">
    <div ref="chartRef" style="width: 100%; height: 100%"></div>
    <p style="color:gray;text-align:center;margin-top:10px;">近十年两家公司工业销售收入对比</p>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from "vue";
import * as echarts from "echarts";

const chartRef = ref<HTMLElement | null>(null);
let chartInstance: echarts.ECharts | null = null;
let timer: ReturnType<typeof setInterval> | null = null;

// 滚动参数
const windowSize = 5;
let currentStart = 0;

// 数据准备
const years = [
  '2015年', '2016年', '2017年', '2018年',
  '2019年', '2020年', '2021年', '2022年',
  '2023年', '2024年'
];

const beiTongrenTangData = [
  780000, 875000, 930000, 970000,
  1025000, 1075000, 1107900, 1112300,
  1191200, 1191227
];

const yunnanBaiyaoData = [
  918040.09, 908000.00, 996202.47, 1072713.71,
  1102400.00, 1171643.38, 1270283.14, 1277200.00,
  1374055.71, 1447500.00
];

// 初始化图表
function initChart() {
  if (!chartRef.value) return;

  // 销毁旧实例
  if (chartInstance) {
    chartInstance.dispose();
  }

  chartInstance = echarts.init(chartRef.value);

  // 初始显示数据
  updateChart(0);
}

// 更新图表显示区域
function updateChart(startIdx: number) {
  if (!chartInstance) return;

  const endIdx = Math.min(startIdx + windowSize, years.length);
  const displayYears = years.slice(startIdx, endIdx);
  const displayBeiData = beiTongrenTangData.slice(startIdx, endIdx);
  const displayYunData = yunnanBaiyaoData.slice(startIdx, endIdx);

  const option: echarts.EChartsOption = {
    backgroundColor: 'transparent',
    grid: {
      left: 60,
      right: 30,
      bottom: 40,
      top: 30,
    },
    tooltip: {
      trigger: 'axis',
      formatter: (params: any) => {
        if (!Array.isArray(params)) return '';
        const year = params[0]?.name;
        if (!year) return '';
        
        const tips = params.map((item: any) => `
          <div style="display:flex;align-items:center;margin:3px 0">
            <span style="display:inline-block;width:10px;height:10px;border-radius:50%;background:${item.color};margin-right:5px"></span>
            ${item.seriesName}: ${Number(item.value).toLocaleString()}万元
          </div>
        `).join('');
        
        return `<div style="font-weight:bold;margin-bottom:5px">${year}</div>${tips}`;
      }
    },
    legend: {
      data: ["北京同仁堂", "云南白药"],
      top: 5,
      textStyle: { color: '#666' },
    },
    xAxis: {
      type: "category",
      data: displayYears,
      axisLabel: { rotate: 30, margin: 15 },
      axisLine: { lineStyle: { color: "rgba(0,0,0,0.2)" } },
      axisTick: { show: false }
    },
    yAxis: {
      type: "value",
      name: "销售收入（万元）",
      axisLabel: {
        formatter: (value: number) => value.toLocaleString() // 直接显示数值
      },
      splitLine: { lineStyle: { type: 'dashed' } }
    },
    series: [
      {
        name: "北京同仁堂",
        type: "line",
        smooth: true,
        data: displayBeiData,
        areaStyle: { color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: "rgba(226, 160, 109, 0.3)" },
          { offset: 1, color: "rgba(226, 160, 109, 0.1)" },
        ])},
        lineStyle: { width: 2.5, color: "#E2A06D" }
      },
      {
        name: "云南白药",
        type: "line",
        smooth: true,
        data: displayYunData,
        areaStyle: { color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: "rgba(213, 195, 151, 0.3)" },
          { offset: 1, color: "rgba(213, 195, 151, 0.1)" },
        ])},
        lineStyle: { width: 2.5, color: "#D5C397" }
      }
    ]
  };

  chartInstance.setOption(option);
}

// 启动自动滚动
function startScrolling() {
  if (timer) clearInterval(timer);
  if (years.length <= windowSize) return;

  timer = setInterval(() => {
    currentStart = (currentStart + 1) % (years.length - windowSize + 1);
    updateChart(currentStart);
  }, 2000);
}

// 生命周期
onMounted(() => {
  initChart();
  startScrolling();

  const resizeObserver = new ResizeObserver(() => {
    chartInstance?.resize();
  });

  chartRef.value && resizeObserver.observe(chartRef.value);
  onBeforeUnmount(() => resizeObserver.disconnect());
});

onBeforeUnmount(() => {
  if (timer) clearInterval(timer);
  chartInstance?.dispose();
});
</script>

<style scoped>
.IncomeComparison {
  background-color: transparent;
}
</style>