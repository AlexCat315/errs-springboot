<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import Tooltip from './Tooltip.vue';
import Cancel from './Cancel.vue';
import {
    Present
} from '@element-plus/icons-vue'

// 模拟数据 - 实际应用中应从后端获取
interface UserAudit {
    id: number;
    username: string;
    inviteCode: string; // 邀请码
    status: 'pending' | 'approved' | 'rejected';
    registrationTime: string;
    auditTime?: string; // 审核时间 (通过/拒绝时)
    reason?: string;    // 拒绝原因 (可选)
}

const userAudits = ref<UserAudit[]>([]);
const loading = ref(true);
const error = ref<string | null>(null);
const currentUser = ref<UserAudit | null>(null); // 假设当前登录的用户信息.  实际中从全局状态或localStorage等获取。
const showAllUsers = ref(false); // 是否显示所有用户 (管理员权限)


const fetchUserAudits = async () => {
    loading.value = true;
    error.value = null;
    try {



    } catch (err: any) {
        error.value = err.message || 'Failed to fetch user audits.';
    } finally {
        loading.value = false;
    }
};

// 模拟管理员操作 (审核)
const approveUser = (userId: number) => {
    const userIndex = userAudits.value.findIndex(user => user.id === userId);
    if (userIndex !== -1) {
        userAudits.value[userIndex].status = 'approved';
        userAudits.value[userIndex].auditTime = new Date().toLocaleString();
        //  这里应该发送API请求到后端，更新数据库
    }
};

const rejectUser = (userId: number) => {
    const userIndex = userAudits.value.findIndex(user => user.id === userId);
    if (userIndex !== -1) {
        const reason = prompt("Please enter the reason for rejection:");
        if (reason) {
            userAudits.value[userIndex].status = 'rejected';
            userAudits.value[userIndex].auditTime = new Date().toLocaleString();
            userAudits.value[userIndex].reason = reason;
            //  这里应该发送API请求到后端，更新数据库
        }
    }
};

onMounted(() => {
    fetchUserAudits();
});
const tableData = [
    {
        date: '2016-05-03',
        name: 'Tom',
        code: 'INVITE123',
    },
]
const historyData = [
    {
        date: '2021-05-03',
        name: 'Tom',
        code: 'INVITE123',
        status: 'approved',
        result: '注册信息无误',
        auditTime: '2021-05-03 10:00:00',
    },
]

const toggleShowAllUsers = () => {
    showAllUsers.value = !showAllUsers.value;
    fetchUserAudits(); // 重新获取数据
};

const isShowLikePanel = ref(false);

const showHistory = ref(true);

const historyText = () =>{
    if(showHistory.value){
        return '待审核'
    }else{
        return '历史记录'
    }
};

</script>

