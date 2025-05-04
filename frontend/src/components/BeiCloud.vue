<template>
  <div class="CommentWordCloud">
    <div v-if="loading" class="loading">数据加载中...</div>
    <div v-else-if="error" class="error">数据加载失败: {{ error }}</div>
    <div v-else-if="commentData.length === 0" class="no-data">
      没有找到评论数据
    </div>
    <div v-else ref="wordCloudChart" style="width: 100%; height: 100%"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, nextTick } from "vue";
import * as echarts from "echarts";
import "echarts-wordcloud";

const wordCloudChart = ref<HTMLElement | null>(null);
const loading = ref(true);
const error = ref<string | null>(null);
let chartInstance: echarts.ECharts | null = null;

// 静态评论数据
const commentData = [
  { name: "不错", value: 590 },
  { name: "物流", value: 516 },
  { name: "效果", value: 496 },
  { name: "安宫", value: 483 },
  { name: "正品", value: 471 },
  { name: "购买", value: 467 },
  { name: "包装", value: 455 },
  { name: "牛黄丸", value: 448 },
  { name: "同仁堂", value: 391 },
  { name: "质量", value: 370 },
  { name: "值得", value: 356 },
  { name: "很快", value: 336 },
  { name: "可以", value: 290 },
  { name: "价格", value: 289 },
  { name: "满意", value: 275 },
  { name: "发货", value: 271 },
  { name: "好评", value: 251 },
  { name: "放心", value: 249 },
  { name: "备用", value: 241 },
  { name: "希望", value: 235 },
  { name: "信赖", value: 232 },
  { name: "客服", value: 225 },
  { name: "购物", value: 218 },
  { name: "速度", value: 208 },
  { name: "品牌", value: 204 },
  { name: "服务", value: 184 },
  { name: "推荐", value: 175 },
  { name: "产品", value: 160 },
  { name: "喜欢", value: 150 },
  { name: "回购", value: 149 },
  { name: "常备", value: 148 },
  { name: "商品", value: 145 },
  { name: "自营", value: 139 },
  { name: "品质", value: 131 },
  { name: "还会", value: 123 },
  { name: "相信", value: 119 },
  { name: "药品", value: 116 },
  { name: "比较", value: 115 },
  { name: "性价比", value: 113 },
  { name: "多次", value: 101 },
  { name: "香港", value: 101 },
  { name: "实惠", value: 97 },
  { name: "保障", value: 96 },
  { name: "牛黄", value: 92 },
  { name: "服务态度", value: 90 },
  { name: "方便", value: 89 },
  { name: "送货", value: 85 },
  { name: "优惠", value: 83 },
  { name: "天然", value: 82 },
  { name: "安心", value: 72 },
  { name: "便宜", value: 71 },
];

const initChart = () => {
  if (!wordCloudChart.value || commentData.length === 0) return;

  if (chartInstance) {
    chartInstance.dispose();
  }

  const sortedData = [...commentData].sort((a, b) => b.value - a.value);

  const wordCloudData = sortedData.map((item, index) => ({
    name: item.name,
    value: item.value,
    textStyle: {
      color:
        index < 5 ? "rgba(185, 142, 109, 0.8)" : "rgba(185, 142, 109, 0.4)",
    },
  }));

  chartInstance = echarts.init(wordCloudChart.value);

  const option = {
    title: {
      text: "北京同仁堂词云图",
      //subtext: "数据来源：电商平台,北京同仁堂黑膏药",
      left: "right",
      textStyle: {
        fontSize: 16,
        color: "gray",
        fontWeight: "bold",
        padding: [100, 0, 0, 0],
      },
    },
    tooltip: {
      show: true,
      confine: true, // 确保tooltip不超出容器
      appendToBody: true, // 将tooltip添加到body中，避免被其他元素遮挡
      position: function(point, params, dom, rect, size) {
        // 自定义tooltip位置，确保在视口内
        return [point[0] + 10, point[1] + 10];
      },
      formatter: (params: any) => {
        return `${params.name}: ${params.value}次`;
      },
      z: 100 // 设置高z-index确保tooltip显示在最上层
    },
    series: [
      {
        type: "wordCloud",
        shape: "circle",
        left: "center",
        top: "center",
        width: "90%",
        height: "95%",
       sizeRange: [10, 45],
        rotationRange: [-45, 45],
        rotationStep: 15,
        gridSize: 8,
        drawOutOfBound: false,
        textStyle: {
          fontFamily: "sans-serif",
          fontWeight: "bold",
          color: "rgba(185, 142, 109, 0.4)",
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

onMounted(() => {
  loading.value = false;
  nextTick(() => initChart());
});
</script>

<style scoped>
.CommentWordCloud {
  width: 100%;
  height: 100%;
  padding: 5px;
  box-sizing: border-box;
  position: relative;
  z-index: 50; /* 确保图表容器在地图之上 */
}

.loading,
.error,
.no-data {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  color: #666;
  font-size: 16px;
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
