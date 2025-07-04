<script setup lang="ts">
import { ref } from 'vue'
import { use } from 'echarts/core'
import { LineChart, PieChart, BarChart } from 'echarts/charts'
import { GridComponent, TooltipComponent, LegendComponent, TitleComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import VChart from 'vue-echarts'

// 注册 ECharts 组件
use([LineChart, PieChart, BarChart, GridComponent, TooltipComponent, LegendComponent, TitleComponent, CanvasRenderer])
const xAxisData: string[] = []
for (let i = 0; i < 7; i++) {
  const date = new Date(Date.now() - i * 24 * 60 * 60 * 1000)
  xAxisData.push(date.toLocaleDateString())
}
// 示例数据
const lineData = ref({
  xAxis: {
    type: 'category',
    data: xAxisData
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '新增用户',
      type: 'line',
      data: [0, 0, 1, 0, 0, 0, 0]
    },
    {
      name: '删除用户',
      type: 'line',
      data: [0, 0, 1, 0, 0, 1, 0]
    },
    {
      name: '修改用户',
      type: 'line',
      data: [1, 0, 1, 0, 0, 0, 0]
    }
  ]
})

const pieData = ref({
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: '用户类型',
      type: 'pie',
      radius: '50%',
      data: [
        { value: 3, name: '普通用户' },
        { value: 2, name: '管理员' }
      ]
    }
  ]
})

const barData = ref({
  xAxis: {
    type: 'category',
    data: ['普通用户', '管理员'],
  },
  yAxis: {
    type: 'value'
  },
  series: [{
    data: [3, 2],
    type: 'bar',
    showBackground: true,
    backgroundStyle: {
      color: 'rgba(180, 180, 180, 0.2)'
    }
  }]
})
</script>

<template>
  <div class="dashboard-container">
    <h2>用户数据统计</h2>

    <div class="chart-container">
      <h3>用户数量变化趋势</h3>
      <v-chart :option="lineData" class="chart" />
    </div>

    <div class="chart-container">
      <h3>用户类型分布</h3>
      <v-chart :option="pieData" class="chart" />
    </div>

    <div class="chart-container">
      <h3>各类型用户总数</h3>
      <v-chart :option="barData" class="chart" />
    </div>
  </div>
</template>

<style scoped>
.dashboard-container {
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

h2 {
  margin-bottom: 20px;
  color: #333;
  text-align: center;
}

.chart-container {
  background-color: #fff;
  margin-bottom: 20px;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

h3 {
  margin-bottom: 10px;
  color: #555;
}

.chart {
  height: 300px;
}
</style>
