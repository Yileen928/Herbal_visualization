<template>
  <div class="DoctorSalaryChart">
    <div ref="chart" style="width: 100%; height: 100%;"></div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, onBeforeUnmount } from 'vue';
import * as echarts from 'echarts';

const chart = ref<HTMLElement | null>(null);
let myChart: echarts.ECharts | null = null;

// 定义 resizeChart 函数
const resizeChart = () => {
  myChart?.resize();
};

// 模拟获取数据的函数
async function fetchData() {
  const response = await fetch('http://106.55.169.134:10010/medical/pay/counts');
  const data = await response.json();

  // 筛选医师数据
  const doctorData = data.filter((item: any) => item.job === '医师');
  return doctorData;
}

const initChart = async () => {
  const doctorData = await fetchData();

  if (!chart.value) return;

  myChart = echarts.init(chart.value);

  // 定义薪资范围
  const salaryRanges = [
    { range: '0-2k', min: 0, max: 2000 },
    { range: '2k-4k', min: 2000, max: 4000 },
    { range: '4k-6k', min: 4001, max: 6000 },
    { range: '6k-8k', min: 6001, max: 8000 },
    { range: '8k-10k', min: 8001, max: 10000 },
    { range: '10k以上', min: 10000, max: Infinity },
  ];

  // 计算各薪资范围内的数量
  const minSalaryCounts = salaryRanges.map(({ min, max }) => {
    return doctorData
      .filter((item: any) => item.salaryType === 'Min Salary' && item.salary >= min && item.salary < max)
      .reduce((total: number, item: any) => total + item.count, 0);
  });

  const maxSalaryCounts = salaryRanges.map(({ min, max }) => {
    return doctorData
      .filter((item: any) => item.salaryType === 'Max Salary' && item.salary >= min && item.salary < max)
      .reduce((total: number, item: any) => total + item.count, 0);
  });

  const option = {
    title: {
      text: '医师薪资分布',
      left: 'left',  // 修改为左对齐
      top: '5%',
      textStyle: {
        fontSize: 14,
        color: '#333'
      }
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      },
      formatter: (params: any) => {
        return `${params[0].axisValue}<br/>
                ${params[0].marker} ${params[0].seriesName}: ${params[0].value}<br/>
                ${params[1].marker} ${params[1].seriesName}: ${params[1].value}`;
      }
    },
    xAxis: {
      type: 'value',
      name: '职位数量',
      nameLocation: 'middle',
      nameGap: 25,
      axisLine: {
        lineStyle: {
          color: '#666'
        }
      },
      axisTick: {
        alignWithLabel: true
      },
      splitLine: {
        lineStyle: {
          type: 'dashed'
        }
      }
    },
    yAxis: {
      type: 'category',
      data: salaryRanges.map(range => range.range),
      axisLine: {
        show: true,
        lineStyle: {
          color: '#666'
        }
      },
      axisTick: {
        show: false
      },
      axisLabel: {
        interval: 0,
        fontSize: 12
      }
    },
    legend: {
      data: ['最低薪资', '最高薪资'],
      top: '5%',
      right: '5%',
      textStyle: {
        color: '#666'
      }
    },
    grid: {
      left: '15%',
      right: '5%',
      top: '20%',
      bottom: '15%',
      containLabel: true
    },
    series: [
      {
        name: '最低薪资',
        type: 'bar',
        barGap: 0,
        barWidth: '30%',
        data: minSalaryCounts,
        itemStyle: {
          color: 'rgba(185, 142, 109, 0.7)',
          borderRadius: [0, 4, 4, 0]
        },
        label: {  // 移除常驻标签
          show: false
        },
        
      },
      {
        name: '最高薪资',
        type: 'bar',
        barWidth: '30%',
        data: maxSalaryCounts,
        itemStyle: {
          color: 'rgba(169, 113, 66, 0.9)',
          borderRadius: [0, 4, 4, 0]
        },
        label: {  // 移除常驻标签
          show: false
        },
        
      }
    ],
    backgroundColor: 'transparent'
  };

  myChart.setOption(option);
  resizeChart();

  window.addEventListener('resize', resizeChart);
};

onMounted(() => {
  initChart();
});

onBeforeUnmount(() => {
  if (myChart) {
    window.removeEventListener('resize', resizeChart);
    myChart.dispose();
  }
});
</script>

<style scoped>
.DoctorSalaryChart {
  width: 100%;
  height: 100%;
  background-color: transparent;
  /* 移除边框 */
  border-radius: 4px;
  box-sizing: border-box;
  overflow: hidden;
}
</style>