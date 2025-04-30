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
  { name: "质量", value: 1251 },
  { name: "品牌", value: 977 },
  { name: "使用", value: 936 },
  { name: "好评", value: 933 },
  { name: "满意", value: 852 },
  { name: "疼痛", value: 810 },
  { name: "便宜", value: 795 },
  { name: "信赖", value: 768 },
  { name: "缓解", value: 734 },
  { name: "正品", value: 730 },
  { name: "药膏", value: 659 },
  { name: "推荐", value: 650 },
  { name: "好用", value: 638 },
  { name: "实惠", value: 614 },
  { name: "回购", value: 495 },
  { name: "多次", value: 482 },
  { name: "舒服", value: 432 },
  { name: "性价比", value: 429 },
  { name: "比较", value: 426 },
  { name: "喜欢", value: 415 },
  { name: "明显", value: 403 },
  { name: "过敏", value: 394 },
  { name: "跌打损伤", value: 338 },
  { name: "皮肤", value: 332 },
  { name: "经常", value: 310 },
  { name: "扭伤", value: 290 },
  { name: "品质", value: 273 },
  { name: "优惠", value: 265 },
  { name: "划算", value: 265 },
  { name: "止痛", value: 256 },
  { name: "肌肉", value: 167 },
  { name: "关节", value: 167 },
  { name: "膝盖", value: 160 },
  { name: "服务态度", value: 246 },
  { name: "活血", value: 142 },
  { name: "有效", value: 244 },
  { name: "客服", value: 241 },
  { name: "物美价廉", value: 239 },
  { name: "挺好用", value: 141 },
  { name: "药房", value: 227 },
  { name: "腰疼", value: 144 },
  { name: "药效", value: 172 },
  { name: "容易", value: 203 },
  { name: "消肿止痛", value: 202 },
  { name: "风湿", value: 201 },
  { name: "疗效", value: 201 },
  { name: "药品", value: 196 },
  { name: "保障", value: 193 },
  { name: "味道", value: 193 },
  { name: "减轻", value: 175 },
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
        index < 5 ? "rgba(91,143,249,0.8)" : "rgba(91,143,249,0.3)",
    },
  }));

  chartInstance = echarts.init(wordCloudChart.value);

  const option = {
    title: {
      text: "云南白药词云图",
      //subtext: "数据来源：电商平台,云南白药膏药",
      left: "left",
      textStyle: {
        fontSize: 16,
        color: "gray",
        fontWeight: "bold",
        padding: [100, 0, 0, 0],
      },
    },
    tooltip: {
      show: true,
      formatter: (params: any) => {
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
}
</style>
