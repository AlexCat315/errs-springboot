<template>
    <div class="upload-form-container">
        <el-card class="form-card">
            <template #header>
                <div class="card-header">
                    <h2>修改书籍信息</h2>
                </div>
            </template>

            <el-form ref="formRef" :model="bookForm" :rules="rules" label-position="top" class="song-form">
                <el-form-item label="书籍名称" prop="name">
                    <el-input v-model="bookForm.name" placeholder="请输入书籍名称" />
                </el-form-item>

                <el-form-item label="作者" prop="author">
                    <el-input v-model="bookForm.author" placeholder="请输入作者名称" />
                </el-form-item>

                <el-form-item label="编辑评语" prop="description">
                    <el-input v-model="bookForm.description" placeholder="请输入编辑评语" />
                </el-form-item>

                <el-form-item label="简介" prop="introduction">
                    <el-input type="textarea" v-model="bookForm.introduction" placeholder="请输入书籍简介" />
                </el-form-item>

                <el-form-item label="封面图片" prop="img">
                    <div class="preview-container">
                        <el-upload class="cover-uploader" :show-file-list="false" :auto-upload="false"
                            :on-change="handleCoverChange">
                            <img v-if="bookForm.img" :src="bookForm.img" class="cover-preview" />
                            <el-icon v-else class="cover-uploader-icon">
                                <Plus />
                            </el-icon>
                        </el-upload>

                        <!--  Removed redundant preview-box, using el-upload's built-in preview -->
                        <!--  <div v-if="bookForm.img" class="preview-box">
                            <img :src="bookForm.img" class="preview-image" />
                            <div class="preview-info">
                                <p>封面预览</p>
                            </div>
                        </div> -->
                    </div>
                </el-form-item>

                <div style="display: flex">
                    <el-form-item label="评分" prop="rating">
                        <div style="display: flex; align-items: center">
                            <el-rate v-model="bookForm.rating" :max="5" :allow-half="true" show-score
                                @change="handleRateChange" />
                            <el-input-number v-model="bookForm.rating" :min="0" :max="5" :step="0.1" :precision="1"
                                style="margin-left: 20px" @change="handleInputChange" />
                        </div>
                    </el-form-item>

                    <el-form-item style="margin-left: 70px" label="推荐度" prop="recommend">
                        <el-input-number v-model="bookForm.recommend" :min="0" :max="1" :step="0.1" :precision="1" />
                    </el-form-item>
                </div>

                <el-form-item label="用户评价数" prop="users">
                    <el-input-number v-model="bookForm.users" :min="0" />
                </el-form-item>

                <el-form-item>
                    <el-button v-if="!buttonLoadingState" style="width: 98%" type="primary" @click="submitForm">
                        提交
                    </el-button>
                    <el-button v-if="buttonLoadingState" style="width: 98%" type="primary" loading>
                        提交中... <!-- Added text for loading state -->
                    </el-button>
                    <el-button style="width: 98%; margin-top: 20px; margin-left: -1px" @click="resetForm">重置</el-button>
                    <el-button style="width: 98%; margin-top: 10px; margin-left: -1px"
                        @click="$emit('cancel')">取消</el-button>
                    <!-- Corrected event name -->
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script lang="ts" setup>
import { ref, reactive, PropType, watch } from "vue"; // Added watch
import type { FormInstance, FormRules, UploadFile } from "element-plus";
import { Plus } from "@element-plus/icons-vue";
import { postFormData, defaultFailure } from "../../../../../net/post";
import { ElMessage } from "element-plus";
import { getBookRatingAndUsersApi } from "../../../../../net/book/get";

interface BookForm {
    id?: number; // Added id, since it's an update
    name: string;
    author: string;
    description: string;
    introduction: string;
    img: string;
    cover: File | null;
    rating: number;
    recommend: number;
    users: number;
}

const formRef = ref<FormInstance>();
const buttonLoadingState = ref(false);
const isShowFile = ref(true);  // Not strictly necessary, but keep for consistency

const bookForm = reactive<BookForm>({
    name: "",
    author: "",
    description: "",
    introduction: "",
    img: "",
    cover: null,
    rating: 0,
    recommend: 0,
    users: 0
});

const rules = reactive<FormRules<BookForm>>({
    name: [{ required: true, message: "请输入书籍名称", trigger: "blur" }],
    author: [{ required: true, message: "请输入作者", trigger: "blur" }],
    description: [{ required: true, message: "请输入编辑评语", trigger: "blur" }],
    img: [{ required: false, message: "请上传封面图片", trigger: "change" }], // Make img optional for updates
    introduction: [{ required: false, message: "请输入书籍简介", trigger: "blur" }],
    rating: [{ required: false, type: 'number', message: "请输入评分", trigger: "blur" }],  // Added type checking
    recommend: [{ required: false, type: 'number', message: "请输入推荐度", trigger: "blur" }], // Added type checking
    users: [{ required: false, type: 'number', message: "请输入用户评价数", trigger: "blur" }], // Added type checking

});

