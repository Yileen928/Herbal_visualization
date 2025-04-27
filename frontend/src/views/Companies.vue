<template>
  <div class="companies">
    <div class="companies-background">
      <div ref="mapContainer" class="map-container"></div>
      <div class="map-footer">
        <span>数据来源：爱企查</span>
      </div>

      <!-- 右侧信息框 -->
      <div
        v-if="selectedCity && isRightSideCity(selectedCity)"
        class="info-panel right"
      >
        <h4
          :class="cityInfo[selectedCity]?.titleClass || ''"
          @click="cityInfo[selectedCity]?.clickHandler"
        >
          {{ cityInfo[selectedCity]?.title || selectedCity }}
        </h4>
        <h5 style="text-align: left">公司概况</h5>
        <div
          v-html="cityInfo[selectedCity]?.description || '暂无详细信息'"
        ></div>
        <div class="stats"></div>
        <div class="decorative-icon" v-if="cityInfo[selectedCity]?.icon"></div>
      </div>

      <!-- 左侧信息框 -->
      <div
        v-if="selectedCity && isLeftSideCity(selectedCity)"
        class="info-panel left"
      >
        <h4 :class="cityInfo[selectedCity]?.titleClass || ''">
          {{ cityInfo[selectedCity]?.title || selectedCity }}
        </h4>
        <h5 style="text-align: left">公司概况</h5>
        <div
          v-html="cityInfo[selectedCity]?.description || '暂无详细信息'"
        ></div>
        <div class="stats"></div>
        <div class="decorative-icon" v-if="cityInfo[selectedCity]?.icon">
          <img
            :src="cityInfo[selectedCity]?.icon"
            :alt="`${selectedCity}图标`"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, reactive } from "vue";
import * as echarts from "echarts";
import yunnanJson from "../assets/yunnan.json";
import { useRouter } from "vue-router";

const mapContainer = ref<HTMLElement | null>(null);
const selectedCity = ref<string | null>(null);
const router = useRouter();
let chartInstance: echarts.ECharts | null = null;

// 定义右侧和左侧城市名单
const rightSideCities = [
  "昆明市",
  "昭通市",
  "曲靖市",
  "文山壮族苗族自治州",
  "红河哈尼族彝族自治州",
  "玉溪市",
  "西双版纳傣族自治州",
];
const leftSideCities = [
  "保山市",
  "普洱市",
  "楚雄彝族自治州",
  "丽江市",
  "大理白族自治州",
  "临沧市",
  "德宏傣族景颇族自治州",
  "怒江傈僳族自治州",
  "迪庆藏族自治州",
];

// 判断城市是否显示在右侧
const isRightSideCity = (city: string) => rightSideCities.includes(city);

// 判断城市是否显示在左侧
const isLeftSideCity = (city: string) => leftSideCities.includes(city);

// 添加跳转方法
const goToYunnanBaiyao = () => {
  router.push("/yunnanbaiyao");
};

