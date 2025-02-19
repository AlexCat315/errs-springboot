<script lang="ts" setup>
import { ref } from "vue";
import { postFormData, defaultFailure } from "../../../net/post"; // 导入封装的方法
import { ElMessage, type DropdownInstance, type TagProps } from "element-plus";
import Search from "./components/Seach.vue";
import Cancel from "./components/update/Cancel.vue";
import SearchGame from "./components/update/SeachGame.vue";
import GameCard from "./components/update/GameCard.vue";
import GameCardSmart from "./components/update/GameCardSmart.vue";
import CancelUpdatePanel from "./components/update/CancelUpdatePanel.vue";
import { watch } from "vue";
import { get_game_info_by_id } from "../../../net/game/get";
const gameID = ref<number>(0);
// 表单数据
const gameName = ref(""); // 游戏名
const gameScore = ref<number | null>(null); // 分数
const gameDeveloper = ref(""); // 开发商
const releaseDate = ref(""); // 游戏发行时间
const gameDescription = ref(""); // 游戏简介
const gameCover = ref<string | null>(null); // 游戏封面
const selectGameCategories = ref([]); // 游戏类别
const selectFormOptions = ref([]); // 游戏平台

const buttonLoadingState = ref(false); // 初始值

// 提交表单
const handleSubmit = async () => {
    buttonLoadingState.value = true;
    // 创建 FormData 对象
    const formData = new FormData();

    // 添加文本数据
    formData.append("gameId", gameID.value.toString());
    formData.append("gameName", gameName.value);
    formData.append("gameScore", gameScore.value?.toString() || "");
    formData.append("gameDeveloper", gameDeveloper.value);
    formData.append("releaseDate", releaseDate.value);
    formData.append("gameDescription", gameDescription.value);
    formData.append(
        "gameCategories",
        JSON.stringify(selectGameCategories.value),
    ); // 数组需要序列化
    formData.append("gamePlatforms", JSON.stringify(selectFormOptions.value)); // 数组需要序列化

    // 添加图片文件
    if (gameCover.value && gameCover.value.startsWith("data:image")) {
        const file = dataURLtoFile(gameCover.value, "gameCover.png"); // 将 base64 转为文件
        formData.append("file", file); // 文件字段名需要与后端一致
    }

    // 使用封装的 postFormData 方法发送请求
    postFormData(
        "/api/admin/game/update/info", // 请求地址
        formData, // FormData 数据
        (response: any) => {
            ElMessage.success("数据更新成功！");
            // 删除表单数据
            clearFrom();
            buttonLoadingState.value = false;
        },
        (message: string, code: number, url: string) => {
            defaultFailure(message, code, url); // 使用默认的失败处理逻辑
            buttonLoadingState.value = false;
        },
    );
};


watch(
    () => gameID.value,
    async (newValue) => {
        if (newValue !== undefined && newValue !== null && newValue !== 0) {
            try {
                // 获取游戏数据
                await get_game_info_by_id(
                    newValue,
                    (data: any) => {
                        // 赋值后台返回的数据
                        gameName.value = data.gameName;
                        gameScore.value = data.gameScore;
                        gameDeveloper.value = data.gameDeveloper;
                        releaseDate.value = data.releaseDate;
                        gameDescription.value = data.gameDescription;
                        gameCover.value = data.gameImageUrl;
                        selectGameCategories.value = data.gameCategories;
                        selectFormOptions.value = data.gamePlatforms;
                    },
                    (message: string) => {
                        console.log(message);
                        ElMessage.warning("发生错误");
                    },
                );
            } catch (error) {
                console.error("获取游戏信息失败", error);
                ElMessage.warning("游戏信息加载失败");
            }
        }
    },
    { immediate: true }, // 立即执行一次
);

const clearFrom = () => {
    gameName.value = "";
    gameScore.value = null;
    gameDeveloper.value = "";
    releaseDate.value = "";
    gameDescription.value = "";
    gameCover.value = "";
    selectGameCategories.value = [];
    selectFormOptions.value = [];
};

// 处理文件选择
const handleFileChange = (event: Event) => {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files[0]) {
        const file = fileInput.files[0];
        const reader = new FileReader();
        reader.onload = () => {
            gameCover.value = reader.result as string; // 存储为 base64 用于预览
        };
        reader.readAsDataURL(file);
    }
};

