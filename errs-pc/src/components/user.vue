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
                    <svg t="1739788302798" class="icon" viewBox="0 0 1024 1024" version="1.1"
                        xmlns="http://www.w3.org/2000/svg" p-id="5590" width="30" height="30">
                        <path
                            d="M343.334 440.538h67.334v-240c0-22.092-17.908-40-40-40h-27.334c-22.092 0-40 17.908-40 40v200c0 22.092 17.908 40 40 40zM680.668 160.64h-27.334c-22.09 0-40 17.91-40 40v34.828c43.288 32.2 71.334 83.742 71.334 141.838v63.13c20.212-2.006 36-19.056 36-39.798v-200c0-22.09-17.91-39.998-40-39.998z"
                            fill="#8C4C17" p-id="5591"></path>
                        <path d="M688.666 392.488m-57.14 0a57.14 57.14 0 1 0 114.28 0 57.14 57.14 0 1 0-114.28 0Z"
                            fill="#FFEACF" p-id="5592"></path>
                        <path d="M328.666 392.488m-57.14 0a57.14 57.14 0 1 0 114.28 0 57.14 57.14 0 1 0-114.28 0Z"
                            fill="#FFD59A" p-id="5593"></path>
                        <path
                            d="M824.666 937.974H199.334v-83.668c0-104.934 85.066-190 190-190h245.334c104.934 0 190 85.066 190 190v83.668z"
                            fill="#FFC34C" p-id="5594"></path>
                        <path
                            d="M327.334 894.306c0-104.934 85.066-190 190-190h233.954c-32.192-25.064-72.66-40-116.62-40H389.334c-104.934 0-190 85.066-190 190v83.668h128v-43.668z"
                            fill="#FFB74B" p-id="5595"></path>
                        <path
                            d="M512 779.306c-47.864 0-86.666-38.802-86.666-86.666V485.974h173.334V692.64c-0.002 47.864-38.804 86.666-86.668 86.666z"
                            fill="#FFEACF" p-id="5596"></path>
                        <path
                            d="M650.272 861.922c-19.45-3.116-32.692-21.408-29.578-40.858l27.252-170.164a12 12 0 0 1 13.746-9.952l46.738 7.486a12 12 0 0 1 9.952 13.746l-27.252 170.164c-3.116 19.452-21.408 32.694-40.858 29.578zM374.504 861.922c19.45-3.116 32.692-21.408 29.578-40.858l-27.252-170.164a12 12 0 0 0-13.746-9.952l-46.738 7.486a12 12 0 0 0-9.952 13.746l27.252 170.164c3.114 19.452 21.408 32.694 40.858 29.578z"
                            fill="#CCC033" p-id="5597"></path>
                        <path
                            d="M369.646 832.344l-27.252-170.166a12 12 0 0 1 9.952-13.746l21.706-3.476a11.974 11.974 0 0 0-10.966-4.008l-46.738 7.486a12 12 0 0 0-9.952 13.746l27.252 170.166c3.114 19.45 21.408 32.692 40.858 29.578a35.538 35.538 0 0 0 12.334-4.442c-8.876-5.206-15.442-14.188-17.194-25.138z"
                            fill="#99AF17" p-id="5598"></path>
                        <path
                            d="M736.5 937.974h-449v-86.666c0-22.092 17.908-40 40-40h369c22.092 0 40 17.908 40 40v86.666z"
                            fill="#CCC033" p-id="5599"></path>
                        <path
                            d="M407.5 811.306h-80c-22.092 0-40 17.908-40 40v86.666h80v-86.666c0-22.092 17.908-40 40-40z"
                            fill="#99AF17" p-id="5600"></path>
                        <path
                            d="M485.334 692.64V485.974h-60V692.64c0 47.866 38.802 86.666 86.668 86.666 10.546 0 20.652-1.888 30-5.338-33.08-12.206-56.668-44.01-56.668-81.328z"
                            fill="#FFD59A" p-id="5601"></path>
                        <path
                            d="M512 595.912c-97.57 0-176.666-79.096-176.666-176.666v-89.94c0-97.57 79.096-176.666 176.666-176.666s176.666 79.096 176.666 176.666v89.94c0 97.57-79.096 176.666-176.666 176.666z"
                            fill="#FFEACF" p-id="5602"></path>
                        <path
                            d="M397.334 397.004v-87.696c0-6.69 0.374-13.292 1.102-19.786 1.126-10.056 9.746-17.6 19.864-17.6h260.83C655.304 202.522 589.482 152.64 512 152.64c-7.584 0-15.052 0.494-22.386 1.42l0.034-0.018c-0.566 0.072-1.12 0.176-1.684 0.252-1.862 0.252-3.72 0.522-5.562 0.834-0.894 0.15-1.78 0.322-2.668 0.486-1.632 0.3-3.258 0.618-4.874 0.962a174.642 174.642 0 0 0-7.494 1.782c-0.912 0.238-1.824 0.474-2.73 0.726-1.614 0.448-3.212 0.928-4.806 1.42-0.812 0.25-1.63 0.49-2.438 0.752-1.91 0.622-3.8 1.282-5.682 1.964-0.464 0.168-0.938 0.32-1.4 0.494a175.272 175.272 0 0 0-6.944 2.76c-0.596 0.252-1.182 0.528-1.776 0.786a177.92 177.92 0 0 0-4.996 2.252c-0.826 0.39-1.638 0.8-2.458 1.204a155.316 155.316 0 0 0-6.786 3.52c-1.31 0.716-2.61 1.45-3.902 2.2-0.866 0.502-1.728 1.006-2.584 1.522-1.294 0.782-2.574 1.586-3.848 2.4-0.804 0.514-1.614 1.022-2.41 1.548-1.404 0.928-2.782 1.89-4.156 2.856-0.632 0.446-1.276 0.874-1.902 1.326a176.248 176.248 0 0 0-5.746 4.336c-0.586 0.462-1.152 0.946-1.732 1.416a182.766 182.766 0 0 0-3.908 3.228c-0.766 0.656-1.514 1.334-2.268 2.002-1.07 0.948-2.136 1.9-3.182 2.874-0.792 0.736-1.57 1.486-2.348 2.236a171.388 171.388 0 0 0-7.99 8.226c-0.736 0.808-1.474 1.61-2.194 2.432-0.94 1.072-1.854 2.166-2.77 3.26-0.652 0.78-1.316 1.548-1.956 2.338a181.46 181.46 0 0 0-3.924 5.056c-0.666 0.886-1.304 1.792-1.952 2.69-0.896 1.244-1.792 2.484-2.656 3.75-0.632 0.928-1.238 1.876-1.854 2.818-0.72 1.1-1.438 2.202-2.134 3.32a171.4 171.4 0 0 0-1.832 3.028 174.06 174.06 0 0 0-3.656 6.434 158.144 158.144 0 0 0-1.762 3.368c-0.546 1.064-1.092 2.126-1.616 3.204-0.57 1.17-1.112 2.356-1.656 3.542-0.484 1.054-0.982 2.1-1.446 3.166-0.626 1.436-1.212 2.894-1.8 4.35-0.64 1.586-1.258 3.182-1.854 4.792-0.488 1.318-0.99 2.628-1.448 3.96h0.014a176.398 176.398 0 0 0-9.552 57.386v87.806c0 97.194 77.608 177.786 174.796 178.792 31.246 0.324 60.646-7.468 86.224-21.394-7.714 0.974-15.58 1.454-23.57 1.4-97.456-0.654-175.442-81.436-175.442-178.9z"
                            fill="#FFD59A" p-id="5603"></path>
                        <path
                            d="M680.668 245.92h-345.28c-38.66 0-70-31.34-70-70V108.026c0-12.15 9.85-22 22-22h307.28c47.496 0 86 38.504 86 86v73.894z"
                            fill="#A56021" p-id="5604"></path>
                        <path
                            d="M420.236 205.92c-39.91 0-72.262-31.34-72.262-70V86.026H288.1c-12.544 0-22.71 9.85-22.71 22V175.92c0 38.66 32.352 70 72.262 70h343.018v-40H420.236z"
                            fill="#8C4C17" p-id="5605"></path>
                        <path
                            d="M647.326 189.43a12 12 0 0 1-11.982-11.534c-0.638-16.376-5.812-28.938-15.38-37.338-17.386-15.27-44.61-13.272-44.876-13.24a11.972 11.972 0 0 1-13.04-10.864 12.002 12.002 0 0 1 10.864-13.04c1.526-0.14 37.558-3.132 62.884 19.108 14.708 12.914 22.624 31.23 23.528 54.44a11.998 11.998 0 0 1-11.998 12.468z"
                            fill="#BF6C0D" p-id="5606"></path>
                        <path d="M688.666 927.306a12 12 0 0 1-12-12v-68a12 12 0 0 1 24 0v68a12 12 0 0 1-12 12z"
                            fill="#EACF32" p-id="5607"></path>
                        <path
                            d="M799.348 919.306a12 12 0 0 1-11.956-13.182c10.866-109.858-35.58-135.156-36.05-135.398-5.984-2.846-8.444-9.964-5.598-15.95 2.848-5.982 10.092-8.488 16.076-5.64 2.542 1.208 62.14 31.098 49.456 159.352a12 12 0 0 1-11.928 10.818z"
                            fill="#FFD551" p-id="5608"></path>
                        <path d="M425.334 862.376m-30.668 0a30.668 30.668 0 1 0 61.336 0 30.668 30.668 0 1 0-61.336 0Z"
                            fill="#99AF17" p-id="5609"></path>
                        <path d="M405.334 145.842m-30.668 0a30.668 30.668 0 1 0 61.336 0 30.668 30.668 0 1 0-61.336 0Z"
                            fill="#8C4C17" p-id="5610"></path>
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
            <!-- <a style="cursor: pointer;font-family: Arial, Helvetica, sans-serif;">用户中心</a> -->
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
