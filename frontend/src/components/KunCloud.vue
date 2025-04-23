<template>
  <div class="doctor-skills-wordcloud">
    <div ref="wordcloudChart" style="width: 100%; height: 100%"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from "vue";
import * as echarts from "echarts";
import "echarts-wordcloud";

const wordcloudChart = ref<HTMLElement | null>(null);
let chartInstance: echarts.ECharts | null = null;

// 昆中药数据（已按词频降序排序）
const kzyData = [
  { name: "颗粒", value: 1068 },
  { name: "宁神丸", value: 286 },
  { name: "牙膏", value: 284 },
  { name: "脾胃", value: 241 },
  { name: "图片", value: 215 },
  { name: "价格", value: 210 },
  { name: "中药止咳", value: 194 },
  { name: "金花", value: 181 },
  { name: "凝胶", value: 163 },
  { name: "说明书", value: 144 },
  { name: "中药三七", value: 135 },
  { name: "云南", value: 115 },
  { name: "名字", value: 112 },
  { name: "产品", value: 107 },
  { name: "止咳", value: 105 },
  { name: "中药面膜", value: 103 },
  { name: "文案", value: 98 },
  { name: "鼻炎", value: 97 },
  { name: "乌梅丸", value: 93 },
  { name: "逍遥丸", value: 86 },
  { name: "牛黄解毒片", value: 82 },
  { name: "三七", value: 79 },
  { name: "轻身消胖丸", value: 78 },
  { name: "丹栀逍遥丸", value: 78 },
  { name: "面膜", value: 73 },
  { name: "中药祛痘", value: 73 },
  { name: "成分", value: 73 },
  { name: "功效", value: 72 },
  { name: "儿童", value: 72 },
  { name: "效果", value: 89 },
].map((item, index) => ({
  ...item,
  // 前5项使用深色，其余使用浅色
  textStyle: {
    color: index < 5 ? "rgba(135, 99, 51, 1)" : "rgba(135, 99, 51, 0.5)",
  },
}));

onMounted(() => {
  if (wordcloudChart.value) {
    chartInstance = echarts.init(wordcloudChart.value);

    const option: echarts.EChartsOption = {
      title: {
        text: "昆中药关键词热度分布",
        left: "center",
        textStyle: {
          fontSize: 16,
          fontWeight: "bold",
          color: "#333",
        },
      },
      tooltip: {
        show: true,
        formatter: (params: any) => {
          return `${params.name}<br/>热度: ${params.value}`;
        },
      },
      series: [
        {
          type: "wordCloud",
          shape: "circle",
          left: "center",
          width: "100%",
          height: "80%",
          sizeRange: [12, 40], // 根据词频调整大小范围
          rotationRange: [-30, 30],
          rotationStep: 10,
          gridSize: 10,
          drawOutOfBound: true,
          textStyle: {
            fontFamily: "Microsoft YaHei",
            fontWeight: "bold",
            // 颜色在数据项中已定义
          },
          emphasis: {
            focus: "self",
          },
          data: kzyData,
        },
      ],
    };

    chartInstance.setOption(option);

    window.addEventListener("resize", resizeHandler);
  }
});

const resizeHandler = () => {
  if (chartInstance) {
    chartInstance.resize();
  }
};

onBeforeUnmount(() => {
  if (chartInstance) {
    chartInstance.dispose();
    window.removeEventListener("resize", resizeHandler);
  }
});
</script>

<style scoped>
.doctor-skills-wordcloud {
  width: 100%;
  height: 100%;
}
</style>
