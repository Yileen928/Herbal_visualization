<template>
  <div class="herbal">
  <div class="herbal-info">
    <div class="herbal-contant">
      <div class="herbal-detail">
        <div class="herbal-title"><!-- 标题 三七 -->
           <div class="herbal-small"></div>
           <div class="herbal-Classification">
            <div class="herbal-Genus"><!-- 这是属 --></div>
            <div class="herbal-Family"><!-- 这是科 --></div>
           </div>
          <h2 class="title">{{ herbalInfo.name }}</h2>
        </div>
        <div class="herbal-allInfor"><!-- 这是信息具体 释名之类 -->
          <div class="herbal-name"> <p>【释名】</p><!-- 这是释名 --></div> 
          <div class="herbal-nature"><p>【性味】{{ herbalInfo.nature }}</p></div>
          <div class="herbal-effect"><p>【功效】{{ herbalInfo.effect }}</p></div>
          <div class="herbal-disease"><p>【主治疾病】</p><!-- 这是疾病 --></div>
        </div>
        <div class="herbal-makeways"><!-- 这是炮制框 -->

      
          

        </div>
          
        
      </div>
    
      <div class="herbal-img"><img src="/images/三七详情.png" alt=""></div>    
    </div>
    <div class="herbal-date"></div>
      
  </div>
  <div class="herbal-echarts"></div>
    
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Herbal',
  data() {
    return {
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
    }
  },
  async created() {
    const herbName = this.$route.query.herbName
    if (herbName) {
      try {
        const response = await axios.get(`http://106.55.169.134:10010/herbs/search`, {
          params: { herbName: decodeURIComponent(herbName) }
        })
        const data = response.data
        console.log(data)
        // 检查数据完整性
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
  methods: {
    processHerbalData(data) {
      const herb = data.herb
      const returnsData = data.returns

      this.herbalInfo = {
        name: herb.herbName || '未知药材',
        nature: herb.flavor || '未知性味',
        effect: herb.efficacy || '未知功效',
        meridian: returnsData.map(item => item.returnName) || [],
        origin: herb.nationwideOrigin || herb.yunnanOrigin || '未知',
        imageUrl: herb.image || '', // 如果 image 为空，前端可以显示默认图片
        chartData: null, // 预留图表数据
        mapData: null // 预留地图数据
      }
    },
    initChart() {
      // 初始化图表的方法
      console.log('初始化图表:', this.herbalInfo.chartData)
    },
    initMap() {
      // 初始化地图的方法
      console.log('初始化地图:', this.herbalInfo.mapData)
    },
    goToHerbalProduct() {
      this.$router.push('/herbal-product')
    }
  }
}
</script>

<style>
@import './Herbal.css';
</style>