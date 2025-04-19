<template>
  <div
    class="chart-container"
    ref="chartContainer"
    style="width: 100%; height: 100%"
  ></div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from "vue";
import * as echarts from "echarts";

const chartContainer = ref<HTMLElement | null>(null);
let chartInstance: echarts.ECharts | null = null;

const productData = [
  { name: "气雾剂", year: 2020, sales: 12.5, marketShare: 89.2 },
  { name: "气雾剂", year: 2021, sales: 13.8, marketShare: 90.1 },
  { name: "气雾剂", year: 2022, sales: 15, marketShare: 90.5 },
  { name: "气雾剂", year: 2023, sales: 17.34, marketShare: 91 },
  { name: "气雾剂", year: 2024, sales: 21.0, marketShare: 91.8 },

  { name: "创可贴", year: 2020, sales: 2.8, marketShare: 63.7 },
  { name: "创可贴", year: 2021, sales: 3.2, marketShare: 65.1 },
  { name: "创可贴", year: 2022, sales: 3.12, marketShare: 66 },
  { name: "创可贴", year: 2023, sales: 4, marketShare: 66.5 },
  { name: "创可贴", year: 2024, sales: 5.8, marketShare: 72.4 },

  { name: "散剂", year: 2020, sales: 9.3, marketShare: 13.2 },
  { name: "散剂", year: 2021, sales: 10.1, marketShare: 13.8 },
  { name: "散剂", year: 2022, sales: 11.5, marketShare: 14.2 },
  { name: "散剂", year: 2023, sales: 12.8, marketShare: 14.5 },
  { name: "散剂", year: 2024, sales: 14.9, marketShare: 14.9 },

  { name: "牙膏", year: 2020, sales: 45, marketShare: 20.9 },
  { name: "牙膏", year: 2021, sales: 52.3, marketShare: 22.1 },
  { name: "牙膏", year: 2022, sales: 60.3, marketShare: 24.4 },
  { name: "牙膏", year: 2023, sales: 61, marketShare: 24.6 },
  { name: "牙膏", year: 2024, sales: 61.04, marketShare: 25.0 },
];

const colorMap: Record<string, string> = {
  气雾剂: "rgba(192, 127, 73, 0.3)",
  创可贴: "rgba(192, 127, 73, 1)",
  散剂: "rgba(145, 99, 51, 1)",
  牙膏: "rgba(168, 117, 89, 0.8)",
};

function initChart() {
  if (!chartContainer.value) return;

  // 销毁之前的实例
  if (chartInstance) {
    chartInstance.dispose();
  }

  chartInstance = echarts.init(chartContainer.value);

  const seriesData = productData.map((item) => ({
    name: item.name,
    value: [item.year, item.sales, item.marketShare],
    itemStyle: {
      color: colorMap[item.name],
    },
  }));

  const option = {
    title: {
      text: "云南白药产品销售分析气泡图",
      left: "center",
      textStyle: {
        fontSize: 16,
        fontWeight: "bold",
      },
    },
    tooltip: {
      trigger: 'item',
      formatter: (params: any) => {
        const data = params.data;
        return `
          <div style="font-weight:bold">${data.name}</div>
          <div>年份: ${data.value[0]}</div>
          <div>销售额: ${data.value[1]}亿元</div>
          <div>市场份额: ${data.value[2]}%</div>
        `;
      }
    },
    legend: {
      data: Object.keys(colorMap),
      right: 10,
      top: 20,
      textStyle: {
        color: '#333'
      }
    },
    grid: {
      left: "5%",
      right: "7%",
      bottom: "3%",
      top: "20%",
      containLabel: true,
    },
    xAxis: {
      type: "value",
      name: "年份",
      nameLocation: "middle",
      nameGap: 30,
      min: 2019,
      max: 2024.5,
      axisLabel: {
        formatter: (value: number) => value.toFixed(0),
        color: "gray",
      },
      axisLine: {
        lineStyle: {
          color: "gray",
        },
      },
      axisTick: {
        lineStyle: {
          color: "rgba(222, 204, 184, 0.5)",
        },
      },
      splitLine: {
        lineStyle: {
          color: "rgba(222, 204, 184, 0.5)",
        },
      },
    },
    yAxis: {
      type: "value",
      name: "销售额（亿元）",
      min: 0,
      max: 70,
      axisLabel: {
        color: "gray",
      },
      axisLine: {
        lineStyle: {
          color: "gray",
        },
      },
      axisTick: {
        lineStyle: {
          color: "rgba(222, 204, 184, 0.5)",
        },
      },
      splitLine: {
        lineStyle: {
          color: "rgba(222, 204, 184, 0.5)",
        },
      },
    },
    visualMap: {
      show: false,
      dimension: 2,
      min: 10,
      max: 100,
      inRange: {
        symbolSize: [10, 40],
      },
    },
    series: [
      {
        name: "市场份额",
        type: "scatter",
        symbolSize: (data: any) => {
          return Math.sqrt(data[2]) * 5;
        },
        data: seriesData,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowColor: "rgba(0, 0, 0, 0.5)",
          },
        },
      },
    ],
  };

  chartInstance.setOption(option);

  // 响应式调整
  const resizeObserver = new ResizeObserver(() => {
    chartInstance?.resize();
  });
  resizeObserver.observe(chartContainer.value);

  onBeforeUnmount(() => {
    resizeObserver.disconnect();
    if (chartInstance) {
      chartInstance.dispose();
    }
  });
}

onMounted(() => {
  initChart();
});
</script>

<style scoped>
.chart-container {
  width: 100%;
  height: 100%;
  background-color: transparent;
}
</style>
