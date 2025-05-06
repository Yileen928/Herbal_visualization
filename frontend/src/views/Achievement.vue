<template>
  <div class="Achievement">
    <div class="Achievement-background">
      <div class="Achievement-content">
        <!-- 默认内容 -->
        <div v-if="!selectedRegion" class="region-detail default-content">
          <h2 class="default-title">默认区域作为引子</h2>
          <div class="detail-content default-content-body">
            <div class="detail-section default-section">
              放一些素材内容作为各个区域的过渡
            </div>
          </div>
        </div>
        
        <!-- 选中区域的内容 -->
        <div v-else class="region-detail">
          <component :is="selectedRegionComponent" v-if="selectedRegionComponent" />
          <div v-else>未找到对应的区域组件</div>
        </div>
      </div>
      <div class="Achievement-map">
        <YunnanMap @region-click="handleRegionClick"/>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, markRaw, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import YunnanMap from '../components/YunnanMap.vue'
import DaliRegion from '../components/AreaBaizu.vue'
import KunmingRegion from '../components/AreaKunming.vue'
import YizuRegion from '../components/AreaYizu.vue'

const router = useRouter()
const selectedRegion = ref('')

const regionComponents = {
  '大理': markRaw(DaliRegion),
  '昆明': markRaw(KunmingRegion),
  '楚雄彝族自治州': markRaw(YizuRegion),
} as const

const selectedRegionComponent = ref<any>(null)

const handleRegionClick = (region: string) => {
  console.log('选中的区域: ', region)
  selectedRegion.value = region
  const componentKey = Object.keys(regionComponents).find(key => region.includes(key))
  selectedRegionComponent.value = componentKey ? regionComponents[componentKey] : null
  console.log('当前选中的组件: ', selectedRegionComponent.value)
}

// 添加 onMounted 钩子，在组件挂载时自动选择大理区域
onMounted(() => {
  handleRegionClick('大理')
})
</script>

<style scoped>
@import './achievement.css';
</style>
