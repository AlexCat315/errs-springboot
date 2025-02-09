<script setup>
import ViewAll from "./components/games/ViewAll.vue";
</script>

<template>
    <div>
        <div class="section-header">
            <p
                :style="{
                    color: globalTheme === 'dark' ? '#FFF' : 'black',
                }"
                style="
                    font-size: 23px;
                    font-family: yousu-title-black;
                    margin-left: 30px;
                    margin-top: -35px;
                "
            >
                高品质游戏
            </p>
            <ViewAll class="view-all" />
        </div>

        <div class="gard">
            <div v-for="index in 6" class="card">
            </div>
        </div>
    </div>
</template>

<style scoped>
.section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 2rem 0;
}
.view-all {
    font-family: Arial, Helvetica, sans-serif;
    text-decoration: none;
    font-size: 11px;
    height: 28px;
    width: 120px;
    margin-top: -35px;
    /* 缩放 */
    transform: scale(0.77);
    font-size: 15px;
    margin-right: 20px;
}
.gard {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 1rem;
    margin-top: -20px;
    transform-origin: center;
    box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
    overflow: hidden; /* 确保内容不会溢出边界 */
    width: 92%;
    margin-left: 30px;
    margin-bottom: 20px;
}
.card {

    height: 130px;
    transform: scale(0.8);
    cursor: pointer;
    border-radius: 9.6px;
    box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1) !important; /* 添加阴影效果 */

}
</style>
