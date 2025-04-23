<template>
  <div ref="chartRef" class="parallel-coordinates-chart"></div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import * as echarts from 'echarts';

const chartRef = ref<HTMLElement | null>(null);

onMounted(() => {
  if (!chartRef.value) return;

  const chart = echarts.init(chartRef.value);

  const option = {
    title: {
      text: '生产指标对比',
      //subtext: '数据来源：官网、搜狐',
      left: 'left',
      top: -4,
      textStyle: {
        fontSize: 14,
        fontWeight: 'bold',
      }
    },
    tooltip: {
      trigger: 'item',
      formatter: function(params: any) {
        const data = params.data;
        const company = params.seriesName;
        return `
          <strong>${company}</strong><br/>
          GMP认证等级: ${data[0]}<br/>
          成品检验耗时: ${data[1]}天<br/>
          车间洁净度: ${data[2]}<br/>
          微生物检出率: ${data[3]}<br/>
          原料溯源比例: ${data[4]}%<br/>
          设备OEE: ${data[5]}%<br/>
          单批次产能: ${data[6]}
        `;
      }
    },
    parallelAxis: [
      // 上方指标
      {
        dim: 0,
        name: 'GMP\n认证',
        type: 'category',
        data: ['30万级', '30万级'],
        position: 'top',
        axisLabel: {
          interval: 0,
          rotate: 0
        }
      },
      {
        dim: 1,
        name: '成品检验耗时(天)',
        type: 'value',
        min: 0,
        max: 10,
        position: 'top'
      },
      {
        dim: 2,
        name: '车间\n洁净度',
        type: 'category',
        data: ['30万级', '30万级'],
        position: 'top'
      },
      // 下方指标
      {
        dim: 3,
        name: '微生物\n检出率',
        type: 'category',
        data: ['行业标准以下'],
        position: 'bottom'
      },
      {
        dim: 4,
        name: '原料溯源\n比例(%)',
        type: 'value',
        min: 50,
        max: 100,
        position: 'top'
      },
      {
        dim: 5,
        name: '设备\nOEE(%)',
        type: 'value',
        min: 50,
        max: 90,
        position: 'top'
      },
      {
        dim: 6,
        name: '单批次\n产能',
        type: 'category',
        data: ['数万瓶', '10-20万', '60-80万'],
        position: 'top'
      }
    ],
    series: [
      {
        name: '昆中药',
        type: 'parallel',
        lineStyle: {
          width: 4,
          color: '#8B4513' // 棕色
        },
        emphasis: {
          lineStyle: {
            width: 6,
            shadowColor: 'rgba(0, 0, 0, 0.3)',
            shadowBlur: 10,
            shadowOffsetY: 5
          }
        },
        data: [
          [
            '30万级',       // GMP认证
            6,             // 成品检验耗时
            '30万级',      // 车间洁净度
            '行业标准以下', // 微生物检出率
            80,            // 原料溯源比例
            85,            // 设备OEE
            '60-80万'      // 单批次产能
          ]
        ]
      },
      {
        name: '胡庆余堂',
        type: 'parallel',
        lineStyle: {
          width: 4,
          color: '#FFA500' // 橙色
        },
        emphasis: {
          lineStyle: {
            width: 6,
            shadowColor: 'rgba(0, 0, 0, 0.3)',
            shadowBlur: 10,
            shadowOffsetY: 5
          }
        },
        data: [
          [
            '30万级',           // GMP认证
            4,                 // 成品检验耗时
            '30万级',          // 车间洁净度
            '行业标准以下', // 微生物检出率
            70,                // 原料溯源比例
            70,                // 设备OEE
            '10-20万'          // 单批次产能
          ]
        ]
      }
    ],
    legend: {
      data: ['昆中药', '胡庆余堂'],
      bottom: -8,
      itemGap: 20,
      textStyle: {
        fontSize: 10
      }
    },
    parallel: {
      left: '5%',
      right: '15%',
      bottom: '5%',
      top: '30%',
      parallelAxisDefault: {
        type: 'value',
        nameLocation: 'end',
        nameGap: 20,
        nameTextStyle: {
          fontSize: 12,
          fontWeight: 'bold'
        },
        axisLine: {
          lineStyle: {
            color: '#555',
            width: 1
          }
        },
        axisTick: {
          lineStyle: {
            color: '#555'
          }
        },
        axisLabel: {
          color: '#333',
          fontSize: 11,
          margin: 10
        }
      }
    },
    grid: {
      top: 80
    }
  };

  chart.setOption(option);

  const resizeHandler = () => {
    chart.resize();
  };
  window.addEventListener('resize', resizeHandler);

  onUnmounted(() => {
    window.removeEventListener('resize', resizeHandler);
    chart.dispose();
  });
});
</script>

<style scoped>
.parallel-coordinates-chart {
  width: 110%;
  height: 100%;
  border-radius: 8px;
  padding: 15px;
  margin: 0 0;
}
</style>