<template>
    <div>
        <div v-show="isShowLikePanel" class="loading-overlay">
            <!-- 审核流程可视化 -->
            <div class="process-flow">
                <Cancel @click="isShowLikePanel = false" style="margin-left: 96%;" />
                <h2 style="margin-top: -20px;">📋 审核流程图解</h2>
                <div class="steps">
                    <div class="step-card">
                        <div class="step-icon">①</div>
                        <h2>邀请码发放</h2>
                        <p>管理员生成唯一邀请码并分发给目标用户</p>
                    </div>
                    <div class="step-card">
                        <div class="step-icon">②</div>
                        <h2>用户注册</h2>
                        <p>用户使用有效邀请码完成注册流程</p>
                    </div>
                    <div class="step-card">
                        <div class="step-icon">③</div>
                        <h2>待审队列</h2>
                        <p>新注册用户进入待审核状态等待处理</p>
                    </div>
                    <div class="step-card">
                        <div class="step-icon">④</div>
                        <h2>人工审核</h2>
                        <p>管理员核查信息并决定通过/拒绝</p>
                    </div>
                    <div class="step-card">
                        <div class="step-icon">⑤</div>
                        <h2>结果通知</h2>
                        <p>通过站内信/邮件通知用户审核结果</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="user-center">
            <div class="header-section">
                <div style="display: flex;">
                    <h2 class="title">📝 用户审核管理</h2>
                    <Tooltip @click="isShowLikePanel = true"
                        style="height: 20px;transform: scale(0.5);margin-top: 14px;margin-left: -1px;" />
                </div>

                <el-button type="primary" @click="showHistory = !showHistory">
                    <el-icon>
                        <svg t="1740989754269" class="icon" viewBox="0 0 1024 1024" version="1.1"
                            xmlns="http://www.w3.org/2000/svg" p-id="1505" width="200" height="200">
                            <path d="M512 512m-512 0a512 512 0 1 0 1024 0 512 512 0 1 0-1024 0Z" fill="#E9F7FA"
                                p-id="1506"></path>
                            <path
                                d="M248.271249 220.20994m3.567116 0l299.637715 0q3.567116 0 3.567116 3.567115l0 299.637715q0 3.567116-3.567116 3.567116l-299.637715 0q-3.567116 0-3.567116-3.567116l0-299.637715q0-3.567116 3.567116-3.567115Z"
                                fill="#24A5CC" p-id="1507"></path>
                            <path
                                d="M546.957733 229.008825v288.936368H257.070135V229.008825H546.957733m5.469578-17.835578H251.838365a12.603809 12.603809 0 0 0-12.603809 12.603808V523.176962a12.366001 12.366001 0 0 0 12.603809 12.366001h299.637715a12.366001 12.366001 0 0 0 12.366001-12.366001V223.777055a12.366001 12.366001 0 0 0-12.366001-12.603808z"
                                fill="#170901" p-id="1508"></path>
                            <path
                                d="M449.218765 430.194148a9.036693 9.036693 0 0 1-6.183001-2.615885l-95.123084-95.123084a8.798885 8.798885 0 0 1 12.603809-12.603809l95.123084 95.123084a8.798885 8.798885 0 0 1 0 12.603809 9.274501 9.274501 0 0 1-6.420808 2.615885z"
                                fill="#FFFFFF" p-id="1509"></path>
                            <path
                                d="M353.857873 430.194148a9.036693 9.036693 0 0 1-6.183001-2.615885 8.798885 8.798885 0 0 1 0-12.603809l95.123084-95.123084a8.798885 8.798885 0 0 1 12.603809 12.603809l-95.123084 95.123084a9.512308 9.512308 0 0 1-6.420808 2.615885z"
                                fill="#FFFFFF" p-id="1510"></path>
                            <path
                                d="M563.842081 760.984673a193.813284 193.813284 0 1 1 193.813284-193.813284A194.051092 194.051092 0 0 1 563.842081 760.984673z"
                                fill="#FF912E" p-id="1511"></path>
                            <path
                                d="M563.842081 383.108221a184.776591 184.776591 0 1 1-184.776591 184.776591 185.014399 185.014399 0 0 1 184.776591-184.776591m0-17.835578a202.612169 202.612169 0 1 0 202.612169 202.612169 202.849977 202.849977 0 0 0-202.612169-202.612169z"
                                fill="#170901" p-id="1512"></path>
                            <path
                                d="M563.842081 567.884812m-151.721319 0a151.721319 151.721319 0 1 0 303.442638 0 151.721319 151.721319 0 1 0-303.442638 0Z"
                                fill="#FFFFFF" p-id="1513"></path>
                            <path
                                d="M523.176962 503.438922l39.238273 63.494659a4.756154 4.756154 0 0 0 7.847654 0l39.238272-63.494659a4.756154 4.756154 0 0 1 4.042731-2.140269h25.683233L578.823967 594.519275a4.518346 4.518346 0 0 0 0 2.378078v61.830004h-23.780771v-61.592197a4.518346 4.518346 0 0 0 0-2.378077l-60.403159-93.45843h25.683233a4.756154 4.756154 0 0 1 2.853692 2.140269zM818.058523 401.89503a8.798885 8.798885 0 0 1-8.798885-9.036693 164.800743 164.800743 0 0 0-164.562936-164.325128 9.036693 9.036693 0 0 1-9.036693-9.036693 8.798885 8.798885 0 0 1 9.036693-8.798885 182.636321 182.636321 0 0 1 182.398514 182.160706 9.036693 9.036693 0 0 1-9.036693 9.036693z"
                                fill="#FF912E" p-id="1514"></path>
                            <path
                                d="M821.625639 428.053878l31.15281-38.524849a4.518346 4.518346 0 0 0-3.567116-7.372039h-62.30562a4.518346 4.518346 0 0 0-3.567116 7.372039L814.491407 428.053878a4.756154 4.756154 0 0 0 7.134232 0z"
                                fill="#FF912E" p-id="1515"></path>
                            <path
                                d="M380.492336 821.625639a182.636321 182.636321 0 0 1-182.398513-182.398514 8.798885 8.798885 0 0 1 9.036693-8.798885 8.561078 8.561078 0 0 1 8.798885 8.798885A164.800743 164.800743 0 0 0 380.492336 803.79006a9.036693 9.036693 0 1 1 0 17.835579z"
                                fill="#24A5CC" p-id="1516"></path>
                            <path
                                d="M202.374361 605.45843L171.221551 643.745471a4.756154 4.756154 0 0 0 3.567116 7.609847H237.80771a4.756154 4.756154 0 0 0 3.804924-7.609847l-32.104041-38.287041a4.518346 4.518346 0 0 0-7.134232 0z"
                                fill="#24A5CC" p-id="1517"></path>
                        </svg>
                    </el-icon>
                    {{ historyText() }}
                </el-button>

            </div>

            <!-- 数据加载状态 -->
            <div v-if="loading" class="loading-state">
                <div class="loader"></div>
                <span>数据加载中...</span>
            </div>

            <!-- 数据展示区域 -->
            <template v-else>
                <div v-if="error" class="error-alert">
                    ⚠️ {{ error }}
                </div>

                <div v-else>
                    <!-- 审核数据表格 -->
                    <div class="table-wrapper">
                        <el-table v-if="!showHistory" :data="tableData" style="width: 100%" height="250">
                            <el-table-column fixed prop="date" label="注册时间" width="150" />
                            <el-table-column prop="name" label="用户名" width="120" />
                            <el-table-column prop="code" label="邀请码" width="120" />
                            <el-table-column align="right">
                                <template #default="scope">
                                    <el-button size="small" type="success" @click="">
                                        同意
                                    </el-button>
                                    <el-button size="small" type="danger" @click="">
                                        拒绝
                                    </el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <el-table v-if="showHistory" :data="tableData" style="width: 100%" height="250">
                            <el-table-column fixed prop="date" label="注册时间" width="150" />
                            <el-table-column prop="name" label="用户名" width="120" />
                            <el-table-column prop="code" label="邀请码" width="120" />
                            <el-table-column prop="status" label="审核状态" width="120" />
                            <el-table-column prop="result" label="审核结果" width="120" />
                            <el-table-column prop="auditTime" label="审核时间" width="150" />
                        </el-table>
                        <div v-if="tableData.length === 0 && historyData.length === 0" class="empty-state">
                            🗃️ 暂无审核数据
                        </div>
                    </div>
                </div>
            </template>
        </div>
    </div>
