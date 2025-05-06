<template>
  <div class="Yizu-region">
    <!-- 导航栏 -->
    <div class="Navigationbar">
      <div 
        class="Navigationbar-con" 
        v-for="(item, index) in navItems" 
        :key="index"
        @click="showDetail(index)"
        :class="{ 'active': activeIndex === index }"
      >
        <img :src="item.icon" :alt="'导航项'+index">
      </div>
    </div>
    
    <!-- 详情区 -->
    <div class="detail-section">
      <div v-if="activeIndex !== null" class="detail-content">
        <img :src="navItems[activeIndex].detailImage" :alt="navItems[activeIndex].title + '的详细图片'" class="detail-image">
      </div>
      <div v-else class="placeholder">
        <img src="/images/彝族默认.png" alt="/placeholder">
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

// 导航数据
const navItems = ref([
  {
    icon: '/images/彝族导航1.png',
    detailImage: '/images/彝族1.png',
  },
  {
    icon: '/images/彝族导航2.png',
    detailImage: '/images/彝族2.png',

  },
  {
    icon: '/images/彝族导航3.png',
    title: '彝族节日',
    detailImage: '/images/detail-festival.jpg',
    description: '火把节是彝族最盛大的传统节日...'
  },
  {
    icon: '/images/彝族导航4.png',
    title: '彝族饮食',
    detailImage: '/images/detail-food.jpg',
    description: '彝族饮食以苦荞、土豆、羊肉为主...'
  },
  {
    icon: '/images/彝族导航5.png',
    title: '彝族音乐',
    detailImage: '/images/detail-music.jpg',
    description: '彝族民歌形式多样，月琴是特色乐器...'
  },
  {
    icon: '/images/彝族导航6.png',
    title: '彝族文字',
    detailImage: '/images/detail-writing.jpg',
    description: '彝文是世界上最古老的文字之一...'
  }
]);

const activeIndex = ref(null);

const showDetail = (index) => {
  activeIndex.value = index;
};
</script>

<style scoped>
.Yizu-region {
  height: 100vh;
  width: 55vw;
  display: flex;
}

/* 导航栏样式 */
.Navigationbar {
  display: flex;
  flex: 2;
  background: url(/images/彝族导航.png);
  background-position: center;
  background-repeat: no-repeat;
  background-size: 95% 95%;
  flex-direction: column;
  padding: 10px;
}

.Navigationbar-con {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 5px 0;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 2px solid transparent;
}

.Navigationbar-con:hover {
  transform: scale(1.05);
}



.Navigationbar-con img {
  max-width: 80%;
  max-height: 80%;
  object-fit: contain;
}

/* 详情区样式 */
.detail-section {
  flex: 8;

  padding: 20px;
  overflow-y: auto;
}

.detail-content {
  height: 100%;
}

.detail-image {
  width: 100%;
  height: 100%;

}

.placeholder {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;

}
.placeholder img  {
  height: 100%;
  width: 100%;
  object-fit: contain; 

}

h3 {
  color: #4c614c;
  font-size: 1.8rem;
  margin-bottom: 15px;
  text-align: center;
}

p {
  line-height: 1.6;
  color: #333;
}
</style>