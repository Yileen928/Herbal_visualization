<template>
  <div ref="container" class="visualizer-container"></div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import * as THREE from 'three';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls';
import { FBXLoader } from 'three/examples/jsm/loaders/FBXLoader';
import TWEEN from '@tweenjs/tween.js';

const container = ref(null);
let scene, camera, renderer, controls;
let model = null;
let particles = [];
let animationFrameId = null;
let originalModel = null; // 保存原始模型引用

// 初始化Three.js场景
const init = () => {
  if (!container.value) {
    console.error("容器元素不存在");
    return;
  }
  
  console.log("开始初始化Three.js场景");
  
  // 创建场景
  scene = new THREE.Scene();
  scene.background = new THREE.Color(0x000000);
  
  // 创建相机
  camera = new THREE.PerspectiveCamera(
    60,
    window.innerWidth / window.innerHeight,
    0.1,
    1000
  );
  camera.position.z = 5;
  
  // 创建渲染器
  renderer = new THREE.WebGLRenderer({ 
    antialias: true,
    alpha: true
  });
  renderer.setSize(window.innerWidth, window.innerHeight);
  renderer.setPixelRatio(window.devicePixelRatio);
  
  // 检查容器是否已经有子元素
  while (container.value.firstChild) {
    container.value.removeChild(container.value.firstChild);
  }
  
  container.value.appendChild(renderer.domElement);
  console.log("渲染器已添加到DOM");
  
  // 添加控制器
  controls = new OrbitControls(camera, renderer.domElement);
  controls.enableDamping = true;
  controls.dampingFactor = 0.05;
  controls.rotateSpeed = 0.5;
  controls.autoRotate = true;
  controls.autoRotateSpeed = 0.5;
  
  // 添加光源
  const ambientLight = new THREE.AmbientLight(0xffffff, 0.5);
  scene.add(ambientLight);
  
  const directionalLight = new THREE.DirectionalLight(0xffffff, 0.8);
  directionalLight.position.set(0, 10, 10);
  scene.add(directionalLight);
  
  // 添加背景光效果
  addBackgroundLights();
  
  // 移除调试辅助工具
  // const axesHelper = new THREE.AxesHelper(5);
  // scene.add(axesHelper);
  
  // 移除网格辅助
  // const gridHelper = new THREE.GridHelper(10, 10);
  // scene.add(gridHelper);
  
  console.log("场景初始化完成，对象数量:", scene.children.length);
  
  // 适应窗口大小变化
  window.addEventListener('resize', onResize);
};

// 添加背景光效果
const addBackgroundLights = () => {
  // 添加几个彩色点光源在场景周围
  const colors = [0x0088ff, 0x8800ff, 0x00ff88];
  const positions = [
    new THREE.Vector3(-10, 5, 5),
    new THREE.Vector3(10, -5, 5),
    new THREE.Vector3(0, 5, -10)
  ];
  
  for (let i = 0; i < colors.length; i++) {
    const light = new THREE.PointLight(colors[i], 0.5, 20);
    light.position.copy(positions[i]);
    scene.add(light);
  }
};

