<script setup lang="ts">

import { inject, ref } from "vue";

const globalTheme = inject("globalTheme");

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
        <div style="padding-left: 30px;padding-right: 30px">
            <div>
                <p style="font-size: 15px;
                          font-weight: bold;
                          font-family: 'system-ui';
                          padding-top: 12px">常规设置</p>
                <p style="font-size: 14px;
                          font-family: 'system-ui';
                          color:#87899c;">App颜色模式：</p>


            </div>
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