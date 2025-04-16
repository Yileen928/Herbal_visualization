<template>
  <div class="herbal-product">
    <div class="herbal-product-background">
      <div class="herbal-product-container1">
        <div class="herbal-product-chart1"></div>
        <div class="herbal-product-chart2"></div>
      </div>
      <div class="herbal-product-container2">
        <div class="herbal-product-chart3" ref="yunnanMap" style="position: relative;">
          <div id="map-container" ref="mapContainer" style="width: 100%; height: 500px;"></div>
          <div class="map-footer" style="position: absolute; bottom: 10px; left: 0; right: 0; text-align: center;">
            <span>数据来源：数字云药平台、中医百科网站</span>
          </div>
        </div>
        <div class="herbal-product-chart2"></div>
      </div>
      <div class="herbal-product-container3">
        <div class="herbal-product-chart1"></div>
        <div class="herbal-product-chart2"></div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';
import { onMounted, ref } from 'vue';
import * as echarts from 'echarts';
import yunnanJson from '../assets/yunnan.json';

const router = useRouter();
const mapContainer = ref(null);

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

// 处理地图点击事件
const handleMapClick = (params) => {
  if (params.data) {
    router.push('/herbal-product');
  }
};

onMounted(() => {
  const chart = echarts.init(mapContainer.value);
  echarts.registerMap('yunnan', yunnanJson);

  const option = {
    title: {
      text: '云南省中药材地理分布',
      left: 'center',
      textStyle: {
        fontSize: 24,
        color: '#333'
      }
    },
    tooltip: {
      trigger: 'item',
      formatter: (params) => {
        const cityInfo = cityData[params.name];
        if (cityInfo) {
          return `${params.name}<br/>
            中药材种植面积：${cityInfo.plantingArea} 万亩<br/>
            农业产量：${cityInfo.agriculturalYield} 万吨<br/>
            农业产值：${cityInfo.agriculturalValue} 亿元<br/>
            加工产量：${cityInfo.processingOutput} 万吨<br/>
            加工产值：${cityInfo.processingValue} 亿元<br/>
            药材数量：${cityInfo.medicineCount} 种`;
        }
        return params.name; // 如果没有找到数据，返回城市名称
      }
    },
    visualMap: {
      min: 0,
      max: 230, // 根据数据调整
      left: 'left',
      top: 'bottom',
      text: ['高', '低'],
      calculable: true,
      inRange: {
        color: ['#e0f7e0', '#1a6d1a']
      }
    },
    series: [{
      name: '中药材品种',
      type: 'map',
      map: 'yunnan', // 使用云南省的地图
      roam: true,
      emphasis: {
        label: {
          show: true
        }
      },
      data: Object.entries(cityData).map(([name, data]) => ({
        name,
        value: data.medicineCount // 以药材数量为值
      }))
    }]
  };

  chart.setOption(option);

  // 监听点击事件
  chart.on('click', handleMapClick);

  // 监听窗口大小变化
  window.addEventListener('resize', () => {
    chart.resize();
  });
});
</script>

<style scoped>
@import './HerbalProduct.css';
</style>