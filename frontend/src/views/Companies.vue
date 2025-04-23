<template>
  <div class="companies">
    <div class="companies-background">
      <div ref="mapContainer" class="map-container"></div>
      <div class="map-footer">
        <span>数据来源：爱企查</span>
      </div>
      <div v-if="showKunmingInfo" class="info-panel right">
        <h4 class="yunnanbaiyao" @click="goToYunnanBaiyao">云南白药集团股份有限公司</h4>
        <h5 style="text-align: left;">公司概况</h5>
        <p style="margin-top: -4%;">
          云南白药集团创制于1902年，由云南民间名医曲焕章根据云南民间中草药配方创制出"曲焕章百宝丹"，后更名为"云南白药"，以止血愈伤功效闻名。
        </p>
        <p>1971年，根据周恩来总理指示，国营云南白药厂正式成立。</p>
        <p>
          1993年，云南白药完成股份制改革，并在深圳证券交易所成功上市，成为云南第一家上市公司。
        </p>
        <p>2001年，云南白药集团股份有限公司成立，成为云南白药的控股母公司。</p>
        <p> 
          2004-2025年，云南白药集团在全世界各大交易所上市，均成为全球首家中药上市公司。
        </p>
        <p style="margin-top: 20px">
          云南白药集团通过持续的产品创新和市场拓展，逐步从传统的中成药企业转型为涵盖药品、健康品、中药资源等多领域的综合性药企。其经典产品如云南白药气雾剂、创可贴、牙膏等，
          不仅巩固了其在医药领域的领先地位，还成功跨界进入消费品市场，成为国内知名的"国民级"品牌。
        </p>
        <div class="stats"></div>
        <div class="decorative-icon">
          <img src="../assets/images/yunnanbaiyao.png" alt="云南白药图标">
        </div>
      </div>


      <div v-if="showBaoshanInfo" class="info-panel left">
        <h4 class="Tengyao" >云南腾药制药股份有限公司</h4>
        <h5 style="text-align: left;">公司概况</h5>
        <p style="margin-top: -4%;">云南腾药制药股份有限公司（以下简称"腾药"）发祥于拥有近400年历史的"药王宫"，前身为腾冲制药厂，成立于1956年。</p>
        <p>1997年，经腾冲县政府批准，腾冲制药厂改制为股份合作制企业。</p>
        <p>2011年9月，完成股份制改造，并更名为云南腾药制药股份有限公司。公司位于云南省腾冲市，是一家以心脑血管现代中药为主，传统中药为辅，功效日化保健品为补的国家级高新技术企业</p>
        <p style="margin-top: 15px">腾药的主营业务涵盖药品生产、研发、销售、中药饮片加工及中药材种养殖。
          公司主要剂型包括注射剂、丸剂、片剂、颗粒剂、散剂、酒剂、酊剂、糖浆剂等，拥有130个国药准字批文，87个品种，137个品规。
          其代表品种有心脉隆注射液（国家二类新药）、藿香正气水、安宫牛黄丸、六味地黄丸、人参再造丸等。
          腾药通过道地选材、遵古炮制与现代科技相结合，不断推出新产品，满足市场需求。</p>
        <div class="stats"></div>
        <div class="decorative-icon">
          <img src="../assets/images/tengyao.jpg" alt="云南腾药图标">
        </div>
      </div>


      
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from "vue";
import * as echarts from "echarts";
import yunnanJson from "../assets/yunnan.json";
import { useRouter } from "vue-router";

const mapContainer = ref<HTMLElement | null>(null);
const showKunmingInfo = ref(false);
const showBaoshanInfo = ref(false);
const router = useRouter();
let chartInstance: echarts.ECharts | null = null;

