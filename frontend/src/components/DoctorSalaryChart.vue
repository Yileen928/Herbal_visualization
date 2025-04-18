<template>
  <div class="DoctorSalaryChart">
    <div ref="chart" style="width: 100%; height: 350px; background-color: transparent;"></div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import * as echarts from 'echarts';

const chart = ref(null);

// 模拟获取数据的函数
async function fetchData() {
  const response = await fetch('http://106.55.169.134:10010/medical/pay/counts');
  const data = await response.json();

  // 筛选医师数据
  const doctorData = data.filter(item => item.job === '医师');

  console.log(doctorData); // 确认获取的数据

  return doctorData;
}

onMounted(async () => {
  const doctorData = await fetchData();

  // 初始化 ECharts
  if (!chart.value) return; // 确保 chart.value 有效

  const myChart = echarts.init(chart.value);

  // 定义薪资范围
  const salaryRanges = [
    { range: '0-2k', min: 0, max: 2000 },
    { range: '2k-4k', min: 2000, max: 4000 },
    { range: '4k-6k', min: 4000, max: 6000 },
    { range: '6k-8k', min: 6000, max: 8000 },
    { range: '8k-10k', min: 8000, max: 10000 },
    { range: '10k以上', min: 100000, max: Infinity },
  ];

  // 计算各薪资范围内的数量
  const minSalaryCounts = salaryRanges.map(({ min, max }) => {
    return doctorData
      .filter(item => item.salaryType === 'Min Salary' && item.salary >= min && item.salary < max)
      .reduce((total, item) => total + item.count, 0); // 汇总数量
  });

  const maxSalaryCounts = salaryRanges.map(({ min, max }) => {
    return doctorData
      .filter(item => item.salaryType === 'Max Salary' && item.salary >= min && item.salary < max)
      .reduce((total, item) => total + item.count, 0); // 汇总数量
  });

  console.log('Min Salary Counts:', minSalaryCounts);
  console.log('Max Salary Counts:', maxSalaryCounts);

  const option = {
    title: {
      text: '医师薪资',
      left: 'right',
      fontSize: 11,
    },
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'value',
      name: '数量',
      fontSize: 9,
      position: 'bottom' // 将 x 轴放置在底部
    },
    yAxis: {
      type: 'category',
      data: salaryRanges.map(range => range.range), // 使用薪资范围作为 y 轴标签
      name: '薪资范围',
      nameGap: 0, // 增加 Y 轴名称与标签的间距
      axisLabel: {
        padding: [0, 0, 5, 0], // 上、右、下、左的内边距
        fontSize: 9, // 调整字体大小
        margin: 2, // 标签与轴之间的间距
      }
    },legend: {
      data: ["最低薪资数量", "最高薪资数量"], // 图例显示的名称
      top:"7%",
      right: "1%", // 图例位置（可根据需要调整）
      textStyle: {
        color: "#333", // 图例文字颜色
      },
    },
    series: [
      {
        name: '最低薪资数量',
        type: 'bar',
        data: minSalaryCounts,
        emphasis: {
          focus: 'series'
        },
        barWidth: '30%', // 设置柱状宽度
        itemStyle: {
          color:  "#B98E6D", // 最低薪资颜色
        }
      },
      {
        name: '最高薪资数量',
        type: 'bar',
        data: maxSalaryCounts,
        emphasis: {
          focus: 'series'
        },
        barWidth: '30%', // 设置柱状宽度
        itemStyle: {
          color: "#6F4C3E"// 最高薪资颜色
        }
      }
    ],
    grid: {
      left: '10%', // 左侧间距
      right: '10%', // 右侧间距
      top: '10%', // 顶部间距
      bottom: '10%', // 底部间距
    }
  };

  myChart.setOption(option);
  myChart.resize(); // 确保图表适应容器大小
});
</script>

<style scoped>
</style>