const handleCoverChange = (uploadFile: UploadFile) => {
    const file = uploadFile.raw;
    if (!file) return;

    const isImage = file.type.startsWith("image/");
    const isLt4M = file.size / 1024 / 1024 < 4;

    if (!isImage) {
        ElMessage.error("封面文件必须是图片格式!");
        // showFileReset(); // Removed unnecessary reset
        return;
    }
    if (!isLt4M) {
        ElMessage.error("封面图片大小不能超过 4MB!");
        // showFileReset(); // Removed unnecessary reset
        return;
    }

    bookForm.cover = file;
    const reader = new FileReader();
    reader.onload = (e) => {
        bookForm.img = e.target?.result as string;
        // formRef.value?.validateField("img"); // Not needed here
    };
    reader.readAsDataURL(file);
};

const handleRateChange = (value: number) => {
    bookForm.rating = value;
};

const handleInputChange = (value: number) => {
    // Keep input value within range
    const boundedValue = Math.max(0, Math.min(5, value));
    bookForm.rating = parseFloat(boundedValue.toFixed(1));
};

// Define emits
const emit = defineEmits(['cancel', 'update:modelValue', 'update:success']);


const submitForm = async () => {
    if (!formRef.value) return;
    try {
        const valid = await formRef.value.validate();
        if (!valid) return;

        buttonLoadingState.value = true;

        const formData = new FormData();
        formData.append("name", bookForm.name);
        formData.append("author", bookForm.author);
        formData.append("description", bookForm.description);
        formData.append("introduction", bookForm.introduction);
        if (bookForm.cover) {
            formData.append("img", bookForm.cover);
        } else if (bookForm.img && typeof bookForm.img === 'string' && bookForm.img.startsWith('http')) {

        }

        formData.append("rating", (bookForm.rating * 2).toString());
        formData.append("recommend", (bookForm.recommend * 100).toString());
        formData.append("users", bookForm.users.toString());
        if (bookForm.id) {  // IMPORTANT: Include the ID for updates!
            formData.append("id", bookForm.id.toString());
        }

        postFormData(
            `/api/admin/book/update/${bookForm.id}`,   // DYNAMIC endpoint
            formData,
            () => {
                ElMessage.success(bookForm.id ? "书籍修改成功" : "书籍上传成功");
                emit('cancel')
                setTimeout(() => {
                    //resetForm(); // Don't reset on success, we want to keep the data.
                    buttonLoadingState.value = false;
                    emit('update:success', false)
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
        buttonLoadingState.value = false;
        console.error("Submission error:", error); // More detailed error logging
    }
};

const resetForm = () => {
    if (formRef.value) {
        formRef.value.resetFields();
    }
    // 通过数据Id获取书籍的评分用户数
    if (bookForm?.id !== null && bookForm?.id !== undefined) {
        getBookRatingAndUsersApi(bookForm.id, (data: any) => {
        console.log("Rating and users data:", data);
        bookForm.users = data;
    })
    }
    isShowFile.value = false;
    setTimeout(() => {
        isShowFile.value = true;
    }, 30);
};

// const showFileReset = () => { // Not needed, handled by resetFields
//     bookForm.img = '';
//     bookForm.cover = null;
// };

// Define Book type (consistent with parent)
interface Book {
    id: number;
    name: string;
    author: string;
    description: string;
    rating: number;
    users: number;
    img: string;
    introduction: string;
    recommend: number;
}
const props = defineProps({
    book: {
        type: Object as PropType<Book | null>,
        required: true
    }
});


// Use watch to update the form when the 'book' prop changes
watch(() => props.book, (newBook) => {
    console.log("Book prop changed:", newBook);
    if (newBook) {
        bookForm.id = newBook.id; // VERY IMPORTANT:  Keep the ID
        bookForm.name = newBook.name;
        bookForm.author = newBook.author;
        bookForm.description = newBook.description;
        bookForm.introduction = newBook.introduction;
        bookForm.img = newBook.img;
        bookForm.rating = newBook.rating;
        bookForm.recommend = newBook.recommend;
        bookForm.cover = null; // Reset cover on prop change.  Important!
    }
    // 通过数据Id获取书籍的评分用户数
    if (newBook?.id !== null && newBook?.id !== undefined) {
        getBookRatingAndUsersApi(newBook.id, (data: any) => {
        console.log("Rating and users data:", data);
        bookForm.users = data;
    })
    }
}, { immediate: true, deep: true }); // immediate: true to run on initial load



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
