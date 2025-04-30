<template>
  <div class="PharmacistSkillsWordCloud">
    <div v-if="loading" class="loading">数据加载中...</div>
    <div v-else-if="error" class="error">数据加载失败: {{ error }}</div>
    <div v-else-if="filteredData.length === 0" class="no-data">
      没有找到药师相关技能数据
    </div>
    <div v-else ref="wordCloudChart" style="width: 100%; height: 100%"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, watch, nextTick, computed } from "vue";
import * as echarts from "echarts";
import "echarts-wordcloud";

const wordCloudChart = ref<HTMLElement | null>(null);
const loading = ref(true);
const error = ref<string | null>(null);
const rawData = ref<any[]>([]);
let chartInstance: echarts.ECharts | null = null;

// 计算属性：过滤出job为"药师"的数据
const filteredData = computed(() => {
  return rawData.value.filter((item) => item.job === "药师");
});

const fetchData = async () => {
  try {
    loading.value = true;
    error.value = null;
    const response = await fetch(
      "http://106.55.169.134:10010/medical/skills/top20"
    );
    if (!response.ok) throw new Error("网络响应不正常");
    const data = await response.json();
    rawData.value = data;
  } catch (err) {
    error.value = err instanceof Error ? err.message : "未知错误";
    console.error("获取数据失败:", err);
  } finally {
    loading.value = false;
  }
};

const initChart = () => {
  if (!wordCloudChart.value || filteredData.value.length === 0) return;

  // 先销毁之前的实例
  if (chartInstance) {
    chartInstance.dispose();
  }

  // 处理数据，按count降序排序
  const sortedData = [...filteredData.value].sort((a, b) => b.count - a.count);

  // 准备词云数据
  const wordCloudData = sortedData.map((item) => ({
    name: item.skillRequirement,
    value: item.count,
    // 前5项使用不透明颜色，其余使用半透明
    textStyle: {
      color:
        sortedData.indexOf(item) < 5
          ? "rgba(226, 160, 109,1)"
          : "rgba(226, 160, 109,0.5)",
    },
  }));

  chartInstance = echarts.init(wordCloudChart.value);

  const option = {
    backgroundColor: "rgba(222, 204, 184, 0.1)",
    title: {
      left: "center",
      textStyle: {
        color: "rgba(59, 65, 25, 0.7)",
        fontSize: 18,
        fontWeight: "bold",
      },
    },
    tooltip: {
      show: true,
      formatter: (params) => {
        return `${params.name}: ${params.value}次`;
      },
    },
    series: [
      {
        type: "wordCloud",
        shape: "circle",
        left: "center",
        top: "center",
        width: "90%",
        height: "90%",
        sizeRange: [8, 35],
        rotationRange: [-45, 45], // 修正了旋转范围，原代码有误写为-450
        rotationStep: 15,
        gridSize: 8,
        drawOutOfBound: false,
        textStyle: {
          fontFamily: "sans-serif",
          fontWeight: "bold",
          color: "rgba(168, 117, 89, 0.4)",
        },
        emphasis: {
          focus: "self",
          textStyle: {
            shadowBlur: 8,
            shadowColor: "gray",
          },
        },
        data: wordCloudData,
      },
    ],
  };

  chartInstance.setOption(option);

  const handleResize = () => chartInstance?.resize();
  window.addEventListener("resize", handleResize);

  onUnmounted(() => {
    window.removeEventListener("resize", handleResize);
    chartInstance?.dispose();
    chartInstance = null;
  });
};

onMounted(async () => {
  await fetchData();
  // 使用nextTick确保DOM已渲染
  await nextTick();
  initChart();
});

// 监听过滤后的数据变化重新渲染图表
watch(
  filteredData,
  () => {
    nextTick(() => initChart());
  },
  { deep: true }
);
</script>

<style scoped>
.PharmacistSkillsWordCloud {
  width: 100%;
  height: 100%;
  padding: 5px;
  box-sizing: border-box;
  position: relative;
}
</style>
