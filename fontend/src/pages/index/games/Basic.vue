<script setup lang="ts">
import { ref } from 'vue'
import { use } from 'echarts/core'
import { LineChart, PieChart, BarChart } from 'echarts/charts'
import { GridComponent, TooltipComponent, LegendComponent, TitleComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import VChart from 'vue-echarts'

// 注册 ECharts 组件
use([LineChart, PieChart, BarChart, GridComponent, TooltipComponent, LegendComponent, TitleComponent, CanvasRenderer])
const dateRange = ref([new Date(Date.now() - 7 * 24 * 60 * 60 * 1000).toString(), new Date().toString()])
// 生成['2023-01-01', '2023-01-02', '2023-01-03', '2023-01-04', '2023-01-05', '2023-01-06', '2023-01-07']
// 这样的数据，从今天的前一周到今天
const xAxisData: string[] = []
for (let i = 0; i < 7; i++) {
  const date = new Date(Date.now() - i * 24 * 60 * 60 * 1000)
  xAxisData.push(date.toLocaleDateString())
}
// 示例数据
const lineData = ref({
  xAxis: {
    type: 'category',
    data: xAxisData,
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '新增玩家',
      type: 'line',
      data: [120, 220, 180, 280, 260, 320, 400]
    },
    {
      name: '流失玩家',
      type: 'line',
      data: [10, 30, 20, 50, 30, 60, 40]
    },
    {
      name: '活跃玩家',
      type: 'line',
      data: [40, 60, 90, 50, 70, 80, 100]
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
      name: '游戏类型',
      type: 'pie',
      radius: '50%',
      data: [
        { value: 60, name: 'RPG' },
        { value: 30, name: 'MOBA' },
        { value: 10, name: 'FPS' }
      ]
    }
  ]
})

const barData = ref({
  xAxis: {
    type: 'category',
    data: ['RPG', 'MOBA', 'FPS'],
  },
  yAxis: {
    type: 'value'
  },
  series: [{
    data: [220, 100, 30],
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
    <h2>游戏数据统计</h2>

    <div class="chart-container">
      <h3>玩家数量变化趋势</h3>
      <v-chart :option="lineData" class="chart" />
    </div>

    <div class="chart-container">
      <h3>游戏类型分布</h3>
      <v-chart :option="pieData" class="chart" />
    </div>

      <div class="chart-container">
          <h3>各类型游戏总数</h3>
          <v-chart :option="barData" class="chart"/>
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
