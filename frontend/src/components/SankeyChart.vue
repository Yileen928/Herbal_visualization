<template>
  <div ref="chartRef" style="width: 100%; height: 500px;"></div>
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
            case 0: color = '#7BC074'; break; // 药用部位 - 绿色
            case 1: color = '#709EF1'; break; // 阴阳属性 - 蓝色
            case 2: color = '#F59363'; break; // 手足部位 - 橙色
            case 3: color = '#E062AE'; break; // 经络 - 紫色
            default: color = '#999';
          }
          nodes.push({ 
            name,
            itemStyle: { color },
            depth // 显式设置层级确保正确排序
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
              curveness: 0.5,
              opacity: 0.3
            },
            nodeAlign: 'right',
            layoutIterations: 0,
            label: {
              color: '#333',
              fontSize: 12,
              position: 'right' // 确保标签显示在节点右侧
            },
            levels: [
              { depth: 0, itemStyle: { color: '#7BC074' } },
              { depth: 1, itemStyle: { color: '#709EF1' } },
              { depth: 2, itemStyle: { color: '#F59363' } },
              { depth: 3, itemStyle: { color: '#E062AE' } }
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
