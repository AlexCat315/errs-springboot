<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { use } from 'echarts/core'
import { LineChart, PieChart, BarChart } from 'echarts/charts'
import { GridComponent, TooltipComponent, LegendComponent, TitleComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import VChart from 'vue-echarts'
import { getALl } from '../../../../net/admin-home/get'
import { getDeleteCount, getInsertCount, getSongTypeCount, getUpdateCount } from '../../../../net/music/get'

// 注册 ECharts 组件
use([LineChart, PieChart, BarChart, GridComponent, TooltipComponent, LegendComponent, TitleComponent, CanvasRenderer])
const xAxisData: string[] = []
for (let i = 0; i < 7; i++) {
  const date = new Date(Date.now() - i * 24 * 60 * 60 * 1000)
  xAxisData.push(date.toLocaleDateString())
}
// 示例数据
const lineData = ref({
  tooltip: {
    trigger: 'item'
  },
  xAxis: {
    type: 'category',
    data: xAxisData,
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '新增歌曲',
      type: 'line',
      data: [12, 22, 18, 28, 26, 32, 40]
    },
    {
      name: '下架歌曲',
      type: 'line',
      data: [1, 3, 2, 5, 3, 6, 4]
    },
    {
      name: '修改歌曲',
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
      name: '音乐类型',
      type: 'pie',
      radius: '50%',
      data: [
        { value: 120, name: '流行' },
        { value: 53, name: '摇滚' },
        { value: 45, name: '古典' }
      ]
    }
  ]
})

const barData = ref({
  tooltip: {
    trigger: 'item'
  },
  xAxis: {
    type: 'category',
    data: ['流行', '摇滚', '古典'],
  },
  yAxis: {
    type: 'value'
  },
  series: [{
    data: [202, 106, 37],
    type: 'bar',
    showBackground: true,
    backgroundStyle: {
      color: 'rgba(180, 180, 180, 0.2)'
    }
  }]
})

onMounted(() => {
  getInsertCount(
    new Date(Date.now() - 7 * 24 * 60 * 60 * 1000),
    new Date(),
    (data: any) => {
      lineData.value.series[0].data = data
    });
  getUpdateCount(
    new Date(Date.now() - 7 * 24 * 60 * 60 * 1000),
    new Date(),
    (data: any) => {
      lineData.value.series[1].data = data
    });

  getDeleteCount(
    new Date(Date.now() - 7 * 24 * 60 * 60 * 1000),
    new Date(),
    (data: any) => {
      lineData.value.series[2].data = data
    });

  getSongTypeCount(
    (data: any) => {
      pieData.value.series[0].data = data.map((item: any) => ({
        value: item.count,
        name: item.type
      }));
      // 更新 barData
      barData.value.xAxis.data = data.map((item: any) => item.type); // 设置横轴分类
      barData.value.series[0].data = data.map((item: any) => item.count); // 设置柱状图数据
    }
  )
})
</script>

<template>
  <div class="dashboard-container">
    <h2>音乐数据统计</h2>

    <div class="chart-container">
      <h3>歌曲数量变化趋势</h3>
      <v-chart :option="lineData" class="chart" />
    </div>

    <div class="chart-container">
      <h3>音乐类型分布</h3>
      <v-chart :option="pieData" class="chart" />
    </div>

    <div class="chart-container">
      <h3>各类型音乐总数</h3>
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