// 各城市详细信息
const cityInfo = reactive({
  昆明市: {
    title: "云南白药集团股份有限公司",
    titleClass: "yunnanbaiyao",
    clickHandler: goToYunnanBaiyao,
    description: `
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
      </p>`,
    icon: "../assets/images/yunnanbaiyao.png",
  },
  保山市: {
    title: "云南腾药制药股份有限公司",
    titleClass: "Tengyao",
    description: `
      <p style="margin-top: -4%;">云南腾药制药股份有限公司（以下简称"腾药"）发祥于拥有近400年历史的"药王宫"，前身为腾冲制药厂，成立于1956年。</p>
      <p>1997年，经腾冲县政府批准，腾冲制药厂改制为股份合作制企业。</p>
      <p>2011年9月，完成股份制改造，并更名为云南腾药制药股份有限公司。公司位于云南省腾冲市，是一家以心脑血管现代中药为主，传统中药为辅，功效日化保健品为补的国家级高新技术企业</p>
      <p style="margin-top: 15px">腾药的主营业务涵盖药品生产、研发、销售、中药饮片加工及中药材种养殖。
        公司主要剂型包括注射剂、丸剂、片剂、颗粒剂、散剂、酒剂、酊剂、糖浆剂等，拥有130个国药准字批文，87个品种，137个品规。
        其代表品种有心脉隆注射液（国家二类新药）、藿香正气水、安宫牛黄丸、六味地黄丸、人参再造丸等。
        腾药通过道地选材、遵古炮制与现代科技相结合，不断推出新产品，满足市场需求。</p>`,
  },
  曲靖市: {
    title: "云南永安制药有限公司",
    titleClass: "qujing",
    description: `
      <p style="margin-top: -4%;">云南永安制药有限公司始建于1990年，原名国营云南永安制药厂，位于云南省第二大经济城市——曲靖。</p>
      <p>云南永安制药厂区占地面积101亩。公司主营中成药制剂、化学药制剂、化学原料药。产品涵盖心脑血管、消化、抗生素等领域，拥有58个产品批文，其中龙心素胶囊、秋泻灵颗粒、乌金胶囊、乙酰吉他霉素片、心达康咀嚼片为全国独家品种，“龙心素胶囊”还荣获云南名牌产品。</p>
      <p>公司重视产品质量，严格按照国家GMP标准建立片剂、颗粒剂、硬胶囊剂、化学原料药现代化生产线，并全部通过国家GMP认证。</p>
      <p >云南永安制药注重诚信经营，公司“永青”商标为云南省著名商标，先后荣获清洁生产企业、守合同重信用企业等。</p>`,
  },
  昭通市: {
    title: "云南永孜堂制药有限公司",
    titleClass: "zhaotong",
    description: `
      <p style="margin-top: -4%;"> 1996年4月，云南永孜堂制药有限公司前身云南滇东北制药总厂成立.</p>
      <p>公司总占地面积约40000平方米，现有员工400余人，具有年生产15亿粒胶囊剂、15亿片片剂、5000万袋颗粒的生产规模，是一家集种植、科研、生产、销售和服务为一体、以开发云南天然生物资源和弘扬民族医药为己任的企业。</p>
      <p> 公司现有产品31个，涵盖了心血管系统、呼吸系统、消化系统、骨伤科、皮肤科、妇科等主要临床科室用药，拥有国家基本药物品种4个、国家医保品种9个、云南省基药品种6个、云南省医保品种14个。</p>
      <p >公司充分利用“植物王国”的生物资源优势，先后研制了“天麻醒脑胶囊”、“八味肉桂胶囊”、“利胆止痛胶囊”、“苦参胶囊”等重点产品，拥有7个自主知识产权品种。</p>`,
  },
  玉溪市: {
    title: "云南省玉溪市维和制药有限公司",
    titleClass: "yuxi",
    description: `
      <p style="margin-top: -4%;">云南省玉溪市维和制药有限公司成立于1993年年，是一家多元化的经营企业。</p>
      <p>维和药业拥有NG-21智能化植物提取共性生产平台，建有占地100余亩的GMP认证生产厂区，已建立6500亩原生态药材种植基地。</p>
      <p>维和研发中心先后被认定为云南省企业技术中心、国家中药现代化工程技术研究中心西南分中心、云南省植物提取物工程研究中心。</p>
      <p >并与国内、外多家科研机构建立战略合作关系，开展植物产品的技术集成体系、新药及健康产品开发等多项业务。“维和”品牌，被认定为云南省著名商标，维和系列产品行销全国十余年，深受广大消费者青睐。</p>`,
  },
  德宏傣族景颇族自治州: {
    title: "红云制药（梁河）有限公司",
    titleClass: "dehong",
    description: `
      <p style="margin-top: -4%;">公司成立于1970年，是德宏州第一家制药企业。</p>
      <p>公司占地9883.63平方米，拥有11个剂型和30个药品批准文号，主力产品包括回春酒、豹骨木瓜酒和绞股蓝总苷胶囊。</p>
      <p>公司具备年处理药材600吨、生产片剂1亿片、胶囊4亿粒、合（酊）剂1亿支、散剂1000万袋、糖浆（酒）剂3000万瓶的能力，是德宏州最大制药企业。</p>
      <p >公司先后30多次获省、州先进企业称号，拥有国家级高新技术企业及美国FSVP认证，为其国际化发展提供保障。</p>`,
  },
  文山壮族苗族自治州: {
    title: "云南七丹药业股份有限公司",
    titleClass: "wenshan",
    description: `
      <p style="margin-top: -4%;">三七健康产品研制专家——云南七丹药业股份有限公司成立于2008年3月。</p>
      <p>公司是专业从事三七药材规范化种植、产品研发、生产、加工、销售的全产业链科技创新型新三板挂牌企业，是三七之乡——文山州第一家专业生产三七饮片的国家高新技术企业。</p>
      <p>公司致力于研究开发和生产以三七为主要原料的中药饮片、保健食品、食品、日化用品等三七健康产品。</p>
      <p>取得7个保健食品批文，获19项国内发明专利、2项国际发明专利、3项实用新型专利，获两项云南省科学技术进步一等奖、一项中医药国际贡献奖、一项云南省科学技术进步三等奖。</p>`,
  },
  红河哈尼族彝族自治州: {
    title: "云南云河药业股份有限公司",
    titleClass: "honghe",
    description: `
      <p style="margin-top: -4%;">云南云河药业股份有限公司（简称云河药业）前身是国有的云南省个旧市制药厂，始建于1958年，坐落在人杰地灵的世界著名锡都——个旧市</p>
      <p>公司共拥有员工400余人，拥有覆盖全国的市场营销网络和经销商队伍；拥有通过了GMP标准认证的12个剂型共77个品种规格的药品现代化生产线,其中49个品种规格列入2019国家医保目录；</p>
      <p>拥有16个国家发明专利和6个外观设计专利证书,其中龙血竭制备工艺和虎力散制备工艺为国家保密专利。</p>
      <p >云河药业坚持“自信、创新、责任、发展”八字方针和“以人为本，科技创新，和谐共赢，共同发展”的经营理念，以“根植民族医药，服务人类健康”为宗旨，与时俱进，奋发向上，正在为发展和振兴中华民族的传统医药作出积极的贡献!</p>`,
  },
  西双版纳傣族自治州: {
    title: "西双版纳佛鑫药业有限公司中药(傣药)饮片厂",
    titleClass: "xishaungbanna",
    description: `
      <p style="margin-top: -4%;">西双版纳佛鑫药业有限公司中药(傣药)饮片厂于2008年12月23日成立。</p>
      <p>西双版纳佛鑫药业有限公司中药（傣药）饮片厂，位于勐海县勐遮镇曼列村，是西双版纳傣族自治州唯一专业从事中药饮片生产的药品生产企业，可以生产十多种中药（傣药）饮片</p>
      <p>公司严格遵循国家药品生产质量管理规范（GMP）标准，确保产品质量和安全，并建立了完善的销售网络和售后服务体系‌</p>
      <p >公司以“传承创新，服务健康”为企业宗旨，结合现代科技，生产高品质的中药及傣药饮片产品。</p>`,
  },
  普洱市: {
    title: "云南普洱丹州制药股份有限公司",
    titleClass: "puer",
    description: `
      <p>公司是集民族天然药物整理、民族药研究开发、民族药材及中药材种植；中药生产和销售；现代化养生庄园旅游、理疗为一体的制药企业。</p>
      <p>“普洱”品牌旗下产品拥有片剂、糖浆剂、颗粒剂、茶剂、原料药、酊剂6个剂型，共计有14个国药准字品种（其中有9个是国家基本药物目录品种和1个中药提取物）、 12个妆字号品种、3个消字号品种（“普洱牌天然植物抗菌液”，国家专利保护）、2个院内制剂（紫芸解毒止痛酊、红金妇炎酊）以及3个日化产品；“丹州德一堂”品牌旗下运营“明衡1390”养疗体验馆等。‌</p>`,
  },
  怒江傈僳族自治州: {
    title: "怒江晓明中药材有限公司",
    titleClass: "nujiang",
    description: `
      <p >怒江晓明中药材有限公司，坐落于风景秀丽、自然资源丰富的中国云南省怒江傈僳族自治州。</p>
      <p>公司是一家专注于高品质中药材种植、研发、加工与销售为一体的现代化企业‌</p>
      <p>公司致力于将怒江地区丰富的中药材资源与现代科技相结合，推动中药材产业的可持续发展。</p>`,
  },
  临沧市: {
    title: "‌云南广福药业有限公司",
    titleClass: "lingchang",
    description: `
      <p>‌云南广福药业有限公司‌前身为临沧地区制药厂，成立于1972年，经过多次更名和发展，最终于2003年1月14日更名为云南广福药业有限公司。</p>
     <p>公司位于云南省临沧市，是一家集研发、生产、销售为一体的现代化制药企业。</p>
      <p>公司主要产品包括中成药、化学药品和保健食品等，涵盖了心脑血管、消化系统、呼吸系统等多个领域。</p>
      <p>公司致力于提供高品质的药品和健康产品，服务于广大患者和消费者。</p>`,
  },
 迪庆藏族自治州: {
    title: "云南香格里拉藏药集团有限公司",
    titleClass: "diqing",
    description: `
      <p>‌云南香格里拉藏药集团有限公司‌是一家集挖掘、研发、弘扬民族医学为一体，融生产销售、推广民族医药为一身的综合型集团医药公司。</p>
  <p>公司成立于2003年，总部位于云南省大理市，拥有多个子公司和分支机构。</p>
      <p>公司致力于藏药的研发、生产和销售，产品涵盖了藏药饮片、藏药制剂、保健品等多个领域。</p>
      <p>公司以“传承民族医学，服务人类健康”为宗旨，致力于推动民族医药的发展和普及。</p>`,
  },
  丽江市: {
    title: "丽江华利生物开发药业有限公司",
    titleClass: "lijiang",
    description: `
      <p>‌丽江华利生物开发药业有限公司‌成立于1999年，位于云南省丽江市玉龙县南口工业园。</p>
      <p>公司是一家集研发、生产、销售为一体的现代化制药企业，主要产品包括中成药、化学药品和保健食品等。</p> 
      <p>公司致力于提供高品质的药品和健康产品，服务于广大患者和消费者。</p>
      <p>公司拥有多条现代化的生产线，严格按照国家GMP标准进行生产，确保产品质量和安全。</p>`,
  },
   大理白族自治州: {
    title: "大理白族自治州中药制药有限公司",
    titleClass: "dali",
    description: `
      <p>大理白族自治州中药制药有限公司地处“云药之乡”巍山县境内，始建于1969年7月，由“慎德堂”中医世家第五代传人朱兆康同志创办，原名：大理州中药制药厂。</p>
      <p>公司是云南省中药制药行业的龙头企业之一，拥有多条现代化的生产线，主要产品包括中成药、化学药品和保健食品等。</p>
      <p>公司以“至善的工作质量，保证至上的产品质量;至善的产品质量，服务至上的大众健康。</p>      `
  },
  楚雄彝族自治州: {
    title: "云南瑞药金方现代中药有限公司",
    titleClass: "chuxiong",
    description: `
      <p>云南瑞药金方现代中药有限公司是一家新型中药破壁饮片研发的企业。</p>
      <p>公司建成中药破壁饮片及冻干系列产品生产基地，已完成148个产品的工艺研究与质量研究，目前可生产上市销售的中药破壁饮片产品和熊胆粉(冻干)、菲牛蛭冻干粉共66个。</p>
      <p>公司秉持着传承精华、守正创新的匠心精神，将传统中药与现代科技深度融合所造就的破壁饮片品牌。</p>      `
  },
  
});

