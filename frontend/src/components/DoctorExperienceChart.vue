<template>
  <div class="transparent-container">
    <div ref="echartsRef" class="echarts-wrapper"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';

const echartsRef = ref<HTMLElement | null>(null);

const drawChart = () => {
  if (echartsRef.value) {
    const myChart = echarts.init(echartsRef.value, null, {
      renderer: 'canvas'
    });

    // 数据按颜色从高到低排序（#B98E6D → #8C6B4F）
    const data = [
      { value: 35, name: '5-10年', itemStyle: { color: '#B98E6D' } },
      { value: 30, name: '3-5年', itemStyle: { color: '#D4BFA8' } },
      { value: 25, name: '1-3年', itemStyle: { color: '#E8D5C5' } },
      { value: 20, name: '经验不限', itemStyle: { color: '#A67C52' } },
      { value: 15, name: '10年以上', itemStyle: { color: '#8C6B4F' } },
      { value: 10, name: '1年以内', itemStyle: { color: '#6A4F3D' } }, // 补充深色
      { value: 5, name: '在校/应届', itemStyle: { color: '#4A3524' } }  // 补充最深色
    ];

    myChart.setOption({
      backgroundColor: 'transparent',
      title: {
        text: '医师对工作经验的要求',
        textStyle: { color: '#000' },
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}: {c} ({d}%)'
      },
      series: [{
        name: '工作经验要求',
        type: 'pie',
        radius: [50, 100], // 南丁格尔图关键参数
        center: ['50%', '50%'],
        roseType: 'radius', // 启用南丁格尔效果
        itemStyle: {
          borderRadius: 5,
          borderColor: '#fff',
          borderWidth: 1
        },
        label: {
          color: 'rgba(125,125, 125, 0.8)', // 设置标签颜色
          formatter: '{b}'
        },
        labelLine: {
          length: 10,
          length2: 20
        },
        data: data
      }]
    }, { notMerge: true });
  }
};

onMounted(() => {
  drawChart();
});
</script>

<style scoped>
.transparent-container {
  width: 100%;
  height: 100%;
  background: transparent !important;
}
.echarts-wrapper {
  width: 100%;
  height: 100%;
  background: transparent !important;
}
</style>