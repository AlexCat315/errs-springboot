<script setup lang="ts">
import { inject, onMounted, Ref, ref } from 'vue';
import { get_system_theme } from '../../util/Theme';

// 注入全局主题变量，并确保其类型
const globalTheme = inject<Ref<string>>("globalTheme");
if (!globalTheme) {
    console.error("globalTheme is not provided.");
}

// 初始化 selectTheme，确保其值为 globalTheme.value
const selectTheme = ref(globalTheme?.value || 'light');

// 从 localStorage 获取主题
const localTheme = ref<string | null>(localStorage.getItem('theme'));

// 定义响应式数据来存储选中的值
const selectedOption = ref<string>('');

// 初始化主题设置
const themeStting = () => {
    if (localTheme.value) {
        selectedOption.value = localTheme.value;
        if (localTheme.value === 'system') {
            get_system_theme().then((isDark) => {
                if (isDark) {
                    selectTheme.value = 'dark';
                    if (globalTheme) {
                        globalTheme.value = 'dark';
                    }
                } else {
                    selectTheme.value = 'light';
                    if (globalTheme) {
                        globalTheme.value = 'light';
                    }
                }
            });

        } else {
            selectTheme.value = localTheme.value;
        }
    } else {
        // 如果 localStorage 中没有主题，使用 selectTheme 的值
        selectedOption.value = selectTheme.value;
        localStorage.setItem('theme', selectTheme.value);
    }
};

// 监听选中的值变化
const handleSelection = () => {
    localStorage.setItem('theme', selectedOption.value);
    if (selectedOption.value === 'system') {
        get_system_theme().then((isDark) => {
            if (isDark) {
                selectTheme.value = 'dark';
                if (globalTheme) {
                    globalTheme.value = 'dark';
                }
            } else {
                selectTheme.value = 'light';
                if (globalTheme) {
                    globalTheme.value = 'light';
                }
            }
        });

    } else {
        selectTheme.value = selectedOption.value;
    }
    if (globalTheme) {
        globalTheme.value = selectTheme.value;
    }
    localStorage.setItem('theme', selectedOption.value);
};

// 初始化时调用主题设置函数
onMounted(() => {
    themeStting();
});
</script>

<template>
    <div class="mydict">
        <div>
            <label>
                <input type="radio" name="radio" value="system" v-model="selectedOption" @change="handleSelection">
                <span>跟随系统</span>
            </label>
            <label>
                <input type="radio" name="radio" value="light" v-model="selectedOption" @change="handleSelection">
                <span>明亮</span>
            </label>
            <label>
                <input type="radio" name="radio" value="dark" v-model="selectedOption" @change="handleSelection">
                <span>暗夜</span>
            </label>
        </div>
    </div>
</template>

<style lang="css" scoped>
:focus {
    outline: 0;
    border-color: #4d82ff;
    box-shadow: 0 0 0 4px #2a4a8f;
}

.mydict div {
    display: flex;
    flex-wrap: wrap;
    margin-top: 0.5rem;
}

.mydict input[type="radio"] {
    clip: rect(0 0 0 0);
    clip-path: inset(100%);
    height: 1px;
    overflow: hidden;
    position: absolute;
    white-space: nowrap;
    width: 1px;
}

.mydict input[type="radio"]:checked+span {
    box-shadow: 0 0 0 0.0625em #4d82ff;
    background-color: #1c2b4a;
    z-index: 1;
    color: #4d82ff;
}

label span {
    display: block;
    cursor: pointer;
    background-color: #1e2635;
    padding: 0.375em 0.75em;
    position: relative;
    margin-left: 0.0625em;
    box-shadow: 0 0 0 0.0625em #4a5568;
    letter-spacing: 0.05em;
    color: #a0aec0;
    text-align: center;
    transition: background-color 0.5s ease;
}

label:first-child span {
    border-radius: 0.375em 0 0 0.375em;
}

label:last-child span {
    border-radius: 0 0.375em 0.375em 0;
}
</style>