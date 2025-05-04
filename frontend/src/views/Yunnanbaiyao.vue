<template>
  <div class="YunnanBaiYao">
    <div class="YunnanBaiYao-background">
      <!-- 地图作为底层 -->
      <div class="map-layer">
        <Migration />
      </div>

      <!-- 标题 -->
      <h2 class="competitor-title" @click="goToCompetitor">
        云南白药VS北京同仁堂
      </h2>

      <!-- 悬浮在地图上的图表 -->
      <div class="floating-charts">
        <!-- 左侧两个图表（上下排布） -->
        <div class="left-charts">
          <div class="chart-container" id="container1">
            <!-- 产品覆盖-雷达图图 -->
            <YunLeiDa />
          </div>
          <div class="chart-container" id="container3">
            <!-- 两集团十年工业收入 -->
            <YunIncome />
          </div>
        </div>

        <!-- 右侧两个图表（上下排布） -->
        <div class="right-charts">
          <div class="chart-container" id="container2">
            <!-- 核心产品矩阵-气泡图横轴 -->
            <YunBrand />
          </div>
          <div class="chart-container" id="container6">
            <!-- 核心产品评论数-动态柱状图 -->
            <YunAllProduct />
          </div>
        </div>

        <!-- 底部中间两个图表 -->
        <div class="bottom-center-charts">
          <div class="bottom-chart">
            <YunCloud />
          </div>
          <div class="bottom-chart">
            <BeiCloud />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { useRouter } from "vue-router";
  import YunLeiDa from "../components/YunLeiDa.vue";
  import YunBrand from "../components/YunBrand.vue";
  import YunAllProduct from "../components/YunAllProduct.vue";
  import YunIncome from "../components/YunIncome.vue";
  import Migration from "./Migration.vue";
  import YunCloud from "../components/YunCloud.vue";
  import BeiCloud from "../components/BeiCloud.vue";

  // 添加跳转方法
  const goToCompetitor = () => {
    router.push("/competitor"); // 跳转到Competitor.vue
  };

  const router = useRouter();
</script>

<style>
  @import "./yunnanbaiyao.css";
</style>