// 将 base64 转为 File 对象
const dataURLtoFile = (dataURL: string, filename: string): File => {
    const arr = dataURL.split(",");
    const mime = arr[0].match(/:(.*?);/)![1];
    const bstr = atob(arr[1]);
    let n = bstr.length;
    const u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, { type: mime });
};

//预定义选项数据
const gameCategories = ref([
    "单机",
    "动作",
    "冒险",
    "角色扮演",
    "策略",
    "体育",
    "模拟",
    "益智",
    "射击",
    "格斗",
    "开放世界",
    "赛博朋克",
    "科幻",
    "二次元",
]);

const platformOptions = ref([
    "PC",
    "PlayStation 5",
    "Xbox Series X/S",
    "Nintendo Switch",
    "iOS",
    "Android",
    "VR",
]);

const selectGameCategoriestDropdownMenu = (select: string) => {
    // 添加进 selectGameCategories.value
    selectGameCategories.value.push(select);
};
const selectPlatformOptions = (select: string) => {
    // 添加进 selectGameCategories.value
    selectFormOptions.value.push(select);
};

const type: Array<TagProps["type"]> = [
    "primary",
    "success",
    "info",
    "warning",
    "danger",
];

const tagType = () => {
    // 随机返回一个 type
    const randomIndex = Math.floor(Math.random() * type.length);
    return type[randomIndex];
};

const dropdown1 = ref<DropdownInstance>();

const showUpdatePanel = ref(false);
const showSeach = ref(false);

const seachValue = ref("");

const handleGameCardUpdate = (value: number) => {
    if (value !== undefined && value !== null) {
        showUpdatePanel.value = true;
        gameID.value = value;
        console.log(gameID.value);
    }
};

const handleSearchGame = (value: string) => {
    seachValue.value = value;
};
</script>

