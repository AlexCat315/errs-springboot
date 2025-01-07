<script setup lang="js">
import Search from '../components/search.vue';
import {reactive, inject, ref} from "vue";
// 定义菜单项数据x
const menuItems = [
  {key: 1, name: '探索', icon: new URL('../assets/icons/探索.svg', import.meta.url).href},
  {key: 2, name: '游戏', icon: new URL('../assets/icons/游戏.svg', import.meta.url).href},
  {key: 3, name: '图书', icon: new URL('../assets/icons/图书.svg', import.meta.url).href},
  {key: 4, name: '新闻', icon: new URL('../assets/icons/新闻.svg', import.meta.url).href},
  {key: 5, name: '视频', icon: new URL('../assets/icons/视频.svg', import.meta.url).href},
];

const bottomMenuItems = [
  {key: 6, name: '收藏', icon: new URL('../assets/icons/收藏.svg', import.meta.url).href},
  {key: 7, name: '设置', icon: new URL('../assets/icons/设置.svg', import.meta.url).href},

];
const globalSelect = inject("globalSelect");
const selectIndex = ref(globalSelect.value);
const globalTheme = inject("globalTheme")
const selectItem = (item) => {
  // 设置选中的菜单项，保存到全局变量中
  globalSelect.value = item;
  selectIndex.value = item;
};
const selectButtomItem = (item) => {
  globalSelect.value = item;
  selectIndex.value = item;
};

const searchValue = reactive({value: ''});

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
    <Search v-if="globalTheme === 'light'" background-color="#fff" style="margin-top: 30px;"
            @update:search="searchFun"/>
    <Search v-if="globalTheme === 'dark'" background-color="#403e3d" style="margin-top: 30px;"
            @update:search="searchFun"/>

    <div :style="{ width: `${props.distanceToLeft}px` }" class="sidebar">
      <ul>
        <li v-for="item in menuItems" :key="item.key" :class="{ active: item.key === selectIndex.value }"
            @click="selectItem(item.key)">
          <img :src="item.icon" alt="" class="icon"/>
          {{ item.name }}
        </li>
      </ul>
    </div>

    <!-- 底部菜单栏 -->
    <div :style="{ width: `${props.distanceToLeft}px` }" class="sidebar" style="margin-top: auto; margin-bottom: 15px;">
      <ul>
        <li v-for="item in bottomMenuItems" :key="item.key" :class="{ active: item.key === selectIndex.value }"
            @click="selectButtomItem(item.key)">
          <img :src="item.icon" alt="" class="icon"/>
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
  background-color: #f0efef;
}

.sidebar li.active {
  background-color: #FFFF;
}

.icon {
  margin-right: 16px;
  width: 22px;
  height: 22px;
}
</style>