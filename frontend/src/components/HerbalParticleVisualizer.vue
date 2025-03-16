<template>
  <div ref="container" class="visualizer-container"></div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import * as THREE from 'three';
import { FBXLoader } from 'three/examples/jsm/loaders/FBXLoader';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls';
import TWEEN from '@tweenjs/tween.js';

const container = ref(null);
let scene, camera, renderer, controls;
let particles = [];
let fbxModel = null;
const animationFrameId = ref(null);

// 初始化Three.js场景
const init = () => {
  if (!container.value) return;
  
  // 创建场景
  scene = new THREE.Scene();
  scene.background = new THREE.Color(0x000000);
  
  // 创建相机
  camera = new THREE.PerspectiveCamera(
    75,
    container.value.clientWidth / container.value.clientHeight,
    0.1,
    1000
  );
  camera.position.z = 10;
  
  // 创建渲染器
  renderer = new THREE.WebGLRenderer({ antialias: true });
  renderer.setSize(container.value.clientWidth, container.value.clientHeight);
  container.value.appendChild(renderer.domElement);
  
  // 添加轨道控制器
  controls = new OrbitControls(camera, renderer.domElement);
  controls.enableDamping = true;
  
  // 添加环境光和平行光
  const ambientLight = new THREE.AmbientLight(0xffffff, 0.5);
  scene.add(ambientLight);
  
  const directionalLight = new THREE.DirectionalLight(0xffffff, 0.8);
  directionalLight.position.set(0, 10, 10);
  scene.add(directionalLight);
  
  // 适应窗口大小变化
  window.addEventListener('resize', onWindowResize);
};

// 加载FBX模型并转换为粒子
const loadFbxModel = (modelPath) => {
  const loader = new FBXLoader();
  loader.load(
    modelPath,
    (fbx) => {
      fbxModel = fbx;
      scene.add(fbx);
      
      // 先隐藏原始模型
      fbxModel.visible = false;
      
      // 从模型创建粒子
      createParticlesFromModel(fbxModel);
      
      // 居中模型
      const box = new THREE.Box3().setFromObject(fbxModel);
      const center = box.getCenter(new THREE.Vector3());
      const size = box.getSize(new THREE.Vector3());
      
      // 调整相机位置
      const maxDim = Math.max(size.x, size.y, size.z);
      camera.position.set(0, 0, maxDim * 2);
      controls.target.copy(center);
      controls.update();
    },
    (xhr) => {
      console.log((xhr.loaded / xhr.total) * 100 + '% 加载中');
    },
    (error) => {
      console.error('加载FBX模型时出错:', error);
    }
  );
};

// 从模型创建粒子
const createParticlesFromModel = (model) => {
  // 清除之前的粒子
  particles.forEach(p => scene.remove(p));
  particles = [];
  
  // 粒子材质
  const particleMaterial = new THREE.PointsMaterial({
    color: 0x00ff00,
    size: 0.05,
    transparent: true,
    opacity: 0.8,
    blending: THREE.AdditiveBlending,
    sizeAttenuation: true
  });
  
  // 收集模型顶点
  model.traverse((child) => {
    if (child.isMesh) {
      const positions = child.geometry.attributes.position;
      const vertices = new Float32Array(positions.count * 3);
      
      // 对每隔几个顶点采样，避免粒子太多
      const samplingRate = Math.max(1, Math.floor(positions.count / 10000));
      const particleVertices = [];
      
      for (let i = 0; i < positions.count; i += samplingRate) {
        const x = positions.getX(i);
        const y = positions.getY(i);
        const z = positions.getZ(i);
        
        // 应用模型的变换矩阵
        const vertex = new THREE.Vector3(x, y, z);
        vertex.applyMatrix4(child.matrixWorld);
        
        particleVertices.push(vertex.x, vertex.y, vertex.z);
      }
      
      if (particleVertices.length > 0) {
        const particleGeometry = new THREE.BufferGeometry();
        particleGeometry.setAttribute('position', new THREE.Float32BufferAttribute(particleVertices, 3));
        
        const particleSystem = new THREE.Points(particleGeometry, particleMaterial);
        scene.add(particleSystem);
        particles.push(particleSystem);
      }
    }
  });
  
  // 创建粒子动画效果
  animateParticles();
};

// 粒子动画效果
const animateParticles = () => {
  particles.forEach(particleSystem => {
    const positions = particleSystem.geometry.attributes.position;
    const originalPositions = new Float32Array(positions.array.length);
    originalPositions.set(positions.array);
    
    // 先将粒子分散到随机位置
    for (let i = 0; i < positions.count; i++) {
      const randomPos = new THREE.Vector3(
        (Math.random() - 0.5) * 10,
        (Math.random() - 0.5) * 10,
        (Math.random() - 0.5) * 10
      );
      
      positions.setXYZ(i, randomPos.x, randomPos.y, randomPos.z);
    }
    positions.needsUpdate = true;
    
    // 使用TWEEN创建动画，将粒子从随机位置聚集到模型形状
    const tween = new TWEEN.Tween({ value: 0 })
      .to({ value: 1 }, 2000)
      .easing(TWEEN.Easing.Exponential.Out)
      .delay(Math.random() * 500)
      .onUpdate(function(obj) {
        for (let i = 0; i < positions.count; i++) {
          const i3 = i * 3;
          const currentX = positions.array[i3];
          const currentY = positions.array[i3 + 1];
          const currentZ = positions.array[i3 + 2];
          
          const targetX = originalPositions[i3];
          const targetY = originalPositions[i3 + 1];
          const targetZ = originalPositions[i3 + 2];
          
          positions.array[i3] = currentX + (targetX - currentX) * obj.value;
          positions.array[i3 + 1] = currentY + (targetY - currentY) * obj.value;
          positions.array[i3 + 2] = currentZ + (targetZ - currentZ) * obj.value;
        }
        positions.needsUpdate = true;
      })
      .start();
  });
};

// 动画循环
const animate = () => {
  animationFrameId.value = requestAnimationFrame(animate);
  TWEEN.update();
  controls.update();
  renderer.render(scene, camera);
};

// 处理窗口大小变化
const onWindowResize = () => {
  if (!container.value) return;
  
  camera.aspect = container.value.clientWidth / container.value.clientHeight;
  camera.updateProjectionMatrix();
  renderer.setSize(container.value.clientWidth, container.value.clientHeight);
};

// 暴露加载模型的方法
const loadModel = (modelPath) => {
  loadFbxModel(modelPath);
};

// 组件挂载时初始化
onMounted(() => {
  init();
  animate();
});

// 组件卸载前清理资源
onBeforeUnmount(() => {
  if (animationFrameId.value) {
    cancelAnimationFrame(animationFrameId.value);
  }
  
  if (container.value && renderer) {
    container.value.removeChild(renderer.domElement);
  }
  
  window.removeEventListener('resize', onWindowResize);
  
  // 清理Three.js资源
  if (scene) {
    scene.clear();
  }
  
  if (renderer) {
    renderer.dispose();
  }
});

// 暴露方法供父组件调用
defineExpose({
  loadModel
});
</script>

<style scoped>
.visualizer-container {
  width: 100%;
  height: 100%;
  min-height: 500px;
}
</style> 