// 加载FBX模型
const loadModel = (modelPath) => {
  console.log("尝试加载模型:", modelPath);
  
  // 清除当前模型和粒子
  if (model) {
    scene.remove(model);
    model = null;
  }
  
  if (originalModel) {
    scene.remove(originalModel);
    originalModel = null;
  }
  
  clearParticles();
  
  // 如果没有提供路径，显示错误
  if (!modelPath) {
    console.error("未提供模型路径");
    alert("错误：未提供模型路径");
    return;
  }
  
  try {
    // 创建加载器
    const loader = new FBXLoader();
    
    // 添加调试信息
    console.log("FBX加载器已创建，开始加载模型:", modelPath);
    
    // 加载FBX模型
    loader.load(
      modelPath,
      (fbx) => {
        console.log("FBX模型加载成功", fbx);
        
        // 保存模型引用
        model = fbx;
        originalModel = fbx;
        
        // 调整模型大小和位置
        const box = new THREE.Box3().setFromObject(model);
        const size = box.getSize(new THREE.Vector3());
        const center = box.getCenter(new THREE.Vector3());
        
        const maxDim = Math.max(size.x, size.y, size.z);
        const scale = 3 / maxDim;
        model.scale.set(scale, scale, scale);
        
        model.position.sub(center.multiplyScalar(scale));
        
        // 添加到场景
        scene.add(model);
        console.log("模型已添加到场景，场景中的对象数量:", scene.children.length);
        
        // 显示原始模型
        model.visible = true;
        
        // 调整相机位置
        camera.position.z = 5;
        controls.update();
        
        // 为模型添加淡入效果
        model.traverse((child) => {
          if (child.isMesh && child.material) {
            // 如果是数组，处理每个材质
            if (Array.isArray(child.material)) {
              child.material.forEach(mat => {
                mat.transparent = true;
                mat.opacity = 0;
              });
              
              // 创建淡入动画
              const fadeInTween = new TWEEN.Tween({ opacity: 0 })
                .to({ opacity: 1 }, 2000)
                .easing(TWEEN.Easing.Quadratic.Out)
                .onUpdate(function(obj) {
                  child.material.forEach(mat => {
                    mat.opacity = obj.opacity;
                  });
                })
                .start();
            } 
            // 单个材质
            else {
              child.material.transparent = true;
              child.material.opacity = 0;
              
              // 创建淡入动画
              const fadeInTween = new TWEEN.Tween({ opacity: 0 })
                .to({ opacity: 1 }, 2000)
                .easing(TWEEN.Easing.Quadratic.Out)
                .onUpdate(function(obj) {
                  child.material.opacity = obj.opacity;
                })
                .start();
            }
          }
        });
        
        // 延迟一段时间后开始粒子化转换
        setTimeout(() => {
          // 创建粒子效果
          createParticlesFromModel(model);
          
          // 创建模型淡出动画
          model.traverse((child) => {
            if (child.isMesh && child.material) {
              // 如果是数组，处理每个材质
              if (Array.isArray(child.material)) {
                // 创建淡出动画
                const fadeOutTween = new TWEEN.Tween({ opacity: 1 })
                  .to({ opacity: 0 }, 3000)
                  .easing(TWEEN.Easing.Quadratic.In)
                  .onUpdate(function(obj) {
                    child.material.forEach(mat => {
                      mat.opacity = obj.opacity;
                    });
                  })
                  .onComplete(function() {
                    // 完全淡出后隐藏模型
                    model.visible = false;
                  })
                  .start();
              } 
              // 单个材质
              else {
                // 创建淡出动画
                const fadeOutTween = new TWEEN.Tween({ opacity: 1 })
                  .to({ opacity: 0 }, 3000)
                  .easing(TWEEN.Easing.Quadratic.In)
                  .onUpdate(function(obj) {
                    child.material.opacity = obj.opacity;
                  })
                  .onComplete(function() {
                    // 完全淡出后隐藏模型
                    model.visible = false;
                  })
                  .start();
              }
            }
          });
        }, 4000); // 显示模型4秒后开始转换为粒子
      },
      (xhr) => {
        // 加载进度
        console.log((xhr.loaded / xhr.total * 100) + '% 已加载');
        // 可以在这里更新加载进度显示
      },
      (error) => {
        // 加载错误
        console.error('加载FBX模型出错:', error);
        alert(`加载模型失败: ${error.message || '未知错误'}\n请确保模型文件存在于 /public/models/ 目录下`);
        
        // 不再自动创建默认叶子模型
        // createLeafModel();
      }
    );
  } catch (error) {
    console.error('FBX加载器错误:', error);
    alert(`FBX加载器错误: ${error.message || '未知错误'}`);
    
    // 不再自动创建默认叶子模型
    // createLeafModel();
  }
};

