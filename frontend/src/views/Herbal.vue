<template>
  <div class="herbal">
  <div class="herbal-info">
    <div class="herbal-contant">
      <div class="herbal-detail">
        <div class="herbal-title"><!-- 标题 三七 -->
           <div class="herbal-small"><img :src="herbalInfo.herbImageArt" alt=""></div>
           <div class="herbal-Classification">
            <div class="herbal-Genus">{{herbalInfo.herbGenus}}</div>
            <hr class="herbal-border">
            <div class="herbal-Family">{{herbalInfo.herbDepartment}}</div>
           </div>
          <h2 class="title">{{ herbalInfo.name }}</h2>
        </div>
        <div class="herbal-allInfor"><!-- 这是信息具体 释名之类 -->
          <div class="herbal-name"> <p>【释名】{{herbalInfo.release}}</p></div> 
          <div class="herbal-nature"><p>【性味】{{ herbalInfo.nature }}</p></div>
          <div class="herbal-effect"><p>【功效】{{ herbalInfo.effect }}</p></div>
          <div class="herbal-disease"><p>【主治疾病】{{herbalInfo.indications}}</p></div>
        </div>
        <div class="herbal-makeways"><!-- 这是炮制框 -->
          <img :src="herbalInfo.Preparation" alt="炮制" style="width: 557px; height: 94%; margin-top: 5pt;">
        </div>
        
      </div>
    
      <div class="herbal-img"><img :src="herbalInfo.herbImageArt" alt=""></div>    
    </div>
    <div class="herbal-data">
      <div class="herbal-data-ancient">

      </div>
      <div class="herbal-data-map">
        <button class="map-button" @click="goToMap">查看分布地图</button>
      </div>
      
    </div>
      
  </div>
  <div class="herbal-echarts">
    <div class="herbal-food">

    </div>
    <div class="herbal-picture">
    </div>
  </div>
    
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Herbal',
  data() {
    return {
      herbalInfo: {
        herbDepartment: '',
        herbGenus: '',
        name: '',
        release: '',
        nature: '',
        effect: '',
        indications: '',
        herbImageArt: '',
        Preparation: '',
        meridian: [],
        origin: '',
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
        herbDepartment: herb.herbDepartment || '未知药科',
        herbGenus: herb.herbGenus || '未知药属',
        name: herb.herbName || '未知药材',
        release: herb.herbRelease || '未知释名',
        nature: herb.flavor || '未知性味',
        effect: herb.efficacy || '未知功效',
        indications: herb.indications || '未知主治疾病',
        meridian: returnsData.map(item => item.returnName) || [],
        origin: herb.nationwideOrigin || herb.yunnanOrigin || '未知',
        herbImageArt: herb.herbImageArt || '', // 如果 image 为空，前端可以显示默认图片
        Preparation: herb.herbImagePreparation || '',// 炮制
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
    },
    goToMap() {
      this.$router.push({
        path: '/map',
        query: { herbName: this.herbalInfo.name }
      })
    }
  }
}
</script>

<style>
@import './Herbal.css';
.herbal-data-map {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
}

.map-button {
  padding: 10px 20px;
  font-size: 16px;
  color: #fff;
  background-color: #4CAF50;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.map-button:hover {
  background-color: #45a049;
}
</style>