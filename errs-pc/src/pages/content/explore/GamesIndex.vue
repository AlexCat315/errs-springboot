<script lang="ts" setup>
import {inject} from "vue";
import ViewAll from "./components/games/ViewAll.vue";

const globalTheme = inject<string>("globalTheme");

if (globalTheme === undefined) {
    throw new Error("Function not implemented.");
}
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
                <div style="display: flex">
                    <p class="rank-index">{{ index }}</p>
                    <img
                        alt="cover"
                        class="games-img"
                        src="../../../assets/img//games/appicon_m.png"
                    />
                    <div>
                        <!-- 名字 -->
                        <div class="text-with-tags app-title">
                            <span class="text text-default--size"
                                >音乐世界 Cytus II</span
                            >
                        </div>
                        <!-- 分数-类别 -->
                        <div class="flex-center--y">
                            <div
                                class="tap-rating tap-rating-medium game-cell__rating"
                                style="margin-right: 10px"
                                type="app"
                            >
                                <svg
                                    class="svg-icon tap-rating__icon"
                                    viewBox="0 0 25 24"
                                >
                                    <path
                                        d="M16.278 8.22c-.938-.019-1.407-.028-1.811-.186a2.43 2.43 0 0 1-.931-.648c-.289-.323-.46-.76-.804-1.633l-.346-.88c-.42-1.067-.629-1.6-.944-1.774a.997.997 0 0 0-.89-.036c-.328.148-.58.663-1.083 1.693L8.414 6.914c-.319.652-.478.978-.71 1.234a2.431 2.431 0 0 1-.728.548c-.31.152-.668.215-1.383.341l-1.95.343c-1.15.203-1.724.304-1.957.582a.997.997 0 0 0-.206.87c.083.353.551.702 1.487 1.398l.96.715c.796.592 1.194.889 1.436 1.274.213.34.34.728.367 1.129.03.454-.117.927-.412 1.875l-.104.332c-.362 1.161-.542 1.742-.397 2.08a.997.997 0 0 0 .699.579c.36.08.896-.205 1.97-.776l1.228-.653c.715-.381 1.073-.572 1.449-.639.333-.06.674-.049 1.002.03.371.09.717.303 1.408.726l2.07 1.268c1.031.632 1.547.948 1.91.89a.997.997 0 0 0 .733-.532c.167-.329.024-.917-.26-2.093l-.457-1.888c-.212-.877-.318-1.315-.275-1.732.039-.368.161-.723.358-1.037.222-.356.576-.636 1.282-1.196l1.855-1.469c1.072-.849 1.607-1.273 1.67-1.652a.996.996 0 0 0-.3-.89c-.28-.263-.962-.276-2.329-.303l-2.552-.049Z"
                                    ></path>
                                </svg>
                                <div
                                    class="tap-rating__number rate-number-font"
                                >
                                    7.6
                                </div>
                            </div>
                            <!---->
                            <div
                                class="tap-label-tag-group flex tap-ellipsis group--adjust game-cell__tags"
                            >
                                <a
                                    class="tap-router tap-router__prefetched tap-label-tag tap-label-tag--small text-hover tap-label-tag--left-point"
                                    itemprop="genre"
                                    >单机</a
                                ><a
                                    class="tap-router tap-router__prefetched tap-label-tag tap-label-tag--small text-hover tap-label-tag--left-point"
                                    itemprop="genre"
                                    >休闲</a
                                ><a
                                    class="tap-router tap-router__prefetched tap-label-tag tap-label-tag--small text-hover tap-label-tag--left-point"
                                    itemprop="genre"
                                    >卡通</a
                                >
                            </div>
                        </div>
                        <!-- 开发商 -->
                        <div>
                           <span> 
                             
                           </span>
                        </div>
                    </div>
                </div>
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
    margin-top: -60px;
    /* 缩放 */
    transform: scale(0.77);
    font-size: 15px;
    margin-right: 20px;
}
.gard {
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
    cursor: pointer;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    width: 100%;
}
.rank-index {
    font-family:
        rate-number-font,
        -apple-system,
        BlinkMacSystemFont,
        Segoe UI,
        Helvetica,
        Arial,
        PingFang SC,
        Noto Sans,
        Roboto,
        Microsoft Yahei,
        sans-serif,
        Apple Color Emoji,
        Segoe UI Emoji;
    -webkit-font-smoothing: antialiased;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    text-align: center;
    font-size: 20px;
    font-weight: 900;
    width: 40px;
    flex-shrink: 0;
    padding-right: 4px;
    padding-top: 30px;
}

.games-img {
    object-fit: cover;
    display: block;
    max-width: 100%;
    width: 100px;
    height: 100px;
    font-size: 0;
    box-shadow: 0;
    border-radius: 22.78%;
    margin-top: 15px;
}
.text-default--size {
    font-size: 14px;
    line-height: 18px;
}
.text {
    display: inline;
    overflow: hidden;
    vertical-align: middle;
    margin-right: 4px;
    font-weight: 700;
}
.text-with-tags {
    position: relative;
    display: block;
}
.app-title {
    margin-top: 19px;
    margin-left: 13px;
    font-family:
        -apple-system,
        BlinkMacSystemFont,
        Segoe UI,
        Helvetica,
        Arial,
        PingFang SC,
        Noto Sans,
        Roboto,
        Microsoft Yahei,
        sans-serif,
        Apple Color Emoji,
        Segoe UI Emoji;
}
.svg-icon {
    fill: currentColor;
    width: 1em;
    height: 1em;
    vertical-align: top;
    margin-left: 14px;
}
.flex-center,
.flex-center--y {
    display: -webkit-box;
    display: flex;
    -webkit-box-align: center;
    align-items: center;
    margin-top: 6px;
}
.tap-rating {
    display: -webkit-box;
    display: flex;
    -webkit-box-orient: horizontal;
    -webkit-box-direction: normal;
    flex-direction: row;
    -webkit-box-pack: start;
    justify-content: flex-start;
    -webkit-box-align: center;
    align-items: center;
    color: rgb(0, 205, 186);
}
a,
button,
div,
input {
    outline: none;
    -webkit-tap-highlight-color: transparent;
}
.rate-number-font {
    font-family:
        rate-number-font,
        -apple-system,
        BlinkMacSystemFont,
        Segoe UI,
        Helvetica,
        Arial,
        PingFang SC,
        Noto Sans,
        Roboto,
        Microsoft Yahei,
        sans-serif,
        Apple Color Emoji,
        Segoe UI Emoji;
}
.tap-label-tag-group {
    flex-wrap: nowrap;
    max-height: calc(18px);
    max-width: 100%;
}
.tap-ellipsis {
    text-overflow: ellipsis;
    white-space: nowrap;
}
.tap-label-tag {
    position: relative;
    color: #93999e;
    display: inline-block;
    font-size: 12px;
    text-decoration: none;
    margin-top: -15px;
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
}
.game-cell__tags a:not(:last-child)::after {
    content: "·";
    margin: 0 0.5em;
    color: #999;
    display: inline-block;
}
</style>
