<template>
  <div class="YunLeiDa ">
   
    <div ref="chartRef" class="chart-container"> 
      <p class="YunLeiDa-footer">数据来源：2024年年报</p>
      </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import * as echarts from 'echarts';

const chartRef = ref<HTMLElement | null>(null);
let chart: echarts.ECharts | null = null;

const initChart = () => {
  if (!chartRef.value) return;
  chart = echarts.init(chartRef.value);

  const option = {
    title: {
      //text: '云南白药集团产品覆盖度',
      left: 'left',  
      textStyle: {
        color: '#333',
        fontSize: 13, // 调整字体大小
      },
    },
    tooltip: true, // 鼠标悬停提示
    legend: {
      data: ['产品覆盖度'],
      orient: 'vertical',
      left: 'right',
      textStyle: {
        fontSize: 12, // 调整字体大小
      },
    },
    radar: {
      name: {
        textStyle: {
          color: '#333',
          fontSize: 12, // 调整指示器文字大小
        },
      },
      indicator: [
        //416个品种的产品布局。在医药产品领城,拥有567个药品批准文号、316个品种,其中含222个中成药品种
       { name: '产品品种', max: 500 },//416
       { name: '药品批文', max: 600 },//567
        { name: '医药品种类', max: 350 },//316
        { name: '中成药品种', max: 300 },//222
        { name: '其他领域品种', max: 100 },//100
        { name: '医药物流覆盖', max: 20 },//云南省16
        { name: '全球销售范围', max: 34 },//全国34甚至国外
      ],
      radius: '72%', // 调整雷达图的大小
    },
    series: [
      {
        name: '产品覆盖度',
        type: 'radar',
        data: [
          {
            value: [416,567,316, 222, 100, 16,34],
            name: '产品覆盖度',
            areaStyle: {},
            symbol: 'circle',
            symbolSize: 6,
            lineStyle: {
              color: '#3890ff',
            },
            areaStyle: {
              color: 'rgba(56, 144, 255, 0.3)',
            },
            label: {
              show: true, // 显示标签
              formatter: function (params: any) {
                return params.value; // 显示数值
              },
              position: 'outside', // 标签位置
              color: '#333', // 标签字体颜色
              fontSize: 10, // 标签字体大小
            },
          },
        ],
      },
    ],
  };

  chart.setOption(option);
};

const handleResize = () => {
  chart?.resize();
};

onMounted(() => {
  initChart();
  window.addEventListener('resize', handleResize);
});

onUnmounted(() => {
  chart?.dispose();
  window.removeEventListener('resize', handleResize);
});
</script>

<style scoped>
.chart-container {
  margin-left: -5%;
  width: 100%;
  height: 300px;
  background-color: rgba(75, 192, 192, 0); /* 设置背景颜色为透明 */
}
.YunLeiDa-footer{
  text-align: center;
  color: #878484bc;
  font-size: 9px;
}
</style>