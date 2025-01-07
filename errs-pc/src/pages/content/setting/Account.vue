<script setup lang="ts">

import {inject, ref} from "vue";

const globalTheme = inject("globalTheme");

const selectTheme = ref(globalTheme)
</script>

<template>
    <div class="account">
        <!--头部设置-->
        <div>
            <h2 style="font-family: LXGWMarkerGothic-Regular,'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    margin-left: 15px;">设置</h2>
            <div class="divider"></div>
        </div>

        <!--主题设置-->
        <div>
            <p style="">主题设置</p>

        </div>


    </div>

</template>

<style scoped lang="css">
.divider {
    height: 1px;
    background-color: #ccc;
    margin: 10px 0;
}
</style>