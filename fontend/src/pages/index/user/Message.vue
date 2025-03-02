<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import Tooltip from './Tooltip.vue';
import Cancel from './Cancel.vue';

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

                <button @click="toggleShowAllUsers" v-if="currentUser?.id === 1" class="admin-toggle">
                    {{ showAllUsers ? '仅显示我的审核' : '查看全部审核' }}
                </button>
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
    background-image: radial-gradient(at 21% 94%, hsla(270, 95%, 76%, 1) , transparent 50%),
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