</template>

<style scoped>
/* 整体布局 */
.user-center {
    max-width: 600px;
    /* Increased max-width */
    margin: 2rem auto;
    padding: 2rem;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    background-color: #fff;
}

.header-section {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 2rem;
}

.title {
    color: #2c3e50;
    font-size: 1.4rem;
    border-left: 4px solid #3498db;
    padding-left: 1rem;
}

/* 表格样式 */
.table-wrapper {
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    margin-top: -20px;
    font-size: 16px;
}

.audit-table {
    width: 100%;
    background: white;
    border-collapse: collapse;

    th {
        background: #3498db;
        color: white;
        padding: 1rem;
        font-weight: 500;
    }

    td {
        padding: 1rem;
        border-bottom: 1px solid #eee;
    }

    tr:hover {
        background: #f5f7fa;
    }
}

.invite-code {
    padding: 4px 12px;
    font-size: 0.9em;
}

/* 状态标签 */
.status-badge {
    padding: 4px 12px;
    border-radius: 20px;
    font-size: 0.9em;
    margin-left: 40px;

    &.status-pending {
        background: #fff3cd;
        color: #856404;
    }

    &.status-approved {
        background: #d4edda;
        color: #155724;
    }

    &.status-rejected {
        background: #f8d7da;
        color: #721c24;
    }
}