// 创建叶子模型（当FBX加载失败时的备选方案）
const createLeafModel = () => {
  console.log("创建叶子模型");
  
  // 创建一个更复杂的叶子形状
  const shape = new THREE.Shape();
  
  // 叶子轮廓点
  shape.moveTo(0, 0);
  shape.bezierCurveTo(1, 1.5, 2.5, 2, 4, 0);
  shape.bezierCurveTo(3.5, -0.5, 3, -0.8, 2.5, -1);
  shape.bezierCurveTo(2, -1.2, 1, -1, 0, 0);
  
  // 添加叶脉
  const holeCount = 5;
  for (let i = 1; i < holeCount; i++) {
    const veinPath = new THREE.Path();
    const x = i * (4 / holeCount);
    const width = 0.05;
    
    veinPath.moveTo(x, -0.01);
    veinPath.lineTo(x + width, -0.01);
    veinPath.lineTo(x + width, 0.01);
    veinPath.lineTo(x, 0.01);
    
    shape.holes.push(veinPath);
  }
  
  const extrudeSettings = {
    steps: 4, // 增加步数
    depth: 0.2,
    bevelEnabled: true,
    bevelThickness: 0.1,
    bevelSize: 0.1,
    bevelSegments: 5 // 增加细分
  };
  
  const geometry = new THREE.ExtrudeGeometry(shape, extrudeSettings);
  
  // 创建更复杂的材质
  const material = new THREE.MeshPhysicalMaterial({ 
    color: 0x00aa00,
    transparent: true,
    opacity: 0,
    metalness: 0.1,
    roughness: 0.8,
    clearcoat: 0.5,
    clearcoatRoughness: 0.3
  });
  
  model = new THREE.Mesh(geometry, material);
  originalModel = model;
  
  // 添加一些随机旋转
  model.rotation.x = Math.PI / 6;
  model.rotation.y = Math.PI / 4;
  
  scene.add(model);
  
  // 显示原始模型并添加淡入效果
  model.visible = true;
  
  // 创建淡入动画
  const fadeInTween = new TWEEN.Tween({ opacity: 0 })
    .to({ opacity: 1 }, 2000)
    .easing(TWEEN.Easing.Quadratic.Out)
    .onUpdate(function(obj) {
      material.opacity = obj.opacity;
    })
    .start();
  
  // 添加额外的叶子作为装饰
  addExtraLeaves();
  
  // 延迟一段时间后开始粒子化
  setTimeout(() => {
    // 创建粒子效果
    createParticlesFromModel(model);
    
    // 创建模型淡出动画
    const fadeOutTween = new TWEEN.Tween({ opacity: 1 })
      .to({ opacity: 0 }, 3000)
      .easing(TWEEN.Easing.Quadratic.In)
      .onUpdate(function(obj) {
        material.opacity = obj.opacity;
      })
      .onComplete(function() {
        // 完全淡出后隐藏模型
        model.visible = false;
      })
      .start();
  }, 4000);
};

