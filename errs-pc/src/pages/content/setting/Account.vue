<script setup lang="ts">

import { inject, ref } from "vue";
import User from "../../../components/user.vue";

const globalTheme = inject<string>("globalTheme");
const selectTheme = ref(globalTheme);
</script>

<template>
    <div>
        <!--头部设置-->
        <div style="display: flex; justify-content: space-between;">
            <h2 :style="{color: selectTheme === 'light' ? 'black' : '#cfd3dc'}" style="font-family: LXGWMarkerGothic-Regular,'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin-top: 8px;">设置</h2>
            <div>
                <User />
            </div>
        </div>
        <div class="divider"></div>



    </div>

</template>

<style scoped lang="css">
.divider {
    height: 1px;
    background-color: #ccc;
    margin: 6px 0;
}
</style>