<template>
  <div class="YunnanBaiYao">
    <div class="YunnanBaiYao-background">
      <h1>各省药师医师招聘数量</h1>
      <div class="container-grid">
        <!-- 上排两个容器 -->
        <div class="top-row">
          <div class="chart-container" id="container1">
            <!-- 云南白药集团产品覆盖-雷达图图 -->
            <YunLeiDa />
          </div>
          <div class="chart-container" id="container2">
            <!-- 医师工作经验-南丁格尔图 -->
            <DoctorExperienceChart />
          </div>
        </div>
        
        <!-- 下排四个容器 -->
        <div class="bottom-row">
          <div class="chart-container" id="container3">
            <!-- 药师薪资-横向柱状折线图 -->
            <PharmacistSalaryChart />
          </div>
          <div class="chart-container" id="container4">
            <!-- 药师技能要求词云图 -->
            <PharmacistSkillsWordCloud />
          </div>
          <div class="chart-container" id="container5">
            <!-- 医师技能要求词云图 -->
            <DoctorSkillsWordCloud />
          </div>
          <div class="chart-container" id="container6">
            <!-- 医师薪资-横向柱状折线图 -->
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
import DoctorExperienceChart from '../components/YunCloud.vue'
import PharmacistSalaryChart from '../components/YunBrand.vue'
import YunMigration from '../components/YunMigration.vue'
import PharmacistSkillsWordCloud from '../components/YunAllProduct.vue'
import DoctorSkillsWordCloud from '../components/YunDingComment.vue'

const router = useRouter()
</script>

<style>
@import './YunnanBaiYao.css';
</style>