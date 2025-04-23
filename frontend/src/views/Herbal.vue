<template>
  <div class="herbal">
    <div class="herbal-detail">
      <h2>{{ herbalInfo.name }}</h2>
      <div class="info-content">
        <p>性味：{{ herbalInfo.nature }}</p>
        <p>功效：{{ herbalInfo.effect }}</p>
        <p>归经：{{ herbalInfo.meridian }}</p>
        <p>产地：{{ herbalInfo.origin }}</p>
      </div>
    </div>
    <div class="herbal-list">
      <div class="herbal-png">
        <img :src="herbalInfo.imageUrl" :alt="herbalInfo.name" />
      </div>
      <div class="herbal-data">
        <div class="herbal-chart">
          <div ref="effectChart"></div>
        </div>
        <div class="herbal-map">
          <button class="product" @click="goToHerbalproduct">产品</button>
          <div ref="distributionMap"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Herbal',
  data() {
    return {
      herbalInfo: {
        name: '',
        nature: '',
        effect: '',
        meridian: '',
        origin: '',
        imageUrl: '',
        chartData: null,
        mapData: null
      }
    }
  },
  async created() {
    const herbId = this.$route.query.id;
    if (herbId) {
      try {
        const response = await fetch(`/api/herbs/${herbId}`);
        const data = await response.json();
        this.herbalInfo = data;
        this.initChart();
        this.initMap();
      } catch (error) {
        console.error('获取数据失败:', error);
      }
    }
  },
  methods: {
    initChart() {
      // 初始化图表的方法
    },
    initMap() {
      // 初始化地图的方法
    },
    goToHerbalproduct() {
      this.$router.push('/herbal-product')
    }
  }
}
</script>

<style>
@import './Herbal.css';
</style>