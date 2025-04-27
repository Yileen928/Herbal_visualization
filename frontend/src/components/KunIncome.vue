<template>
  <div class="dashboard-container">
    <div class="dashboard" ref="salesChartRef"></div>
    <div class="dashboard" ref="profitChartRef"></div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import * as echarts from "echarts";

// 定义数据
const data = {
  huqingyutang: {
    name: "胡庆余堂",
    sales: 2.62, // 亿元
    profit: 1129.45, // 万元
    color: "rgba(245, 170, 109,1)",
  },
  kunzhongyao: {
    name: "昆中药",
    sales: 4.67, // 亿元
    profit: 9597.56, // 万元
    color: "rgba(135, 99, 51, 1)",
  },
};

// 定义图表容器的引用
const salesChartRef = ref<HTMLDivElement | null>(null);
const profitChartRef = ref<HTMLDivElement | null>(null);

// 初始化销售额仪表盘图
const initSalesChart = (chartRef: HTMLDivElement | null) => {
  if (chartRef) {
    const chart = echarts.init(chartRef);
    const option = {
      title: {
        text: "2024年销售\n额(亿元)",
        left: "left",
        color: "gray",
        FontSize: 12,
      },
      tooltip: {
        trigger: "item",
        formatter: function (params) {
          // 根据系列名称判断是销售额还是利润
          const unit = params.seriesName === "销售额" ? "亿元" : "万元";
          // 根据数据索引获取对应的企业名称
          const companyName =
            params.dataIndex === 0
              ? data.huqingyutang.name
              : data.kunzhongyao.name;
          return `${params.seriesName}<br/>${companyName}: ${params.value}${unit}`;
        },
      },
      legend: {
        data: [data.huqingyutang.name, data.kunzhongyao.name],
        orient: "horizontal",
        bottom: 0,
        textStyle: {
          color: "#000",
        },
      },
      series: [
        {
          name: "销售额",

          type: "gauge",
          min: 0,
          max: 5,
          splitNumber: 5,
          radius: "100%",
          axisLine: {
            lineStyle: {
              width: 5,
            },
          },
          pointer: {
            width: 5,
          },
          detail: {
            show: false,
          },
          data: [
            {
              value: data.huqingyutang.sales,
              itemStyle: {
                color: data.huqingyutang.color,
              },
            },
            {
              value: data.kunzhongyao.sales,
              itemStyle: {
                color: data.kunzhongyao.color,
              },
            },
          ],
        },
      ],
    };
    chart.setOption(option);
  }
};

// 初始化利润仪表盘图
const initProfitChart = (chartRef: HTMLDivElement | null) => {
  if (chartRef) {
    const chart = echarts.init(chartRef);
    const option = {
      title: {
        text: "2024年利润\n(万元)",
        left: "left",
        color: "gray",
        FontSize: 12,
      },
      tooltip: {
        trigger: "item",
        formatter: function (params) {
          const companyName =
            params.dataIndex === 0
              ? data.huqingyutang.name
              : data.kunzhongyao.name;
          return `${params.seriesName}<br/>${companyName}: ${params.value}万元`;
        },
      },
      legend: {
        data: [data.huqingyutang.name, data.kunzhongyao.name],
        orient: "horizontal",
        bottom: 0,
        textStyle: {
          color: "#000",
        },
      },
      series: [
        {
          name: "利润",
          type: "gauge",
          min: 0,
          max: 10000,
          splitNumber: 4,
          radius: "100%",
          axisLine: {
            lineStyle: {
              width: 5,
            },
          },
          pointer: {
            width: 2,
          },
          detail: {
            show: false,
          },
          data: [
            {
              value: data.huqingyutang.profit,
              itemStyle: {
                color: data.huqingyutang.color,
              },
            },
            {
              value: data.kunzhongyao.profit,
              itemStyle: {
                color: data.kunzhongyao.color,
              },
            },
          ],
        },
      ],
    };
    chart.setOption(option);
  }
};

// 在组件挂载后初始化图表
onMounted(() => {
  initSalesChart(salesChartRef.value);
  initProfitChart(profitChartRef.value);
});
</script>

<style scoped>
.dashboard-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
  gap: 20px; /* 添加间距 */
}
.dashboard {
  width: 100%; /* 调整宽度 */
  height: 100%; /* 固定高度 */
}
</style>
