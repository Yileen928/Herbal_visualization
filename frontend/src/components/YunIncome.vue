//云南白药集团工业年收入

<template>
  <div class="YunIncome">
    <p class="titie1">云南白药集团近十年工业销售年收入</p>
    <canvas ref="incomeChart" width="400" height="200"></canvas>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

const incomeChart = ref<HTMLCanvasElement | null>(null);

const years = [
  '2015年', '2016年', '2017年', '2018年', 
  '2019年', '2020年', '2021年', '2022年', 
  '2023年', '2024年'
];

const salesData = [
  918040.09, 908000.00, 996202.47, 1072713.71, 
  1102400.00, 1171643.38, 1270283.14, 1277200.00, 
  1374055.71, 1447500.00
];

onMounted(() => {
  if (incomeChart.value) {
    new Chart(incomeChart.value, {
      type: 'line',
      data: {
        labels: years,
        datasets: [{
          label: '销售收入（万元）',
          data: salesData,
          borderColor: 'rgba(213, 195, 151, 0.817)',
          backgroundColor: 'rgba(213, 195, 151, 0.4)',
          borderWidth: 2,
          fill: true,
        }],
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            display: true,
          },
          tooltip: {
            callbacks: {
              label: (tooltipItem) => {
                return `收入: ${tooltipItem.raw.toLocaleString()} 万元`;
              }
            }
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            title: {
              display: true,
              text: '销售收入（万元）',
            },
          },
          x: {
            title: {
              display: true,
              text: '年份',
            },
          }
        },
      },
    });
  }
});
</script>

<style scoped>
.YunIncome {
  max-width: 600px;
  margin: 0 auto;
}
.canvas {
  width: 100% !important; /* 使画布宽度适应容器 */
  height: auto !important; /* 自适应高度 */
}
.titie1{
    text-align: center;
    margin-top: 0%;
    margin-bottom: 5px;
    font-size: 1em;
    color: #333333;
}

</style>
