<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { use } from 'echarts/core'
import { LineChart, PieChart, BarChart } from 'echarts/charts'
import { GridComponent, TooltipComponent, LegendComponent, TitleComponent, DataZoomComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import VChart from 'vue-echarts'
import { ElDatePicker } from 'element-plus'

// 注册 ECharts 组件
use([LineChart, PieChart, BarChart, GridComponent, TooltipComponent,
 LegendComponent, TitleComponent, DataZoomComponent, CanvasRenderer])

// 日期范围
const dateRange = ref([new Date(Date.now() - 7 * 24 * 60 * 60 * 1000).toString(), new Date().toString()])

const xAxisData: string[] = []
for (let i = 0; i < 7; i++) {
  const date = new Date(Date.now() - i * 24 * 60 * 60 * 1000)
  xAxisData.push(date.toLocaleDateString())
}
// 定义接口
interface MovieStats {
  date: string;
  added: number;
  deleted: number;
  modified: number;
}

interface MovieTypeStats {
  type: string;
  count: number;
}

// 数据状态
const loading = ref(true)
const refreshInterval = ref<number | null>(null)

// 生成时间数据从当前是前7天到今天
const dates = [new Date(Date.now() - 7 * 24 * 60 * 60 * 1000).toString(), new Date().toString()]

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
      name: '新增电影',
      type: 'line',
      data: [10, 20, 15, 18, 25, 22, 30]
    },
    {
      name: '删除电影',
      type: 'line',
      data: [2, 1, 3, 2, 4, 3, 5]
    },
    {
      name: '修改电影',
      type: 'line',
      data: [5, 3, 7, 6, 8, 10, 9]
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
      name: '电影类型',
      type: 'pie',
      radius: '50%',
      data: [
        { value: 40, name: '动作片' },
        { value: 30, name: '喜剧片' },
        { value: 20, name: '爱情片' },
        { value: 10, name: '科幻片' },
        { value: 5, name: '恐怖片' }
      ]
    }
  ]
})

const barData = ref({
  xAxis: {
    type: 'category',
    data: ['动作片', '喜剧片', '爱情片', '科幻片', '恐怖片'],
  },
  yAxis: {
    type: 'value'
  },
  series: [{
    data: [120, 200, 150, 80, 70],
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
    <div class="header">
      <h2>电影数据统计</h2>
      <el-date-picker
        v-model="dateRange"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
      />
    </div>

    <div class="chart-container">
      <h3>电影数量变化趋势</h3>
      <v-chart :option="lineData" class="chart" />
    </div>

    <div class="chart-container">
      <h3>电影类型分布</h3>
      <v-chart :option="pieData" class="chart" />
    </div>

    <div class="chart-container">
      <h3>各类型电影总数</h3>
      <v-chart :option="barData" class="chart" />
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