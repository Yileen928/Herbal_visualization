<template>
  <div class="DoctorExperienceChart">
    <div ref="echartsRef" style="height: 400px; width: 100%"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import axios from "axios";
import * as echarts from "echarts";

const echartsRef = ref<HTMLElement | null>(null);

const fetchData = async () => {
  try {
    const response = await axios.get(
      "http://106.55.169.134:10010/medical/experience/counts"
    );
    const data = response.data;

    console.log(data); // 调试输出数据

    // 筛选出医师的数据
    const doctorData = data.filter(
      (item: { job: string }) => item.job === "医师"
    );

    // 提取工作经验和数量
    const experience = doctorData.map(
      (item: { workExperience: string }) => item.workExperience
    );
    const counts = doctorData.map((item: { count: number }) => item.count);

    // 结合经验和数量，并根据数量排序
    const sortedData = experience
      .map((exp, index) => ({
        name: exp,
        value: counts[index],
      }))
      .sort((a, b) => b.value - a.value); // 从高到低排序

    // 绘制南丁格尔玫瑰图
    drawChart(sortedData);
  } catch (error) {
    console.error(
      "Error fetching data:",
      error.response ? error.response.data : error.message
    );
  }
};

const drawChart = (sortedData: { name: string; value: number }[]) => {
  if (echartsRef.value) {
    const myChart = echarts.init(echartsRef.value);

    const option = {
      title: {
        text: "医师对工作经验的要求",
        left: "right",
        padding: [15, 5],
        textStyle: {
          color: "#333", // 标题文字颜色
        },
      },
      tooltip: {
        trigger: "item",
        formatter: "{b}: {c} ({d}%)", // 鼠标悬停时显示名称、值和占比
      },
      series: [
        {
          name: "工作经验",
          type: "pie",
          radius: [50, 100],
          center: ["50%", "50%"],
          roseType: "area",
          label: {
            show: true, // 始终显示标签
            color: "gray", // 设置文字颜色为灰色
            formatter: (params) => {
              return params.data.value > 0 ? params.name : ""; // 只有在有值时显示名称
            },
          },
          itemStyle: {
            borderRadius: 8,
            color: (params) => {
              const colors = [
                "rgba(185, 142, 109, 1)",
                "rgba(185, 142, 109, 0.5)",
              ];
              return colors[params.dataIndex % colors.length];
            },
          },
          data: sortedData,
        },
      ],
      backgroundColor: "transparent", // 设置背景颜色为透明
      legend: {
        show: false, // 隐藏图例
      },
    };

    myChart.setOption(option);
    myChart.resize(); // 确保图表适应容器大小
  } else {
    console.error("ECharts reference is null");
  }
};

onMounted(() => {
  fetchData();
});
</script>

<style scoped>
.DoctorExperienceChart {
  height: 370px; /* 确定图表容器的高度 */
  margin-top: -4%;
  background-color: rgba(191, 188, 183, 0.349); /* 设置背景颜色 */
  border-radius: 8px;
}
</style>