// 添加额外的叶子作为装饰
const addExtraLeaves = () => {
  // 添加几个小叶子围绕主叶子
  const leafCount = 5;
  
  for (let i = 0; i < leafCount; i++) {
    // 创建小叶子形状
    const smallShape = new THREE.Shape();
    smallShape.moveTo(0, 0);
    smallShape.bezierCurveTo(0.5, 0.7, 1.2, 1, 2, 0);
    smallShape.bezierCurveTo(1.7, -0.3, 1.5, -0.4, 1.2, -0.5);
    smallShape.bezierCurveTo(1, -0.6, 0.5, -0.5, 0, 0);
    
    const smallExtrudeSettings = {
      steps: 2,
      depth: 0.1,
      bevelEnabled: true,
      bevelThickness: 0.05,
      bevelSize: 0.05,
      bevelSegments: 3
    };
    
    const smallGeometry = new THREE.ExtrudeGeometry(smallShape, smallExtrudeSettings);
    const smallMaterial = new THREE.MeshPhysicalMaterial({
      color: 0x00cc00,
      transparent: true,
      opacity: 0,
      metalness: 0.1,
      roughness: 0.8
    });
    
    const smallLeaf = new THREE.Mesh(smallGeometry, smallMaterial);
    
    // 放置在主叶子周围
    const angle = (i / leafCount) * Math.PI * 2;
    const radius = 3;
    smallLeaf.position.x = Math.cos(angle) * radius;
    smallLeaf.position.y = Math.sin(angle) * radius;
    smallLeaf.position.z = (Math.random() - 0.5) * 2;
    
    // 随机旋转
    smallLeaf.rotation.x = Math.random() * Math.PI;
    smallLeaf.rotation.y = Math.random() * Math.PI;
    smallLeaf.rotation.z = Math.random() * Math.PI;
    
    // 随机缩放
    const scale = 0.5 + Math.random() * 0.5;
    smallLeaf.scale.set(scale, scale, scale);
    
    scene.add(smallLeaf);
    
    // 创建淡入动画
    const fadeInTween = new TWEEN.Tween({ opacity: 0 })
      .to({ opacity: 0.8 }, 2000)
      .delay(i * 200) // 错开淡入时间
      .easing(TWEEN.Easing.Quadratic.Out)
      .onUpdate(function(obj) {
        smallMaterial.opacity = obj.opacity;
      })
      .start();
    
    // 添加浮动动画
    const startY = smallLeaf.position.y;
    const floatTween = new TWEEN.Tween({ y: startY })
      .to({ y: startY + 0.3 }, 2000 + i * 300)
      .repeat(Infinity)
      .yoyo(true)
      .easing(TWEEN.Easing.Sinusoidal.InOut)
      .onUpdate(function(obj) {
        smallLeaf.position.y = obj.y;
      })
      .start();
    
    // 延迟后将小叶子转换为粒子
    setTimeout(() => {
      // 创建淡出动画
      const fadeOutTween = new TWEEN.Tween({ opacity: 0.8 })
        .to({ opacity: 0 }, 2000)
        .easing(TWEEN.Easing.Quadratic.In)
        .onUpdate(function(obj) {
          smallMaterial.opacity = obj.opacity;
        })
        .onComplete(function() {
          smallLeaf.visible = false;
          
          // 从小叶子创建粒子
          const particleMaterial = new THREE.PointsMaterial({
            color: 0x00ff88,
            size: 0.02,
            transparent: true,
            opacity: 0.8,
            blending: THREE.AdditiveBlending,
            sizeAttenuation: true
          });
          
          const particleGeometry = new THREE.BufferGeometry();
          const positions = smallGeometry.attributes.position;
          const particleVertices = [];
          
          // 使用所有顶点
          for (let j = 0; j < positions.count; j++) {
            // 应用小叶子的变换
            const vertex = new THREE.Vector3(
              positions.getX(j),
              positions.getY(j),
              positions.getZ(j)
            );
            
            // 应用小叶子的变换
            vertex.applyMatrix4(smallLeaf.matrixWorld);
            
            particleVertices.push(vertex.x, vertex.y, vertex.z);
          }
          
          // 添加额外的随机粒子
          for (let j = 0; j < 300; j++) {
            const randomIndex = Math.floor(Math.random() * particleVertices.length / 3) * 3;
            const baseX = particleVertices[randomIndex];
            const baseY = particleVertices[randomIndex + 1];
            const baseZ = particleVertices[randomIndex + 2];
            
            const offsetScale = 0.1;
            const offsetX = (Math.random() - 0.5) * offsetScale;
            const offsetY = (Math.random() - 0.5) * offsetScale;
            const offsetZ = (Math.random() - 0.5) * offsetScale;
            
            particleVertices.push(baseX + offsetX, baseY + offsetY, baseZ + offsetZ);
          }
          
          particleGeometry.setAttribute('position', new THREE.Float32BufferAttribute(particleVertices, 3));
          
          const particleSystem = new THREE.Points(particleGeometry, particleMaterial);
          scene.add(particleSystem);
          particles.push(particleSystem);
          
          // 为这些粒子添加特殊动画
          animateParticles();
        })
        .start();
    }, 5000 + i * 500);
  }
};

