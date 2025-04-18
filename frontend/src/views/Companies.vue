<template>
  <div class="companies">
    <div class="companies-background">
      <h3 class="title">云南省企业分布</h3>
      <div ref="mapContainer" class="map-container"></div>
      <div v-if="showKunmingInfo" class="info-panel right">
        <h4>昆明市企业概况</h4>
        <p>昆明市作为云南省省会，拥有全省最多的企业数量...</p>
        <div class="stats">
          
        </div>
        <div class="decorative-icon">昆</div>
      </div>
      <div v-if="showBaoshanInfo" class="info-panel left">
        <h4>保山市企业概况</h4>
        <p>保山市位于滇西边境，特色企业以中药材加工为主...</p>
        <div class="stats">
            
        </div>
        <div class="decorative-icon">保</div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from "vue";
import * as echarts from "echarts";
import yunnanJson from '../assets/yunnan.json';

console.log(yunnanJson);



const mapContainer = ref<HTMLElement | null>(null);
const showKunmingInfo = ref(false);
const showBaoshanInfo = ref(false);
let chartInstance: echarts.ECharts | null = null;

// 城市数据
const cityData = {
  '昆明市': { plantingArea: 16.1, agriculturalYield: 5.0, agriculturalValue: 30.9, processingOutput: 11.0, processingValue: 158.9, medicineCount: 38 },
  '曲靖市': { plantingArea: 90.1, agriculturalYield: 26.8, agriculturalValue: 82.7, processingOutput: 7.2, processingValue: 29.1, medicineCount: 35 },
  '玉溪市': { plantingArea: 9.3, agriculturalYield: 6.5, agriculturalValue: 4.6, processingOutput: 1.4, processingValue: 13.7, medicineCount: 19 },
  '保山市': { plantingArea: 51.6, agriculturalYield: 5.8, agriculturalValue: 39.3, processingOutput: 5.3, processingValue: 51.3, medicineCount: 29 },
  '昭通市': { plantingArea: 46.6, agriculturalYield: 5.2, agriculturalValue: 54.7, processingOutput: 1.1, processingValue: 36.4, medicineCount: 22 },
  '丽江市': { plantingArea: 31.3, agriculturalYield: 4.8, agriculturalValue: 17.7, processingOutput: 5.0, processingValue: 21.2, medicineCount: 38 },
  '普洱市': { plantingArea: 48.0, agriculturalYield: 9.7, agriculturalValue: 42.7, processingOutput: 2.5, processingValue: 13.8, medicineCount: 36 },
  '临沧市': { plantingArea: 69.0, agriculturalYield: 3.1, agriculturalValue: 11.7, processingOutput: 0.1, processingValue: 1.2, medicineCount: 18 },
  '德宏傣族景颇族自治州': { plantingArea: 26.6, agriculturalYield: 2.5, agriculturalValue: 13.3, processingOutput: 0.2, processingValue: 1.8, medicineCount: 12 },
  '怒江傈僳族自治州': { plantingArea: 127.1, agriculturalYield: 2.2, agriculturalValue: 6.6, processingOutput: 1.8, processingValue: 5.5, medicineCount: 6 },
  '迪庆藏族自治州': { plantingArea: 14.2, agriculturalYield: 2.5, agriculturalValue: 7.0, processingOutput: 0.3, processingValue: 0.8, medicineCount: 3 },
  '大理白族自治州': { plantingArea: 38.3, agriculturalYield: 4.8, agriculturalValue: 38.7, processingOutput: 2.8, processingValue: 41.2, medicineCount: 9 },
  '楚雄彝族自治州': { plantingArea: 44.0, agriculturalYield: 13.8, agriculturalValue: 37.0, processingOutput: 3.3, processingValue: 15.8, medicineCount: 6 },
  '红河哈尼族彝族自治州': { plantingArea: 160.9, agriculturalYield: 20.4, agriculturalValue: 51.4, processingOutput: 11.3, processingValue: 33.0, medicineCount: 15 },
  '文山壮族苗族自治州': { plantingArea: 229.5, agriculturalYield: 30.1, agriculturalValue: 88.8, processingOutput: 10.9, processingValue: 51.6, medicineCount: 16 },
  '西双版纳傣族自治州': { plantingArea: 28.0, agriculturalYield: 1.2, agriculturalValue: 8.4, processingOutput: 2.9, processingValue: 13.3, medicineCount: 23 }
};

// 初始化地图
onMounted(() => {
  const chart = echarts.init(mapContainer.value)
  echarts.registerMap('yunnan', yunnanJson)

  const option = {
    title: {
      text: '云南省中药材地理分布',
      left: 'center',
      top: '2%',
      textStyle: {
        fontSize: 24,
        color: '#333',
      },
    },
    gird:{
      top: '100%',
    },
    tooltip: {
      trigger: 'item',
      formatter: (params) => {
        const cityInfo = cityData[params.name]
        if (cityInfo) {
          return `${params.name}<br/>
            中药材种植面积：${cityInfo.plantingArea} 万亩<br/>
            农业产量：${cityInfo.agriculturalYield} 万吨<br/>
            农业产值：${cityInfo.agriculturalValue} 亿元<br/>
            加工产量：${cityInfo.processingOutput} 万吨<br/>
            加工产值：${cityInfo.processingValue} 亿元<br/>
            药材数量：${cityInfo.medicineCount} 种`
        }
        return params.name
      },
    },
    visualMap: {
      min: 0,
      max: 40,
      left: 'left',
      top: 'bottom',
      text: ['高', '低'],
      calculable: true,
      inRange: {
        color: ['#E8C4A3', '#C07F49'],
      },
    },
    series: [
      {
        name: '中药材品种',
        type: 'map',
        map: 'yunnan',
        roam: false,
        zoom:1.1,
        emphasis: {
          label: {
            show: true,
          },
        },
        data: Object.entries(cityData).map(([name, data]) => ({
          name,
          value: data.medicineCount,
        })),
      },
    ],
  }

  chart.setOption(option)
})
onBeforeUnmount(() => {
  if (chartInstance) {
    chartInstance.dispose();
  }
});
</script>

<style>
@import "./companies.css";
</style>
