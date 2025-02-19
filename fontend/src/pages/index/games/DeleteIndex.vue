<script lang="ts" setup>
import { ref } from "vue";
import Search from "./components/Seach.vue";
import Cancel from "./components/delete/Cancel.vue";
import SearchGame from "./components/delete/SeachGame.vue";
import GameCard from "./components/delete/GameCard.vue";
import GameCardSmart from "./components/delete/GameCardSmart.vue";
import DeleteCard from "./components/delete/DeleteCard.vue";

// 定义 Game 类型
interface Game {
    id: number;
    gameName: string;
    gameScore: number;
    gameDeveloper: string;
    releaseDate: string;
    gameDescription: string;
    gameCategories: string[];
    gamePlatforms: string[];
    gameImageUrl: string;
    gameUsers: string;
}

const showGameCardPanel = ref(false);
const showSeach = ref(false);

const seachValue = ref("");

const game = ref<Game | null>(null);
const showDeleteCard = ref(false);
const handleSearchGame = (value: string) => {
    seachValue.value = value;
};

const handleGameCardUpdate = (value: Game) => {
    game.value = value;
    showDeleteCard.value = true;
};

const handleGameCardSmartUpdate = (value: Game) => {
    game.value = value;
    showDeleteCard.value = true;
};

const disableDeleteCard = (value: boolean) => {
    showDeleteCard.value = value;
};

</script>

<template>
    <div>
        <div>
            <Search @click="
                showSeach = true;
            showGameCardPanel = true;
            " v-if="!showDeleteCard" style="margin-left: 400px" />
        </div>
        <div v-if="showSeach && showGameCardPanel && !showDeleteCard" style="overflow-y: auto" class="loading-overlay">
            <div style="margin-left: 400px; display: flex; margin-top: 50px">
                <Cancel @click="showGameCardPanel = false" />
                <SearchGame @update:modelValue="handleSearchGame" style="margin-left: 20px; margin-top: -5px" />
            </div>
            <div style="margin-left: 310px">
                <GameCard :modelValue="seachValue" @update:modelValue="handleGameCardUpdate" style="margin-top: 50px" />
            </div>
        </div>

        <GameCardSmart @update:modelValue="handleGameCardSmartUpdate" v-if="!showGameCardPanel && !showDeleteCard" />
        <DeleteCard style="margin-left: 35%;" :game="game" @update:modelValue="disableDeleteCard" v-if="showDeleteCard" />
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
