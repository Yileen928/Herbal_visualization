<template>
  <div class="YunnanBaiYao">
    <div class="YunnanBaiYao-background">
      <h2 style="margin-top: -3%;">云南白药集团</h2>
      <div class="container-grid">
        <!-- 上排两个容器 -->
        <div class="top-row">
          <div class="chart-container" id="container1">
            <!-- 云南白药集团产品覆盖-雷达图图 -->
            <YunLeiDa />
          </div>
           <div class="chart-container" id="container7">
            <!-- 云南白药集团工业收入 -->
            <YunIncome />
          </div>
          <div class="chart-container" id="container2">
            <!-- 品牌矩阵-气泡图横轴 -->
            <YunBrand />
          </div>
        </div>
        
        <!-- 下排四个容器 -->
        <div class="bottom-row">
          <div class="chart-container" id="container3">
            <!-- 云南白药膏药评论-词云图 -->
            <YunCloud />
          </div>
          <div class="chart-container" id="container4">
            <!-- 云南白药各产品评论数-动态柱状图 -->
            <YunAllProduct />
          </div>
          <div class="chart-container" id="container5">
            <!-- 云南白药酊评论趋势图-动态柱状图 -->
            <YunDingComment />
          </div>
          <div class="chart-container" id="container6">
            <!-- 云南白药气喷雾-迁徙图 -->
            <YunMigration />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import YunLeiDa from '../components/YunLeiDa.vue'
import YunCloud from '../components/YunCloud.vue'
import YunBrand from '../components/YunBrand.vue'
import YunMigration from '../components/YunMigration.vue'
import YunAllProduct from '../components/YunAllProduct.vue'
import YunDingComment from '../components/YunDingComment.vue'
import YunIncome from '../components/YunIncome.vue'

const router = useRouter()
</script>

<style>
@import './YunnanBaiYao.css';
</style>