// 清除粒子
const clearParticles = () => {
  particles.forEach(p => scene.remove(p));
  particles = [];
};

// 从模型创建粒子
const createParticlesFromModel = (model) => {
  console.log("从模型创建粒子");
  
  // 清除之前的粒子
  clearParticles();
  
  // 粒子材质
  const particleMaterial = new THREE.PointsMaterial({
    color: 0x00ff88,
    size: 0.03, // 减小粒子大小以适应更高密度
    transparent: true,
    opacity: 0.8,
    blending: THREE.AdditiveBlending,
    sizeAttenuation: true
  });
  
  // 收集模型顶点
  model.traverse((child) => {
    if (child.isMesh) {
      const positions = child.geometry.attributes.position;
      
      // 减小采样率，使用更多顶点作为粒子
      const samplingRate = Math.max(1, Math.floor(positions.count / 50000)); // 增加到50000个粒子
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
        
        console.log(`创建了 ${particleVertices.length / 3} 个粒子`);
      }
    }
  });
  
  // 创建粒子动画效果
  animateParticles();
};

// 粒子动画效果 - 模型直接变成粒子
const animateParticles = () => {
  console.log("创建粒子动画 - 模型直接变成粒子");
  
  particles.forEach(particleSystem => {
    const positions = particleSystem.geometry.attributes.position;
    const originalPositions = new Float32Array(positions.array.length);
    originalPositions.set(positions.array);
    
    // 初始时粒子位于模型原始位置
    // 不需要将粒子分散到随机位置
    
    // 创建粒子大小和透明度的初始值
    particleSystem.material.size = 0.01; // 开始时粒子很小
    particleSystem.material.opacity = 0.1; // 开始时粒子半透明
    
    // 使用TWEEN创建动画，让粒子逐渐变大并增加透明度
    const appearTween = new TWEEN.Tween({ size: 0.01, opacity: 0.1 })
      .to({ size: 0.03, opacity: 0.8 }, 3000) // 3秒内粒子逐渐显现
      .easing(TWEEN.Easing.Quadratic.Out)
      .onUpdate(function(obj) {
        particleSystem.material.size = obj.size;
        particleSystem.material.opacity = obj.opacity;
      })
      .start();
    
    // 创建粒子位置的动画，让粒子在原位置周围轻微扩散
    setTimeout(() => {
      // 创建粒子扩散动画
      const disperseTween = new TWEEN.Tween({ value: 0 })
        .to({ value: 1 }, 5000) // 5秒内粒子逐渐扩散
        .easing(TWEEN.Easing.Quadratic.InOut)
        .onUpdate(function(obj) {
          for (let i = 0; i < positions.count; i++) {
            const i3 = i * 3;
            
            // 为每个粒子创建一个独特的扩散方向
            const dispersionFactor = obj.value * 0.1; // 减小扩散程度，从0.15改为0.1
            const uniqueOffset = (Math.sin(i * 0.1) * 0.5 + 0.5) * dispersionFactor;
            
            // 计算扩散方向（从中心向外）
            const dirX = originalPositions[i3] * uniqueOffset;
            const dirY = originalPositions[i3 + 1] * uniqueOffset;
            const dirZ = originalPositions[i3 + 2] * uniqueOffset;
            
            // 应用扩散
            positions.array[i3] = originalPositions[i3] + dirX;
            positions.array[i3 + 1] = originalPositions[i3 + 1] + dirY;
            positions.array[i3 + 2] = originalPositions[i3 + 2] + dirZ;
          }
          positions.needsUpdate = true;
        })
        .start();
    }, 2000); // 在粒子显现后开始扩散
    
    // 添加一个循环动画，使粒子轻微波动
    setTimeout(() => {
      const waveTween = new TWEEN.Tween({ value: 0 })
        .to({ value: 1 }, 4000)
        .repeat(Infinity)
        .yoyo(true) // 来回波动
        .easing(TWEEN.Easing.Sinusoidal.InOut)
        .onUpdate(function(obj) {
          for (let i = 0; i < positions.count; i++) {
            const i3 = i * 3;
            // 在当前位置基础上添加小幅度波动
            const waveX = Math.sin(i * 0.1 + obj.value * Math.PI * 2) * 0.02;
            const waveY = Math.cos(i * 0.1 + obj.value * Math.PI * 2) * 0.02;
            const waveZ = Math.sin(i * 0.2 + obj.value * Math.PI * 2) * 0.02;
            
            // 获取当前扩散后的位置
            const currentX = positions.array[i3];
            const currentY = positions.array[i3 + 1];
            const currentZ = positions.array[i3 + 2];
            
            // 添加波动
            positions.array[i3] = currentX + waveX;
            positions.array[i3 + 1] = currentY + waveY;
            positions.array[i3 + 2] = currentZ + waveZ;
          }
          positions.needsUpdate = true;
        })
        .start();
    }, 7000); // 在扩散动画后开始波动
    
    // 添加颜色变化动画
    const colorTween = new TWEEN.Tween({ r: 0, g: 1, b: 0.5 })
      .to({ r: 0, g: 0.5, b: 1 }, 10000)
      .repeat(Infinity)
      .yoyo(true)
      .easing(TWEEN.Easing.Sinusoidal.InOut)
      .onUpdate(function(obj) {
        particleSystem.material.color.setRGB(obj.r, obj.g, obj.b);
      })
      .start();
  });
};

