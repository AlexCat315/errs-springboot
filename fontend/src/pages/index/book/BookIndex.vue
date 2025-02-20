<script lang="ts" setup>
import Search from "./components/Serach.vue";
import { onMounted, Ref } from "vue";
import { inject } from "vue";
import { ref } from "vue";
import DeleteIndex from "./DeleteIndex.vue";
import AddInfo from "./AddInfo.vue";
import UpdateIndex from "./UpdateIndex.vue";
import DataMap from "./DataMap.vue";

const globalSelect = inject<Ref<string>>("globalSelect");
if (!globalSelect) {
    throw new Error("globalSelect is not provided");
}

const activeIndex = ref("1");
const handleSelect = (key: string, keyPath: string[]) => {
    globalSelect.value = "6-" + key;
};
onMounted(() => {
    globalSelect.value = "6-1";
});

</script>

<template>
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
        <DataMap v-if="globalSelect === '6-1'" />
        <AddInfo v-if="globalSelect === '6-2'" />
        <DeleteIndex v-if="globalSelect === '6-3'" />
        <UpdateIndex v-if="globalSelect === '6-4'" />

    </div>
</template>

<style scoped></style>
