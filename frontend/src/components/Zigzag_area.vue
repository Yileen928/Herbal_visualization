<template>
  <div ref="chartRef" style="width: 30vw; height: 35vh"></div>
</template>

<script>
import * as echarts from "echarts";
import axios from "axios";

export default {
  name: "LineAreaChart",
  props: {
    herbName: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      chart: null,
      fallbackData: {
        "三七": [
          { bookName: "本草经集注", year: 500, count: 6 },
          { bookName: "新修本草", year: 659, count: 5 },
          { bookName: "食疗本草", year: 720, count: 7 },
          { bookName: "证类本草", year: 1108, count: 8 },
          { bookName: "太平惠民和剂局方", year: 1150, count: 12 },
          { bookName: "饮膳正要", year: 1330, count: 9 },
          { bookName: "滇南本草", year: 1436, count: 10 },
          { bookName: "本草纲目", year: 1593, count: 23 },
          { bookName: "本草纲目拾遗", year: 1765, count: 15 }
        ],
        "灯盏花": [
          { bookName: "本草经集注", year: 500, count: 0 },
          { bookName: "新修本草", year: 659, count: 0 },
          { bookName: "食疗本草", year: 720, count: 0 },
          { bookName: "证类本草", year: 1108, count: 3 },
          { bookName: "太平惠民和剂局方", year: 1150, count: 4 },
          { bookName: "饮膳正要", year: 1330, count: 2 },
          { bookName: "滇南本草", year: 1436, count: 8 },
          { bookName: "本草纲目", year: 1593, count: 6 },
          { bookName: "本草纲目拾遗", year: 1765, count: 5 }
        ]
      }
    };
  },
  mounted() {
    this.initChart();
    window.addEventListener("resize", this.handleResize);
  },
  beforeDestroy() {
    if (this.chart) {
      this.chart.dispose();
    }
    window.removeEventListener("resize", this.handleResize);
  },
  methods: {
    handleResize() {
      if (this.chart) {
        this.chart.resize();
      }
    },
    async fetchData(name) {
      try {
        const res = await axios.get('/book/search', {
          baseURL: '/api',
          params: { herbsName: encodeURIComponent(name) }
        });
        const list = Array.isArray(res.data) ? res.data : [];
        if (list.length === 0 && this.fallbackData[name]) {
          console.warn("API无数据，使用备用数据");
          return this.fallbackData[name];
        }
        // 按 year 排序
        return list.sort((a, b) => a.year - b.year);
      } catch (e) {
        console.error("请求失败，使用备用数据", e);
        return this.fallbackData[name] || [];
      }
    },
    async initChart() {
      if (!this.$refs.chartRef) return;
      this.chart = echarts.init(this.$refs.chartRef);

      const dataList = await this.fetchData(this.herbName);
      if (!dataList.length) {
        console.warn("没有数据可渲染");
        return;
      }

      const option = {
        title: {
          text: `${this.herbName} 历史文献引用统计`,
          left: "center",
          textStyle: {
            fontSize: 16,
            fontWeight: "bold"
          }
        },
        tooltip: {
          trigger: "item",      // 仅在数据点上触发
          axisPointer: {
            type: "none"       // 不显示十字准星
          },
          backgroundColor: "rgba(255,255,255,0.9)",
          borderColor: "#ccc",
          borderWidth: 1,
          textStyle: {
            color: "#333"
          },
          formatter: function (params) {
            // params.data 正是我们下面填入的原始对象
            return [
              `药材：${this.herbName}`,
              `古籍：《${params.data.bookName}》`,
              `古籍年份：${params.data.year}`,
              `提及次数：${params.data.value} 次`
            ].join("<br/>");
          }.bind(this)
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: dataList.map(item => item.year),
          name: "年份",
          nameLocation: "center",
          nameGap: 30
        },
        yAxis: {
          type: "value",
          name: "引用次数",
          nameLocation: "center",
          nameGap: 30
        },
        series: [
          {
            name: "引用次数",
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 6,
            // 这里把每个点的完整信息都放到 data 数组里
            data: dataList.map(item => ({
              value: item.count,
              year: item.year,
              bookName: item.bookName
            })),
            areaStyle: {
              opacity: 0.3
            },
            emphasis: {
              itemStyle: {
                borderWidth: 2,
                borderColor: "#fff",
                shadowBlur: 10,
                shadowColor: "rgba(0,0,0,0.3)"
              }
            }
          }
        ],
        grid: {
          left: "5%",
          right: "5%",
          bottom: "10%",
          top: "10%",
          containLabel: true
        },
        color: ["#91ad80"]
      };

      this.chart.setOption(option);
    }
  },
  watch: {
    herbName: {
      handler() {
        this.initChart();
      },
      immediate: true
    }
  }
};
</script>