/* 操作按钮 */
.action-buttons {
    display: flex;
    gap: 0.5rem;

    button {
        padding: 6px 12px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        transition: all 0.2s;

        &:hover {
            transform: translateY(-1px);
        }
    }

    .btn-approve {
        background: #28a745;
        color: white;
    }

    .btn-reject {
        background: #dc3545;
        color: white;
    }
}

/* 流程步骤 */
.process-flow {
    width: 1200px;
    margin: 3rem 0;
    padding: 2rem;
    background: white;
    border-radius: 12px;
    font-size: 16px;

    .steps {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 1.5rem;
        margin-top: 1.5rem;
    }

    .step-card {
        text-align: center;
        padding: 1.5rem;
        background: #f8f9fa;
        border-radius: 8px;

        .step-icon {
            width: 40px;
            height: 40px;
            background: #3498db;
            color: white;
            border-radius: 50%;
            margin: 0 auto 1rem;
            line-height: 40px;
        }
    }
}

/* 用户状态卡片 */
.user-status-card {
    margin-top: 2rem;
    padding: 2rem;
    background: white;
    border-radius: 12px;

    .status-details {
        display: grid;
        gap: 1rem;

        .detail-item {
            display: flex;
            align-items: center;
            gap: 1rem;

            label {
                color: #6c757d;
                min-width: 80px;
            }
        }
    }
}

/* 加载状态 */
.loading-state {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 2rem;

    .loader {
        width: 24px;
        height: 24px;
        border: 3px solid #f3f3f3;
        border-top: 3px solid #3498db;
        border-radius: 50%;
        animation: spin 1s linear infinite;
        margin-right: 1rem;
    }
}

@keyframes spin {
    0% {
        transform: rotate(0deg);
    }

    100% {
        transform: rotate(360deg);
    }
}

/* 空状态提示 */
.empty-state {
    text-align: center;
    padding: 3rem;
    color: #6c757d;
    font-size: 1.1rem;
}

.loading-overlay {
    position: fixed;
    top: 0;
    right: 0;
    width: 100%;
    height: 100%;
    background-color: #99d1ff;
    background-image: radial-gradient(at 21% 94%, hsla(270, 95%, 76%, 1), transparent 50%),
        radial-gradient(at 56% 5%, hsla(252, 65%, 79%, 1) 0, transparent 50%),
        radial-gradient(at 67% 94%, hsla(194, 81%, 67%, 1) 0, transparent 50%),
        radial-gradient(at 15% 72%, hsla(304, 97%, 77%, 1) 0, transparent 50%),
        radial-gradient(at 63% 56%, hsla(38, 98%, 63%, 1) 0, transparent 50%),
        radial-gradient(at 41% 67%, hsla(321, 61%, 77%, 1) 0, transparent 50%),
        radial-gradient(at 68% 68%, hsla(263, 67%, 69%, 1) 0, transparent 50%);
    /* 半透明背景 */
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 2;
    /* 确保遮罩层在最上层 */
}
</style>