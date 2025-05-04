# 关于FBX模型

请将您的中草药FBX模型文件放在此目录中。

建议模型命名为：herbal_model.fbx 或 test.fbx

## 获取FBX模型的方法

1. 可以使用3D建模软件创建（如Blender、Maya、3DS Max等）
2. 可以从3D模型网站下载现成的模型（如Sketchfab、TurboSquid等）
3. 可以使用3D扫描设备扫描实物后导出为FBX格式

## 免费模型资源

以下是一些可以获取免费3D模型的网站：

1. Sketchfab - https://sketchfab.com/features/free-3d-models
2. TurboSquid - https://www.turbosquid.com/Search/3D-Models/free
3. Free3D - https://free3d.com/
4. CGTrader - https://www.cgtrader.com/free-3d-models

## 模型优化建议

1. 减少模型面数，一般建议控制在10万面以内
2. 简化材质，减少纹理的数量和大小
3. 确保模型比例适当，便于在场景中展示
4. 保存时使用较新版本的FBX格式（如FBX 2013或更新版本）

## 如果没有FBX模型

如果您暂时没有FBX模型，系统会自动创建一个简单的叶子形状模型进行演示。

## 测试模型

如果您暂时没有FBX模型，可以修改代码使用Three.js内置的基础几何体进行测试：

在 src/components/HerbalParticleVisualizer.vue 文件中，可以将加载FBX模型的部分替换为：

```javascript
// 创建一个基础几何体用于测试
const createTestGeometry = () => {
  // 创建一个叶子形状的几何体
  const geometry = new THREE.TorusKnotGeometry(2, 0.5, 100, 16);
  const material = new THREE.MeshStandardMaterial({ 
    color: 0x00aa00,
    roughness: 0.5,
    metalness: 0.5
  });
  const mesh = new THREE.Mesh(geometry, material);
  
  scene.add(mesh);
  mesh.visible = false; // 隐藏原始模型
  
  // 从几何体创建粒子
  createParticlesFromModel(mesh);
};

// 调用测试几何体函数代替加载FBX
createTestGeometry();
```

这样可以在没有FBX模型的情况下测试粒子效果功能。 