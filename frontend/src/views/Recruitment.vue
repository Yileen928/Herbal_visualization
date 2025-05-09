<template>
  <div class="hci">
    <div class="hci-background">
      <h1 @click="goToTenAchievement" style="cursor: pointer;">各省药师医师招聘数量</h1>
      <div class="container-grid" style="gap: 12%">
        <!-- 上排三个容器 -->
        <div class="top-row" style="height: 40%; margin-top: -4%">
          <div
            class="chart-container"
            id="container1"
            style="width: 100%; height: 125%"
          >
            <!-- 药师工作经验-南丁格尔图 -->
            <PharmacistExperienceChart />
          </div>
          <div
            class="chart-container"
            id="container6"
            style="width: 53%; height: 125%"
          >
            <!-- 在职药师、医师 -->
            <DoctorPharmactist />
          </div>
          <div
            class="chart-container"
            id="container2"
            style="width: 25%; height: 125%"
          >
            <!-- 医师工作经验-南丁格尔图 -->
            <DoctorExperienceChart />
          </div>
        </div>

        <!-- 下排四个容器 -->
        <div class="bottom-row" style="height: 48%; margin-bottom:-1%">
          <div
            class="chart-container"
            id="container3"
            style="height: 100%; flex: 1 1 30%"
          >
            <!-- 药师薪资-横向柱状图 -->
            <PharmacistSalaryChart />
          </div>
          <div
            class="chart-container"
            id="container4"
            style="height: 100%; flex: 1 1 21%"
          >
            <!-- 药师技能要求词云图 -->
            <PharmacistSkillsWordCloud />
          </div>
          <div
            class="chart-container"
            id="container5"
            style="height: 100%; flex: 1 1 21%"
          >
            <!-- 医师技能要求词云图 -->
            <DoctorSkillsWordCloud />
          </div>
          <div
            class="chart-container"
            id="container6"
            style="height: 100%; flex: 1 1 30%"
          >
            <!-- 医师薪资-横向柱状图 -->
            <DoctorSalaryChart />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router";
import PharmacistExperienceChart from "../components/PharmacistExperienceChart.vue";
import DoctorExperienceChart from "../components/DoctorExperienceChart.vue";
import PharmacistSalaryChart from "../components/PharmacistSalaryChart.vue";
import DoctorSalaryChart from "../components/DoctorSalaryChart.vue";
import PharmacistSkillsWordCloud from "../components/PharmacistSkillsWordCloud.vue";
import DoctorSkillsWordCloud from "../components/DoctorSkillsWordCloud.vue";
import DoctorPharmactist from "../components/DoctorPharmactist.vue";

const router = useRouter();

const goToTenAchievement = () => {
  router.push('/ten-achievement');
};
</script>


<style>
@import "./recruitment.css";
</style>
