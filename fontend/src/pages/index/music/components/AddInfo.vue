<template>
    <div class="upload-form-container">
        <el-card class="form-card">
            <template #header>
                <div class="card-header">
                    <h2>上传歌曲</h2>
                </div>
            </template>

            <el-form
                ref="formRef"
                :model="songForm"
                :rules="rules"
                label-position="top"
                class="song-form"
            >
                <el-form-item label="歌曲名称" prop="name">
                    <el-input
                        v-model="songForm.name"
                        placeholder="请输入歌曲名称"
                    />
                </el-form-item>

                <el-form-item label="歌手" prop="artist">
                    <el-input
                        v-model="songForm.artist"
                        placeholder="请输入歌手名称"
                    />
                </el-form-item>
                <div style="display: flex">
                    <el-form-item label="封面图片" prop="cover">
                        <div class="preview-container">
                            <el-upload
                                class="cover-uploader"
                                :show-file-list="false"
                                :auto-upload="false"
                                :on-change="handleCoverChange"
                            >
                                <img
                                    v-if="songForm.coverUrl"
                                    :src="songForm.coverUrl"
                                    class="cover-preview"
                                />
                                <el-icon v-else class="cover-uploader-icon">
                                    <Plus />
                                </el-icon>
                            </el-upload>

                            <div v-if="songForm.coverUrl" class="preview-box">
                                <img
                                    :src="songForm.coverUrl"
                                    class="preview-image"
                                />
                                <div class="preview-info">
                                    <p>预览效果</p>
                                </div>
                            </div>
                        </div>
                    </el-form-item>

                    <el-form-item
                        style="margin-left: 40px"
                        label="音频文件"
                        prop="audio"
                    >
                        <div class="audio-container">
                            <el-upload
                                v-if="isUploadVisible"
                                class="audio-uploader"
                                :auto-upload="false"
                                :on-change="handleAudioChange"
                            >
                                <el-button type="primary"
                                    >上传音频文件</el-button
                                >
                                <template #tip>
                                    <div class="el-upload__tip">
                                        支持 MP3 格式文件
                                    </div>
                                </template>
                            </el-upload>

                            <audio
                                v-if="songForm.audioUrl"
                                controls
                                class="audio-preview"
                            >
                                <source
                                    :src="songForm.audioUrl"
                                    type="audio/mpeg"
                                />
                                您的浏览器不支持音频播放
                            </audio>
                        </div>
                    </el-form-item>
                </div>
                <div style="display: flex">
                    <el-form-item label="评分" prop="score">
                        <el-rate
                            v-model="songForm.score"
                            :max="5"
                            :allow-half="true"
                            show-score
                        />
                    </el-form-item>
                    <el-form-item
                        style="margin-left: 70px"
                        label="评价人数"
                        prop="users"
                    >
                        <el-input-number
                            v-model="songForm.users"
                            :min="0"
                            :allow-half="true"
                            show-score
                        />
                    </el-form-item>
                </div>

                <el-form-item label="标签" prop="tags">
                    <el-select
                        v-model="songForm.tags"
                        multiple
                        filterable
                        allow-create
                        default-first-option
                        placeholder="请选择或输入标签"
                    >
                        <el-option
                            v-for="tag in defaultTags"
                            :key="tag"
                            :label="tag"
                            :value="tag"
                        />
                    </el-select>
                </el-form-item>

                <el-form-item>
                    <el-button
                        v-if="!buttonLoadingState"
                        style="width: 98%"
                        type="primary"
                        @click="submitForm"
                    >
                        提交
                    </el-button>
                    <el-button
                        v-if="buttonLoadingState"
                        style="width: 98%"
                        type="primary"
                        loading
                    >
                        <template #loading>
                            <div class="custom-loading">
                                <svg
                                    class="circular"
                                    viewBox="-10, -10, 50, 50"
                                >
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
                        提交
                    </el-button>
                    <el-button
                        style="width: 98%; margin-top: 20px; margin-left: -1px"
                        @click="resetForm"
                        >重置</el-button
                    >
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script lang="ts" setup>
import { ref, reactive } from "vue";
import type { FormInstance, FormRules, UploadFile } from "element-plus";
import { Plus } from "@element-plus/icons-vue";
import { postFormData, defaultFailure } from "../../../../net/post"; // 导入封装方法
import { ElMessage } from "element-plus";

interface SongForm {
    name: string;
    artist: string;
    coverUrl: string;
    audioUrl: string;
    cover: File | null;
    audio: File | null;
    score: number;
    tags: string[];
    users: number;
}

const formRef = ref<FormInstance>();
const buttonLoadingState = ref(false); // 新增加载状态

const songForm = reactive<SongForm>({
    name: "",
    artist: "",
    coverUrl: "",
    audioUrl: "",
    cover: null,
    audio: null,
    score: 0,
    tags: [],
    users: 0,
});

const defaultTags = ["流行", "摇滚", "民谣", "电子", "古典", "爵士"];

