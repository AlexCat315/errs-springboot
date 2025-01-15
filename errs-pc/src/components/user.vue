<script setup lang="js" >
import { inject, ref } from "vue";
const globalTheme = inject("globalTheme");
const selectTheme = ref(globalTheme.value);
const isLogin = false;
const globalShowSetting = inject("globalShowSetting");

const loginClick = () => {
    globalShowSetting.value = false;

}


</script>
<template>
    <!-- 已登录 -->
    <button v-if="isLogin" id="btn-message" style="--online-status: #93e200;"
        :style="{ backgroundColor: selectTheme === 'dark' ? '#212121' : '#f5f5f5' }" class="button-message">
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
                <div :style="{ 'color': selectTheme === 'dark' ? '#fff' : '#212121' }" class="lable-message">
                    Message<span class="number-message">3</span></div>
            </div>
        </div>
    </button>
    <!-- 未登录 -->
    <button @click="loginClick()" v-if="!isLogin" id="btn-message" :style="{ '--bg-color': selectTheme === 'dark' ? '#212121' : '#f5f5f5' }"
        style="--online-status: red;" class="button-message">
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
        <div :style="{ 'color': selectTheme === 'dark' ? '#fff' : '#212121' }" class="notice-content">
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
</style>