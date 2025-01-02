<script setup lang="js">
import Search from '../components/search.vue';
import {reactive, ref, watch} from "vue";
import {Get} from "../net/get.ts";

// 定义菜单项数据
const menuItems = [
  { name: '探索', icon: new URL('../assets/icons/探索.svg', import.meta.url).href },
  { name: '游戏', icon: new URL('../assets/icons/游戏.svg', import.meta.url).href },
  { name: '图书', icon: new URL('../assets/icons/图书.svg', import.meta.url).href },
  { name: '新闻', icon: new URL('../assets/icons/新闻.svg', import.meta.url).href },
  { name: '视频', icon: new URL('../assets/icons/视频.svg', import.meta.url).href },
];

const activeItem = ref('');
const selectItem = (item) => {
  activeItem.value = item;
  Get(`/api/user/account/get-test?string=post parameter string success`, (data) => {
    console.log(data.data);
  }, (data) => {
    console.log(data);
  })
};

const searchValue = reactive({value: ''});

const props = defineProps({
  distanceToLeft: {
    type: Number,
    required: true,
  },
});

watch(() => props.distanceToLeft, (newValue, oldValue) => {
  console.log(`右侧距离从 ${oldValue} 改变为 ${newValue}`)
});
const searchFun = (value) => {
  searchValue.value = value
}
</script>


<template>
  <div style="margin-top: 35px;">
    <Search style="margin-top: 30px;" @update:search="searchFun"/>

    <div :style="{ width: `${props.distanceToLeft}px` }" class="sidebar">
      <ul>
        <li
            v-for="item in menuItems"
            :key="item.name"
            :class="{ 'active': activeItem === item.name }"
            @click="selectItem(item.name)"
        >
          <!-- 显示图标 -->
          <img :src=item.icon  alt="" class="icon"/>
          <!-- 显示文字 -->
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
  display: flex; /* 使图标和文字水平排列 */
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
  margin-right: 16px; /* 图标与文字之间的间距 */
  font-size: 18px; /* 根据需要调整图标大小 */
  width: 22px;
  height: 22px;
}
</style>
