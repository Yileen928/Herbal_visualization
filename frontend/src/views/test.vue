<template>
  <div id="map" ref="map" style="width: 100%; height: 500px;"></div>
</template>

import * as echarts from 'echarts';
import 'echarts/extension/bmap/bmap';

<script>
export default {
  mounted() {
    this.initMap();
  },
    loadEcharts() {
    return new Promise((resolve, reject) => {
      const script = document.createElement('script');
      script.src = 'https://cdn.jsdelivr.net/npm/echarts@5.1.2/dist/echarts.min.js';
      script.onload = resolve;
      script.onerror = reject;
      document.head.appendChild(script);
    });
  },
  methods: {
    initMap() {
      const chart = echarts.init(this.$refs.map);
      const option = {
        bmap: {
          center: [104.114129, 37.550339],
          zoom: 5,
          roam: true,
        },
        series: [{
          type: 'map',
          map: 'china',
        }],
      };
      chart.setOption(option);
    },
  },
};
</script>

<style>
#map {
  width: 100%;
  height: 500px;
  background-color: aqua;
}
</style>