<template>
    <div>
        <div>
            <Search
                @click="
                    showSeach = true;
                    showUpdatePanel = false;
                "
                style="margin-left: 400px"
            />
        </div>
        <div
            v-if="showSeach && !showUpdatePanel"
            style="overflow-y: auto"
            class="loading-overlay"
        >
            <div style="margin-left: 400px; display: flex; margin-top: 50px">
                <Cancel @click="showSeach = false" />
                <SearchGame
                    @update:modelValue="handleSearchGame"
                    style="margin-left: 20px; margin-top: -5px"
                />
            </div>
            <div style="margin-left: 310px">
                <GameCard
                    :modelValue="seachValue"
                    @update:modelValue="handleGameCardUpdate"
                    style="margin-top: 50px"
                />
            </div>
        </div>

        <GameCardSmart
            @update:modelValue="handleGameCardUpdate"
            v-if="!showUpdatePanel"
        />

        <div
            v-if="showUpdatePanel"
            class="card"
            style="transform: scale(0.8); margin-top: -60px"
        >
            <CancelUpdatePanel
                @click="showUpdatePanel = false"
                style="margin-left: 95%"
            />
            <h2 class="card-title">修改信息</h2>
            <form @submit.prevent="handleSubmit">
                <div class="form-grid">
                    <div style="display: flex">
                        <div class="form-group">
                            <label for="gameName">游戏名：</label>
                            <input
                                id="gameName"
                                v-model="gameName"
                                class="input-field"
                                placeholder="游戏名：黑神话·悟空"
                                required
                                style="width: 300px"
                                type="text"
                            />
                        </div>

                        <div class="form-group">
                            <label class="file-label" for="gameCover">
                                <span style="margin-left: -70px">上传封面</span>
                                <input
                                    id="gameCover"
                                    accept="image/*"
                                    class="file-input"
                                    type="file"
                                    @change="handleFileChange"
                                />
                                <div v-if="gameCover" class="image-preview">
                                    <img
                                        :src="gameCover"
                                        alt="封面预览"
                                        class="preview-image"
                                    />
                                </div>
                            </label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="gameDeveloper">游戏开发商：</label>
                        <input
                            id="gameDeveloper"
                            v-model="gameDeveloper"
                            class="input-field"
                            placeholder="游戏开发商名称：育碧、腾讯"
                            required
                            type="text"
                        />
                    </div>

                    <div style="display: flex">
                        <div class="form-group">
                            <label for="releaseDate">发行时间：</label>
                            <input
                                id="releaseDate"
                                v-model="releaseDate"
                                class="input-field date-input"
                                required
                                style="width: 300px; padding-right: 0"
                                type="date"
                            />
                        </div>
                        <div class="form-group" style="margin-left: 100px">
                            <label for="gameScore">游戏评分：</label>
                            <input
                                id="gameScore"
                                v-model.number="gameScore"
                                class="input-field score-input"
                                max="10"
                                min="0"
                                required
                                style="width: 113px"
                                type="number"
                                placeholder="10分制"
                                step="0.01"
                            />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="gameCategory">游戏分类：</label>
                        <el-input-tag
                            v-model="selectGameCategories"
                            :tag-type="tagType()"
                            class="input-field"
                            placeholder="点击 Enter 键生成标签"
                            tag-effect="dark"
                        />
                        <div style="margin: 15px">
                            <el-dropdown
                                ref="dropdown1"
                                placement="bottom"
                                trigger="click"
                            >
                                <el-icon
                                    style="
                                        margin-top: -50px;
                                        margin-left: 490px;
                                        font-size: 20px;
                                    "
                                >
                                    <svg
                                        class="icon"
                                        p-id="4195"
                                        t="1739184410370"
                                        version="1.1"
                                        viewBox="0 0 1024 1024"
                                        xmlns="http://www.w3.org/2000/svg"
                                    >
                                        <path
                                            d="M512 817.664l264.832-264.832-60.330667-60.330667L512 697.002667l-204.501333-204.501334-60.330667 60.330667L512 817.664z m0-241.066667l264.832-264.832-60.330667-60.330666L512 455.936 307.498667 251.434667 247.168 311.765333 512 576.597333z"
                                            fill="#000000"
                                            p-id="4196"
                                        ></path>
                                    </svg>
                                </el-icon>
                                <template #dropdown>
                                    <el-dropdown-menu>
                                        <el-dropdown-item
                                            v-for="item in gameCategories"
                                            @click="
                                                selectGameCategoriestDropdownMenu(
                                                    item,
                                                )
                                            "
                                            >{{ item }}</el-dropdown-item
                                        >
                                    </el-dropdown-menu>
                                </template>
                            </el-dropdown>
                        </div>
                    </div>

                    <div class="form-group" style="margin-top: -30px">
                        <label for="gameCategory">运行平台</label>
                        <el-input-tag
                            v-model="selectFormOptions"
                            :tag-type="tagType()"
                            class="input-field"
                            placeholder="点击 Enter 键生成标签"
                            tag-effect="dark"
                        />
                        <el-dropdown
                            ref="dropdown1"
                            placement="bottom"
                            trigger="click"
                        >
                            <el-icon
                                style="
                                    margin-top: -35px;
                                    margin-left: 500px;
                                    font-size: 20px;
                                "
                            >
                                <svg
                                    class="icon"
                                    p-id="4195"
                                    t="1739184410370"
                                    version="1.1"
                                    viewBox="0 0 1024 1024"
                                    xmlns="http://www.w3.org/2000/svg"
                                >
                                    <path
                                        d="M512 817.664l264.832-264.832-60.330667-60.330667L512 697.002667l-204.501333-204.501334-60.330667 60.330667L512 817.664z m0-241.066667l264.832-264.832-60.330667-60.330666L512 455.936 307.498667 251.434667 247.168 311.765333 512 576.597333z"
                                        fill="#000000"
                                        p-id="4196"
                                    ></path>
                                </svg>
                            </el-icon>
                            <template #dropdown>
                                <el-dropdown-menu>
                                    <el-dropdown-item
                                        v-for="item in platformOptions"
                                        @click="selectPlatformOptions(item)"
                                        >{{ item }}</el-dropdown-item
                                    >
                                </el-dropdown-menu>
                            </template>
                        </el-dropdown>
                    </div>

                    <div class="form-group full-width">
                        <label for="gameDescription">游戏介绍：</label>
                        <textarea
                            id="gameDescription"
                            v-model="gameDescription"
                            class="textarea-field"
                            required
                        ></textarea>
                    </div>
                </div>
                <button
                    v-if="!buttonLoadingState"
                    class="submit-btn"
                    type="submit"
                >
                    更新
                </button>
                <el-button
                    v-if="buttonLoadingState"
                    class="submit-btn"
                    type="primary"
                    loading
                >
                    <template #loading>
                        <div class="custom-loading">
                            <svg class="circular" viewBox="-10, -10, 50, 50">
                                <path
                                    class="path"
                                    d="
                        M 30 15
                        L 28 17
                        M 25.61 25.61
                        A 15 15, 0, 0, 1, 15 30
                        A 15 15, 0, 1, 1, 27.99 7.5
                        L 15 15
                      "
                                    style="
                                        stroke-width: 4px;
                                        fill: rgba(0, 0, 0, 0);
                                    "
                                />
                            </svg>
                        </div>
                    </template>
                    更新
                </el-button>
            </form>
        </div>
    </div>
