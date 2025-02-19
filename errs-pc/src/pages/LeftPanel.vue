<script setup lang="js">
import Search from "../components/search.vue";
import { reactive, inject, ref, watch } from "vue";
// 定义菜单项数据x
const menuItems = [
    {
        key: 1,
        name: "探索",
        icon: new URL("../assets/icons/探索.svg", import.meta.url).href,
    },
    {
        key: 2,
        name: "游戏",
        icon: new URL("../assets/icons/游戏.svg", import.meta.url).href,
    },
    {
        key: 3,
        name: "图书",
        icon: new URL("../assets/icons/图书.svg", import.meta.url).href,
    },
    {
        key: 4,
        name: "音乐",
        icon: new URL("../assets/icons/音乐.svg", import.meta.url).href,
    },
    {
        key: 5,
        name: "电影",
        icon: new URL("../assets/icons/视频.svg", import.meta.url).href,
    },
    {
        key: 8,
        name: "RevoCat AI",
        icon: new URL("../assets/icons/icon.png", import.meta.url).href,
    },
];

const bottomMenuItems = [
    {
        key: 7,
        name: "设置",
        icon: new URL("../assets/icons/设置.svg", import.meta.url).href,
    },
];
const globalSelect = inject("globalSelect");
const selectIndex = ref(globalSelect.value);
const globalTheme = inject("globalTheme");
const selectTheme = ref(globalTheme);
const globalSearch = inject('globalSearch');

const selectItem = (key) => {
    globalSelect.value = key;
    selectIndex.value = key;
};

const selectButtomItem = (key) => {
    globalSelect.value = key;
    selectIndex.value = key;
};

watch(() => globalSelect.value, (newValue) => {
    selectIndex.value = newValue;
});

const searchValue = reactive({ value: "" });

const props = defineProps({
    distanceToLeft: {
        type: Number,
        required: true,
    },
});

const searchFun = (value) => {
    searchValue.value = value;
    globalSelect.value = 6;
    globalSearch.value = value;
};
</script>

<template>
    <div
        style="
            margin-top: 35px;
            height: 100vh;
            display: flex;
            flex-direction: column;
        "
    >
        <Search

            style="margin-top: 30px"
            @update:search="searchFun"
        />


        <div
            :style="{
                width: `${props.distanceToLeft}px`,
                '--hover-background':
                    selectTheme === 'light' ? '#f0efef' : '#1a1a1a',
                '--active-background':
                    selectTheme === 'light' ? '#FFF' : 'black',
                '--hover-color':
                    selectTheme === 'light' ? '#1a1a1a' : '#f0efef',
                '--active-color': selectTheme === 'light' ? 'black' : '#FFF',
            }"
            class="sidebar"
        >
            <ul>
                <li
                    v-for="item in menuItems"
                    :key="item.key"
                    :class="{ active: item.key === selectIndex }"
                    @click="selectItem(item.key)"
                >
                    <img :src="item.icon" alt="" class="icon" />
                    {{ item.name }}
                </li>
            </ul>
        </div>

        <!-- 底部菜单栏 -->
        <div
            :style="{
                width: `${props.distanceToLeft}px`,
                '--hover-background':
                    selectTheme === 'light' ? '#f0efef' : '#1a1a1a',
                '--active-background':
                    selectTheme === 'light' ? '#FFF' : 'black',
                '--hover-color':
                    selectTheme === 'light' ? '#1a1a1a' : '#f0efef',
                '--active-color': selectTheme === 'light' ? 'black' : '#FFF',
            }"
            class="sidebar"
            style="margin-top: auto; margin-bottom: 15px"
        >
            <ul>
                <li
                    v-for="item in bottomMenuItems"
                    :key="item.key"
                    :class="{ active: item.key === selectIndex }"
                    @click="selectButtomItem(item.key)"
                >
                    <img :src="item.icon" alt="" class="icon" />
                    {{ item.name }}
                </li>
            </ul>
        </div>
    </div>
</template>

<style scoped>
.sidebar ul {
    list-style-type: none;
    padding: 10px;
    border-radius: 10px;
    font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
}

.sidebar li {
    display: flex;
    align-items: center;
    padding: 10px;
    font-size: 15px;
    cursor: pointer;
    border-radius: 10px;
    height: 23px;
}

.sidebar li:hover {
    background-color: var(--hover-background);
    color: var(--hover-color);
}

.sidebar li.active {
    background-color: var(--active-background);
    color: var(--active-color);
}

.icon {
    margin-right: 16px;
    width: 22px;
    height: 22px;
}
</style>
