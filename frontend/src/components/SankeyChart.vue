<template>
  <div ref="chartRef" style="width: 60vw; height: 35vh;margin: 0;"></div>
</template>

<script>
import * as echarts from 'echarts';
import axios from 'axios';

export default {
  name: 'SankeyChart',
  props: {
    herbName: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      chart: null
    }
  },
  mounted() {
    this.initChart();
    window.addEventListener('resize', this.handleResize);
  },
  beforeDestroy() {
    if (this.chart) {
      this.chart.dispose();
    }
    window.removeEventListener('resize', this.handleResize);
  },
  methods: {
    handleResize() {
      if (this.chart) {
        this.chart.resize();
      }
    },
    async fetchData(herbName) {
      try {
        const response = await axios.get(`http://121.4.116.71:10010/song/search?herbsName=${herbName}`);
        const rawData = response?.data || [];
        
        console.log('原始数据:', rawData);
        
        const nodes = [];
        const links = [];
        const nodeMap = new Map(); // 用于记录节点和它们的层级

        if (!Array.isArray(rawData)) {
          console.error('API返回的数据不是数组:', rawData);
          return { nodes: [], links: [] };
        }

        // 第一遍：收集所有节点并确定层级
        rawData.forEach(item => {
          const { herbs = {}, returns = [] } = item || {};
          const { medicinalPart, yinyangAttributes, handsfeetParts } = herbs;
          
          // 添加节点并设置层级
          if (medicinalPart && !nodeMap.has(medicinalPart)) {
            nodeMap.set(medicinalPart, 0); // 药用部位为第0层
          }
          if (yinyangAttributes && !nodeMap.has(yinyangAttributes)) {
            nodeMap.set(yinyangAttributes, 1); // 阴阳属性为第1层
          }
          if (handsfeetParts && !nodeMap.has(handsfeetParts)) {
            nodeMap.set(handsfeetParts, 2); // 手足部位为第2层
          }
          
          // 添加经络节点
          if (Array.isArray(returns)) {
            returns.forEach(genus => {
              if (genus?.genusName && !nodeMap.has(genus.genusName)) {
                nodeMap.set(genus.genusName, 3); // 经络为第3层
              }
            });
          }
        });

        console.log('收集到的节点:', [...nodeMap.keys()]);

        // 第二遍：构建节点数组
        nodeMap.forEach((depth, name) => {
          let color;
          switch(depth) {
            case 0: {
              // 根据药用部位分配不同颜色
              if (name.includes('根')) {
                color = '#B7A28E';  // 根部 - 深褐色
              } else if (name.includes('茎')) {
                color = '#B8A38F';  // 茎部 - 浅褐色
              } else if (name.includes('叶')) {
                color = '#E7D0BA';  // 叶部 - 浅绿褐色
              } else if (name.includes('花')) {
                color = '#F3E0D5';  // 花部 - 粉色
              } else {
                color = '#DBC1B5';  // 其他部位 - 中性色
              }
              break;
            }
            case 1: color = '#E7D0BA'; break;  // 阴阳属性
            case 2: color = '#B3BCAE'; break;  // 手足部位
            case 3: {
              // 经络层使用多种颜色
              const colors = ['#CD866D', '#B3BCAE', '#707263', '#57625B'];
              const randomIndex = Math.floor(Math.random() * colors.length);
              color = colors[randomIndex];
              break;
            }
            default: color = '#E4CB97';
          }
          nodes.push({ 
            name,
            itemStyle: { color },
            depth
          });
        });

        // 第三遍：构建链接关系
        rawData.forEach(item => {
          const { herbs = {}, returns = [] } = item || {};
          const { medicinalPart, yinyangAttributes, handsfeetParts } = herbs;
          
          if (medicinalPart && yinyangAttributes) {
            links.push({
              source: medicinalPart,
              target: yinyangAttributes,
              value: 1
            });
          }
          if (yinyangAttributes && handsfeetParts) {
            links.push({
              source: yinyangAttributes,
              target: handsfeetParts,
              value: 1
            });
          }
          if (handsfeetParts && Array.isArray(returns)) {
            returns.forEach(genus => {
              if (genus?.genusName) {
                links.push({
                  source: handsfeetParts,
                  target: genus.genusName,
                  value: 1
                });
              }
            });
          }
        });

        console.log('节点数组:', nodes);
        console.log('连接数组:', links);

        return { nodes, links };
      } catch (error) {
        console.error('数据获取失败:', error);
        return { nodes: [], links: [] };
      }
    },
    async initChart() {
      if (!this.$refs.chartRef) return;
      
      try {
        this.chart = echarts.init(this.$refs.chartRef);
        const { nodes, links } = await this.fetchData(this.herbName);

        if (nodes.length === 0 || links.length === 0) {
          console.warn('无有效数据可渲染');
          return;
        }

        const option = {
          title: {
            text: `${this.herbName}药材属性桑基图`,
            left: 'center',
            textStyle: {
              fontSize: 16,
              fontWeight: 'bold'
            }
          },
          tooltip: {
            trigger: 'item',
            formatter: params => {
              if (params.dataType === 'node') {
                return `${params.name}`;
              }
              return `${params.source} → ${params.target}`;
            }
          },
          series: [{
            type: 'sankey',
            data: nodes,
            links: links,
            emphasis: {
              focus: 'adjacency'
            },
            lineStyle: {
              color: 'source',
              curveness: 0.5,
              opacity: 0.4,  // 降低透明度使颜色更柔和
              gradient: {
                enable: true
              }
            },
            nodeAlign: 'left',
            layoutIterations: 32,
            left: '5%',
            right: '5%',
            label: {
              color: '#333',
              fontSize: 12,
              position: 'right'
            },
            levels: [
              { depth: 0, itemStyle: { color: '#B7A28E' } },
              { depth: 1, itemStyle: { color: '#E7D0BA' } },
              { depth: 2, itemStyle: { color: '#F3E0D5' } },
              // 第四层不设置固定颜色，使用随机分配的颜色
            ]
          }]
        };

        this.chart.setOption(option);
      } catch (error) {
        console.error('图表初始化失败:', error);
      }
    }
  },
  watch: {
    herbName(newVal) {
      if (newVal) {
        this.initChart();
      }
    }
  }
}
</script>
