<template>
  <div class="comparison-radar" ref="chartRef" style="width: 100%; height: 100%;"></div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import * as echarts from 'echarts';

const chartRef = ref<HTMLElement>();

onMounted(() => {
  if (!chartRef.value) return;
  
  const chart = echarts.init(chartRef.value);
  
  const option = {
    title: {
      text: '生产体系对比',
      //subtext: '​​企业官方发布、​​政府及行业协会报告等',
      left: 'left',
    },
    legend: {
      data: ['昆中药', '胡庆余堂'],
      bottom: 15
    },
    radar: {
      shape: 'polygon',
      center: ['50%', '50%'],
      radius: '55%',
      indicator: [
        { name: '药材基地(亩)', max: 200, color: '#000000'  },
        { name: '生产线数量(种)', max: 15, color: '#000000'  },
        { name: '专利情况', max: 20, color: '#000000'  },
        { name: '研发投入(%)', max: 20, color: '#000000'  },
        { name: '产品种类', max: 200, color: '#000000'  }
      ],
      axisLine: {
        lineStyle: {
          color: 'rgba(155, 155, 155, 0.4)'
        }
      },
      splitArea: {
        show: false
      }
    },
    series: [{
      type: 'radar',
      data: [
        {
          value: [172, 11, 17, 20, 140],
          name: '昆中药',
          symbol: 'circle',
          symbolSize: 8,
          areaStyle: {
            color: 'rgba(135, 99, 51, 0.4)'
          },
          lineStyle: {
            color: 'rgba(135, 99, 51, 1)',
            width: 2
          },
          itemStyle: {
            color: 'rgba(135, 99, 51, 1)'
          }
        },
        {
          value: [107, 12, 13, 15, 180],
          name: '胡庆余堂',
          symbol: 'rect',
          symbolSize: 8,
          areaStyle: {
            color: 'rgba(245, 170, 109, 0.4)'
          },
          lineStyle: {
            color: 'rgba(245, 170, 109, 1)',
            width: 2
          },
          itemStyle: {
            color: 'rgba(245, 170, 109, 1)'
          }
        }
      ]
    }],
tooltip: {
      trigger: "item",
      formatter: (params: any) => {
        return `
          <div style="font-weight:bold;margin-bottom:5px"> ${params.name}</div>
          <div style="display:flex;justify-content:space-between">
            <span>​​生产基地​​(亩)：</span>
            <span>${params.value[0]}</span>
          </div>
          <div style="display:flex;justify-content:space-between">
            <span>生产线数量(种)：</span>
            <span>${params.value[1]}</span>
          </div>
          <div style="display:flex;justify-content:space-between">
            <span>生产线：</span>
            <span>${params.value[2]}</span>
          </div>
          <div style="display:flex;justify-content:space-between">
            <span>研发投入(%)：</span>
            <span>${params.value[3]}</span>
          </div>
          <div style="display:flex;justify-content:space-between">
            <span>产品种类：</span>
            <span>${params.value[4]}</span>
          </div>
          
        `;
      }
    },
  };

  chart.setOption(option);
  
  const resizeChart = () => chart.resize();
  window.addEventListener('resize', resizeChart);
  
  onUnmounted(() => {
    window.removeEventListener('resize', resizeChart);
    chart.dispose();
  });
});
</script>

<style scoped>
.comparison-radar {
  border-radius: 8px;
  padding: 10px;
}
</style>