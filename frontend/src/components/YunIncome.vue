<template>
  <div class="IncomeComparison">
    <div class="Incomtitle">近十年两家公司工业销售收入对比</div>
    <div ref="chartRef" style="width: 100%; height: 100%"></div>
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
      right: 10,
      bottom: 80,
      top: 30,
    },
    tooltip: {
      trigger: 'axis',
      confine: true, // 确保tooltip不超出容器
      appendToBody: true, // 将tooltip添加到body中，避免被其他元素遮挡
      position: function(point, params, dom, rect, size) {
        // 自定义tooltip位置，确保在视口内
        return [point[0] + 10, point[1] + 10];
      },
      formatter: (params: any) => {
        if (!Array.isArray(params)) return '';
        const year = params[0]?.name;
        if (!year) return '';

        const tips = params.map((item: any) => `
          <div style="display:flex;align-items:center;margin:3px 0">
            <span style="display:inline-block;width:8px;height:12px;border-radius:50%;background:${item.color};margin-right:6px"></span>
            ${item.seriesName}: ${Number(item.value).toLocaleString()}万元
          </div>
        `).join('');

        return `<div style="font-weight:bold;margin-bottom:5px">${year}</div>${tips}`;
      },
      z: 100 // 设置高z-index确保tooltip显示在最上层
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
  width: 100%;
  height: 100%;
  margin: auto;
  position: relative;
  z-index: 50; /* 确保图表容器在地图之上 */
}
.Incomtitle {
  font-size: 18px;
  font-weight: bold;
  color: #666;
  margin-bottom: 10px;
  position: relative;
  z-index: 51;
}

/* 确保echarts的tooltip显示在最顶层 */
:deep(.echarts-tooltip),
:deep(div[class^="echarts-tooltip"]) {
  pointer-events: auto !important;
  z-index: 99999 !important;
}
</style>