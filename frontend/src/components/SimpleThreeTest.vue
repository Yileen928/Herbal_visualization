<template>
  <div ref="container" class="three-container"></div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import * as THREE from 'three';

const container = ref(null);
let scene, camera, renderer;
let cube;

function init() {
  // 创建场景
  scene = new THREE.Scene();
  scene.background = new THREE.Color(0x000000);

  // 创建相机
  camera = new THREE.PerspectiveCamera(75, container.value.clientWidth / container.value.clientHeight, 0.1, 1000);
  camera.position.z = 5;

  // 创建渲染器
  renderer = new THREE.WebGLRenderer({ antialias: true });
  renderer.setSize(container.value.clientWidth, container.value.clientHeight);
  container.value.appendChild(renderer.domElement);

  // 创建一个简单的立方体
  const geometry = new THREE.BoxGeometry(1, 1, 1);
  const material = new THREE.MeshBasicMaterial({ color: 0x00ff00 });
  cube = new THREE.Mesh(geometry, material);
  scene.add(cube);

  // 开始动画循环
  animate();
}

function animate() {
  requestAnimationFrame(animate);

  // 旋转立方体
  cube.rotation.x += 0.01;
  cube.rotation.y += 0.01;

  // 渲染
  renderer.render(scene, camera);
}

onMounted(() => {
  console.log('组件已挂载');
  if (container.value) {
    console.log('容器已找到');
    init();
  }
});
</script>

<style scoped>
.three-container {
  width: 100%;
  height: 500px;
  background-color: #000;
}
</style>
