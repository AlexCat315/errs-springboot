<script lang="ts" setup>
import Search from "./components/Serach.vue";
import { onMounted } from "vue";
import { inject, Ref } from "vue";
import { ref } from "vue";
import AddInfo from "./components/AddInfo.vue";
import DeleteInfo from "./components/delete/MusicList.vue";
import UpdateInfo from "./components/update/MusicList.vue";

const globalSelect = inject<Ref<string>>("globalSelect");
if (!globalSelect) {
    throw new Error("globalSelect is not provided");
}

const activeIndex = ref("1");
const handleSelect = (key: string, keyPath: string[]) => {
    globalSelect.value = "4-" + key;
};
onMounted(() => {
    globalSelect.value = "4-1";
});
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
                    <Search style="background: #ccc" />
                </el-menu-item>
            </el-menu>
        </div>
        <AddInfo v-if="globalSelect === '4-2'" />
        <DeleteInfo v-if="globalSelect === '4-3'" />
        <UpdateInfo v-if="globalSelect === '4-4'" />
    </div>
</template>

<style scoped></style>
