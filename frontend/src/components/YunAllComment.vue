<template>
  <div
    class="YunDingComment"
    ref="containerRef"
    style="width: 100%; height: 90%"
  >
    <div ref="chartContainer" style="width: 100%; height: 100%"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import * as echarts from "echarts";

// 容器元素引用
const containerRef = ref<HTMLElement | null>(null);
const chartContainer = ref<HTMLElement | null>(null);
const chartInstance = ref<echarts.ECharts | null>(null);

// 产品列表
const productNames = ["云南白药漱口水", "云南白药膏药", "云南白药蒲地蓝消炎片"];

// 存储每个产品每年总评论数
const productYearData: Record<string, Record<string, number>> = {};

// 存储所有年份（字符串）
const allYears = ref<string[]>([]);

// 当前显示的年份起点和长度
let startIndex = 0;
let displayCount = 5;
let autoTimer: any = null;

// 请求单个产品数据
const fetchProductData = async (productName: string) => {
  const res = await fetch(
    `http://106.55.169.134:10010/product/countDate?productName=${encodeURIComponent(
      productName
    )}`
  );
  const data = await res.json();
  return { productName, data };
};

// 请求所有产品数据，整理为每年每个产品的总数
async function fetchAll() {
  const promises = productNames.map((name) => fetchProductData(name));
  const results = await Promise.all(promises);

  // 初始化存储结构
  productNames.forEach((name) => {
    productYearData[name] = {};
  });

  // 填充每个产品每年总数
  results.forEach(({ productName, data }) => {
    data.forEach((item) => {
      const yearStr = `${item.commentsYear}`;
      if (!productYearData[productName][yearStr]) {
        productYearData[productName][yearStr] = 0;
      }
      productYearData[productName][yearStr] += item.commentCount;
    });
  });

  // 获取所有年份（合并所有产品的年份）
  const yearSet = new Set<string>();
  Object.values(productYearData).forEach((years) => {
    Object.keys(years).forEach((y) => yearSet.add(y));
  });
  const yearsArray = Array.from(yearSet).sort((a, b) => Number(a) - Number(b));
  allYears.value = yearsArray;

  setupResizeObserver();
  startAutoPlay();
  updateChart();
}

// 自适应宽度，调整显示数量
function setupResizeObserver() {
  if (containerRef.value) {
    new ResizeObserver(() => {
      const width = containerRef.value!.clientWidth;
      const barWidth = 50;
      displayCount = Math.max(1, Math.floor(width / barWidth));
      if (startIndex + displayCount > allYears.value.length) {
        startIndex = Math.max(0, allYears.value.length - displayCount);
      }
      updateChart();
    }).observe(containerRef.value);
  }
}

// 获取当前年份段
function getCurrentYears() {
  return allYears.value.slice(startIndex, startIndex + displayCount);
}

// 构造系列
function constructSeries(currentYears: string[]) {
  return productNames.map((productName, idx) => {
    const data = currentYears.map((year) => {
      return productYearData[productName][year] || 0;
    });
    return {
      name: productName,
      type: "bar",
      data,
      itemStyle: { color: getColorForProduct(productName) },
    };
  });
}

// 颜色配置
function getColorForProduct(product: string): string {
  const colors = ["#5470C6", "#91CC75", "#EE6666", "#FAC858"];
  const index = productNames.indexOf(product);
  return colors[index % colors.length];
}

// 更新图表
function updateChart() {
  if (!chartInstance.value || allYears.value.length === 0) return;
  const currentYears = getCurrentYears();
  const series = constructSeries(currentYears);
  const option = {
    title: {
      text: "云南白药产品年度评论总数",
      left: "center",
      textStyle: {
        fontSize: 16,
        color: "gray",
        fontWeight: "bold",
      },
    },
    tooltip: { trigger: "axis" },
    legend: { bottom: 0, data: productNames },
    xAxis: {
      type: "category",
      data: currentYears,
      axisLine: { lineStyle: { color: "gray" } },
      axisLabel: { color: "gray", rotate: 30 },
    },
    yAxis: {
      type: "value",
      axisLine: { lineStyle: { color: "gray" } },
      axisLabel: { color: "gray" },
    },
    series,
  };
  chartInstance.value.setOption(option, true);
}

// 自动轮播
function startAutoPlay() {
  if (autoTimer) clearInterval(autoTimer);
  autoTimer = setInterval(() => {
    if (startIndex + displayCount >= allYears.value.length) {
      startIndex = 0;
    } else {
      startIndex++;
    }
    updateChart();
  }, 2000);
}

onMounted(async () => {
  await fetchAll();
  if (chartContainer.value) {
    chartInstance.value = echarts.init(chartContainer.value);
    updateChart();
  }
});
</script>

<style scoped>
.YunDingComment {
  width: 100%;
  height: 90%;
  border-radius: 8px;
  margin-bottom: 5%;
}
</style>
