<template>
    <div ref="chartRef" class="chart-container"></div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from "vue";
import * as echarts from "echarts";
import { useRouter } from 'vue-router';

const router = useRouter();
const chartRef = ref<HTMLElement | null>(null);
let chart: echarts.ECharts | null = null;

// 同仁堂数据
const tengyaoData = {
  value: [3200, 530, 2600, 400, 500, 1353, 28],
  name: "同仁堂",
  symbol: "circle",
  symbolSize: 6,
  lineStyle: {
    color: "rgba(226, 160, 109, 1)", // 棕色
    width: 2
  },
  areaStyle: {
    color: "rgba(226, 160, 109, 0.5)"
  },
  label: {
    show: true,
    formatter: "{c}",
    color: "#333",
    fontSize: 10,
    position: "top"
  }
};

// 云南白药数据
const yunnanbaiyaoData = {
  value: [3257, 567, 2580, 222, 420, 1353, 21],
  name: "云南白药",
  symbol: "circle",
  symbolSize: 6,
  lineStyle: {
    color: "#3890ff", // 蓝色
    width: 2
  },
  areaStyle: {
    color: "rgba(56, 144, 255, 0.3)"
  },
  label: {
    show: true,
    formatter: "{c}",
    color: "#333",
    fontSize: 10,
    position: "bottom"
  }
};

const initChart = () => {
  if (!chartRef.value) return;
  chart = echarts.init(chartRef.value);

  const option = {
    title: {
      text: "产品覆盖度对比",
      left: "center",
      textStyle: {
        color: "#666",
        fontSize: 18,
        fontWeight: "bold"
      },
      //subtext: "各自的24年年报及诚通证券研究所",
      triggerEvent: true // 允许触发事件
    },
    tooltip: {
      trigger: "item",
      formatter: (params: any) => {
        return `
          <div style="font-weight:bold;margin-bottom:5px">${params.seriesName} - ${params.name}</div>
          <div style="display:flex;justify-content:space-between">
            <span>产品品种：</span>
            <span>${params.value[0]}</span>
          </div>
          <div style="display:flex;justify-content:space-between">
            <span>药品批文：</span>
            <span>${params.value[1]}</span>
          </div>
          <div style="display:flex;justify-content:space-between">
            <span>医药品种类：</span>
            <span>${params.value[2]}</span>
          </div>
          <div style="display:flex;justify-content:space-between">
            <span>中成药品种：</span>
            <span>${params.value[3]}</span>
          </div>
          <div style="display:flex;justify-content:space-between">
            <span>非医药品种：</span>
            <span>${params.value[4]}</span>
          </div>
          <div style="display:flex;justify-content:space-between">
            <span>中药饮片：</span>
            <span>${params.value[5]}</span>
          </div>
          <div style="display:flex;justify-content:space-between">
            <span>全球销售范围：</span>
            <span>${params.value[6]}</span>
          </div>
        `;
      }
    },
    legend: {
      data: [
        {
          name: "同仁堂",
          icon: "circle",
          itemStyle: { color: "rgba(226, 160, 109, 1)" } // 棕色图例
        },
        {
          name: "云南白药",
          icon: "circle",
          itemStyle: { color: "#3890ff" } // 蓝色图例
        }
      ],
      orient: "vertical",
      right: 10,
      top: 40,
      textStyle: {
        fontSize: 12,
        color: "#333"
      }
    },
    radar: {
      center: ["45%", "55%"],
      radius: "58%",
      axisName: {
        color: "#666",
        fontSize: 12,
        padding: [3, 5]
      },
      indicator: [
        { name: "产品品种", max: 3300 },
        { name: "药品批文", max: 600 },
        { name: "医药品种类", max: 3000 },
        { name: "中成药品种", max: 400 },
        { name: "非医药品种", max: 500 },
        { name: "中药饮片", max: 1400 },
        { name: "全球销售范围", max: 30 }
      ],
      splitArea: {
        areaStyle: {
          color: ["rgba(150, 150, 150, 0.1)"]
        }
      }
    },
    series: [
      {
        name: "产品覆盖度",
        type: "radar",
        data: [tengyaoData, yunnanbaiyaoData]
      }
    ]
  };

  chart.setOption(option);


  // 添加点击事件监听
  chart.on('click', function(params) {
    if (params.componentType === 'title') {
      router.push('/competitor');  // 跳转到Competitor.vue页面
    }
  });

  window.addEventListener("resize", () => chart?.resize());
};

onMounted(() => {
  initChart();
});

onUnmounted(() => {
  chart?.dispose();
  window.removeEventListener("resize", () => chart?.resize());
});
</script>

<style scoped>
.chart-container {
  width: 100%;
  height: 100%;
    background-color: transparent;
}
</style>