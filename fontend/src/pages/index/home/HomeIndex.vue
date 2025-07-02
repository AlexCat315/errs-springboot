<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { use } from 'echarts/core'
import { LineChart, PieChart, BarChart } from 'echarts/charts'
import { GridComponent, TooltipComponent, LegendComponent, TitleComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import VChart from 'vue-echarts'
import { getALl, getlineDataAll } from '../../../net/admin-home/get'


// 注册 ECharts 组件
use([LineChart, PieChart, BarChart, GridComponent, TooltipComponent, LegendComponent, TitleComponent, CanvasRenderer])
const xAxisData: string[] = []
for (let i = 6; i >= 0; i--) { // Changed to start from 6 to get older dates first for chronological order
    const date = new Date(Date.now() - i * 24 * 60 * 60 * 1000)
    xAxisData.push(date.toLocaleDateString())
}
// 示例数据 - 网站访问量
const lineData = ref({
    title: {
        text: '网站访问量趋势' // Changed chart title
    },
    tooltip: {
        trigger: 'axis'
    },
    xAxis: {
        type: 'category',
        data: xAxisData,
        name: '日期' // Added x-axis name for clarity
    },
    yAxis: {
        type: 'value',
        name: '访问量 (次)' // Added y-axis name for clarity
    },
    series: [
        {
            name: '总访问量', // Changed series name
            type: 'line',
            data: [150, 200, 180, 220, 0, 0, 0] // Example website visit data
        }
    ]
})

const pieData = ref({
    title: {
        text: '内容访问类型分布', // Changed chart title
        left: 'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)' // Added formatter for percentage in tooltip
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: ['音乐访问量', '游戏访问量', '书籍访问量', '其他访问量'] // Changed legend data
    },
    series: [
        {
            name: '访问类型', // Changed series name
            type: 'pie',
            radius: '50%',
            data: [
                { value: 300, name: '音乐访问量' }, // Changed data and names
                { value: 200, name: '游戏访问量' },
                { value: 100, name: '书籍访问量' },
                { value: 50, name: '其他访问量' }
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
})

const barData = ref({
    title: {
        text: '各内容类型总访问量', // Changed chart title
        left: 'center'
    },
    xAxis: {
        type: 'category',
        data: ['音乐', '游戏', '书籍', '其他'], // Changed x-axis categories
        name: '内容类型' // Added x-axis name for clarity
    },
    yAxis: {
        type: 'value',
        name: '访问量 (次)' // Added y-axis name for clarity
    },
    series: [{
        data: [350, 250, 150, 80], // Example website content visit data, adjusted values
        type: 'bar',
        name: '总访问量', // Changed series name
        showBackground: true,
        backgroundStyle: {
            color: 'rgba(180, 180, 180, 0.2)'
        }
    }]
})

const totalVisits = ref(0)

onMounted(() => {
    getALl(
        (data: any) => {
            totalVisits.value = data.allCount;
            pieData.value.series[0].data[0].value = data.songCount;
            pieData.value.series[0].data[1].value = data.gameCount;
            pieData.value.series[0].data[2].value = data.bookCount;
            pieData.value.series[0].data[3].value = data.otherCount;
            barData.value.series[0].data[0] = data.songCount;
            barData.value.series[0].data[1] = data.gameCount;
            barData.value.series[0].data[2] = data.bookCount;
            barData.value.series[0].data[3] = data.otherCount;
        },
        (error) => {
            console.error(error)
        }
    );
    getlineDataAll(
        new Date(Date.now() - 7 * 24 * 60 * 60 * 1000),
        new Date(),
        (data: any) => {
            console.log(data);
            lineData.value.series[0].data =  data.slice(1);;
        },
        (error) => {
            console.error(error)
        }
    );

})
</script>

<template>
    <div class="dashboard-container">
        <h2>网站数据统计</h2> <!-- Changed main title -->

        <div class="chart-container">
            <div class="header-wrapper">
                <h3>网站访问量趋势</h3>
                <div class="total-box">
                    <span class="label">总访问量：</span>
                    <span class="value">{{ totalVisits }}</span>
                </div>
            </div>
            <v-chart :option="lineData" class="chart" />
        </div>

        <div class="chart-container">
            <h3>内容访问类型分布</h3> <!-- Changed chart container title -->
            <v-chart :option="pieData" class="chart" />
        </div>

        <div class="chart-container">
            <h3>各内容类型总访问量</h3> <!-- Changed chart container title -->
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

.header-wrapper {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
}

.total-box {
    background: #f8f9fa;
    padding: 8px 15px;
    border-radius: 20px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.total-box .label {
    color: #666;
    font-size: 14px;
}

.total-box .value {
    color: #2c3e50;
    font-size: 20px;
    font-weight: bold;
    margin-left: 8px;
}
</style>