<template>
  <div ref="pieChart" style="width: 100%; height: 352px"></div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from "vue";
import * as echarts from "echarts";

const pieChart = ref(null);
let chartInstance = null;

const fetchData = async () => {
  try {
    const response = await fetch(
      "http://106.55.169.134:10010/store/type/counts"
    );
    if (!response.ok) {
      throw new Error("网络请求失败");
    }
    const data = await response.json();
    createChart(data);
  } catch (error) {
    console.error(error);
  }
};

const createChart = (data) => {
  if (chartInstance) {
    chartInstance.dispose(); // 销毁旧的实例
  }

  chartInstance = echarts.init(pieChart.value);

  // 只取前五名并处理类型名称，去掉“服务”字样
  const topFiveData = data.slice(0, 5).map((item) => ({
    type: item.type.replace(/服务/g, ""), // 替换“服务”
    count: item.count,
  }));

  const labels = topFiveData.map((item) => item.type);
  const sizes = topFiveData.map((item) => item.count);

  const options = {
    title: {
      text: "药店类型TOP5的分布",
      left: "center",
      textStyle: {
        top: "25%",
      },
    },
    tooltip: {
      trigger: "item",
      formatter: (params) => {
        return `${params.name}: ${params.percent.toFixed(1)}%`;
      },
    },
    series: [
      {
        name: "类型",
        type: "pie",
        radius: "50%",
        data: labels.map((label, index) => ({
          name: label,
          value: sizes[index],
        })),
        emphasis: {
          itemStyle: {
            shadowBlur: 0,
            shadowOffsetX: 0,
            shadowColor: "gray",
          },
        },
        label: {
          show: true,
          color: "gray", // 设置文字颜色为灰色
          formatter: "{b}: {d}%",
        },
        itemStyle: {
          // 使用莫兰迪色系
          color: (params) => {
            const colors = [
              "#B98E6D",
              "#6F4C3E",
              "#A25D3B",
              "#E4C6B2",
              "#E1B7A6",
            ];
            return colors[params.dataIndex % colors.length];
          },
        },
      },
    ],
  };

  chartInstance.setOption(options);
};

const handleResize = () => {
  if (chartInstance) {
    chartInstance.resize(); // 重新调整图表大小
  }
};

onMounted(() => {
  fetchData();
  window.addEventListener("resize", handleResize); // 监听窗口变化
});

onBeforeUnmount(() => {
  window.removeEventListener("resize", handleResize); // 移除事件监听
  if (chartInstance) {
    chartInstance.dispose(); // 销毁图表实例
  }
});
</script>

<style scoped>
/* 根据需要添加样式 */
</style>
