<template>
  <div class="doctor-skills-wordcloud" ref="wordcloud" style="height: 330px;"></div>
</template>

<script setup lang="ts">
import { onMounted, ref, nextTick } from 'vue';
import * as echarts from 'echarts';
import 'echarts-wordcloud';

const wordcloud = ref<HTMLElement | null>(null);

// 静态数据 - 医师技能
const doctorSkills = [
  { skillRequirement: "中医医师资格证", count: 3566, job: "医师" },
  { skillRequirement: "无销售性质", count: 2095, job: "医师" },
  { skillRequirement: "中医执业医师资格证", count: 1910, job: "医师" },
  { skillRequirement: "执业药师资格证", count: 1211, job: "医师" },
  { skillRequirement: "中医内科", count: 1169, job: "医师" },
  { skillRequirement: "接受兼职", count: 1043, job: "医师" },
  { skillRequirement: "中医科医生", count: 1004, job: "医师" },
  { skillRequirement: "有中医科医生经验", count: 684, job: "医师" },
  { skillRequirement: "独立诊疗经验", count: 631, job: "医师" },
  { skillRequirement: "中医医生", count: 621, job: "医师" },
  { skillRequirement: "针灸科", count: 485, job: "医师" },
  { skillRequirement: "门诊部/诊所", count: 447, job: "医师" },
  { skillRequirement: "中医推拿", count: 445, job: "医师" },
  { skillRequirement: "中西医结合医学", count: 432, job: "医师" },
  { skillRequirement: "可兼职", count: 399, job: "医师" },
  { skillRequirement: "有销售性质", count: 392, job: "医师" },
  { skillRequirement: "中医执业医师证", count: 322, job: "医师" },
  { skillRequirement: "中医儿科", count: 305, job: "医师" },
  { skillRequirement: "中医馆", count: 304, job: "医师" },
  { skillRequirement: "中医妇科", count: 279, job: "医师" }
];

// 根据计数获取颜色
function getColorByCount(count: number): string {
  // 获取排序后的前5个技能
  const top5Skills = doctorSkills
    .sort((a, b) => b.count - a.count)
    .slice(0, 5)
    .map(skill => skill.skillRequirement);

  // 检查当前技能是否在前5名中
  const isTop5 = top5Skills.includes(doctorSkills.find(skill => skill.count === count)?.skillRequirement || '');

  return isTop5 ? "#6F4C3E" : "#B98E6D";
}

onMounted(async () => {
  await nextTick(); // 确保DOM已渲染
  
  if (!wordcloud.value) {
    console.error('Wordcloud container not found');
    return;
  }

  // 提取技能名称和计数，设置颜色
  const wordCloudData = doctorSkills.map(item => ({
    name: item.skillRequirement,
    value: item.count,
    itemStyle: {
      color: getColorByCount(item.count)
    }
  }));

  // 初始化 ECharts 实例
  const myChart = echarts.init(wordcloud.value);

  // 配置词云图的选项
  const option = {
    title: {
      text: '医师技能词云',
      left: 'center',
      textStyle: {
        fontSize: 18,
        fontWeight: 'bold'
      }
    },
    tooltip: {
      show: true,
      formatter: (params: any) => `${params.name}: ${params.value}次`
    },
    series: [{
      type: 'wordCloud',
      shape: 'circle',
      left: 'center',
      top: 'center',
      width: '90%',
      height: '90%',
      right: null,
      bottom: null,
      sizeRange: [2, 20], // 调整字体大小范围
      rotationRange: [-90, 90], // 调整旋转角度范围
      rotationStep: 45,
      gridSize: 8,
      drawOutOfBound: false,
      textStyle: {
        fontFamily: 'sans-serif',
        fontWeight: 'bold'
      },
      emphasis: {
        focus: 'self',
        textStyle: {
          shadowBlur: 10,
          shadowColor: '#333'
        }
      },
      data: wordCloudData
    }]
  };

  // 设置图表的选项
  myChart.setOption(option);

  // 响应式调整
  window.addEventListener('resize', () => {
    myChart.resize();
  });
});
</script>

<style scoped>
.doctor-skills-wordcloud {
  border: 1px solid #ccc;
  margin: 5px auto;
  border-radius: 8px;
}
</style>
