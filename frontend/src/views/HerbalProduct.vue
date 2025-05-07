<template>
  <div class="herbal-product">
    <div class="herbal-product-background">
      <!-- 第一行：趋势图 -->
      <div class="herbal-product-container1" @click="goToCompanies">
        <HerbalTrendChart />
        <div class="herbal-product-chart2">
          <PharmacyNum />
        </div>
      </div>

      <div class="herbal-product-container2">
        <div class="herbal-product-chart3" style="position: relative">
          <div
            id="map-container"
            ref="mapContainer"
            style="width: 100%; height: 530px"
          ></div>
          <div class="map-footer">
            <span>数据来源：数字云药平台、中医百科网站</span>
          </div>
        </div>
        
        <!-- 药品表格组件 -->
        <MedicineTable />
      </div>

      <!-- 第三行：采购图表 -->
      <div class="herbal-product-container3">
        <PurchaseChart />
        <div class="herbal-product-chart2" @click="goToHci">
          <PharmacyType />
        </div>
      </div>  
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import * as echarts from 'echarts'
import yunnanJson from '../assets/yunnan.json'
import HerbalTrendChart from '../components/HerbalTrendChart.vue' // 中药材趋势图
import MedicineTable from'../components/MedicineTable.vue' // 药品表格
import PurchaseChart from '../components/PurchaseChart.vue' // 采购投入赋码图
import PharmacyType from '../components/PharmacyType.vue' // 药店类型
import PharmacyNum from '../components/PharmacyNum.vue' // 药店数量
import 'echarts-gl'  // 需要安装并引入这个包
const router = useRouter()
const mapContainer = ref(null)

const goToCompanies = () => {
  router.push('/companies');
};
const goToHci = () => {
  router.push('/hci');
};


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

// 地图点击事件处理
const handleMapClick = (params) => {
  if (params.data) {
    router.push('/herbal-product')
  }
}

// 初始化地图
onMounted(() => {
  const chart = echarts.init(mapContainer.value)
  echarts.registerMap('yunnan', yunnanJson)

  const option = {
    title: {
      text: '云南省中药材地理分布',
      left: 'center',
      textStyle: {
        fontSize: 24,
        color: '#333',
        padding: [20, 0, 0, 0]
      },
    },
    grid: {
      top: '10%',
      bottom: '10%'
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
      left: '5%',
      bottom: '5%',
      text: ['高', '低'],
      calculable: true,
      inRange: {
        color: ['#E8C4A3', '#C07F49'],
      },
      textStyle: {
        color: '#333'
      }
    },
    series: [
      {
        name: '中药材品种',
        type: 'map3D',
        map: 'yunnan',
        roam: true,
        aspectScale: 0.85,
        layoutCenter: ['50%', '50%'],
        layoutSize: '95%',
        label: {
          show: false  // 将 show 设置为 false，隐藏标签
        },
        itemStyle: {
          areaColor: '#E8C4A3',
          borderColor: '#fff',
          borderWidth: 1,
          opacity: 1
        },
        emphasis: {
          label: {
            show: false,  // 悬停时也不显示标签
            color: '#fff',
            fontSize: 13,
            fontWeight: 'bold'
          },
          itemStyle: {
            areaColor: '#C07F49',
            opacity: 1,
            shadowBlur: 20,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        viewControl: {
          distance: 150,      // 保持视角距离
          alpha: 50,          // 调整俯仰角度，使视角更加平缓
          beta: 0,            // 初始旋转角度
          autoRotate: true,   // 保持自动旋转
          autoRotateSpeed: 3, // 降低旋转速度，使其更加平滑
          damping: 0.9,       // 增加阻尼系数，减少卡顿
          rotateSensitivity: 0, // 禁用鼠标旋转，避免干扰
          zoomSensitivity: 0,   // 禁用缩放
          panSensitivity: 0,    // 禁用平移
          animation: true,
          animationDurationUpdate: 1500, // 增加动画时长
          animationEasingUpdate: 'linear' // 使用线性缓动，更加流畅
        },
        light: {
          main: {
            intensity: 1.2,
            shadow: true
          },
          ambient: {
            intensity: 0.3
          }
        },
        data: Object.entries(cityData).map(([name, data]) => ({
          name,
          value: data.medicineCount,
          height: data.medicineCount * 0.8  // 将高度系数从 2 降低到 0.8
        })),
      }
    ],
  }

  chart.setOption(option)
  chart.on('click', handleMapClick)
})
</script>

<style scoped>
/* 原有样式保持不变 */
@import "./HerbalProduct.css";
</style>