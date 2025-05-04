<template>
  <div class="map-container">
    <div class="china-map">
      <div class="mapTitle">中国药材分布</div>
      <div id="bmap-container" ref="bmapContainer"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { useRouter } from 'vue-router'
  import { onMounted, onUnmounted, ref } from 'vue'

  // 声明全局BMap类型，避免TypeScript报错
  declare const BMap: any;

  const router = useRouter()
  const bmapContainer = ref < HTMLElement | null > (null)
  let map: any = null;

  // 初始化地图
  const initMap = () => {
    if (!bmapContainer.value) return;

    // 初始化2D地图
    map = new BMap.Map("bmap-container");
    map.enableScrollWheelZoom(); // 启用滚轮缩放

    // 设置地图中心点和缩放级别
    const point = new BMap.Point(104.114129, 37.550339); // 中国中心点
    map.centerAndZoom(point, 5);

    // 添加2D控件
    map.addControl(new BMap.NavigationControl());
    map.addControl(new BMap.ScaleControl());

    // 设置地图显示范围，只显示中国
    setBoundary();

    // 添加所有省份边界
    addProvincesBoundaries();

    // 添加主要城市标记和数据
    addCityMarkers();
  };

  // 设置地图边界，只显示中国
  const setBoundary = () => {
    // 设置地图样式，隐藏国外区域，只显示省份标签，隐藏市县标签
    const styleJson = [
      {
        "featureType": "water",
        "elementType": "all",
        "stylers": {
          "color": "#d1d1d1"
        }
      },
      {
        "featureType": "land",
        "elementType": "all",
        "stylers": {
          "color": "#f3f3f3"
        }
      },
      {
        "featureType": "boundary",
        "elementType": "geometry",
        "stylers": {
          "color": "#aaaaaa",
          "weight": "1",
          "lightness": -29
        }
      },
      {
        "featureType": "poi",
        "elementType": "all",
        "stylers": {
          "visibility": "off"
        }
      },
      {
        "featureType": "road",
        "elementType": "all",
        "stylers": {
          "visibility": "off"
        }
      },
      {
        "featureType": "administrative",
        "elementType": "all",
        "stylers": {
          "visibility": "on"
        }
      },
      {
        "featureType": "administrative.province",
        "elementType": "labels",
        "stylers": {
          "visibility": "on"
        }
      },
      {
        "featureType": "administrative.city",
        "elementType": "labels",
        "stylers": {
          "visibility": "off"
        }
      },
      {
        "featureType": "administrative.district",
        "elementType": "labels",
        "stylers": {
          "visibility": "off"
        }
      },
      {
        "featureType": "administrative.county",
        "elementType": "labels",
        "stylers": {
          "visibility": "off"
        }
      },
      {
        "featureType": "administrative.town",
        "elementType": "labels",
        "stylers": {
          "visibility": "off"
        }
      }
    ];
    map.setMapStyleV2({styleJson: styleJson});

    // 设置地图显示范围
    const bounds = new BMap.Bounds(
      new BMap.Point(73.66, 18.16), // 中国西南角
      new BMap.Point(135.05, 53.55)  // 中国东北角
    );

    // 限制地图的显示范围
    const viewportOptions = {
      onviewportchange: function() {
        // 如果地图视野超出中国范围，重置回中国范围
        if (!bounds.containsPoint(map.getCenter())) {
          map.setCenter(new BMap.Point(104.114129, 37.550339));
        }
      }
    };
    map.setViewport(bounds, viewportOptions);
  };

  // 添加所有省份边界并根据数据着色
  const addProvincesBoundaries = () => {
    // 中国所有省份及其中药材数量数据
    const provincesData = [
      { name: '北京市', value: 4 },
      { name: '天津市', value: 4 },
      { name: '上海市', value: 5 },
      { name: '重庆市', value: 7 },
      { name: '河北省', value: 21 },
      { name: '山西省', value: 19 },
      { name: '辽宁省', value: 19 },
      { name: '吉林省', value: 11 },
      { name: '黑龙江省', value: 6 },
      { name: '江苏省', value: 29 },
      { name: '浙江省', value: 37 },
      { name: '安徽省', value: 29 },
      { name: '福建省', value: 41 },
      { name: '江西省', value: 35 },
      { name: '山东省', value: 18 },
      { name: '河南省', value: 26 },
      { name: '湖北省', value: 42 },
      { name: '湖南省', value: 37 },
      { name: '广东省', value: 47 },
      { name: '海南省', value: 5 },
      { name: '四川省', value: 75 },
      { name: '贵州省', value: 47 },
      { name: '云南省', value: 156 },
      { name: '陕西省', value: 33 },
      { name: '甘肃省', value: 29 },
      { name: '青海省', value: 5 },
      { name: '台湾省', value: 16 },
      { name: '内蒙古自治区', value: 10 },
      { name: '广西壮族自治区', value: 62 },
      { name: '西藏自治区', value: 18 },
      { name: '宁夏回族自治区', value: 2 },
      { name: '新疆维吾尔自治区', value: 4 },
      { name: '香港特别行政区', value: 0 },
      { name: '澳门特别行政区', value: 0 }
    ];

    // 找出最大值和最小值，用于计算颜色深浅
    const values = provincesData.map(item => item.value);
    const maxValue = Math.max(...values);
    const minValue = Math.min(...values);

    // 添加所有省份边界
    const bdary = new BMap.Boundary();

    // 存储省份中心点，用于后续添加标签
    const provincesCenters = {};

    // 为每个省份添加边界和热力图颜色
    provincesData.forEach(province => {
      bdary.get(province.name, (rs) => {
        const count = rs.boundaries.length;
        if (count === 0) return;

        // 计算颜色深浅 - 值越大颜色越深
        // 使用绿色渐变，从浅绿到深绿
        const ratio = (province.value - minValue) / (maxValue - minValue);

        // 计算颜色 - 从浅绿色(#e5f7e5)到深绿色(#006400)
        const r = Math.floor(229 - ratio * 229);
        const g = Math.floor(247 - ratio * 147);
        const b = Math.floor(229 - ratio * 229);
        const fillColor = `rgb(${r}, ${g}, ${b})`;

        // 边框颜色和粗细
        const strokeColor = '#666666';
        const strokeWeight = province.name === '云南省' ? 2 : 1;

        // 计算省份的中心点（取第一个多边形的中心）
        if (count > 0) {
          const ply = new BMap.Polygon(rs.boundaries[0]);
          provincesCenters[province.name] = {
            center: ply.getBounds().getCenter(),
            value: province.value
          };
        }

        for (let i = 0; i < count; i++) {
          const ply = new BMap.Polygon(rs.boundaries[i], {
            strokeWeight: strokeWeight,
            strokeColor: strokeColor,
            fillColor: fillColor,
            fillOpacity: 0.8
          });

          map.addOverlay(ply);

          // 为每个省份添加点击事件和提示信息
          ply.addEventListener('click', () => {
            if (province.name === '云南省') {
              router.push('/herbal-product');
            }
          });

          // 使用自定义提示框替代InfoWindow，提高响应速度
          ply.addEventListener('mouseover', function(e) {
            // 创建或获取自定义提示框
            let tooltip = document.getElementById('map-tooltip');
            if (!tooltip) {
              tooltip = document.createElement('div');
              tooltip.id = 'map-tooltip';
              tooltip.style.position = 'absolute';
              tooltip.style.zIndex = '10000';
              tooltip.style.backgroundColor = 'white';
              tooltip.style.border = '1px solid #ccc';
              tooltip.style.padding = '8px';
              tooltip.style.borderRadius = '4px';
              tooltip.style.boxShadow = '0 2px 6px rgba(0,0,0,0.2)';
              tooltip.style.pointerEvents = 'none'; // 避免鼠标事件被提示框捕获
              tooltip.style.transition = 'opacity 0.2s';
              document.body.appendChild(tooltip);
            }

            // 设置提示框内容
            tooltip.innerHTML = `
              <div style="padding: 2px;">
                <h4 style="margin: 0 0 5px 0; font-size: 14px;">${province.name}</h4>
                <p style="margin: 0; font-size: 13px;">中药材数量: <b>${province.value}</b> 种</p>
              </div>
            `;

            // 显示提示框
            tooltip.style.display = 'block';
            tooltip.style.opacity = '1';

            // 获取鼠标在页面上的位置
            const updatePosition = (mouseEvent) => {
              // 获取鼠标位置并偏移，避免遮挡鼠标
              const x = mouseEvent.clientX + 15;
              const y = mouseEvent.clientY + 15;

              // 设置提示框位置
              tooltip.style.left = x + 'px';
              tooltip.style.top = y + 'px';
            };

            // 初始定位
            updatePosition(e);

            // 添加鼠标移动事件，实时更新提示框位置
            ply._tooltipMoveHandler = (moveEvent) => {
              updatePosition(moveEvent);
            };

            // 监听鼠标移动
            document.addEventListener('mousemove', ply._tooltipMoveHandler);
          });

          // 鼠标移出时隐藏提示框
          ply.addEventListener('mouseout', function() {
            const tooltip = document.getElementById('map-tooltip');
            if (tooltip) {
              tooltip.style.opacity = '0';
              tooltip.style.display = 'none';

              // 移除鼠标移动事件监听
              if (ply._tooltipMoveHandler) {
                document.removeEventListener('mousemove', ply._tooltipMoveHandler);
                delete ply._tooltipMoveHandler;
              }
            }
          });
        }
      });
    });

    // 延迟添加省份标签，确保所有省份边界都已加载完成
    // 辅助函数：生成圆形经纬度点数组
    function getCirclePoints(center, radiusDegree, numPoints = 40) {
      const points = [];
      for (let i = 0; i < numPoints; i++) {
        const angle = (2 * Math.PI * i) / numPoints;
        const dx = radiusDegree * Math.cos(angle);
        const dy = radiusDegree * Math.sin(angle);
        points.push(new BMap.Point(center.lng + dx, center.lat + dy));
      }
      return points;
    }

    setTimeout(() => {
      // 定义省份标签的位置偏移量，避免重叠
      const labelOffsets = {
        '北京市': { x: -10, y: 0 },
        '天津市': { x: 10, y: 10 },
        '河北省': { x: 0, y: -15 },
        '山西省': { x: 0, y: 0 },
        '内蒙古自治区': { x: -30, y: -20 },
        '辽宁省': { x: 20, y: 0 },
        '吉林省': { x: 0, y: -10 },
        '黑龙江省': { x: 0, y: -20 },
        '上海市': { x: 10, y: 10 },
        '江苏省': { x: 0, y: 10 },
        '浙江省': { x: 0, y: 15 },
        '安徽省': { x: 0, y: 0 },
        '福建省': { x: 0, y: 10 },
        '江西省': { x: 0, y: 0 },
        '山东省': { x: 10, y: -10 },
        '河南省': { x: 0, y: 0 },
        '湖北省': { x: 0, y: 0 },
        '湖南省': { x: 0, y: 15 },
        '广东省': { x: 0, y: 10 },
        '广西壮族自治区': { x: -20, y: 10 },
        '海南省': { x: 0, y: 0 },
        '重庆市': { x: -10, y: 0 },
        '四川省': { x: -10, y: -10 },
        '贵州省': { x: 0, y: 0 },
        '云南省': { x: -10, y: 10 },
        '西藏自治区': { x: -20, y: 0 },
        '陕西省': { x: 0, y: 0 },
        '甘肃省': { x: -10, y: -10 },
        '青海省': { x: 0, y: 0 },
        '宁夏回族自治区': { x: 0, y: 0 },
        '新疆维吾尔自治区': { x: -30, y: 0 },
        '台湾省': { x: 0, y: 0 },
        '香港特别行政区': { x: 10, y: 0 },
        '澳门特别行政区': { x: -10, y: 10 }
      };

      // 添加省份标签和中心点透明Marker，增强鼠标在省份中间的交互体验
      for (const provinceName in provincesCenters) {
        const provinceData = provincesCenters[provinceName];
        const offset = labelOffsets[provinceName] || { x: 0, y: 0 };

        // 添加省份标签
        const label = new BMap.Label(provinceName, {
          position: provinceData.center,
          offset: new BMap.Size(offset.x, offset.y)
        });
        label.setStyle({
          color: '#333',
          fontSize: '12px',
          fontWeight: 'bold',
          border: 'none',
          backgroundColor: 'transparent'
        });
        map.addOverlay(label);

        // 添加一个透明的圆形 Polygon 用于增强省份中间的鼠标交互
        const centerCircle = new BMap.Polygon(
          getCirclePoints(provinceData.center, 1.0), // 半径1.0经度，视实际地图缩放可调整
          {
            strokeColor: 'transparent',
            fillColor: 'transparent',
            fillOpacity: 0.01
          }
        );
        map.addOverlay(centerCircle);

        // 绑定mouseover事件，显示tooltip
        centerCircle.addEventListener('mouseover', function(e) {
          let tooltip = document.getElementById('map-tooltip');
          if (!tooltip) {
            tooltip = document.createElement('div');
            tooltip.id = 'map-tooltip';
            tooltip.style.position = 'absolute';
            tooltip.style.zIndex = '10000';
            tooltip.style.backgroundColor = 'white';
            tooltip.style.border = '1px solid #ccc';
            tooltip.style.padding = '8px';
            tooltip.style.borderRadius = '4px';
            tooltip.style.boxShadow = '0 2px 6px rgba(0,0,0,0.2)';
            tooltip.style.pointerEvents = 'none';
            tooltip.style.transition = 'opacity 0.2s';
            document.body.appendChild(tooltip);
          }
          tooltip.innerHTML = `
            <div style="padding: 2px;">
              <h4 style="margin: 0 0 5px 0; font-size: 14px;">${provinceName}</h4>
              <p style="margin: 0; font-size: 13px;">中药材数量: <b>${provinceData.value}</b> 种</p>
            </div>
          `;
          tooltip.style.display = 'block';
          tooltip.style.opacity = '1';
          // 定位到鼠标位置
          const x = e.domEvent.clientX + 15;
          const y = e.domEvent.clientY + 15;
          tooltip.style.left = x + 'px';
          tooltip.style.top = y + 'px';

          // 鼠标移动时更新tooltip位置
          centerCircle._tooltipMoveHandler = (moveEvent) => {
            const mx = moveEvent.clientX + 15;
            const my = moveEvent.clientY + 15;
            tooltip.style.left = mx + 'px';
            tooltip.style.top = my + 'px';
          };
          document.addEventListener('mousemove', centerCircle._tooltipMoveHandler);
        });
        centerCircle.addEventListener('mouseout', function() {
          const tooltip = document.getElementById('map-tooltip');
          if (tooltip) {
            tooltip.style.opacity = '0';
            tooltip.style.display = 'none';
            if (centerCircle._tooltipMoveHandler) {
              document.removeEventListener('mousemove', centerCircle._tooltipMoveHandler);
              delete centerCircle._tooltipMoveHandler;
            }
          }
        });
      }
    }, 200); // 延迟显示，确保所有省份边界都已加载完成
  };

  // 空函数，不再添加城市标记
  const addCityMarkers = () => {
    // 不再添加城市标记，改为使用省级热力图
  };

  // 清理函数，移除事件监听和DOM元素
  const cleanup = () => {
    // 移除窗口大小变化监听
    window.removeEventListener('resize', handleResize);

    // 移除提示框
    const tooltip = document.getElementById('map-tooltip');
    if (tooltip && tooltip.parentNode) {
      tooltip.parentNode.removeChild(tooltip);
    }
  };

  // 窗口大小变化处理函数
  const handleResize = () => {
    if (map) {
      map.checkResize && map.checkResize();
      // 重新设置地图容器大小
      if (bmapContainer.value) {
        const container = document.getElementById('bmap-container');
        if (container) {
          container.style.height = 'calc(100% - 2rem)';
          container.style.width = '100%';
        }
      }
    }
  };

  onMounted(() => {
    // 确保容器有正确的高度
    if (bmapContainer.value) {
      const container = document.getElementById('bmap-container');
      if (container) {
        container.style.height = 'calc(100% - 2rem)';
        container.style.width = '100%';
      }
    }

    // 初始化地图
    initMap();

    // 监听窗口大小变化，调整地图大小
    window.addEventListener('resize', handleResize);

    // 手动触发一次resize事件，确保地图正确渲染
    setTimeout(() => {
      handleResize();
    }, 100);
  });

  // 组件卸载时清理
  onUnmounted(() => {
    cleanup();
  });
</script>

<style scoped>
  @import './Map_new.css';
</style>