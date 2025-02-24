<script setup>
import { logoutJS } from "../net/account/logout.js";
import router from "../router/router.js";
import { ElMessage } from "element-plus";
import { inject, ref, watch } from "vue";
import { Expand, Fold, House, User } from "@element-plus/icons-vue";
import GamesIndex from "./index/games/GamesIndex.vue";
import MusicIndex from "./index/music/MusicIndex.vue";
import AccountIndex from "./index/account/AccountIndex.vue";
import VideoIndex from "./index/video/VideoIndex.vue";
import BookIndex from "./index/book/BookIndex.vue";
import HomeIndex from "./index/home/HomeIndex.vue";

function logout() {
    logoutJS(
        () => {
            // 删除token
            localStorage.removeItem("token");
            sessionStorage.removeItem("token");
            router.push({ name: "login" });
            ElMessage.success("退出成功");
        },
        () => {
            ElMessage.error("退出失败");
        },
    );
}

const collapseBtnClass = ref("el-icon-s-fold");
const isCollapse = ref(false);
const sideWidth = ref(200);
const logoTextShow = ref(true);

const collapse = () => {
    isCollapse.value = !isCollapse.value;
    if (isCollapse.value) {
        sideWidth.value = 64;
        collapseBtnClass.value = "el-icon-s-unfold";
        logoTextShow.value = false;
    } else {
        sideWidth.value = 200;
        collapseBtnClass.value = "el-icon-s-fold";
        logoTextShow.value = true;
    }
};

const activeTextColor = ref("#FFd04b");

// 接收全局变量
const globalSelect = inject("globalSelect");
// 检查 globalSelect 是否存在并设置默认值
if (!globalSelect) {
    throw new Error("globalSelect is not provided");
}

const selectIndex = ref(globalSelect);

const handleSelect = (index) => {
    globalSelect.value = index;
    console.log(index);
};
// 监听全局变量变化
watch(globalSelect, (newValue, oldValue) => {
    if (newValue === oldValue) return;
    switch (newValue) {
        case "1":
            tag.value = "主菜单";
            break;
        case "2":
            tag.value = "用户管理";
            break;
        case "2-1":
            tag.value = "用户管理 - 数据视图";
            break;
        case "2-2":
            tag.value = "用户管理 - 多维管理";
            break;
        case "3-1":
            tag.value = "游戏管理 - 基本信息";
            break;
        case "3-2":
            tag.value = "游戏管理 - 添加信息";
            break;
        case "3-3":
            tag.value = "游戏管理 - 修改信息";
            break;
        case "3-4":
            tag.value = "游戏管理 - 删除信息";
            break;
        case "4":
            tag.value = "音乐管理";
            break;
        case "4-1":
            tag.value = "音乐管理 - 数据视图";
            break;
        case "4-2":
            tag.value = "音乐管理 - 添加信息";
            break;
        case "4-3":
            tag.value = "音乐管理 - 删除信息";
            break;
        case "4-4":
            tag.value = "音乐管理 - 修改信息";
            break;
        case "4-5":
            tag.value = "音乐管理 - 智能搜索";
            break;
        case "5":
            tag.value = "电影管理";
            break;
        case "5-1":
            tag.value = "电影管理 - 数据视图";
            break;
        case "5-2":
            tag.value = "电影管理 - 添加信息";
            break;
        case "5-3":
            tag.value = "电影管理 - 删除信息";
            break;
        case "5-4":
            tag.value = "电影管理 - 修改信息";
            break;
        case "5-5":
            tag.value = "电影管理 - 智能搜索";
            break;
        case "6":
            tag.value = "图书管理";
            break;
        case "6-1":
            tag.value = "图书管理 - 数据视图";
            break;
        case "6-2":
            tag.value = "图书管理 - 添加信息";
            break;
        case "6-3":
            tag.value = "图书管理 - 删除信息";
            break;
        case "6-4":
            tag.value = "图书管理 - 修改信息";
            break;
    }
});
const toMyInfo = () => {
    selectIndex.value = "5";
};
const tag = ref("");
</script>

