<script setup lang="js">
import Search from '../components/search.vue';
import { reactive } from "vue";
import { getSelectMeunKey, setSelectMeunKey } from "../script/selectMenu.ts";

// 定义菜单项数据
const menuItems = [
  { key: 1, name: '探索', icon: new URL('../assets/icons/探索.svg', import.meta.url).href },
  { key: 2, name: '游戏', icon: new URL('../assets/icons/游戏.svg', import.meta.url).href },
  { key: 3, name: '图书', icon: new URL('../assets/icons/图书.svg', import.meta.url).href },
  { key: 4, name: '新闻', icon: new URL('../assets/icons/新闻.svg', import.meta.url).href },
  { key: 5, name: '视频', icon: new URL('../assets/icons/视频.svg', import.meta.url).href },
];

const bottomMenuItems = [
  { key: 6, name: '收藏', icon: new URL('../assets/icons/收藏.svg', import.meta.url).href },
  { key: 7, name: '设置', icon: new URL('../assets/icons/设置.svg', import.meta.url).href },

];

const selectItem = (item) => {
  setSelectMeunKey(item);
};
const selectButtomItem = (item) => {
  setSelectMeunKey(item);
};

const searchValue = reactive({ value: '' });

const props = defineProps({
  distanceToLeft: {
    type: Number,
    required: true,
  },
});

const searchFun = (value) => {
  searchValue.value = value
}
</script>

<template>
  <div style="margin-top: 35px; height: 100vh; display: flex; flex-direction: column;">
    <Search style="margin-top: 30px;" @update:search="searchFun" />

    <div :style="{ width: `${props.distanceToLeft}px` }" class="sidebar">
      <ul>
        <li v-for="item in menuItems" :key="item.key" :class="{ active: item.key === getSelectMeunKey() }"
          @click="selectItem(item.key)">

          <img :src="item.icon" alt="" class="icon" />
          {{ item.name }}
        </li>
      </ul>
    </div>

    <!-- 底部菜单栏 -->
    <div :style="{ width: `${props.distanceToLeft}px` }" class="sidebar" style="margin-top: auto; margin-bottom: 15px;">
      <ul>
        <li v-for="item in bottomMenuItems" :key="item.key" :class="{ active: item.key === getSelectMeunKey() }"
          @click="selectButtomItem(item.key)">
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
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
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
  background-color: #e0e0e0;
}

.sidebar li.active {
  background-color: #ccc;
}

.icon {
  margin-right: 16px;
  width: 22px;
  height: 22px;
}
</style>