// 重新生成粒子效果
const regenerateParticles = () => {
  console.log("重新生成粒子效果");
  
  if (model) {
    // 清除当前粒子
    clearParticles();
    
    // 先显示原始模型
    if (originalModel) {
      originalModel.visible = true;
      
      // 延迟后开始粒子化
      setTimeout(() => {
        originalModel.visible = false;
        // 重新创建粒子
        createParticlesFromModel(model);
      }, 1500);
    } else {
      // 如果没有原始模型，直接创建粒子
      createParticlesFromModel(model);
    }
  }
};

// 动画循环
const animate = () => {
  animationFrameId = requestAnimationFrame(animate);
  
  // 更新TWEEN
  TWEEN.update();
  
  // 更新控制器
  if (controls) controls.update();
  
  // 渲染场景
  if (renderer && scene && camera) {
    renderer.render(scene, camera);
  }
};

// 调整窗口大小
const onResize = () => {
  if (!camera || !renderer) return;
  
  camera.aspect = window.innerWidth / window.innerHeight;
  camera.updateProjectionMatrix();
  renderer.setSize(window.innerWidth, window.innerHeight);
};

// 组件挂载
onMounted(() => {
  console.log("组件已挂载");
  init();
  
  // 不再自动创建默认几何体
  // createDefaultGeometry();
  
  animate();
  window.addEventListener('resize', onResize);
});

// 创建默认几何体
const createDefaultGeometry = () => {
  console.log("创建默认几何体");
  
  // 创建一个更复杂的几何体，具有更多顶点
  const geometry = new THREE.TorusKnotGeometry(1, 0.4, 128, 32); // 增加细分数量
  const material = new THREE.MeshStandardMaterial({
    color: 0x00ff88,
    metalness: 0.5,
    roughness: 0.5
  });
  
  const mesh = new THREE.Mesh(geometry, material);
  scene.add(mesh);
  
  // 添加调试信息
  console.log("默认几何体已添加到场景");
  console.log("几何体顶点数量:", geometry.attributes.position.count);
  console.log("场景中的对象数量:", scene.children.length);
  
  // 延迟后将默认几何体转换为粒子
  setTimeout(() => {
    // 隐藏原始几何体
    mesh.visible = false;
    
    // 创建粒子
    createParticlesFromGeometry(geometry);
  }, 3000);
  
  // 添加额外的小球体作为装饰
  addDecorationSpheres();
};

