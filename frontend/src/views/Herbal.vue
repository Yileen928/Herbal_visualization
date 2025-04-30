<template>
  <div class="herbal">
    <div class="herbal-detail">
      <!-- <h2>{{ herbalInfo.name }}</h2> -->
      <div class="info-content">
        <!-- <p>性味：{{ herbalInfo.nature }}</p> -->
        <!-- <p>功效：{{ herbalInfo.effect }}</p> -->
        <!-- <p>归经：{{ herbalInfo.meridian.join('、') }}</p> -->
        <!-- <p>产地：{{ herbalInfo.origin }}</p> -->
      </div>
    </div>

    <div class="herbal-list">
      <div class="herbal-png">
        <!-- <img :src="herbalInfo.imageUrl || '/default-image.png'" :alt="herbalInfo.name" /> -->
       <img src="/images/三七详情.png" alt="图片" />
      </div>
      <div class="herbal-data">
        <div class="herbal-chart">
          <!-- <div ref="effectChart"></div> -->

        </div>
        <div class="herbal-map">
          <button class="product" @click="goToHerbalProduct">产品</button>
          <!-- <div ref="distributionMap"></div> -->

        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import axios from 'axios'

export default {
  name: 'Herbal',
  data() {
    return {
      /*
      herbalInfo: {
        name: '',
        nature: '',
        effect: '',
        meridian: [],
        origin: '',
        imageUrl: '',
        chartData: null,
        mapData: null
      }
      */
    }
  },
  /*
  async created() {
    const herbName = this.$route.query.herbName
    if (herbName) {
      try {
        const response = await axios.get(`http://106.55.169.134:10010/herbs/search`, {
          params: { herbName: decodeURIComponent(herbName) }
        })
        const data = response.data
        console.log(data)
        if (data && data.length > 0 && data[0].herb && data[0].returns) {
          this.processHerbalData(data[0])
        } else {
          console.error('数据格式不正确:', data)
        }

        this.initChart()
        this.initMap()
      } catch (error) {
        console.error('获取数据失败:', error)
      }
    }
  },
  */
  methods: {
    /*
    processHerbalData(data) {
      const herb = data.herb
      const returnsData = data.returns

      this.herbalInfo = {
        name: herb.herbName || '未知药材',
        nature: herb.flavor || '未知性味',
        effect: herb.efficacy || '未知功效',
        meridian: returnsData.map(item => item.returnName) || [],
        origin: herb.nationwideOrigin || herb.yunnanOrigin || '未知',
        imageUrl: herb.image || '',
        chartData: null,
        mapData: null
      }
    },
    initChart() {
      console.log('初始化图表:', this.herbalInfo.chartData)
    },
    initMap() {
      console.log('初始化地图:', this.herbalInfo.mapData)
    },
    */
    goToHerbalProduct() {
      this.$router.push('/herbal-product')
    }
  }
}
</script>

<style>
@import './Herbal.css';
</style>
