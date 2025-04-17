//医师工作经验

<template>
  <div class="doctor-skills-wordcloud">
    <h3>医师工作经验</h3>
    <p>南丁格尔图</p>
  </div>
</template>

<script setup lang="ts">
// 这里可以留空，但必须有 script 部分<template>
  <div class="herbal-product">
    <div class="herbal-product-background">
      <!-- 第一行：趋势图 -->
      <div class="herbal-product-container1">
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
            style="width: 100%; height: 500px"
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
        <div class="herbal-product-chart2">
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
import MedicineTable from '../components/MedicineTable.vue' // 药品表格
import PurchaseChart from '../components/PurchaseChart.vue' // 采购投入赋码图
import PharmacyType from '../components/PharmacyType.vue' // 药店类型
import PharmacyNum from '../components/PharmacyNum.vue' // 药店数量
</script>

<style scoped>

</style>