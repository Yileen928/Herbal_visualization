<template>
  <div class="model-container" ref="container">
    <div v-if="loading" class="loading-text">模型加载中...</div>
    <div v-if="error" class="error-text">模型加载失败</div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import * as THREE from 'three'
import { FBXLoader } from 'three/examples/jsm/loaders/FBXLoader'
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls'

const props = defineProps({
  modelPath: {
    type: String,
    required: true
  },
  modelName: {
    type: String,
    required: true
  }
})

const container = ref(null)
const loading = ref(true)
const error = ref(false)
let scene, camera, renderer, model, controls
let animationFrameId

const initThreeJS = () => {
  try {
    scene = new THREE.Scene()
    // 移除白色背景，使其透明
    scene.background = null
    
    // 调整相机位置和视角
    camera = new THREE.PerspectiveCamera(45, 870 / 600, 0.1, 2000)  // 调整远平面距离
    camera.position.set(0, 300, 600)  // 增加相机距离和高度
    camera.lookAt(0, 100, 0)  // 调整视点
    
    // 修改渲染器设置
    renderer = new THREE.WebGLRenderer({ 
      antialias: true,
      alpha: true,
      preserveDrawingBuffer: true
    })
    renderer.setSize(870, 600)
    renderer.setClearColor(0x000000, 0)
    renderer.setPixelRatio(window.devicePixelRatio)
    
    // 增强光源
    const ambientLight = new THREE.AmbientLight(0xffffff, 3) // 增加环境光强度
    scene.add(ambientLight)
    
    // 添加多个方向光源以提供更好的照明
    const directionalLight1 = new THREE.DirectionalLight(0xffffff, 2.0)
    directionalLight1.position.set(1, 1, 1)
    scene.add(directionalLight1)
    
    const directionalLight2 = new THREE.DirectionalLight(0xffffff, 1.5)
    directionalLight2.position.set(-1, 1, -1)
    scene.add(directionalLight2)
    
    // 添加点光源以增强细节
    const pointLight = new THREE.PointLight(0xffffff, 1.0)
    pointLight.position.set(0, 200, 0)
    scene.add(pointLight)
    
    
    // 修改控制器设置
    controls = new OrbitControls(camera, renderer.domElement)
    controls.enableDamping = true
    controls.dampingFactor = 0.05
    controls.enableZoom = true
    controls.enablePan = true
    
    const loader = new FBXLoader()
    loader.load(
      props.modelPath,
      (fbx) => {
        model = fbx
        
        // 调整模型位置和大小
        const box = new THREE.Box3().setFromObject(model)
        const size = box.getSize(new THREE.Vector3())
        const center = box.getCenter(new THREE.Vector3())
        
        const maxDim = Math.max(size.x, size.y, size.z)
        const scale = 400 / maxDim  // 稍微调整缩放比例
        model.scale.multiplyScalar(scale)
        
        // 创建一个组来包含模型
        const modelGroup = new THREE.Group()
        modelGroup.add(model)
        
        // 将模型移动到组的中心并调整高度
        model.position.sub(center.multiplyScalar(scale))
        modelGroup.position.set(0, 50, 0)  // 调整模型高度
        
        scene.add(modelGroup)
        
        loading.value = false
        animate()
      },
      (progress) => {
        console.log(`${props.modelName}模型加载进度：`, (progress.loaded / progress.total * 100) + '%')
      },
      (error) => {
        console.error(`${props.modelName}模型加载失败：`, error)
        error.value = true
        loading.value = false
      }
    )
    
    container.value.appendChild(renderer.domElement)
  } catch (err) {
    console.error('初始化3D场景失败：', err)
    error.value = true
    loading.value = false
  }
}

const animate = () => {
  animationFrameId = requestAnimationFrame(animate)
  if (controls) {
    controls.update()
  }
  // 添加模型自转
  if (model && model.parent) {
    // 让模型绕其自身的中心轴旋转
    model.parent.rotation.y += 0.01
  }
  renderer.render(scene, camera)
}

onMounted(() => {
  initThreeJS()
})

onBeforeUnmount(() => {
  if (animationFrameId) {
    cancelAnimationFrame(animationFrameId)
  }
  if (controls) {
    controls.dispose()
  }
  if (renderer) {
    renderer.dispose()
  }
  if (scene) {
    scene.clear()
  }
})
</script>

<style scoped>
.model-container {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: transparent;
  position: relative;
  margin: 0 auto;  /* 添加水平居中 */
}

.loading-text, .error-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 16px;
  color: #666;
}

.error-text {
  color: #ff4444;
}
</style>