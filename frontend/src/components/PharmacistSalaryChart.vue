<template>
  <div class="DoctorSalaryChart">
    <div
      ref="chart"
      style="width: 100%; height:100%; background-color: transparent"
    ></div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, onBeforeUnmount } from "vue";
import * as echarts from "echarts";

const chart = ref<HTMLElement | null>(null);
let myChart: echarts.ECharts | null = null;

// 响应式调整函数
const resizeChart = () => {
  if (myChart) {
    myChart.resize();
  }
};

// 模拟获取数据的函数
async function fetchData() {
  const response = await fetch(
    "http://106.55.169.134:10010/medical/pay/counts"
  );
  const data = await response.json();

  // 筛选医师数据
  const doctorData = data.filter((item: any) => item.job === "药师");
  return doctorData;
}

const initChart = async () => {
  const doctorData = await fetchData();

  // 初始化 ECharts
  if (!chart.value) return;

  myChart = echarts.init(chart.value);

  // 定义薪资范围
  const salaryRanges = [
    { range: "0-2k", min: 0, max: 2000 },
    { range: "2k-4k", min: 2000, max: 4000 },
    { range: "4k-6k", min: 4000, max: 6000 },
    { range: "6k-8k", min: 6000, max: 8000 },
    { range: "8k-10k", min: 8000, max: 10000 },
    { range: "10k以上", min: 10000, max: Infinity },
  ];

  // 计算各薪资范围内的数量
  const minSalaryCounts = salaryRanges.map(({ min, max }) => {
    return doctorData
      .filter(
        (item: any) =>
          item.salaryType === "Min Salary" &&
          item.salary >= min &&
          item.salary < max
      )
      .reduce((total: number, item: any) => total + item.count, 0);
  });

  const maxSalaryCounts = salaryRanges.map(({ min, max }) => {
    return doctorData
      .filter(
        (item: any) =>
          item.salaryType === "Max Salary" &&
          item.salary >= min &&
          item.salary < max
      )
      .reduce((total: number, item: any) => total + item.count, 0);
  });

  const option = {
    title: {
      text: "药师薪资",
      left: "left",
      textStyle: {
        fontSize: 14,
      },
    },
    tooltip: {
      trigger: "axis",
      axisPointer: {
        type: "shadow"
      }
    },
    xAxis: {
      type: "value",
      name: "数量",
      nameTextStyle: {
        fontSize: 12
      },
      axisLine: {
        lineStyle: {
          color: 'gray',
          width: 1,
        }
      },
      axisTick: {
        show: true,
        lineStyle: {
          color: 'gray',
        }
      },
      axisLabel: {
        color: 'gray',
        fontSize: 11
      },
      splitLine: {
        lineStyle: {
          type: 'dashed'
        }
      }
    },
    yAxis: {
      type: "category",
      data: salaryRanges.map((range) => range.range),
      name: "薪资范围",
      nameTextStyle: {
        fontSize: 12
      },
      axisLine: {
        show: true,
        lineStyle: {
          color: 'gray'
        }
      },
      axisTick: {
        show: false
      },
      axisLabel: {
        fontSize: 11,
        margin: 8
      }
    },
    legend: {
      data: ["最低薪资数量", "最高薪资数量"],
      top: "5%",
      right: "5%",
      textStyle: {
        fontSize: 12
      }
    },
    series: [
      {
        name: "最低薪资数量",
        type: "bar",
        data: minSalaryCounts,
        barWidth: "30%",
        itemStyle: {
          color: "rgba(226, 160, 109, 0.5)",
          borderRadius: [0, 4, 4, 0]
        },
        label: {
          show: false
        },
        emphasis: {
          label: {
            show: true,
            position: 'right'
          }
        }
      },
      {
        name: "最高薪资数量",
        type: "bar",
        data: maxSalaryCounts,
        barWidth: "30%",
        itemStyle: {
          color: "rgba(226, 160, 109, 1)",
          borderRadius: [0, 4, 4, 0]
        },
        label: {
          show: false
        },
        emphasis: {
          label: {
            show: true,
            position: 'right'
          }
        }
      }
    ],
    grid: {
      left: "15%",
      right: "15%",
      top: "20%",
      bottom: "15%",
      containLabel: true
    },
    backgroundColor: "transparent"
  };

  myChart.setOption(option);
  resizeChart();

  // 添加窗口大小变化监听
  window.addEventListener('resize', resizeChart);
};

onMounted(() => {
  initChart();
});

onBeforeUnmount(() => {
  if (myChart) {
    // 移除监听器
    window.removeEventListener('resize', resizeChart);
    myChart.dispose();
  }
});
</script>

<style scoped>
.DoctorSalaryChart {
  width: 100%;
  height: 100%;

}
</style>