// 城市数据
const cityData = [
  {
    name: "昆明市",
    value: 8980,
    coord: [102.833, 24.879],
    panelCoord: [105, 25], // 右侧信息框位置
  },
  {
    name: "保山市",
    value: 3137,
    coord: [99.161, 25.112],
    panelCoord: [95, 25], // 左侧信息框位置
  },
  { name: "曲靖市", value: 5527, coord: [103.796, 25.489] },
  { name: "玉溪市", value: 1578, coord: [102.543, 24.35] },
  { name: "昭通市", value: 3302, coord: [103.717, 27.337] },
  { name: "丽江市", value: 4523, coord: [100.227, 26.855] },
  { name: "普洱市", value: 2722, coord: [100.966, 22.825] },
  { name: "临沧市", value: 1431, coord: [100.087, 23.886] },
  { name: "楚雄彝族自治州", value: 2248, coord: [101.546, 25.041] },
  { name: "红河哈尼族彝族自治州", value: 3665, coord: [103.374, 23.363] },
  { name: "文山壮族苗族自治州", value: 4978, coord: [104.244, 23.369] },
  { name: "西双版纳傣族自治州", value: 910, coord: [100.797, 22.001] },
  { name: "大理白族自治州", value: 3721, coord: [100.225, 25.589] },
  { name: "德宏傣族景颇族自治州", value: 711, coord: [98.578, 24.436] },
  { name: "怒江傈僳族自治州", value: 1459, coord: [98.854, 25.85] },
  { name: "迪庆藏族自治州", value: 2238, coord: [99.706, 27.826] },
];

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
        data: cityData.map((item) => ({
          name: item.name,
          value: item.value,
        })),
      },
      {
        type: "effectScatter",
        coordinateSystem: "geo",
        zlevel: 3,
        rippleEffect: {
          brushType: "stroke",
        },
        label: {
          show: true,
          position: "right",
          formatter: "{b}",
          fontSize: 12,
          color: "#5a3921",
        },
        symbolSize: 8,
        itemStyle: {
          color: "#8c4e2a",
        },
        data: cityData
          .filter((item) => item.coord)
          .map((item) => ({
            name: item.name,
            value: [...(item.coord || []), item.value],
          })),
      },
    ],
  };

  chartInstance.setOption(option);

  // 添加点击事件
  chartInstance.on("click", (params: any) => {
    if (params.name) {
      selectedCity.value = params.name;
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
<style>
@import "./companies.css";
</style>
