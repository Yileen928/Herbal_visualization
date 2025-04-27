<template>
  <div class="YunAllProductd" style="width: 110%; height: 100%; margin: auto">
    <div ref="chartRef" style="width: 100%; height: 115%"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue';
import * as echarts from 'echarts';

interface ProductData {
  productsName: string;
  commentsSum: number;
}

const chartRef = ref<HTMLDivElement | null>(null);
let chartInstance: echarts.ECharts | null = null;

const groupSize = 2; // 每组2个产品组成一对
let dataList: ProductData[] = [];
let timer: ReturnType<typeof setInterval> | null = null;
let currentStart = 0;

async function fetchProductComments(): Promise<ProductData[]> {
  try {
    const res = await fetch('http://106.55.169.134:10010/comments/all');
    if (!res.ok) {
      console.error('接口请求失败:', res.statusText);
      return [];
    }
    const data = await res.json();
    // 过滤有效数据，commentsSum转number，不过滤产品名
    const filtered = data
      .filter(
        (item: any) =>
          item.productsName && !isNaN(Number(item.commentsSum))
      )
      .map((item: any) => ({
        productsName: item.productsName,
        commentsSum: Number(item.commentsSum),
      }));
    filtered.sort((a, b) => a.commentsSum - b.commentsSum);
    return filtered;
  } catch (error) {
    console.error('请求异常:', error);
    return [];
  }
}

function initChart() {
  if (!chartRef.value) return;
  if (chartInstance) {
    chartInstance.dispose();
  }
  chartInstance = echarts.init(chartRef.value);

  const option = {
    title: {
      text: '产品销量对比',
      left: 'center',
      top: 20,
      textStyle: {
        fontSize: 18,
        fontWeight: 'bold',
        color: '#666',
      },
    },
tooltip: {
  trigger: 'axis',
  axisPointer: { type: 'shadow' },
  formatter: function (params) {
    const leftName = params[0].name.split(' vs ')[0]; 
    const rightName = params[1].name.split(' vs ')[1]; 
    const leftValue = params[0].value;
    const rightValue = params[1].value; 

    return `${leftName} VS ${rightName}<br />
       ${leftValue.toLocaleString()} VS ${rightValue.toLocaleString()}`;
  }
},
    legend: {
      data: ['北京同仁堂', '云南白药'],
      bottom: 4,
    },
    grid: { left: 65, right: 20, top: 70, bottom: 70 },
    xAxis: {
      type: 'category',
      data: [],
      axisLine: { lineStyle: { color: '#666' } },
      axisLabel: { rotate: 0, color: '#666', interval: 0 },
      show: false // 完全隐藏 x 轴
    },
    yAxis: {
      type: 'value',
      axisLine: { lineStyle: { color: '#666' } },
      axisLabel: { color: '#666' },
      min: 'dataMin',
      max: 'dataMax',
      fontsize: 8,
    },
    series: [
      {
        name: '北京同仁堂',
        type: 'bar',
        data: [],
        barGap: '15%',
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(192,127,73,0.8)' },
            { offset: 1, color: 'rgba(192,127,73,0.3)' },
          ]),
        },
        label: { show: true, position: 'top' },
      },
      {
        name: '云南白药',
        type: 'bar',
        data: [],
        barGap: '20%',
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [

            { offset: 0, color: 'rgba(91,143,249,0.8)' },
            { offset: 1, color: 'rgba(91,143,249,0.3)' },
          ]),
        },
        label: { show: true, position: 'top' },
      },
    ],
  };

  chartInstance.setOption(option);
}


function getLabel(name: string): string {
  if (name === '云南白药' || name === '北京同仁堂') return '';
  return name;
}

function updateChart(startIdx: number) {
  if (!chartInstance || dataList.length === 0) return;

  // 取4个数据，如果不足4个，则用空数据填充
  const sliceData = dataList.slice(startIdx, startIdx + groupSize * 2);

  while (sliceData.length < groupSize * 2) {
    sliceData.push({ productsName: '', commentsSum: 0 });
  }

  const categoryLabels: string[] = [];
  const seriesA: number[] = [];
  const seriesB: number[] = [];

  for (let i = 0; i < groupSize * 2; i += 2) {
    const left = sliceData[i];
    const right = sliceData[i + 1];
    // x轴标签替换
    categoryLabels.push(
      `${getLabel(left.productsName) || '-'} vs ${getLabel(right.productsName) || '-'}`
    );
    seriesA.push(left.commentsSum);
    seriesB.push(right.commentsSum);
  }

  chartInstance.setOption({
    xAxis: {
      data: categoryLabels,
    },
    series: [
      {
        name: '北京同仁堂',
        data: seriesA,
      },
      {
        name: '云南白药',
        data: seriesB,
      },
    ],
  });
}

function startAutoScroll() {
  if (timer) clearInterval(timer);
  if (dataList.length <= groupSize * 2) return;

  timer = setInterval(() => {
    currentStart += groupSize * 2;
    if (currentStart >= dataList.length) currentStart = 0;
    updateChart(currentStart);
  }, 3000);
}

onMounted(async () => {
  dataList = await fetchProductComments();
  if (dataList.length === 0) {
    console.warn('无有效数据，图表无法渲染');
    return;
  }
  initChart();
  updateChart(0);
  startAutoScroll();

  if (chartRef.value) {
    const resizeObserver = new ResizeObserver(() => {
      chartInstance?.resize();
    });
    resizeObserver.observe(chartRef.value);

    onBeforeUnmount(() => {
      resizeObserver.disconnect();
    });
  }
});

onBeforeUnmount(() => {
  if (timer) clearInterval(timer);
  if (chartInstance) {
    chartInstance.dispose();
    chartInstance = null;
  }
});
</script>

<style scoped>
.YunAllProductd {
  border-radius: 8px;
  padding: 4px;
}
</style>
