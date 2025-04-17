//采购投入赋码图
<template>
  <div ref="chart2" style="height: 400px"></div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import * as echarts from "echarts";

const chart2 = ref(null);

const renderChart = () => {
  const chart = echarts.init(chart2.value);

  const chartData = {
    years: ["2021", "2022", "2023", "2024"],
    purchase: [1063, 9788, 12992, 30578],
    investment: [0, 2613, 22399, 42490],
    code: [0, 865, 3741, 3709],
  };

  const option = {
    grid: {
      containLabel: true,
    },
    title: {
      text: "云南省中药采购、投入与赋码量",
      left: "center",
    },
    tooltip: {
      trigger: "axis",
      axisPointer: {
        type: "cross",
      },
    },
    legend: {
      data: ["采购量", "投入量", "赋码量"],
      top: "10%",
    },
    xAxis: {
      type: "category",
      data: chartData.years,
      boundaryGap: false,
    },
    yAxis: [
      {
        type: "value",
        name: "投入量（吨）",
        position: "left",
        min: 0,
        max: 50000,
        interval: 10000,
        axisLabel: {
          formatter: "{value}",
        },
        axisLine: {
          lineStyle: {
            color: "#87cefa",
          },
        },
      },
      {
        type: "value",
        name: "赋码量（万个）",
        position: "right",
        min: 0,
        max: 4000,
        interval: 1000,
        axisLabel: {
          formatter: "{value}",
        },
        axisLine: {
          lineStyle: {
            color: "#da70d6",
          },
        },
      },
    ],
    series: [
      {
        name: "采购量",
        type: "line",
        data: chartData.purchase,
        smooth: true,
        lineStyle: { color: "#ff7f50" },
        areaStyle: {
          color: "rgba(255, 127, 80, 0.32)",
        },
        yAxisIndex: 0,
      },
      {
        name: "投入量",
        type: "line",
        yAxisIndex: 0,
        data: chartData.investment,
        smooth: true,
        lineStyle: { color: "#87cefa" },
        areaStyle: {
          color: "rgba(135, 206, 250, 0.3)",
        },
      },
      {
        name: "赋码量",
        type: "line",
        yAxisIndex: 1,
        data: chartData.code,
        smooth: true,
        lineStyle: { color: "#da70d6" },
        areaStyle: {
          color: "rgba(218, 112, 214, 0.3)",
        },
      },
    ],
  };

  chart.setOption(option);
  window.addEventListener("resize", () => chart.resize());
};

onMounted(() => {
  renderChart();
});
</script>