// 城市数据
const cityData = [
  { 
    name: "昆明市", 
    value: 8980, 
    coord: [102.833, 24.879],
    panelCoord: [105, 25] // 右侧信息框位置
  },
  { 
    name: "保山市", 
    value: 3137, 
    coord: [99.161, 25.112],
    panelCoord: [95, 25] // 左侧信息框位置
  },
  { name: "曲靖市", value: 5527 },
  { name: "玉溪市", value: 1578 },
  { name: "昭通市", value: 3302 },
  { name: "丽江市", value: 4523 },
  { name: "普洱市", value: 2722 },
  { name: "临沧市", value: 1431 },
  { name: "楚雄彝族自治州", value: 2248 },
  { name: "红河哈尼族彝族自治州", value: 3665 },
  { name: "文山壮族苗族自治州", value: 4978 },
  { name: "西双版纳傣族自治州", value: 910 },
  { name: "大理白族自治州", value: 3721 },
  { name: "德宏傣族景颇族自治州", value: 711 },
  { name: "怒江傈僳族自治州", value: 1459 },
  { name: "迪庆藏族自治州", value: 2238 },
];

// 添加跳转方法
const goToYunnanBaiyao = () => {
  router.push('/yunnanbaiyao');
};

// 初始化地图
onMounted(() => {
  if (!mapContainer.value) return;

  chartInstance = echarts.init(mapContainer.value);
  echarts.registerMap("yunnan", yunnanJson);

  const option = {
    title: {
      text: "云南省中药相关企业分布",
      left: "center",
      top: "2%",
      textStyle: {
        fontSize: 24,
        color: "#333",
      },
    },
    tooltip: {
      trigger: "item",
      formatter: (params: any) => {
        return `${params.name}<br/>企业数量: ${params.value || 0}`;
      },
    },
    visualMap: {
      min: 0,
      max: 9000,
      left: "30%",
      top: "bottom",
      text: ["高", "低"],
      calculable: true,
      inRange: {
        color: ["#E8C4A3", "#C07F49"],
      },
    },
    series: [
      {
        name: "企业分布",
        type: "map",
        map: "yunnan",
        roam: false,
        zoom: 1.1,
        label: {
          show: true,
          color: "#333",
        },
        emphasis: {
          label: {
            show: true,
            color: "#333",
          },
          itemStyle: {
            areaColor: "#D4B483",
          },
        },
        itemStyle: {
          areaColor: "#E8C4A3",
          borderColor: "#8C4E2A",
          borderWidth: 0.5,
        },
        data: cityData.map(item => ({
          name: item.name,
          value: item.value
        }))
      },
      {
        type: "lines",
        coordinateSystem: "geo",
        zlevel: 2,
        effect: {
          show: true,
          period: 6,
          trailLength: 0.7,
          color: "#8c4e2a",
          symbolSize: 3
        },
        lineStyle: {
          color: "#8c4e2a",
          width: 1,
          opacity: 0.6,
          curveness: 0.2
        },
        data: [
          {
            coords: [
              cityData[0].coord,    // 昆明市坐标
              cityData[0].panelCoord // 右侧信息框坐标
            ]
          },
          {
            coords: [
              cityData[1].coord,    // 保山市坐标
              cityData[1].panelCoord // 左侧信息框坐标
            ]
          }
        ]
      },
      {
        type: "effectScatter",
        coordinateSystem: "geo",
        zlevel: 3,
        rippleEffect: {
          brushType: "stroke"
        },
        label: {
          show: true,
          position: "right",
          formatter: "{b}",
          fontSize: 12,
          color: "#5a3921"
        },
        symbolSize: 8,
        itemStyle: {
          color: "#8c4e2a"
        },
        data: cityData.filter(item => item.name === "昆明市" || item.name === "保山市")
          .map(item => ({
            name: item.name,
            value: [...item.coord, item.value]
          }))
      }
    ]
  };

  chartInstance.setOption(option);

  // 添加点击事件
  chartInstance.on("click", (params: any) => {
    if (params.name === "昆明市") {
      showKunmingInfo.value = true;
      showBaoshanInfo.value = false;
    } else if (params.name === "保山市") {
      showBaoshanInfo.value = true;
      showKunmingInfo.value = false;
    } else {
      showKunmingInfo.value = false;
      showBaoshanInfo.value = false;
    }
  });
});

onBeforeUnmount(() => {
  if (chartInstance) {
    chartInstance.dispose();
    chartInstance = null;
  }
});
</script>

<style scoped>
@import './companies.css';
</style>