</template>

<style scoped>
/* Loading 遮罩层 */
.loading-overlay {
    position: fixed;
    top: 0;
    right: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(58, 58, 58);
    align-items: center;
    justify-content: center;
    z-index: 2;
}
.card {
    width: 600px;
    padding: 2rem;
    margin-top: 40px;
    margin-left: 240px;
    background: #ffffff;
    border-radius: 12px;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
    transition: transform 0.2s ease;
}

.card:hover {
    transform: translateY(-2px);
}

.card-title {
    text-align: center;
    font-size: 1.8rem;
    color: #2c3e50;
    margin-bottom: 1.5rem;
    padding-bottom: 0.5rem;
    border-bottom: 2px solid #eee;
    margin-top: -25px;
}

.form-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 1.2rem;
}

.form-group {
    margin-bottom: 1rem;
}

.form-group.full-width {
    grid-column: 1 / -1;
}

label {
    display: block;
    font-weight: 600;
    color: #4a5568;
    margin-bottom: 0.5rem;
    font-size: 0.9rem;
}

.input-field {
    width: 90%;
    padding: 0.75rem;
    border: 2px solid #e2e8f0;
    border-radius: 6px;
    font-size: 0.95rem;
    transition: all 0.3s ease;
    padding-right: 50px;
}

.input-field:focus {
    border-color: #4a9eff;
    box-shadow: 0 0 0 3px rgba(74, 158, 255, 0.2);
    outline: none;
}

.textarea-field {
    min-height: 120px;
    line-height: 1.5;
    width: 100%;
}

.file-label {
    display: block;
    cursor: pointer;
    position: relative;
    margin-top: 28px;
    margin-left: 100px;
    max-height: 50px;
}

.file-input {
    position: absolute;
    opacity: 0;
    width: 1px;
    height: 1px;
}

.file-label span {
    display: inline-block;
    padding: 0.75rem 1.5rem;
    background: #f8fafc;
    border: 2px dashed #cbd5e0;
    border-radius: 6px;
    color: #718096;
    transition: all 0.3s ease;
}

.file-label:hover span {
    border-color: #4a9eff;
    color: #4a9eff;
}
.el-button .custom-loading .circular {
    margin-right: 6px;
    width: 18px;
    height: 18px;
    animation: loading-rotate 2s linear infinite;
}
.el-button .custom-loading .circular .path {
    animation: loading-dash 1.5s ease-in-out infinite;
    stroke-dasharray: 90, 150;
    stroke-dashoffset: 0;
    stroke-width: 2;
    stroke: var(--el-button-text-color);
    stroke-linecap: round;
}

.preview-image {
    width: 100%;
    height: 150px;
    object-fit: cover;
    position: relative;
    padding-left: 50px;
    z-index: 100;
    margin-top: -100px;
    transform: scale(0.7);
}

.submit-btn {
    width: 100%;
    height: 50px;
    padding: 1rem;
    background: linear-gradient(135deg, #4a9eff, #357abd);
    color: white;
    border: none;
    border-radius: 6px;
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
    margin-top: 1rem;
}

.submit-btn:hover {
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(74, 158, 255, 0.3);
}

input[type="number"] {
    appearance: none;
    -moz-appearance: textfield;
}

input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}

.date-input::-webkit-calendar-picker-indicator {
    filter: invert(0.5);
    cursor: pointer;
}
</style>
