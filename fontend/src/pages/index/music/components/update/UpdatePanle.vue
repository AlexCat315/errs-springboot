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

<script setup lang="ts">
import { ref, reactive, PropType, onMounted } from "vue";
import type { FormInstance, FormRules, UploadFile } from "element-plus";
import { postFormData, defaultFailure } from "../../../../../net/post";
import { ElMessage } from "element-plus";
import { get_music_info_by_id } from "../../../../../net/music/get";

// 定义接收的props类型
interface Song {
    id: number;
    name: string;
    artist: string;
    coverUrl: string;
    audioUrl: string;
    tags: string[];
    users: string;
    score: number;
}

const props = defineProps({
    song: {
        type: Object as PropType<Song>,
        required: true
    }
});

const emit = defineEmits(['update:modelValue', 'update:success']);

// 修改表单数据接口
interface SongForm {
    id: number;
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
const buttonLoadingState = ref(false);
const isUploadVisible = ref(true);

// 使用props初始化表单数据
const songForm = reactive<SongForm>({
    id: props.song.id,
    name: props.song.name,
    artist: props.song.artist,
    coverUrl: props.song.coverUrl,
    audioUrl: props.song.audioUrl,
    cover: null,
    audio: null,
    score: props.song.score,
    tags: props.song.tags,
    users: parseInt(props.song.users) // 转换为数字
});

onMounted(() => {
    get_music_info_by_id(
        props.song.id,
        (data: any) => {
            songForm.id = data.id;
            songForm.name = data.name;
            songForm.artist = data.artist;
            songForm.coverUrl = data.coverUrl;
            songForm.audioUrl = data.audioUrl;
            songForm.score = data.score;
            songForm.tags = data.tags;
            songForm.users = parseInt(data.users);
        },
        (error) => {
            console.error(error);
        }
    )
});

const defaultTags = ["流行", "摇滚", "民谣", "电子", "古典", "爵士"];

// 修改验证规则
const rules = reactive<FormRules>({
    name: [{ required: true, message: "请输入歌曲名称", trigger: "blur" }],
    artist: [{ required: true, message: "请输入歌手名称", trigger: "blur" }],
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
};

const handleAudioChange = (uploadFile: UploadFile) => {
    const file = uploadFile.raw;
    if (!file) return;

    const isMP3 = ["audio/mpeg", "audio/mp3"].includes(file.type);
    const isLt20M = file.size / 1024 / 1024 < 20;

    if (!isMP3) {
        ElMessage.error("音频文件必须是 MP3 格式!");
        resetAudioUpload();
        return;
    }
    if (!isLt20M) {
        ElMessage.error("音频文件大小不能超过 20MB!");
        resetAudioUpload();
        return;
    }

    songForm.audio = file;
    songForm.audioUrl = URL.createObjectURL(file);
};

const resetAudioUpload = () => {
    songForm.audioUrl = props.song.audioUrl; // 恢复原始音频URL
    songForm.audio = null;
    isUploadVisible.value = false;
    setTimeout(() => {
        isUploadVisible.value = true;
    }, 0);
};

const submitForm = async () => {
    if (!formRef.value) return;
    try {
        const valid = await formRef.value.validate();
        if (!valid) return;

        buttonLoadingState.value = true;

        const formData = new FormData();
        // 基础信息始终需要提交
        formData.append("id", songForm.id.toString());
        formData.append("name", songForm.name);
        formData.append("artist", songForm.artist);
        formData.append("score", songForm.score.toString());
        formData.append("tags", songForm.tags.toString());
        formData.append("users", songForm.users.toString());

        // 只有当选择了新文件时才提交文件
        if (songForm.audio && songForm.audioUrl !== props.song.audioUrl) {
            formData.append("audio", songForm.audio);
        }
        if (songForm.cover && songForm.coverUrl !== props.song.coverUrl) {
            formData.append("cover", songForm.cover);
        }

        postFormData(
            "/api/admin/song/update",
            formData,
            (response: any) => {
                ElMessage.success("修改成功");
                emit('update:success', true);
                emit('update:modelValue', false);
                buttonLoadingState.value = false;
            },
            (message: string, code: number, url: string) => {
                defaultFailure(message, code, url);
                buttonLoadingState.value = false;
            }
        );
    } catch (error) {
        buttonLoadingState.value = false;
        console.error(error);
    }
};
const resetForm = () => {
    if (!formRef.value) return;

    // 重置为原始数据
    songForm.name = props.song.name;
    songForm.artist = props.song.artist;
    songForm.coverUrl = props.song.coverUrl;
    songForm.audioUrl = props.song.audioUrl;
    songForm.cover = null;
    songForm.audio = null;
    songForm.score = props.song.score;
    songForm.tags = [...props.song.tags];
    songForm.users = parseInt(props.song.users);

    // 重置上传组件
    isUploadVisible.value = false;
    setTimeout(() => {
        isUploadVisible.value = true;
    }, 10);
};

const handleCancel = () => {
    emit('update:modelValue', false);
};
</script>

<template>
    <div class="upload-form-container">
        <el-card class="form-card">
            <template #header>
                <div class="card-header">
                    <h2>修改歌曲</h2>
                </div>
            </template>

            <!-- 其余模板部分保持不变 -->

            <el-form ref="formRef" :model="songForm" :rules="rules" label-position="top" class="song-form">
                <el-form-item label="歌曲名称" prop="name">
                    <el-input v-model="songForm.name" placeholder="请输入歌曲名称" />
                </el-form-item>

                <el-form-item label="歌手" prop="artist">
                    <el-input v-model="songForm.artist" placeholder="请输入歌手名称" />
                </el-form-item>
                <div style="display: flex">
                    <el-form-item label="封面图片" prop="cover">
                        <div class="preview-container">
                            <el-upload class="cover-uploader" :show-file-list="false" :auto-upload="false"
                                :on-change="handleCoverChange">
                                <img v-if="songForm.coverUrl" :src="songForm.coverUrl" class="cover-preview" />
                                <el-icon v-else class="cover-uploader-icon">
                                    <Plus />
                                </el-icon>
                            </el-upload>

                            <div v-if="songForm.coverUrl" class="preview-box">
                                <img :src="songForm.coverUrl" class="preview-image" />
                                <div class="preview-info">
                                    <p>预览效果</p>
                                </div>
                            </div>
                        </div>
                    </el-form-item>

                    <el-form-item style="margin-left: 40px" label="音频文件" prop="audio">
                        <div class="audio-container">
                            <el-upload v-if="isUploadVisible" class="audio-uploader" :auto-upload="false"
                                :on-change="handleAudioChange">
                                <el-button type="primary">上传音频文件</el-button>
                                <template #tip>
                                    <div class="el-upload__tip">
                                        支持 MP3 格式文件
                                    </div>
                                </template>
                            </el-upload>

                            <audio v-if="songForm.audioUrl" controls class="audio-preview">
                                <source :src="songForm.audioUrl" type="audio/mpeg" />
                                您的浏览器不支持音频播放
                            </audio>
                        </div>
                    </el-form-item>
                </div>
                <div style="display: flex">
                    <el-form-item label="评分" prop="score">
                        <el-rate v-model="songForm.score" :max="5" :allow-half="true" show-score />
                    </el-form-item>
                    <el-form-item style="margin-left: 70px" label="评价人数" prop="users">
                        <el-input-number v-model="songForm.users" :min="0" :allow-half="true" show-score />
                    </el-form-item>
                </div>

                <el-form-item label="标签" prop="tags">
                    <el-select v-model="songForm.tags" multiple filterable allow-create default-first-option
                        placeholder="请选择或输入标签">
                        <el-option v-for="tag in defaultTags" :key="tag" :label="tag" :value="tag" />
                    </el-select>
                </el-form-item>

                <el-form-item>
                    <el-button v-if="!buttonLoadingState" style="width: 98%" type="primary" @click="submitForm">
                        保存修改
                    </el-button>
                    <el-button v-if="buttonLoadingState" style="width: 98%" type="primary" loading>
                        保存中...
                    </el-button>
                    <el-button style="width: 98%; margin-top: 20px; margin-left: -1px" @click="resetForm">重置</el-button>
                    <el-button style="width: 98%; margin-top: 20px; margin-left: -1px"
                        @click="handleCancel">取消</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>
