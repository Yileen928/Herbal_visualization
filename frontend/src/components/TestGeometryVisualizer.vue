<template>
  <div ref="container" class="visualizer-container"></div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import * as THREE from 'three';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls';
import TWEEN from '@tweenjs/tween.js';

const container = ref(null);
let scene, camera, renderer, controls;
let particles = [];
let testModel = null;
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
  
  // 创建测试几何体
  createTestGeometry();
};

// 创建测试几何体
const createTestGeometry = () => {
  // 创建一个适合中草药形状的几何体（此处用叶子的形状近似表示）
  const shape = new THREE.Shape();
  
  // 绘制叶子形状的轮廓
  shape.moveTo(0, 0);
  shape.bezierCurveTo(1, 1, 2, 1, 3, 0);
  shape.bezierCurveTo(2, -1, 1, -1, 0, 0);
  
  // 挤压成3D形状
  const extrudeSettings = {
    steps: 2,
    depth: 0.2,
    bevelEnabled: true,
    bevelThickness: 0.1,
    bevelSize: 0.1,
    bevelOffset: 0,
    bevelSegments: 3
  };
  
  const geometry = new THREE.ExtrudeGeometry(shape, extrudeSettings);
  
  // 叶子材质
  const material = new THREE.MeshStandardMaterial({ 
    color: 0x00aa00,
    roughness: 0.7,
    metalness: 0.2
  });
  
  // 创建叶子模型
  testModel = new THREE.Mesh(geometry, material);
  testModel.scale.set(1, 1, 1);
  scene.add(testModel);
  
  // 创建茎的几何体
  const stemGeometry = new THREE.CylinderGeometry(0.1, 0.1, 5, 8);
  const stemMaterial = new THREE.MeshStandardMaterial({ color: 0x8B4513 });
  const stem = new THREE.Mesh(stemGeometry, stemMaterial);
  stem.position.set(0, -2.5, 0);
  scene.add(stem);
  
  // 将茎和叶子组合为一个组
  const group = new THREE.Group();
  group.add(testModel);
  group.add(stem);
  
  // 隐藏原始模型
  testModel.visible = false;
  stem.visible = false;
  
  // 从模型创建粒子
  createParticlesFromModel(group);
};

// 从模型创建粒子
const createParticlesFromModel = (model) => {
  // 清除之前的粒子
  particles.forEach(p => scene.remove(p));
  particles = [];
  
  // 叶子粒子材质（绿色）
  const leafParticleMaterial = new THREE.PointsMaterial({
    color: 0x00ff00,
    size: 0.05,
    transparent: true,
    opacity: 0.8,
    blending: THREE.AdditiveBlending,
    sizeAttenuation: true
  });
  
  // 茎粒子材质（棕色）
  const stemParticleMaterial = new THREE.PointsMaterial({
    color: 0x8B4513,
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
      
      // 对每隔几个顶点采样，避免粒子太多
      const samplingRate = Math.max(1, Math.floor(positions.count / 5000));
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
        
        // 根据子对象选择材质（叶子或茎）
        const material = child.material.color.getHex() === 0x00aa00 ? 
                         leafParticleMaterial : stemParticleMaterial;
        
        const particleSystem = new THREE.Points(particleGeometry, material);
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

// 重新生成粒子效果
const regenerateParticles = () => {
  if (scene && testModel) {
    createParticlesFromModel(testModel);
  }
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
  regenerateParticles
});
</script>

<style scoped>
.visualizer-container {
  width: 100%;
  height: 100%;
  min-height: 500px;
}
</style> 