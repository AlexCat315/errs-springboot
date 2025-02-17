<script setup lang="js">
import { inject, ref } from "vue";
import { validate_token } from "../net/account/validate_token";
import { logout_user } from "../net/account/logout";

const globalTheme = inject("globalTheme");
const isLogin = ref(false);
const globalShowSetting = inject("globalShowSetting");

const loginClick = () => {
    globalShowSetting.value = false;
}


const token = ref(localStorage.getItem('token'));
if (token.value !== null && token.value !== '' && token.value !== undefined) {
    validate_token(token.value, (data) => {
        if (data.code === 200) {
            isLogin.value = true;
        }
        console.log("success:", data);
    }, (message) => {
        localStorage.removeItem('token');
        console.log("failure:", message);
    }, (message) => {
        console.log("error:", message);
    })
}
const fetch = ref(true);
// 退出
const logoutClick = () => {
    logout_user(() => {
        localStorage.removeItem('token');
        isLogin.value = false;
        fetch.value = false;
        setTimeout(() => {
            fetch.value = true;
        }, 10);
    }, () => {
        console.log("logout failure")
    })
}


</script>
<template>
    <!-- 下拉列表卡片 -->
    <div v-if="fetch" class="dropdown">
        <!-- 已登录 -->
        <button class="dropdown-btn button-message" v-if="isLogin" id="btn-message" style="--online-status: #93e200;"
            :style="{ backgroundColor: globalTheme === 'dark' ? '#212121' : '#f5f5f5' }">
            <div class="content-avatar">
                <div class="status-user"></div>
                <div class="avatar">
                    <svg class="user-img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                        <path
                            d="M12,12.5c-3.04,0-5.5,1.73-5.5,3.5s2.46,3.5,5.5,3.5,5.5-1.73,5.5-3.5-2.46-3.5-5.5-3.5Zm0-.5c1.66,0,3-1.34,3-3s-1.34-3-3-3-3,1.34-3,3,1.34,3,3,3Z">
                        </path>
                    </svg>
                </div>
            </div>
            <div class="notice-content">
                <div>
                    <div :style="{ 'color': globalTheme === 'dark' ? '#fff' : '#212121' }" class="lable-message">
                        用户中心<span class="number-message">3</span></div>
                </div>
            </div>
        </button>

        <div class="dropdown-content">
            <a style="cursor: pointer;font-family: Arial, Helvetica, sans-serif;">用户中心</a>
            <a @click="logoutClick()" style="cursor: pointer; font-family: Arial, Helvetica, sans-serif;">退出登录</a>
        </div>
    </div>
    <!-- 未登录 -->
    <button @click="loginClick()" v-if="!isLogin" id="btn-message"
        :style="{ '--bg-color': globalTheme === 'dark' ? '#212121' : '#f5f5f5' }" style="--online-status: red;"
        class="button-message">
        <div class="content-avatar">
            <div class="status-user"></div>
            <div class="avatar">
                <svg class="user-img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path
                        d="M12,12.5c-3.04,0-5.5,1.73-5.5,3.5s2.46,3.5,5.5,3.5,5.5-1.73,5.5-3.5-2.46-3.5-5.5-3.5Zm0-.5c1.66,0,3-1.34,3-3s-1.34-3-3-3-3,1.34-3,3,1.34,3,3,3Z">
                    </path>
                </svg>
            </div>
        </div>
        <div :style="{ 'color': globalTheme === 'dark' ? '#fff' : '#212121' }" class="notice-content">
            <div v-if="!isLogin">
                <div class="lable-message">未登录</div>

            </div>

        </div>
    </button>

</template>

<style lang="css" scoped>
#btn-message {
    --text-color: rgb(255, 255, 255);
    --bg-color-sup: #5e5e5e;
    --bg-color: rgb(255, 255, 255);
    --bg-hover-color: #161616;
    --font-size: 16px;
}

.button-message {
    display: flex;
    justify-content: center;
    align-items: center;
    font: 400 var(--font-size) Helvetica Neue, sans-serif;
    background-color: var(--bg-color);
    border-radius: 68px;
    cursor: pointer;
    padding: 6px 10px 6px 6px;
    width: fit-content;
    height: 40px;
    border: 0;
    overflow: hidden;
    position: relative;
}



.content-avatar {
    width: 30px;
    height: 30px;
    margin: 0;
    position: relative;
}

.avatar {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    overflow: hidden;
    background-color: var(--bg-color-sup);
}

.user-img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.status-user {
    position: absolute;
    width: 6px;
    height: 6px;
    right: 1px;
    bottom: 1px;
    border-radius: 50%;
    outline: solid 2px var(--bg-color);
    background-color: var(--online-status);
}

/* Removed scaling on hover */
.button-message:hover .status-user {
    right: 1px;
    bottom: 1px;
    outline: solid 3px var(--bg-hover-color);
}

.notice-content {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: center;
    padding-left: 8px;
    text-align: initial;
    color: var(--text-color);
}

.username {
    letter-spacing: -6px;
    height: 0;
    opacity: 0;
}

.user-id {
    font-size: 12px;
    letter-spacing: -6px;
    height: 0;
    opacity: 0;
}

.lable-message {
    display: flex;
    align-items: center;
    opacity: 1;
}

.button-message:hover .username {
    height: auto;
    letter-spacing: normal;
    opacity: 1;
}

.button-message:hover .user-id {
    height: auto;
    letter-spacing: normal;
    opacity: 1;
}



.lable-message,
.username {
    font-weight: 600;
}

.number-message {
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    margin-left: 8px;
    font-size: 12px;
    width: 16px;
    height: 16px;
    background-color: var(--bg-color-sup);
    border-radius: 20px;
}

.dropdown {
    position: relative;
    display: inline-block;
}

.dropdown-btn {
    background-color: #4CAF50;
    color: white;
    padding: 12px;
    font-size: 16px;
    border: none;
    cursor: pointer;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f9f9f9;
    min-width: 160px;
    /* box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2); */
    z-index: 1;
    border-radius: 10px;
}

.dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
    padding-left: 50px;
    border-radius: 10px;
}

/* 鼠标悬停时改变背景颜色 */
.dropdown-content a:hover {
    background-color: #ccc;
}

/* 鼠标悬停时显示下拉菜单 */
.dropdown:hover .dropdown-content {
    display: block;
}

/* 鼠标悬停时改变按钮背景色 */
.dropdown:hover .dropdown-btn {
    background-color: #3e8e41;
}
</style>
