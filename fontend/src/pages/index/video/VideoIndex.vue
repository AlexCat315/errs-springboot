<script lang="ts" setup>
import Search from "./components/Serach.vue";
import { onMounted, Ref } from "vue";
import { inject } from "vue";
import AddInfo from "./AddInfo.vue";
import UpdateIndex from "./UpdateIndex.vue";
import DeleteIndex from "./DeleteIndex.vue";
import DataMap from "./DataMap.vue";

const globalSelect = inject<Ref<string>>("globalSelect");
if (!globalSelect) {
    throw new Error("globalSelect is not provided");
}

import { ref } from "vue";

const activeIndex = ref("1");
const handleSelect = (key: string, keyPath: string[]) => {
    globalSelect!.value = "5-" + key;
};
onMounted(() => {
    globalSelect!.value = "5-1";
})

</script>

<template>
<div>
    <div>
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
            <el-menu-item index="1">数据视图</el-menu-item>
            <el-menu-item index="2">添加数据</el-menu-item>
            <el-menu-item index="3">删除数据</el-menu-item>
            <el-menu-item index="4">修改数据</el-menu-item>
            <el-menu-item>
                <Search style="background: #ccc;" />
            </el-menu-item>
        </el-menu>
    </div>
    <DataMap v-if="globalSelect === '5-1'" />
    <AddInfo v-if="globalSelect === '5-2'" />
    <UpdateIndex v-if="globalSelect === '5-4'" />
    <DeleteIndex v-if="globalSelect === '5-3'" />
    </div>

</template>

<style scoped></style>
