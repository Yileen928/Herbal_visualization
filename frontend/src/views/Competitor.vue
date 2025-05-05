<template>
  <div class="Competitor">
    <div class="Competitor-background">
      <h2 class="page-title" @click="goToHci">
        云南昆中药VS杭州胡庆余堂
      </h2>
      <div class="container-grid">
        <!-- 上排三个容器 -->
        <div class="top-row">
          <div class="chart-container" id="container1">
            <!-- 2024年利润及销售--仪表盘 -->
            <KunIncome />
          </div>
          <div class="chart-container" id="container7">
            <!-- 生产指标对比 -->
            <KunIndicator />
          </div>
          <div class="chart-container" id="container2">
            <!-- 产品结构-旭日图 -->
            <KunProduct />
          </div>
        </div>

        <!-- 下排四个容器 -->
        <div class="bottom-row">
          <div class="chart-container" id="container3" style="height: 100%">
            <KunLeiDa />
          </div>
          <div class="chart-container" id="container4">
            <KunCloud />
          </div>
          <div class="chart-container" id="container5">
            <HuCloud />
          </div>
          <div class="chart-container" id="container6">
            <KunSangJi />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router";
import KunLeiDa from "../components/KunLeiDa.vue";
import KunProduct from "../components/KunProduct.vue";
import KunIndicator from "../components/KunIndicator.vue";
import KunSangJi from "../components/KunSangJi.vue";
import KunIncome from "../components/KunIncome.vue";
import HuCloud from "../components/HuCloud.vue";
import KunCloud from "../components/KunCloud.vue";
import { onMounted } from "vue";

// 添加跳转方法
const goToHci = () => {
  router.push("/hci"); // 跳转到Hci.vue
};

const router = useRouter();
</script>
<style>
@import "./competitor.css";
</style>
