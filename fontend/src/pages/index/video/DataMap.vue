<script setup lang="ts">
import { ref } from 'vue'
import { use } from 'echarts/core'
import { LineChart, PieChart } from 'echarts/charts'
import { GridComponent, TooltipComponent, LegendComponent, TitleComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import VChart from 'vue-echarts'

// 注册 ECharts 组件
use([LineChart, PieChart, GridComponent, TooltipComponent, LegendComponent, TitleComponent, CanvasRenderer])

// 示例数据
const lineData = ref({
  xAxis: {
    type: 'category',
    data: ['2023-01-01', '2023-01-02', '2023-01-03']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '新增电影',
      type: 'line',
      data: [10, 20, 15]
    },
    {
      name: '删除电影',
      type: 'line',
      data: [2, 1, 3]
    },
    {
      name: '修改电影',
      type: 'line',
      data: [5, 3, 7]
    }
  ]
})

const pieData = ref({
  tooltip: {
    trigger: 'item'
  },
  series: [
    {
      name: '电影类型',
      type: 'pie',
      radius: '50%',
      data: [
        { value: 40, name: '动作片' },
        { value: 30, name: '喜剧片' },
        { value: 20, name: '爱情片' },
        { value: 10, name: '科幻片' }
      ]
    }
  ]
})
</script>

<template>
  <div class="dashboard-container">
    <h2>电影数据统计</h2>

    <div class="chart-container">
      <h3>电影数量变化趋势</h3>
      <v-chart :option="lineData" class="chart" />
    </div>

    <div class="chart-container">
      <h3>电影类型分布</h3>
      <v-chart :option="pieData" class="chart" />
    </div>
  </div>
</template>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.chart-container {
  margin-top: 30px;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

h2 {
  margin-bottom: 20px;
  color: #333;
}

h3 {
  margin-bottom: 15px;
  color: #666;
}

.chart {
  width: 100%;
  height: 400px;
}
</style>