<template>
  <!-- 地图容器，需确保全屏显示 -->
  <div id="map_container"></div>
</template>

<script setup>
import { onMounted } from 'vue';

onMounted(() => {
  /* ==================== 地图基础设置 ==================== */
  // 获取北京中心点坐标（作为地图初始中心）
  const cityCenter = window.mapv.utilCityCenter.getCenterByCityName('南宁');
  
  // 初始化地图参数（匹配用户图片效果）
  const map = window.initMap({
    tilt: 60,             // 3D倾斜角度
    center: [cityCenter.lng, cityCenter.lat], // 初始中心坐标
    zoom: 6,              // 缩放级别（覆盖全国范围）
    style: window.purpleStyle, // 深色主题
    pitch: 60             // 地图俯仰角度（增强3D效果）
  });

  /* ==================== 数据获取与处理 ==================== */
  Promise.all([
    // 同时获取两种药品数据
    fetch('/api/product/countPlace?productName=云南白药蒲地蓝消炎片').then(res => res.json()),
    fetch('/api/product/countPlace?productName=安宫牛黄丸').then(res => res.json())
  ])
    .then(([data1, data2]) => {
      // 数据存储容器
      const yunnanData = [];  // 云南白药飞线数据
      const tongrenData = []; // 同仁堂飞线数据
      const yunnanPoints = []; // 云南白药点数据
      const tongrenPoints = []; // 同仁堂点数据

      /* ----- 处理云南白药数据 ----- */
      if (Array.isArray(data1)) {
        const yunnanCenter = window.mapv.utilCityCenter.getCenterByCityName('云南');
        data1.forEach(item => {
          const endCity = window.mapv.utilCityCenter.getCenterByCityName(item.location);
          if (endCity) {
            // 飞线数据：从云南中心到目标城市
            yunnanData.push({
              start: [yunnanCenter.lng, yunnanCenter.lat],
              end: [endCity.lng, endCity.lat]
            });
            // 点数据：目标城市坐标+评论数
            yunnanPoints.push({
              geometry: {
                type: 'Point',
                coordinates: [endCity.lng, endCity.lat]
              },
              properties: {
                count: item.commentCount
              }
            });
          }
        });
      }

      /* ----- 处理同仁堂数据 ----- */
      if (Array.isArray(data2)) {
        const beijingCenter = window.mapv.utilCityCenter.getCenterByCityName('北京');
        data2.forEach(item => {
          const endCity = window.mapv.utilCityCenter.getCenterByCityName(item.location);
          if (endCity) {
            // 飞线数据：从北京中心到目标城市
            tongrenData.push({
              start: [beijingCenter.lng, beijingCenter.lat],
              end: [endCity.lng, endCity.lat]
            });
            // 点数据：目标城市坐标+评论数
            tongrenPoints.push({
              geometry: {
                type: 'Point',
                coordinates: [endCity.lng, endCity.lat]
              },
              properties: {
                count: item.commentCount
              }
            });
          }
        });
      }

      /* ==================== 可视化渲染 ==================== */
      // 创建地图视图容器
      const view = new window.mapvgl.View({ map });
      
      // 贝塞尔曲线生成器（用于飞线路径）
      const curve = new window.mapvgl.BezierCurve();

      /* ----- 云南白药飞线图层（红色）----- */
      const yunnanFlyLineData = [];
      yunnanData.forEach(item => {
        curve.setOptions({
          start: item.start,
          end: item.end,
        });
        yunnanFlyLineData.push({
          geometry: {
            type: 'LineString',
            coordinates: curve.getPoints(), // 生成曲线路径点
          },
        });
      });

      const yunnanFlyLine = new window.mapvgl.FlyLineLayer({
        style: 'chaos',       // 动态流动样式
        step: 0.2,            // 动画速度（值越小越慢）
        color: 'rgba(56, 144, 255,0.8)',     // 主颜色
        textureColor: 'rgba(56, 144, 255,0.8)', // 纹理颜色（必须与主色一致）
        textureWidth: 8,      // 飞线宽度
        textureLength: 60     // 飞线长度
      });
      view.addLayer(yunnanFlyLine);
      yunnanFlyLine.setData(yunnanFlyLineData);

      /* ----- 同仁堂飞线图层（红色）----- */
      const tongrenFlyLineData = [];
      tongrenData.forEach(item => {
        curve.setOptions({
          start: item.start,
          end: item.end,
        });
        tongrenFlyLineData.push({
          geometry: {
            type: 'LineString',
            coordinates: curve.getPoints(),
          },
        });
      });

      const tongrenFlyLine = new window.mapvgl.FlyLineLayer({
        style: 'chaos',
        step: 0.2,
        color: 'rgba(226, 160, 109, 0.8)', // 主颜色  
        textureColor: 'rgba(226, 160, 109, 0.8)',
        textureWidth: 8,
        textureLength: 60
      });
      view.addLayer(tongrenFlyLine);
      tongrenFlyLine.setData(tongrenFlyLineData);

      /* ----- 点聚合图层设置 ----- */
      // 云南白药点图层（蓝色半透明）
      const yunnanPointLayer = new window.mapvgl.PointLayer({
        blend: 'lighter',     // 混合模式（增强亮区）
        size: function(data) {
          // 点大小与评论数平方根成正比（防止过大）
          return Math.sqrt(data.properties.count) * 2;
        },
        color: 'rgba(255,221,0,0.6)' // 蓝色半透明
      });
      view.addLayer(yunnanPointLayer);
      yunnanPointLayer.setData(yunnanPoints);

      // 同仁堂点图层（橙色半透明）
      const tongrenPointLayer = new window.mapvgl.PointLayer({
        blend: 'lighter',
        size: function(data) {
          return Math.sqrt(data.properties.count) * 2;
        },
        color: 'rgba(0,255,157,0.6)' // 橙色半透明
      });
      view.addLayer(tongrenPointLayer);
      tongrenPointLayer.setData(tongrenPoints);
    })
    .catch(error => {
      console.error('数据获取失败:', error);
    });
});
</script>

<style scoped>
/* 确保地图容器全屏显示 */
#map_container {
  position: absolute;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden;
}

/* 隐藏百度地图版权信息 */
.BMap_cpyCtr, .anchorBL {
  display: none !important;
}
</style>