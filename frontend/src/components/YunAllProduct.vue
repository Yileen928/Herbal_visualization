<template>
  <div class="YunAllProductd" style="width: 90%; height: 100%; margin: auto">
    <div ref="chartRef" style="width: 100%; height: 100%"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from "vue";
import * as echarts from "echarts";

const chartRef = ref<HTMLElement | null>(null);
const chartInstance = ref<echarts.ECharts | null>(null);

let dataList: { productsName: string; commentsSum: number }[] = [];

// 定义滚动参数
const windowSize = 4; // 每次显示4个
let currentStart = 0;
let timer: ReturnType<typeof setInterval> | null = null;

// 请求评论数据
async function fetchProductComments() {
  try {
    const response = await fetch("http://106.55.169.134:10010/comments/all");
    if (!response.ok) {
      console.error("接口请求失败", response.statusText);
      return [];
    }
    const data = await response.json();
    // 过滤无效数据
    const validData = data.filter(
      (item: any) => item.productsName && !isNaN(item.commentsSum)
    );
    // 排序：评论数由低到高
    validData.sort((a: any, b: any) => a.commentsSum - b.commentsSum);
    return validData;
  } catch (err) {
    console.error("请求出错:", err);
    return [];
  }
}

// 初始化图表，定义完整的option（只做一次）
function initChart() {
  if (!chartRef.value) return;

  // 销毁之前的实例
  if (chartInstance.value) {
    chartInstance.value.dispose();
  }

  chartInstance.value = echarts.init(chartRef.value);

  // 确保有足够的数据
  if (dataList.length === 0) return;

  const categories = dataList.map((item) => item.productsName);
  const data = dataList.map((item) => item.commentsSum);

  const option = {
    grid: {
      left: 70,
      right: 10,
      bottom: 70,
      top: 50,
    },
    title: {
      text: "云南白药各产品评论总数",
      left: "center",
      textStyle: {
        fontSize: 16,
        color: "gray",
        fontWeight: "bold",
      },
    },
    tooltip: {
      trigger: "axis",
      formatter: (params: any) => {
        const data = params[0];
        return `${data.name}<br/>评论数: ${data.value}`;
      },
    },
    xAxis: {
      type: "category",
      data: categories.slice(0, Math.min(windowSize, categories.length)),
      axisLine: { lineStyle: { color: "gray" } },
      axisLabel: {
        color: "gray",
        rotate: 30,
        interval: 0, // 强制显示所有标签
      },
    },
    yAxis: {
      type: "value",
      axisLine: { lineStyle: { color: "gray" }, show: true },
      axisLabel: { color: "gray" },
      min: "dataMin", // 自动计算最小值
      max: "dataMax", // 自动计算最大值
    },
    series: [
      {
        type: "bar",
        data: data.slice(0, Math.min(windowSize, data.length)),
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: "rgba(192, 127, 73, 0.2)" },
            { offset: 0.5, color: "rgba(192, 127, 73, 0.4)" },
            { offset: 1, color: "rgba(192, 127, 73, 0.5)" },
          ]),
        },
        barWidth: "50%",
        label: {
          show: true,
          position: "top",
          formatter: "{c}",
        },
      },
    ],
  };

  chartInstance.value.setOption(option);
}

// 更新图表显示区域
function updateChart(startIdx: number) {
  if (!chartInstance.value || dataList.length === 0) return;

  const categories = dataList.map((item) => item.productsName);
  const data = dataList.map((item) => item.commentsSum);

  // 计算结束索引
  let endIdx = startIdx + windowSize - 1;
  if (endIdx >= dataList.length) {
    endIdx = dataList.length - 1;
  }

  // 准备新的数据
  const newCategories = categories.slice(startIdx, endIdx + 1);
  const newData = data.slice(startIdx, endIdx + 1);

  chartInstance.value.setOption({
    xAxis: {
      data: newCategories,
    },
    series: [
      {
        data: newData,
      },
    ],
  });
}

// 开始循环滚动
function startScrolling() {
  if (timer) clearInterval(timer);

  // 如果数据不足窗口大小，不滚动
  if (dataList.length <= windowSize) return;

  timer = setInterval(() => {
    currentStart++;
    if (currentStart + windowSize > dataList.length) {
      currentStart = 0;
    }
    updateChart(currentStart);
  }, 1500);
}

// 在组件销毁前清除定时器和图表实例
onBeforeUnmount(() => {
  if (timer) clearInterval(timer);
  if (chartInstance.value) {
    chartInstance.value.dispose();
  }
});

// 组件挂载后执行
onMounted(async () => {
  dataList = await fetchProductComments();
  if (dataList.length === 0) {
    console.warn("无数据，无法渲染图表");
    return;
  }

  initChart();
  startScrolling();

  // 添加响应式调整大小
  const resizeObserver = new ResizeObserver(() => {
    if (chartInstance.value) {
      chartInstance.value.resize();
    }
  });

  if (chartRef.value) {
    resizeObserver.observe(chartRef.value);
  }

  onBeforeUnmount(() => {
    resizeObserver.disconnect();
  });
});
</script>

<style scoped>
.YunAllProductd {
  border-radius: 8px;
  padding: 15px;
}
</style>
