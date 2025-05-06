<template>
    <div class="dali-region">
      <!-- 三塔视图 -->
      <div v-if="currentView === 'pagodas'">
        <h3>白族医药</h3>
        <div class="dali-Pagodas">
          <div class="pagodas pagodas-left" @click="showContent('left')">
            <img src="/images/左塔.svg" alt="左塔">
          </div>
          <div class="pagodas pagodas-center" @click="showContent('center')">
            <img src="/images/中塔.svg" alt="中塔">
          </div>
          <div class="pagodas pagodas-right" @click="showContent('right')">
            <img src="/images/右塔.svg" alt="右塔">
          </div>
        </div>
      </div>
  
      <!-- 详情视图 -->
      <div v-else class="detail-view">
        <img 
          :src="detailImage" 
          :alt="currentView + '塔详情图'"
          class="detail-image"
        >
        <button @click="backToPagodas" class="back-button">

        </button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed } from 'vue';
  
  const currentView = ref('pagodas');
  
  const detailImages = {
    left: "/images/历史沿革.svg",
    center: "/images/医教融合.png",
    right: "/images/现代白族医药发展.png"
  };
  
  const detailImage = computed(() => detailImages[currentView.value]);
  
  const showContent = (pagoda) => {
    currentView.value = pagoda;
  };
  
  const backToPagodas = () => {
    currentView.value = 'pagodas';
  };
  </script>
  
  <style scoped>
  .dali-region {
    height: 100vh;
    width: 55vw;

    

  }
  
  /* 三塔布局 */
  .dali-Pagodas {
    display: flex;
    justify-content: center;
    align-items: flex-end;
    height: 80%;
    gap: 20px;
  }
  
  .pagodas {
    display: flex;
    justify-content: center;
    align-items: flex-end;
    cursor: pointer;
    transition: all 0.3s ease;
  }
  
  .pagodas-left, .pagodas-right {
    width: 25%;
    height: 70%;
  }
  
  .pagodas-center {
    width: 40%;
    height: 90%;
  }
  
  .pagodas img {
    width: auto;
    height: 100%;
    max-width: 100%;
    object-fit: contain;
    transition: transform 0.3s ease;
  }
  
  .pagodas:hover img {
    transform: scale(1.05);
  }
  
  /* 详情视图 - 图片充满容器 */
  .detail-view {
    position: relative;
    height: 100%;
    width: 100%;
  }
  
  .detail-image {
    width: 101%;
    height: 94%;
    display: block;
  }
  
  /* 返回按钮 */
  .back-button {
    position: absolute;
    bottom: 21px;
    right: 38px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    z-index: 10;
    width: 56px;
    height: 63px;
    background: url(/images/三塔返回.svg);
    background-size: cover;
    transition: all 0.3s;
  }
  
  .back-button:hover {
    transform: translateY(-2px);
  }
  
  /* 标题 */
  h3 {
    text-align: center;
    margin: 20px 0;
    font-size: 4rem;
    color: #000000;
    font-family: 'LiangPeiShengXiaoJian', sans-serif;
  }
  </style>