<script lang="ts" setup>
import Search from "./components/Serach.vue";
import { onMounted, Ref } from "vue";
import { inject } from "vue";
import { ref } from "vue";
import MDMIndex from "./MDMIndex.vue";

const globalSelect = inject<Ref<string>>("globalSelect");
if (!globalSelect) {
    throw new Error("globalSelect is not provided");
}

const activeIndex = ref("1");
const handleSelect = (key: string, keyPath: string[]) => {
    globalSelect.value = "2-" + key;
};
onMounted(() => {
    globalSelect.value = "2-1";
});

</script>

<template>
    <div>
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
            <el-menu-item index="1">数据视图</el-menu-item>
            <el-menu-item index="2">多维管理</el-menu-item>
            <el-menu-item>
                <Search style="background: #ccc;" />
            </el-menu-item>
        </el-menu>


        <MDMIndex v-if="globalSelect === '2-2'" />
    </div>
</template>

<style scoped></style>