<template>
    <div class="index-class">
        <el-container style="height: 100%; min-height: 100vh">
            <el-aside :width="sideWidth + 'px'" style="
                    background-color: rgb(238, 241, 246);
                    min-height: 100%;
                    border-radius: 0 20px 0 0;
                ">
                <el-menu :active-text-color="activeTextColor" :collapse="isCollapse" :collapse-transition="false"
                    :default-active="selectIndex" background-color="#5d6d7e" style="height: 100%; overflow-x: hidden"
                    text-color="#FFFFFF" @select="handleSelect">
                    <div style="
                            height: 60px;
                            line-height: 60px;
                            text-align: center;
                        ">
                        <img alt="logo" src="../assets/img/icon.png" style="
                                width: 30px;
                                position: relative;
                                top: 5px;
                                margin-right: 5px;
                            " />
                        <b v-show="logoTextShow" class="logo-text">文娱推荐与评分系统</b>
                    </div>
                    <el-menu-item v-if="isCollapse" @click="collapse">
                        <el-icon>
                            <Expand />
                        </el-icon>
                        <template #title>展开菜单</template>
                    </el-menu-item>
                    <el-menu-item index="1">
                        <el-icon>
                            <House />
                        </el-icon>
                        <template #title>主菜单</template>
                    </el-menu-item>
                    <el-menu-item index="2">
                        <el-icon>
                            <User />
                        </el-icon>
                        <template #title>用户管理</template>
                    </el-menu-item>

                    <el-sub-menu index="3">
                        <template #title><el-icon>
                                <svg class="icon" height="200" p-id="5383" t="1739167593796" version="1.1"
                                    viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M470.7 641.2m-104.3 0a104.3 104.3 0 1 0 208.6 0 104.3 104.3 0 1 0-208.6 0Z"
                                        fill="#A5E8D0" p-id="5384"></path>
                                    <path :fill="globalSelect === '3'
                                        ? '#FFd04b'
                                        : '#FFF'
                                        " d="M710.5 710m-47.4 0a47.4 47.4 0 1 0 94.8 0 47.4 47.4 0 1 0-94.8 0Z"
                                        p-id="5385"></path>
                                    <path :fill="globalSelect === '3'
                                        ? '#FFd04b'
                                        : '#FFF'
                                        " d="M783.4 615.3m-33.5 0a33.5 33.5 0 1 0 67 0 33.5 33.5 0 1 0-67 0Z"
                                        p-id="5386"></path>
                                    <path :fill="globalSelect === '3'
                                        ? '#FFd04b'
                                        : '#FFF'
                                        "
                                        d="M664.8 375.4H507.6c7-33.9 23.4-78.4 61.6-78.7 80.8-0.7 114.7-64.1 125.5-118 4.3-21.5-12.1-41.5-34-41.3-16.2 0.1-30.4 11.5-33.4 27.4-5 26.5-19 62.7-58.8 63-85.8 0.8-121.1 81.6-131.4 147.6h-58.3C232.2 375.4 113 494.6 113 641.2 113 787.8 232.2 907 378.8 907h285.9c146.6 0 265.8-119.2 265.8-265.8 0.1-146.6-119.1-265.8-265.7-265.8z m0 462.7H378.9c-108.6 0-196.9-88.3-196.9-196.9 0-108.6 88.3-196.9 196.9-196.9h285.9c108.6 0 196.9 88.3 196.9 196.9 0 108.6-88.3 196.9-196.9 196.9z"
                                        p-id="5387"></path>
                                    <path :fill="globalSelect === '3'
                                        ? '#FFd04b'
                                        : '#FFF'
                                        "
                                        d="M358.2 537.5c-57.2 0-103.7 46.5-103.7 103.7S301 744.9 358.2 744.9s103.7-46.5 103.7-103.7-46.5-103.7-103.7-103.7z m0 137.2c-18.5 0-33.5-15-33.5-33.5s15-33.5 33.5-33.5 33.5 15 33.5 33.5-15 33.5-33.5 33.5z"
                                        p-id="5388"></path>
                                </svg>
                            </el-icon>
                            <span> 游戏管理 </span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="3-1">基本信息</el-menu-item>
                            <el-menu-item index="3-2">添加信息</el-menu-item>
                            <el-menu-item index="3-3">修改信息</el-menu-item>
                            <el-menu-item index="3-4">删除信息</el-menu-item>
                        </el-menu-item-group>
                    </el-sub-menu>
                    <el-menu-item index="4">
                        <el-icon>
                            <svg t="1739509828144" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="4377" width="200" height="200">
                                <path
                                    d="M512 972.8C258.048 972.8 51.2 765.952 51.2 512S258.048 51.2 512 51.2s460.8 206.848 460.8 460.8c0 17.408-13.312 30.72-30.72 30.72s-30.72-13.312-30.72-30.72c0-220.16-179.2-399.36-399.36-399.36S112.64 291.84 112.64 512s179.2 399.36 399.36 399.36c17.408 0 30.72 13.312 30.72 30.72s-13.312 30.72-30.72 30.72z"
                                    p-id="4378"></path>
                                <path
                                    d="M512 972.8C258.048 972.8 51.2 765.952 51.2 512S258.048 51.2 512 51.2s460.8 206.848 460.8 460.8c0 83.968-22.528 164.864-65.536 236.544-9.216 14.336-27.648 19.456-41.984 10.24-14.336-9.216-19.456-27.648-10.24-41.984 36.864-61.44 56.32-133.12 56.32-204.8 0-220.16-179.2-399.36-399.36-399.36S112.64 291.84 112.64 512s179.2 399.36 399.36 399.36c91.136 0 177.152-29.696 247.808-87.04 6.144-5.12 13.312-11.264 19.456-16.384 5.12-4.096 9.216-9.216 14.336-13.312 12.288-12.288 31.744-12.288 43.008 0 12.288 12.288 12.288 31.744 0 43.008-5.12 5.12-10.24 10.24-16.384 15.36-7.168 6.144-14.336 13.312-22.528 19.456C716.8 937.984 617.472 972.8 512 972.8z"
                                    p-id="4379"></path>
                                <path
                                    d="M752.64 352.256c-9.216 0-18.432-4.096-24.576-11.264-41.984-53.248-102.4-89.088-169.984-101.376-16.384-3.072-27.648-18.432-24.576-35.84 3.072-16.384 18.432-27.648 35.84-24.576 81.92 14.336 155.648 57.344 206.848 122.88 10.24 13.312 8.192 32.768-5.12 43.008-5.12 5.12-11.264 7.168-18.432 7.168zM381.952 795.648c-23.552 0-47.104-6.144-68.608-18.432-31.744-18.432-55.296-48.128-64.512-83.968-9.216-35.84-5.12-72.704 13.312-104.448 18.432-31.744 48.128-55.296 83.968-64.512l73.728-19.456c35.84-9.216 72.704-5.12 104.448 13.312 31.744 18.432 55.296 48.128 64.512 83.968 9.216 35.84 5.12 72.704-13.312 104.448-18.432 31.744-48.128 55.296-83.968 64.512l-73.728 19.456c-11.264 3.072-23.552 5.12-35.84 5.12z m73.728-234.496c-7.168 0-13.312 1.024-19.456 3.072L362.496 583.68c-19.456 5.12-35.84 18.432-46.08 35.84s-13.312 38.912-7.168 58.368c5.12 19.456 18.432 35.84 35.84 46.08s38.912 13.312 58.368 7.168l73.728-19.456C495.616 706.56 512 693.248 522.24 675.84c10.24-17.408 13.312-38.912 7.168-58.368-5.12-19.456-18.432-35.84-35.84-46.08-11.264-7.168-24.576-10.24-37.888-10.24z"
                                    p-id="4380"></path>
                                <path
                                    d="M563.2 665.6c-17.408 0-30.72-13.312-30.72-30.72V215.04c0-17.408 13.312-30.72 30.72-30.72s30.72 13.312 30.72 30.72v419.84c0 17.408-13.312 30.72-30.72 30.72z"
                                    p-id="4381"></path>
                            </svg>
                        </el-icon>
                        <template #title>音乐管理</template>
                    </el-menu-item>
                    <el-menu-item index="5">
                        <el-icon>
                            <svg t="1739682839047" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="7680" width="200" height="200">
                                <path
                                    d="M512 85.333333c235.637333 0 426.666667 191.029333 426.666667 426.666667a432.554667 432.554667 0 0 1-4.490667 62.112 423.36 423.36 0 0 1-8.789333 43.914667l-0.458667 1.749333-0.330667 1.301333-1.418666 5.216a423.210667 423.210667 0 0 1-9.365334 29.546667l-1.066666 2.944a426.698667 426.698667 0 0 1-94.357334 150.154667c22.986667 3.082667 43.818667 10.837333 62.4 23.466666a32 32 0 1 1-35.957333 52.938667c-30.485333-20.714667-77.781333-18.965333-144.053333 9.386667a343.68 343.68 0 0 1-5.706667 2.773333l-1.834667 0.853333c-1.568 0.746667-3.136 1.472-4.704 2.186667l-0.874666 0.394667c-1.834667 0.832-3.68 1.653333-5.525334 2.453333l-0.778666 0.330667c-1.706667 0.746667-3.413333 1.472-5.12 2.197333-0.533333 0.202667-1.034667 0.416-1.546667 0.629333-3.658667 1.514667-7.349333 2.986667-11.061333 4.394667l-2.250667 0.832a278.826667 278.826667 0 0 1-3.914667 1.450667l-2.453333 0.874666c-1.6 0.576-3.232 1.152-4.853333 1.706667a322.752 322.752 0 0 1-6.08 2.026667l-1.941334 0.618666c-1.781333 0.576-3.573333 1.130667-5.365333 1.685334l-0.693333 0.202666c-1.717333 0.533333-3.445333 1.034667-5.173334 1.536l-2.101333 0.597334c-1.248 0.362667-2.485333 0.704-3.733333 1.045333l-3.082667 0.832c-1.173333 0.32-2.346667 0.629333-3.530667 0.928l-2.922666 0.736c-1.706667 0.426667-3.434667 0.853333-5.162667 1.258667l-1.173333 0.266666c-10.346667 2.410667-20.821333 4.448-31.424 6.08l-2.592 0.394667-0.746667 0.106667A426.250667 426.250667 0 0 1 512 938.666667C276.362667 938.666667 85.333333 747.637333 85.333333 512S276.362667 85.333333 512 85.333333z m0 522.666667a64 64 0 1 0 0 128 64 64 0 0 0 0-128z m160-160a64 64 0 1 0 0 128 64 64 0 0 0 0-128z m-320 0a64 64 0 1 0 0 128 64 64 0 0 0 0-128z m160-160a64 64 0 1 0 0 128 64 64 0 0 0 0-128z"
                                    fill="#FFF" p-id="7681"></path>
                            </svg>
                        </el-icon>
                        <template #title>电影管理</template>
                    </el-menu-item>
                    <el-menu-item index="6">
                        <el-icon>
                            <svg t="1740034911641" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="5651" width="200" height="200">
                                <path
                                    d="M512 4.836531c280.097364 0 507.163469 227.066105 507.163469 507.173503 0 280.08733-227.066105 507.153435-507.163469 507.153435S4.836531 792.097364 4.836531 512.010034C4.836531 231.902636 231.902636 4.836531 512 4.836531zM209.86732 264.042489c-10.234983 0-18.543381 8.288329-18.543381 18.503244l0 440.304949c0 5.2078 2.227614 10.245017 6.110887 13.746987 3.863204 3.451798 8.56929 5.217834 14.33901 4.665948 0.371269-0.040137 41.040274-4.13413 94.282254-4.13413 54.476198 0 130.917472 4.395022 185.664596 25.206154 2.107202 0.802744 4.344851 1.21415 6.56243 1.21415 2.960118 0 5.699481-0.903087 8.248192-2.197511 5.41852 2.117237 10.827006 4.314748 16.27563 6.682842 1.996825 0.903087 4.114062 1.35463 6.311573 1.35463 2.759432 0 5.408486-0.963293 7.836786-2.348025 2.448368 1.414836 5.097423 2.348025 7.856854 2.348025 2.177442 0 4.294679-0.451543 6.291504-1.334561 5.428555-2.388163 10.897246-4.585674 16.305732-6.70291 2.528643 1.294424 5.288074 2.197511 8.228123 2.197511 2.277785 0 4.445194-0.411406 6.582499-1.21415 54.73709-20.8312 131.168329-25.206154 185.654561-25.206154 53.252014 0 93.910985 4.093993 94.34246 4.13413 5.308143 0.471612 10.425634-1.21415 14.288839-4.665948 3.883273-3.512004 6.110887-8.549221 6.110887-13.746987l0-440.304949c0-10.214914-8.328466-18.523312-18.543381-18.523312s-18.523312 8.308398-18.523312 18.523312l0 420.176149c-12.502734-0.923155-29.04929-1.846311-48.254934-2.348025L797.29513 258.062048c0-9.522548-6.502224-17.459677-15.191926-18.422969-13.736952-1.505145-27.774934-2.297854-41.682469-2.297854-113.829064 0-193.792376 50.372171-203.505576 56.814189-9.723234-6.452053-89.686546-56.814189-203.485507-56.814189-13.937638 0-27.97562 0.782675-41.702538 2.297854-8.63953 0.963293-15.171857 8.900421-15.171857 18.422969l0 442.301774c-19.215679 0.531818-35.7522 1.454973-48.295071 2.348025L228.260186 282.545732C228.350495 272.330818 220.042097 264.042489 209.86732 264.042489L209.86732 264.042489zM561.74001 321.46877c21.955042-12.663283 89.937403-47.070887 178.740931-47.070887 7.70634 0 15.462852 0.250857 23.108986 0.762607l0 419.413542c-8.92049-0.551886-18.282489-0.822812-27.945517-0.822812-42.414973 0-106.473925 5.368349-173.894366 30.514297L561.750044 321.46877 561.74001 321.46877zM310.380872 275.150456c7.646134-0.511749 15.382577-0.762607 23.108986-0.762607 88.823596 0 156.785889 34.437707 178.761 47.070887l0 402.796747c-67.420441-25.145948-131.499461-30.514297-173.914434-30.514297-9.663028 0-19.045096 0.270926-27.935483 0.822812L310.400941 275.150456 310.380872 275.150456 310.380872 275.150456z"
                                    fill="#FFF" p-id="5652"></path>
                            </svg>
                        </el-icon>
                        <template #title>图书管理</template>
                    </el-menu-item>
                </el-menu>
            </el-aside>

            <el-container>
                <el-header style="
                        font-size: 12px;
                        border-bottom: 1px solid #ccc;
                        line-height: 60px;
                        display: flex;
                        background: #fff;
                    ">
                    <div style="flex: 1; font-size: 20px">
                        <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
                        <el-button v-if="!isCollapse" :icon="Fold" plain round style="transform: scale(0.8)" type="info"
                            @click="collapse">
                        </el-button>
                        <span class="tag">{{ tag }}</span>
                    </div>
                    <el-dropdown style="width: 70px; cursor: pointer">
                        <el-button :icon="User" circle style="margin-top: 10px" />

                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item @click="toMyInfo()">个人信息</el-dropdown-item>
                                <el-dropdown-item @click="toMyInfo()">修改密码</el-dropdown-item>
                                <el-dropdown-item @click="logout()">退出登录</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </el-header>

                <el-main style="background: #edeef0">
                    <HomeIndex v-if="globalSelect.startsWith('1')" />
                    <AccountIndex v-if="globalSelect.startsWith('2')" />
                    <GamesIndex v-if="globalSelect.startsWith('3')" />
                    <MusicIndex v-if="globalSelect.startsWith('4')" />
                    <VideoIndex v-if="globalSelect.startsWith('5')" />
                    <BookIndex v-if="globalSelect.startsWith('6')" />
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<style scoped>
.logo-text {
    color: #fff;
    /* 字体颜色 */
    font-size: 16px;
    /* 字体大小 */
    font-weight: bold;
    /* 字体加粗 */
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.6);
    /* 加上阴影效果 */
    margin-left: 10px;
    /* 左侧边距 */
    font-family: "yousu-title-black", serif;
    /* 字体样式，可以替换为其他字体 */
}

.index-class {
    font-family:
        system-ui,
        -apple-system,
        BlinkMacSystemFont,
        "Segoe UI",
        Roboto,
        Oxygen,
        Ubuntu,
        Cantarell,
        "Open Sans",
        "Helvetica Neue",
        sans-serif;
    font-size: 22px;
    font-weight: 900;
    background: #0c0837;
}

.tag {
    font-size: 11px;
    color: #8a8a8a;
    margin-top: -15px;
    margin-left: 10px;
}
</style>
