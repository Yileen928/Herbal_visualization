<template>
  <div class="Achievement">
    <div class="Achievement-background">
      <div class="Achievement-content">
        <!-- 默认内容 -->
        <div v-if="!selectedRegion" class="region-detail default-content">
          <h2>默认区域作为引子</h2>
          <div class="detail-content">
            <div class="detail-section">
              放一些素材内容作为各个区域的过渡
              
            </div>
            <div class="detail-section">

            </div>
            <div class="detail-section">

            </div>
          </div>
        </div>
        
        <!-- 选中区域的内容 -->
        <div v-else class="region-detail">
          <h2>{{ selectedRegion }}</h2>
          <div class="detail-content">
            <div class="detail-section">
              <h3>地理位置</h3>
              <p>{{ regionDetails[selectedRegion]?.location }}</p>
            </div>
            <div class="detail-section">
              <h3>特色药材</h3>
              <ul>
                <li v-for="herb in regionDetails[selectedRegion]?.herbs" :key="herb">
                  {{ herb }}
                </li>
              </ul>
            </div>
            <div class="detail-section">
              <h3>文化特色</h3>
              <p>{{ regionDetails[selectedRegion]?.culture }}</p>
            </div>
            
            <!-- 为昆明市添加跳转按钮 -->
            <div v-if="selectedRegion === '昆明市'" class="action-section">
              <button class="ancient-btn" @click="goToAncient">
                古籍
              </button>
            </div>
          </div>
        </div>
      </div>
      <div class="Achievement-map">
        <YunnanMap @region-click="handleRegionClick"/>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import YunnanMap from '../components/YunnanMap.vue'

const router = useRouter()
const selectedRegion = ref('')

// 添加跳转方法
const goToAncient = () => {
  router.push('/ancient')
}

const regionDetails = {
  '昆明市': {
    location: '兰茂主要内容',
    herbs: ['三七', '铁皮石斛', '重楼', '灯盏花'],
    culture: '昆明是云南省最大的中药材集散地，拥有多个专业的中药材市场和加工基地。'
  },
  // 可以添加更多地区的数据
}

const handleRegionClick = (region: string) => {
  selectedRegion.value = region
}
</script>

<style>
@import './achievement.css';
</style>