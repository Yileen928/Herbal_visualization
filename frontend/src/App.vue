<script setup>
import { ref, onMounted } from 'vue';
import BasicHerbalVisualizer from './components/BasicHerbalVisualizer.vue';

// 引用可视化器组件
const visualizer = ref(null);

// 组件挂载后自动加载模型
onMounted(() => {
  console.log("App组件已挂载");
  // 延迟更长时间确保组件完全挂载
  setTimeout(() => {
    try {
      // 加载中草药模型
      const modelPath = '/models/herbal_model.fbx';
      console.log("尝试加载模型路径:", modelPath);
      
      // 检查visualizer引用是否存在
      if (visualizer.value) {
        console.log("visualizer引用存在，调用loadModel方法");
        visualizer.value.loadModel(modelPath);
      } else {
        console.error("visualizer引用不存在");
        // 如果visualizer不存在，可能需要重新尝试
        setTimeout(() => {
          if (visualizer.value) {
            console.log("重试：visualizer引用现在存在");
            visualizer.value.loadModel(modelPath);
          } else {
            console.error("重试失败：visualizer引用仍然不存在");
            alert("加载模型失败：组件引用不存在");
          }
        }, 2000);
      }
    } catch (error) {
      console.error("加载模型时出错:", error);
      alert("加载模型时出错: " + error.message);
    }
  }, 2000); // 增加延迟时间确保组件完全挂载
});
</script>

<template>
  <div class="app-container">
    <div class="visualizer-fullscreen">
      <BasicHerbalVisualizer ref="visualizer" />
    </div>
    
    <!-- 可选：添加一个简单的标题 -->
    <div class="title-overlay">
      <h1>中草药可视化</h1>
      <p class="status-message">正在加载模型，请稍候...</p>
    </div>
  </div>
</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body, html {
  width: 100%;
  height: 100%;
  overflow: hidden;
  background-color: #000;
}

.app-container {
  width: 100vw;
  height: 100vh;
  position: relative;
  font-family: 'Microsoft YaHei', Arial, sans-serif;
}

.visualizer-fullscreen {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
}

/* 可选：半透明标题 */
.title-overlay {
  position: absolute;
  top: 20px;
  left: 0;
  width: 100%;
  text-align: center;
  z-index: 2;
  pointer-events: none; /* 允许点击穿透 */
}

.title-overlay h1 {
  color: rgba(255, 255, 255, 0.7);
  font-size: 2rem;
  text-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  margin: 0;
  padding: 0;
}

.status-message {
  color: rgba(255, 255, 255, 0.7);
  font-size: 1rem;
  margin-top: 10px;
}
</style>
