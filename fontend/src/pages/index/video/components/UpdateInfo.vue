<template>
    <div class="upload-form-container">
        <el-card class="form-card">
            <template #header>
                <div class="card-header">
                    <h2>修改电影信息</h2>
                </div>
            </template>

            <el-form ref="formRef" :model="movieForm" :rules="rules" label-position="top" class="song-form">
                <el-form-item label="电影名称" prop="name">
                    <el-input v-model="movieForm.name" placeholder="请输入电影名称" />
                </el-form-item>

                <el-form-item label="导演" prop="director">
                    <el-input v-model="movieForm.director" placeholder="请输入导演名称" />
                </el-form-item>

                <el-form-item label="主演" prop="actor">
                    <el-input v-model="movieForm.actor" placeholder="请输入主演名称" />
                </el-form-item>

                <el-form-item label="上映时间" prop="year">
                    <el-date-picker v-model="movieForm.year" type="date" placeholder="选择上映时间" :editable="true"
                        :clearable="false" />
                </el-form-item>

                <el-form-item label="语言" prop="language">
                    <el-input v-model="movieForm.language" placeholder="请输入语言" />
                </el-form-item>

                <el-form-item label="简介" prop="summary">
                    <el-input type="textarea" v-model="movieForm.summary" placeholder="请输入电影简介" />
                </el-form-item>
                <div v-if="isShowFile" style="display: flex">
                    <el-form-item label="封面图片" prop="cover">
                        <div class="preview-container">
                            <el-upload class="cover-uploader" :show-file-list="false" :auto-upload="false"
                                :on-change="handleCoverChange">
                                <img v-if="movieForm.coverUrl" :src="movieForm.coverUrl" class="cover-preview" />
                                <el-icon v-else class="cover-uploader-icon">
                                    <Plus />
                                </el-icon>
                            </el-upload>

                            <div v-if="movieForm.coverUrl" class="preview-box">
                                <img :src="movieForm.coverUrl" class="preview-image" />
                                <div class="preview-info">
                                    <p>预览效果</p>
                                </div>
                            </div>
                        </div>
                    </el-form-item>

                    <el-form-item style="margin-left: 40px" label="电影文件" prop="video">
                        <div class="audio-container">
                            <el-upload v-if="isUploadVisible" class="audio-uploader" :auto-upload="false"
                                :on-change="handleVideoChange">
                                <el-button type="primary">上传电影文件</el-button>
                            </el-upload>

                            <video v-if="movieForm.videoUrl" controls class="video-preview">
                                <source :src="movieForm.videoUrl" type="video/mp4" />
                                您的浏览器不支持视频播放
                            </video>
                        </div>
                    </el-form-item>
                </div>
                <div style="display: flex">
                    <el-form-item label="评分" prop="rating">
                        <div style="display: flex; align-items: center">
                            <el-rate v-model="movieForm.rating" :max="5" :allow-half="true" show-score
                                @change="handleRateChange" />
                            <el-input-number v-model="movieForm.rating" :min="0" :max="5" :step="0.1" :precision="1"
                                style="margin-left: 20px" @change="handleInputChange" />
                        </div>
                    </el-form-item>
                    <el-form-item style="margin-left: 70px" label="评价人数" prop="users">
                        <el-input-number v-model="movieForm.users" :min="0" />
                    </el-form-item>
                </div>

                <el-form-item label="电影类型" prop="tags">
                    <el-select v-model="movieForm.types" multiple filterable allow-create default-first-option
                        placeholder="请选择或输入电影类型">
                        <el-option v-for="tag in defaultTags" :key="tag" :label="tag" :value="tag" />
                    </el-select>
                </el-form-item>

                <el-form-item>
                    <el-button v-if="!buttonLoadingState" style="width: 98%" type="primary" @click="submitForm">
                        提交
                    </el-button>
                    <el-button v-if="buttonLoadingState" style="width: 98%" type="primary" loading>
                        <template #loading>
                            <div class="custom-loading">
                                <svg class="circular" viewBox="-10, -10, 50, 50">
                                    <path class="path" d="
                                M 30 15
                                L 28 17
                                M 25.61 25.61
                                A 15 15, 0, 0, 1, 15 30
                                A 15 15, 0, 1, 1, 27.99 7.5
                                L 15 15
                              " style="
                                            stroke-width: 4px;
                                            fill: rgba(0, 0, 0, 0);
                                        " />
                                </svg>
                            </div>
                        </template>
                        提交
                    </el-button>
                    <el-button style="width: 98%; margin-top: 20px; margin-left: -1px" @click="resetForm">重置</el-button>
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
import { onMounted } from "vue";
import { get_movie_info_by_id } from "../../../../net/movie/get";

interface MovieForm {
    name: string;
    director: string;
    actor: string;
    year: string;
    language: string;
    summary: string;
    coverUrl: string;
    videoUrl: string;
    cover: File | null;
    video: File | null;
    rating: number;
    types: string[];
    users: number;
}

const poros = defineProps({
    movieId: {
        type: Number,
        required: true
    }
});

onMounted(() => {
    get_movie_info_by_id(poros.movieId, (data: any) => {
        movieForm.name = data.name;
        movieForm.director = data.director;
        movieForm.actor = data.actor;
        movieForm.year = data.year;
        movieForm.language = data.language;
        movieForm.summary = data.summary;
        // 直接设置 URL，不需要创建 File 对象
        movieForm.coverUrl = data.cover;  // 假设后端返回的是完整的图片 URL
        movieForm.videoUrl = data.video;  // 假设后端返回的是完整的视频 URL
        movieForm.rating = data.rating ? (data.rating / 2) : 0;
        movieForm.types = data.types;
        movieForm.users = data.users;
    }, (message: string) => {
        console.log(message);
    });
});



