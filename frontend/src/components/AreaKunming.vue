<template>
    <div class="Kunming-region">
      <div class="detail-content">
        <router-link to="/ancient">
          <img src="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/acientbutton.png" alt="acientbutton" class="ancientbutton">
        </router-link>
      </div>
       
      <div class="detail-section">
        <img src="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/兰茂生平.png" alt="兰茂" class="lanmao">
      </div>
    </div>
  </template>
  
  <script setup>
  import { useRouter } from 'vue-router'
  
  const router = useRouter()
  
  // 如果需要在点击时执行其他逻辑，可以添加这个方法
  const navigateToAncient = () => {
    router.push('/ancient')
  }
  </script>
  
  <style scoped>
  .Kunming-region {
    height: 100vh;
    display:flex;
    width: 55vw;
  }
  .detail-content {
    flex: 2.2;
    position: relative; /* 为绝对定位的子元素提供参考 */
  }
  .detail-section {
    flex: 7.8;
  }
  .ancientbutton{
    position: absolute;
  bottom: 40%;
  left: 50%;
  transform: translateX(-50%);
  width: 48%;
  height: auto;
  max-height: 15%;
  aspect-ratio: 1/1;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  
  }
  .ancientbutton:hover{
    transform: scale(1.3);
  }
  .lanmao{
    width: 120%;
    height: 108%;
  }
  
  /* 确保router-link不添加额外样式 */
  a {
    text-decoration: none;
    color: inherit;
  }
  </style>