// 添加装饰性小球体
const addDecorationSpheres = () => {
  // 创建一些小球体围绕主几何体
  const sphereCount = 10;
  const radius = 3;
  
  for (let i = 0; i < sphereCount; i++) {
    const angle = (i / sphereCount) * Math.PI * 2;
    const x = Math.cos(angle) * radius;
    const y = Math.sin(angle) * radius;
    const z = (Math.random() - 0.5) * 2;
    
    const sphereGeometry = new THREE.SphereGeometry(0.2, 16, 16);
    const sphereMaterial = new THREE.MeshStandardMaterial({
      color: 0x00ffaa,
      emissive: 0x003311,
      metalness: 0.8,
      roughness: 0.2
    });
    
    const sphere = new THREE.Mesh(sphereGeometry, sphereMaterial);
    sphere.position.set(x, y, z);
    scene.add(sphere);
    
    // 为小球体添加动画
    const startY = sphere.position.y;
    const startX = sphere.position.x;
    
    // 创建小球体的动画
    const sphereTween = new TWEEN.Tween({ y: startY, x: startX, scale: 1 })
      .to({ y: startY + 0.5, x: startX + 0.3, scale: 0.8 }, 2000 + i * 200)
      .repeat(Infinity)
      .yoyo(true)
      .easing(TWEEN.Easing.Sinusoidal.InOut)
      .onUpdate(function(obj) {
        sphere.position.y = obj.y;
        sphere.position.x = obj.x;
        sphere.scale.set(obj.scale, obj.scale, obj.scale);
      })
      .start();
    
    // 延迟后将小球体也转换为粒子
    setTimeout(() => {
      // 隐藏小球体
      sphere.visible = false;
      
      // 从小球体创建粒子
      const particleMaterial = new THREE.PointsMaterial({
        color: 0x00ffaa,
        size: 0.02,
        transparent: true,
        opacity: 0.8,
        blending: THREE.AdditiveBlending,
        sizeAttenuation: true
      });
      
      const particleGeometry = new THREE.BufferGeometry();
      const positions = sphereGeometry.attributes.position;
      const particleVertices = [];
      
      // 使用所有顶点
      for (let j = 0; j < positions.count; j++) {
        const x = positions.getX(j) + sphere.position.x;
        const y = positions.getY(j) + sphere.position.y;
        const z = positions.getZ(j) + sphere.position.z;
        
        particleVertices.push(x, y, z);
      }
      
      // 添加额外的随机粒子
      for (let j = 0; j < 200; j++) {
        const randomIndex = Math.floor(Math.random() * particleVertices.length / 3) * 3;
        const baseX = particleVertices[randomIndex];
        const baseY = particleVertices[randomIndex + 1];
        const baseZ = particleVertices[randomIndex + 2];
        
        const offsetScale = 0.1;
        const offsetX = (Math.random() - 0.5) * offsetScale;
        const offsetY = (Math.random() - 0.5) * offsetScale;
        const offsetZ = (Math.random() - 0.5) * offsetScale;
        
        particleVertices.push(baseX + offsetX, baseY + offsetY, baseZ + offsetZ);
      }
      
      particleGeometry.setAttribute('position', new THREE.Float32BufferAttribute(particleVertices, 3));
      
      const particleSystem = new THREE.Points(particleGeometry, particleMaterial);
      scene.add(particleSystem);
      particles.push(particleSystem);
      
      // 为这些粒子添加特殊动画
      const spherePositions = particleSystem.geometry.attributes.position;
      const sphereOriginalPositions = new Float32Array(spherePositions.array.length);
      sphereOriginalPositions.set(spherePositions.array);
      
      const waveTween = new TWEEN.Tween({ value: 0 })
        .to({ value: 1 }, 3000)
        .repeat(Infinity)
        .yoyo(true)
        .easing(TWEEN.Easing.Sinusoidal.InOut)
        .onUpdate(function(obj) {
          for (let j = 0; j < spherePositions.count; j++) {
            const j3 = j * 3;
            const waveX = Math.sin(j * 0.1 + obj.value * Math.PI * 2) * 0.05;
            const waveY = Math.cos(j * 0.1 + obj.value * Math.PI * 2) * 0.05;
            const waveZ = Math.sin(j * 0.2 + obj.value * Math.PI * 2) * 0.05;
            
            spherePositions.array[j3] = sphereOriginalPositions[j3] + waveX;
            spherePositions.array[j3 + 1] = sphereOriginalPositions[j3 + 1] + waveY;
            spherePositions.array[j3 + 2] = sphereOriginalPositions[j3 + 2] + waveZ;
          }
          spherePositions.needsUpdate = true;
        })
        .start();
    }, 4000 + i * 300);
  }
};