const rules = reactive<FormRules<SongForm>>({
    name: [{ required: true, message: "请输入歌曲名称", trigger: "blur" }],
    artist: [{ required: true, message: "请输入歌手名称", trigger: "blur" }],
    cover: [{ required: true, message: "请上传封面图片", trigger: "change" }],
    audio: [{ required: true, message: "请上传音频文件", trigger: "change" }],
});

const handleCoverChange = (uploadFile: UploadFile) => {
    const file = uploadFile.raw;
    if (!file) return;

    const isImage = file.type.startsWith("image/");
    const isLt4M = file.size / 1024 / 1024 < 4;

    if (!isImage) {
        ElMessage.error("封面文件必须是图片格式!");
        return;
    }
    if (!isLt4M) {
        ElMessage.error("封面图片大小不能超过 4MB!");
        return;
    }

    songForm.cover = file;
    songForm.coverUrl = URL.createObjectURL(file);
    formRef.value?.validateField("cover");
};

const handleAudioChange = (uploadFile: UploadFile) => {
    const file = uploadFile.raw;
    if (!file) return;

    const isMP3 = ["audio/mpeg", "audio/mp3"].includes(file.type);
    const isLt20M = file.size / 1024 / 1024 < 20;

    if (!isMP3) {
        ElMessage.error("音频文件必须是 MP3 格式!");
        songForm.audioUrl = null; // 清空音频链接
        songForm.audio = null; // 清空音频文件
        // 隐藏组件
        isUploadVisible.value = false;
        // 延时显示组件，确保组件被销毁并重新创建
        setTimeout(() => {
            isUploadVisible.value = true;
        }, 0);
        return;
    }
    if (!isLt20M) {
        ElMessage.error("音频文件大小不能超过 20MB!");
        songForm.audioUrl = null; // 清空音频链接
        songForm.audio = null; // 清空音频文件
        // 隐藏组件
        isUploadVisible.value = false;
        // 延时显示组件，确保组件被销毁并重新创建
        setTimeout(() => {
            isUploadVisible.value = true;
        }, 0);
        return;
    }

    songForm.audio = file;
    songForm.audioUrl = URL.createObjectURL(file);
    formRef.value?.validateField("audio");
};

const submitForm = async () => {
    if (!formRef.value) return;
    try {
        const valid = await formRef.value.validate();
        if (!valid) return;

        buttonLoadingState.value = true;

        const formData = new FormData();
        formData.append("name", songForm.name);
        formData.append("artist", songForm.artist);
        if (songForm.audio) formData.append("audio", songForm.audio);
        if (songForm.cover) formData.append("cover", songForm.cover);
        formData.append("score", (songForm.score * 2).toString());
        formData.append("tags", songForm.tags.toString()); // 序列化数组
        formData.append("users", songForm.users.toString());

        postFormData(
            "/api/admin/song/create",
            formData,
            (response: any) => {
                console.log(response);
                ElMessage.success("提交成功");
                resetForm();
                buttonLoadingState.value = false;
            },
            (message: string, code: number, url: string) => {
                defaultFailure(message, code, url);
                buttonLoadingState.value = false;
            },
        );
    } catch (error) {
        buttonLoadingState.value = false;
        console.error(error);
    }
};
const isUploadVisible = ref(true);
const resetForm = () => {
    if (!formRef.value) return;
    formRef.value.resetFields();

    // 手动撤销对象 URL
    if (songForm.audioUrl) {
        URL.revokeObjectURL(songForm.audioUrl);
    }

    // 重置表单数据
    songForm.audioUrl = null; // 清空音频链接
    songForm.audio = null; // 清空音频文件
    songForm.coverUrl = ""; // 清空封面链接
    songForm.cover = null; // 清空封面文件
    songForm.tags = [];
    songForm.users = 0;
    // 隐藏组件
    isUploadVisible.value = false;
    // 延时显示组件，确保组件被销毁并重新创建
    setTimeout(() => {
        isUploadVisible.value = true;
    }, 0);
};
</script>

<style scoped>
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

.upload-form-container {
    max-width: 800px;
    margin: 20px auto;
    padding: 0 20px;
}

.form-card {
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.song-form {
    margin-top: 20px;
}

.preview-container {
    display: flex;
    gap: 20px;
    align-items: center;
}

.cover-uploader {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: border-color 0.3s;
}

.cover-uploader:hover {
    border-color: #409eff;
}

.cover-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    text-align: center;
    line-height: 178px;
}

.cover-preview {
    width: 178px;
    height: 178px;
    display: block;
    object-fit: cover;
}

.preview-box {
    border: 1px solid #dcdfe6;
    border-radius: 6px;
    padding: 10px;
    text-align: center;
}

.preview-image {
    width: 120px;
    height: 120px;
    object-fit: cover;
    border-radius: 4px;
}

.preview-info {
    margin-top: 8px;
    color: #909399;
    font-size: 14px;
}

.audio-container {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.audio-uploader {
    width: 100%;
}

.audio-preview {
    width: 100%;
    margin-top: 10px;
}

:deep(.el-upload) {
    width: 100%;
}

:deep(.el-upload-dragger) {
    width: 100%;
}
</style>
