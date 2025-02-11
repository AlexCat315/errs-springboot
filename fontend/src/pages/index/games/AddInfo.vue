<script lang="ts" setup>
import { ref } from "vue";
import type { DropdownInstance, TagProps } from "element-plus";

const gameName = ref(""); // 游戏名
const gameScore = ref<number | null>(null); // 分数
const gameDeveloper = ref(""); //开发商
const releaseDate = ref(""); //游戏发行时间
const gameDescription = ref(""); //游戏简介
const gameCover = ref<string | null>(null); //游戏封面
const selectGameCategories = ref([]); //游戏类别
const selectFormOptions = ref([]); // 游戏平台

const handleSubmit = () => {
    console.log({
        gameName: gameName.value,
        gameScore: gameScore.value,
        gameDeveloper: gameDeveloper.value,
        gamePlatforms: selectFormOptions.value,
        releaseDate: releaseDate.value,
        gameDescription: gameDescription.value,
        gameCover: gameCover.value,
        gameCategories: selectGameCategories.value,
    });
};

const handleFileChange = (event: Event) => {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files[0]) {
        const file = fileInput.files[0];
        const reader = new FileReader();
        reader.onload = () => {
            gameCover.value = reader.result as string;
        };
        reader.readAsDataURL(file);
    }
};

//预定义选项数据
const gameCategories = ref([
    "动作",
    "冒险",
    "角色扮演",
    "策略",
    "体育",
    "模拟",
    "益智",
    "射击",
    "格斗",
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

const buttonState = ref(false); // 初始值

const onHover = () => {
    buttonState.value = true; // 当鼠标悬停时改变值
};

const onLeave = () => {
    buttonState.value = false; // 当鼠标离开时恢复值
};
</script>

<template>
    <div class="card">
        <h2 class="card-title">添加游戏</h2>
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
                            <span style="margin-left: -70px;">上传封面</span>
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
                                    alt="游戏封面预览"
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
            <button class="submit-btn" type="submit">提交</button>
        </form>
    </div>
</template>

<style scoped>
.card {
    width: 600px;
    margin: 2rem auto;
    padding: 2rem;
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
/* 
.image-preview {
    margin-top: 1rem;
    border-radius: 6px;
    overflow: hidden;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);

} */

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