// 从几何体创建粒子
const createParticlesFromGeometry = (geometry) => {
  console.log("从几何体创建粒子");
  
  // 清除之前的粒子
  clearParticles();
  
  // 粒子材质
  const particleMaterial = new THREE.PointsMaterial({
    color: 0x00ff88,
    size: 0.03, // 减小粒子大小以适应更高密度
    transparent: true,
    opacity: 0.8,
    blending: THREE.AdditiveBlending,
    sizeAttenuation: true
  });
  
  // 从几何体创建粒子
  const particleGeometry = new THREE.BufferGeometry();
  const positions = geometry.attributes.position;
  
  // 减小采样率，使用更多顶点作为粒子
  const samplingRate = Math.max(1, Math.floor(positions.count / 30000)); // 增加到30000个粒子
  const particleVertices = [];
  
  for (let i = 0; i < positions.count; i += samplingRate) {
    const x = positions.getX(i);
    const y = positions.getY(i);
    const z = positions.getZ(i);
    
    particleVertices.push(x, y, z);
  }
  
  // 添加额外的随机粒子以增加密度
  const extraParticles = 20000; // 额外添加20000个粒子
  for (let i = 0; i < extraParticles; i++) {
    // 随机选择一个已有顶点作为基准
    const baseIndex = Math.floor(Math.random() * particleVertices.length / 3) * 3;
    const baseX = particleVertices[baseIndex];
    const baseY = particleVertices[baseIndex + 1];
    const baseZ = particleVertices[baseIndex + 2];
    
    // 在基准点附近添加随机偏移
    const offsetScale = 0.1; // 偏移范围
    const offsetX = (Math.random() - 0.5) * offsetScale;
    const offsetY = (Math.random() - 0.5) * offsetScale;
    const offsetZ = (Math.random() - 0.5) * offsetScale;
    
    particleVertices.push(baseX + offsetX, baseY + offsetY, baseZ + offsetZ);
  }
  
  particleGeometry.setAttribute('position', new THREE.Float32BufferAttribute(particleVertices, 3));
  
  const particleSystem = new THREE.Points(particleGeometry, particleMaterial);
  scene.add(particleSystem);
  particles.push(particleSystem);
  
  // 创建粒子动画效果
  animateParticles();
  
  console.log("粒子已创建，数量:", particleVertices.length / 3);
};

// 组件卸载
onBeforeUnmount(() => {
  if (animationFrameId) {
    cancelAnimationFrame(animationFrameId);
  }
  
  window.removeEventListener('resize', onResize);
  
  if (container.value && renderer) {
    container.value.removeChild(renderer.domElement);
  }
  
  if (renderer) {
    renderer.dispose();
  }
});

// 向父组件暴露方法
defineExpose({
  loadModel,
  regenerateParticles
});
</script>

<style scoped>
.visualizer-container {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  background: #000;
  overflow: hidden;
}
</style>
