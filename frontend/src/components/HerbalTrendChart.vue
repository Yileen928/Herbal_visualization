//中药产业趋势图
<template>
  <div ref="chart1" style="height: 400px"></div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import * as echarts from "echarts";

const chart1 = ref(null);

const renderChart = () => {
  const chart = echarts.init(chart1.value);

  const chartData = {
    years: ["2019", "2020", "2021", "2022", "2023"],
    totalValue: [388, 425, 445, 484, 535],
    totalVolume: [95, 115, 127, 135, 144],
    totalArea: [873, 901, 902, 904, 1031],
  };

  const option = {
    title: {
      text: "云南省中药产业现状",
      subtext: "中药总产值与总产量",
      left: "center",
    },
    tooltip: {
      trigger: "item",
      formatter: (params) => {
        const yearIndex = parseInt(params.name);
        if (!isNaN(yearIndex)) {
          const year = chartData.years[yearIndex];
          const totalValue = chartData.totalValue[yearIndex];
          const totalVolume = chartData.totalVolume[yearIndex];
          const totalArea = chartData.totalArea[yearIndex];

          return `
            <div style="text-align: left;">
              <strong>${year} 年</strong><br>
              <span style="color: rgba(240, 226, 70, 0.47);">总产值: ${totalValue} 亿元</span><br>
              <span style="color: rgba(211, 190, 106, 0.59);">总产量: ${totalVolume} 万吨</span><br>
              <span style="color: rgba(128, 104, 24, 0.48);">总面积: ${totalArea} 万亩</span>
            </div>
          `;
        }
        return params.name;
      },
    },
    legend: {
      data: ["总产值", "总产量", "总面积"],
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
        name: "产值（亿元）",
        position: "left",
        splitLine: { show: false },
        min: 0,
        max: 700,
        interval: 100,
      },
      {
        type: "value",
        name: "种植面积（万亩）",
        position: "right",
        splitLine: { show: false },
        min: 0,
        max: 1200,
        interval: 200,
      },
    ],
    series: [
      {
        name: "总产值",
        type: "line",
        areaStyle: {
          color: "rgba(240, 226, 70, 0.32)",
        },
        data: chartData.totalValue,
        smooth: true,
        lineStyle: { color: "rgba(240, 226, 70, 0.96)" },
      },
      {
        name: "总产量",
        type: "line",
        areaStyle: {
          color: "rgba(211, 190, 106, 0.59)",
        },
        data: chartData.totalVolume,
        smooth: true,
        lineStyle: { color: "rgb(211, 190, 106)" },
        yAxisIndex: 1,
      },
      {
        name: "总面积",
        type: "line",
        areaStyle: {
          color: "rgba(128, 104, 24, 0.48)",
        },
        data: chartData.totalArea,
        smooth: true,
        lineStyle: { color: "rgba(128, 104, 24, 0.7)" },
        yAxisIndex: 1,
      },
    ],
  };

  chart.setOption(option);
  window.addEventListener("resize", () => chart.resize());

  let currentIndex = 0;
  setInterval(() => {
    chart.dispatchAction({
      type: "showTip",
      seriesIndex: 0,
      dataIndex: currentIndex,
    });
    currentIndex = (currentIndex + 1) % chartData.years.length;
  }, 2000);
};

onMounted(() => {
  renderChart();
});
</script>