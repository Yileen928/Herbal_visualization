<template>
  <div class="DoctorExperienceChart" ref="echartsRef"></div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import * as echarts from 'echarts';

const echartsRef = ref<HTMLElement | null>(null);

// 获取药师工作经验数据
const fetchData = async () => {
  try {
    const response = await axios.get('http://106.55.169.134:10010/medical/experience/counts');
    const data = response.data;

    // 过滤出药师的数据
    const PharmacistData = data.filter((item: { job: string }) => item.job === '药师');

    // 提取工作经验和数量
    const experience = PharmacistData.map((item: { workExperience: string }) => item.workExperience);
    const counts = PharmacistData.map((item: { count: number }) => item.count);

    // 结合经验和数量，并根据数量排序
    const sortedData = experience.map((exp, index) => ({
      name: exp,
      value: counts[index]
    })).sort((a, b) => b.value - a.value); // 从高到低排序

    // 绘制南丁格尔玫瑰图
    drawChart(sortedData);
  } catch (error) {
    console.error('获取数据时出错:', error.response ? error.response.data : error.message);
  }
};

// 绘制图表
const drawChart = (sortedData: { name: string; value: number }[]) => {
  if (echartsRef.value) {
    const myChart = echarts.init(echartsRef.value);

    const option = {
      title: {
        text: '药师对工作经验的要求',
        left: 'left',
        padding: [15, 5],
        textStyle: {
          color: '#333' // 标题文字颜色
        }
      },
      tooltip: {
        trigger: 'item',
        formatter: '{b}: {c} ({d}%)', // 鼠标悬停时显示名称、值和占比
      },
      series: [
        {
          name: '工作经验',
          type: 'pie',
          radius: [50, 100],
          center: ['50%', '50%'],
          roseType: 'area',
          label: {
            show: true, // 始终显示标签
            color: "gray", // 设置文字颜色为灰色
            formatter: (params) => {
              return params.data.value > 0 ? params.name : ''; // 只有在有值时显示名称
            },
          },
          itemStyle: {
            borderRadius: 8,
            color: (params) => {
              const colors = ["#E4C6B2", "#E1B7A6"];
              return colors[params.dataIndex % colors.length];
            }
          },
          data: sortedData
        }
      ],
      backgroundColor: 'transparent', // 设置背景颜色为透明
      legend: {
        show: false // 隐藏图例
      }
    };

    myChart.setOption(option);
    myChart.resize(); // 确保在数据更新后重绘图表
  }
};

onMounted(() => {
  fetchData(); // 组件挂载时获取数据
});
</script>

<style scoped>
.DoctorExperienceChart {
  width: 100%; /* 确定图表容器的宽度 */
  height: 370px; /* 设置图表容器的高度为固定值，确保图表显示正常 */
  margin-top: -3%; /* 调整顶部间距 */
  background-color: #c0bab254; /* 设置背景颜色 */
  border-radius: 8px; /* 设置圆角 */
}
</style>
