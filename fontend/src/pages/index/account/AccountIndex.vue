<script lang="ts" setup>
import Search from "./components/Serach.vue";
import { onMounted } from "vue";
import { inject } from "vue";

const globalSelect = inject<{ value: string }>("globalSelect");
import { ref } from "vue";

const activeIndex = ref("1"); 
const activeIndex2 = ref("1");
const handleSelect = (key: string, keyPath: string[]) => {
     globalSelect!.value = "2-" + key;
};
onMounted(()=>{
  globalSelect!.value = "2-1";
})

</script>

<template>
    <div>
        <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
        >
            <el-menu-item index="1">数据视图</el-menu-item>
            <el-menu-item index="2">添加数据</el-menu-item>
            <el-menu-item index="3" >删除数据</el-menu-item>
            <el-menu-item index="4">修改数据</el-menu-item>
            <el-menu-item > <Search  style="background: #ccc;"/></el-menu-item>
        </el-menu>
    </div>
</template>

<style scoped></style>