const formRef = ref<FormInstance>();
const buttonLoadingState = ref(false); // 新增加载状态
const isShowFile = ref(true); // 显示文件上传组件

const movieForm = reactive<MovieForm>({
    name: "",
    director: "",
    actor: "",
    year: "",
    language: "",
    summary: "",
    coverUrl: "",
    videoUrl: "",
    cover: null,
    video: null,
    rating: 0,
    types: [],
    users: 0
});

const defaultTags = ["动作", "喜剧", "爱情", "科幻", "恐怖", "动画"];

const rules = reactive<FormRules<MovieForm>>({
    name: [{ required: true, message: "请输入电影名称", trigger: "blur" }],
    director: [{ required: true, message: "请输入导演名称", trigger: "blur" }],
    actor: [{ required: true, message: "请输入主演名称", trigger: "blur" }],
    // 只有当没有现有文件时才验证
    cover: [{ 
        required: true, 
        validator: (rule, value, callback) => {
            if (!movieForm.cover && !movieForm.coverUrl) {
                callback(new Error('请上传封面图片'));
            } else {
                callback();
            }
        }, 
        trigger: "change" 
    }],
    video: [{ 
        required: true, 
        validator: (rule, value, callback) => {
            if (!movieForm.video && !movieForm.videoUrl) {
                callback(new Error('请上传视频文件'));
            } else {
                callback();
            }
        }, 
        trigger: "change" 
    }],
});

const handleCoverChange = (uploadFile: UploadFile) => {
    const file = uploadFile.raw;
    if (!file) return;

    const isImage = file.type.startsWith("image/");
    const isLt4M = file.size / 1024 / 1024 < 4;

    if (!isImage) {
        ElMessage.error("封面文件必须是图片格式!");
        showFileReset();
        return;
    }
    if (!isLt4M) {
        ElMessage.error("封面图片大小不能超过 4MB!");
        showFileReset();
        return;
    }

    movieForm.cover = file;
    movieForm.coverUrl = URL.createObjectURL(file);
    formRef.value?.validateField("cover");
};

const handleVideoChange = (uploadFile: UploadFile) => {
    const file = uploadFile.raw;
    if (!file) return;

    const isVideo = file.type.startsWith("video/");
    const isLt500M = file.size / 1024 / 1024 < 500;

    if (!isVideo) {
        ElMessage.error("请上传视频格式文件!");
        showFileReset();
        return;
    }
    if (!isLt500M) {
        ElMessage.error("视频文件大小不能超过 500MB!");
        showFileReset();
        return;
    }

    movieForm.video = file;
    movieForm.videoUrl = URL.createObjectURL(file);
    formRef.value?.validateField("video");
};

const handleRateChange = (value: number) => {
    movieForm.rating = value;
};

const handleInputChange = (value: number) => {
    if (value < 0) movieForm.rating = 0;
    if (value > 5) movieForm.rating = 5;
    // 移除四舍五入到0.5的逻辑
    movieForm.rating = parseFloat(value.toFixed(1)); // 保留一位小数
};

const submitForm = async () => {
    if (!formRef.value) return;
    try {
        const valid = await formRef.value.validate();
        if (!valid) return;

        buttonLoadingState.value = true;

        const formData = new FormData();
        formData.append("id", poros.movieId.toString());
        formData.append("name", movieForm.name);
        formData.append("director", movieForm.director);
        formData.append("actor", movieForm.actor);
        const formattedDate = new Date(movieForm.year).toISOString().split('T')[0];
        formData.append("year", formattedDate);
        formData.append("language", movieForm.language);
        formData.append("summary", movieForm.summary);
        
        // 只有当选择了新文件时才附加到 FormData
        if (movieForm.video) {
            formData.append("video", movieForm.video);
        }
        if (movieForm.cover) {
            formData.append("cover", movieForm.cover);
        }
        
        formData.append("rating", (movieForm.rating * 2).toString());
        formData.append("type", movieForm.types.toString());
        formData.append("users", movieForm.users.toString());

        postFormData(
            "/api/admin/movie/update/info",
            formData,
            () => {
                ElMessage.success("电影更新成功");
                setTimeout(() => {
                    resetForm();
                    buttonLoadingState.value = false;
                    showMovieListCard();
                }, 1400);
            },
            (message: string, code: number, url: string) => {
                defaultFailure(message, code, url);
                setTimeout(() => {
                    buttonLoadingState.value = false;
                }, 1400);
            }
        );
    } catch (error) {
        setTimeout(() => {
            buttonLoadingState.value = false;
        }, 1400);
    }
};

const emit = defineEmits(['update:modelValue']);

const showMovieListCard = () => {
    emit('update:modelValue', false);
};

const isUploadVisible = ref(true);
const resetForm = () => {

    formRef.value.resetFields();
    showFileReset();
    // 隐藏组件
    isUploadVisible.value = false;
    // 延时显示组件，确保组件被销毁并重新创建
    setTimeout(() => {
        isUploadVisible.value = true;
    }, 10);

};
const showFileReset = () => {
    movieForm.coverUrl = '';
    movieForm.cover = null;
    movieForm.video = null;
    movieForm.videoUrl = '';
    isShowFile.value = false;
    setTimeout(() => {
        isShowFile.value = true;
    }, 10);
};
</script>

<style scoped>
.video-preview {
    width: 100%;
    height: 150px
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
