<template>
  <div>
    <div ref="chartContainer" style="width: 100%; height: 400px"></div>
    <div class="province-info" v-if="provinceInfo.length">
      <h3>省会药店数量</h3>
      <ul>
        <li v-for="(item, index) in provinceInfo" :key="index">
          {{ item.province }}: {{ item.count }} 家
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import * as echarts from "echarts";

const chartContainer = ref(null);
const provinceInfo = ref([]); // 用于存储省会信息

// 获取药店数量数据
const fetchPharmacyData = async () => {
  try {
    const response = await fetch(
      "http://106.55.169.134:10010/store/city/counts"
    );
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching pharmacy data:", error);
  }
};

// 初始化图表
const initChart = (data) => {
  const chart = echarts.init(chartContainer.value);

  // 按数量从高到低排序
  const sortedData = data.sort((a, b) => b.count - a.count).slice(0, 17);

  const option = {
    title: {
      text: "TOP17省会中药饮品店的数量分布",
      left: "center",
    },
    tooltip: {
      trigger: "item",
      formatter: (params) =>
        `${params.name}: ${params.value} (${(
          (params.value / data.reduce((acc, item) => acc + item.count, 0)) *
          100
        ).toFixed(2)}%)`,
    },
    series: [
      {
        name: "药店数量",
        type: "pie",
        roseType: "radius",
        radius: ["20%", "65%"],
        label: {
          show: true,
          formatter: (params) => params.name, // 只显示省份名称
        },
        labelLine: {
          show: true,
          length: 3, // 设置引导线的长度
          lineStyle: {
            width: 1, // 设置线的宽度
            type: "solid", // 设置线的类型
          },
        },
        itemStyle: {
          // 使用莫兰迪色系
          color: (params) => {
            const colors = [
              "#6F4C3E",
              "#A25D3B",
              "#B98E6D",
              "#C69C6D",
              "#C4A17A",
              "#D3B184",
              "#BB9A7D",
              "#C9A98A",
              "#E4C6B2",
              "#E1B7A6",
            ];
            return colors[params.dataIndex % colors.length];
          },
        },
        data: sortedData.map((item) => ({
          name: item.province,
          value: item.count,
        })),
      },
    ],
  };

  // 设定图表数据
  chart.setOption(option);

  // 响应式调整
  window.addEventListener("resize", () => {
    chart.resize();
  });
};

// 组件挂载时
onMounted(async () => {
  const data = await fetchPharmacyData();
  if (data) {
    initChart(data);
  }
});
</script>

<style scoped></style>
