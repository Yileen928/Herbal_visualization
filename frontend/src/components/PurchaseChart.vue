//采购投入赋码图
<template>
  <div ref="chart2" style="height: 400px"></div>
</template>

<script setup lang="ts">
import { onMounted, ref ,onUnmounted} from "vue";
import * as echarts from "echarts";

const chart2 = ref(null);
let timer = null;

const renderChart = () => {
  const chart = echarts.init(chart2.value);

  const chartData = {
    years: ["2021", "2022", "2023", "2024"],
    purchase: [1063, 9788, 12992, 30578],
    investment: [0, 2613, 22399, 42490],
    code: [0, 865, 3741, 3709],
  };

  const option = {
    title: {
      text: "云南省中药采购、投入与赋码量",
      left: "center",
    },
     grid: {
      top: "25%",
      bottom:'8%'
    },
    tooltip: {
      trigger: "axis",
       backgroundColor: 'rgba(0, 0, 0, 0.6)',
       borderColor:'transparent',
      formatter: (params) => {
        const html = params.map(item=>{
          return `<div class="tooltip-item"><span class="label">${item.marker}${item.seriesName}:</span><span class="text">${item.value}</span></div>`;
        })
        
        return `<div class="tooltip-box">${html.join('')}</div>`;
          
      },
    },
    legend: {
      data: ["采购量", "投入量", "赋码量"],
      top: "10%",
    },
    xAxis: {
      type: "category",
      data: chartData.years,
    },
    yAxis: [
      {
        type: "value",
        name: "投入量（吨）",
        position: "left",
        axisLine: {
          show:true,
          lineStyle: {
            color: "gray",
          },
        },
      },
      {
        type: "value",
        name: "赋码量（万个）",
        position: "right",
        axisLabel: {
          formatter: "{value}",
        },
        axisLine: {
           show:true,
          lineStyle: {
            color: "gray",
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

  let currentIndex = 0;

  clearInterval(timer);
  timer = setInterval(() => {
    chart.dispatchAction({
      type: "showTip",
      seriesIndex: 0,
      dataIndex: currentIndex,
    });
    currentIndex = (currentIndex + 1) % chartData.years.length;
  }, 2000);
  chart.setOption(option);
  window.addEventListener("resize", () => chart.resize());
};

onMounted(() => {
  renderChart();
});
onUnmounted(()=>{
  clearInterval(timer);
  chart2.value.dispose(); // 销毁图表实例
  chart2.value = null; // 清空引用
})
</script>