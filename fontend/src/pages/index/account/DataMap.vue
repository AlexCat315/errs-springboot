<script setup lang="ts">
import { ref } from 'vue'
import { use } from 'echarts/core'
import { LineChart, PieChart, BarChart } from 'echarts/charts'
import { GridComponent, TooltipComponent, LegendComponent, TitleComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import VChart from 'vue-echarts'

// 注册 ECharts 组件
use([LineChart, PieChart, BarChart, GridComponent, TooltipComponent, LegendComponent, TitleComponent, CanvasRenderer])

// 示例数据
const lineData = ref({
  xAxis: {
    type: 'category',
    data: ['2023-01-01', '2023-01-02', '2023-01-03', '2023-01-04', '2023-01-05', '2023-01-06', '2023-01-07']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '新增用户',
      type: 'line',
      data: [12, 22, 18, 28, 26, 32, 40]
    },
    {
      name: '删除用户',
      type: 'line',
      data: [1, 3, 2, 5, 3, 6, 4]
    },
    {
      name: '修改用户',
      type: 'line',
      data: [4, 6, 9, 5, 7, 8, 10]
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
        { value: 60, name: '普通用户' },
        { value: 30, name: 'VIP用户' },
        { value: 10, name: '管理员' }
      ]
    }
  ]
})

const barData = ref({
    xAxis: {
        type: 'category',
        data: ['普通用户', 'VIP用户', '管理员'],
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
