<script lang="ts" setup>
import { inject, onMounted, ref } from "vue";
import Favorites from "./components/games/Favorites.vue";
import FavoritesTrue from "./components/games/FavoritesTrue.vue";
import Like from "./components/games/Like.vue";
import {
    get_game_top50_info,
    get_game_highest_rated,
    get_game_most_reviewed,
} from "../../../net/games/get_top";
import RatingCard from "./components/games/RatingCard.vue";

const globalTheme = inject<string>("globalTheme");

if (globalTheme === undefined) {
    throw new Error("Function not implemented.");
}

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

// 添加不同榜单类型
enum ListType {
    TOP50 = "top50",
    MOST_REVIEWED = "mostReviewed",
    HIGHEST_RATED = "highestRated",
}

const currentTab = ref<ListType>(ListType.TOP50);
const tabs = {
    [ListType.TOP50]: { title: "Top50榜单", apiParam: 0 },
    [ListType.MOST_REVIEWED]: { title: "最多评价", apiParam: 1 },
    [ListType.HIGHEST_RATED]: { title: "好评最高", apiParam: 2 },
};

// 用于存储游戏数据的 ref
const gamesList = ref<Game[]>([]);
function formatNumber(num: number) {
    if (num >= 1000000) {
        return (num / 1000000).toFixed(1) + "m+";
    } else if (num >= 10000) {
        return (num / 10000).toFixed(1) + "w+";
    } else if (num >= 1000) {
        return (num / 1000).toFixed(1) + "k+";
    }
    return num.toString();
}

// 通用获取游戏数据方法
const fetchGames = (listType: ListType) => {
    get_game_top50_info(
        0,
        (data: any) => {
            const newGames = data.data.map((game: any) => {
                game.gameUsers = formatNumber(game.gameUsers);
                game.gameScore = parseFloat(game.gameScore.toFixed(1));
                return game;
            });

            // 去重逻辑
            const uniqueGames = [...gamesList.value, ...newGames].filter(
                (game, index, self) =>
                    self.findIndex((g) => g.id === game.id) === index,
            );
            gamesList.value = uniqueGames;
        },
        (message: string) => {
            console.error(message);
        },
        (message: string) => {
            console.error(message);
        },
    );
};

const getGameTop50Info = (start: number) => {
    get_game_top50_info(
        start,
        (data: any) => {
            const newGames = data.data.map((game: any) => {
                game.gameUsers = formatNumber(game.gameUsers);
                game.gameScore = parseFloat(game.gameScore.toFixed(1));
                return game;
            });

            // 去重逻辑
            const uniqueGames = [...gamesList.value, ...newGames].filter(
                (game, index, self) =>
                    self.findIndex((g) => g.id === game.id) === index,
            );
            gamesList.value = uniqueGames;
        },
        (message: string) => {
            console.error(message);
        },
        (message: string) => {
            console.error(message);
        },
    );
};
const getGameHighestRated = (start: number) => {
    get_game_highest_rated(
        start,
        (data: any) => {
            const newGames = data.data.map((game: any) => {
                game.gameUsers = formatNumber(game.gameUsers);
                game.gameScore = parseFloat(game.gameScore.toFixed(1));
                return game;
            });

            // 去重逻辑
            const uniqueGames = [...gamesList.value, ...newGames].filter(
                (game, index, self) =>
                    self.findIndex((g) => g.id === game.id) === index,
            );
            gamesList.value = uniqueGames;
        },
        (message: string) => {
            console.error(message);
        },
        (message: string) => {
            console.error(message);
        },
    );
};

const getGameMostReviewed = (start: number) => {
    get_game_most_reviewed(
        start,
        (data: any) => {
            data.data.forEach((game: any) => {
                game.gameUsers = formatNumber(game.gameUsers);
                game.gameScore = parseFloat(game.gameScore.toFixed(1));
            });
            gamesList.value = data.data;
        },
        (message: string) => {
            console.error(message);
        },
        (message: string) => {
            console.error(message);
        },
    );
};

const switchFetch = (listType: ListType, start: number) => {
    switch (listType) {
        case ListType.TOP50: {
            getGameTop50Info(start);
            break;
        }
        case ListType.HIGHEST_RATED: {
            getGameHighestRated(start);
            break;
        }
        case ListType.MOST_REVIEWED: {
            getGameMostReviewed(start);
            break;
        }
    }
};

const switchTab = (tab: ListType) => {
    currentTab.value = tab;
    gamesList.value = []
    switchFetch(tab, 0);
};

const scrollContainer = ref(null);

const handleScroll = () => {
    if (!scrollContainer.value) return;

    const { scrollTop, scrollHeight, clientHeight } = scrollContainer.value;
    const isBottom = scrollTop + clientHeight >= scrollHeight - 5;

    if (isBottom) {
        switchFetch(currentTab.value, gamesList.value.length);
    }
};

const colors = [
    "#ec8236",
    "#f56c6c",
    "#67c23a",
    "#e6a23c",
    "#409eff",
    "#c239e6",
    "#5941d3",
];
const colorsRandom = () => colors[Math.floor(Math.random() * colors.length)];

onMounted(() => {
    fetchGames(currentTab.value);
});

const showErrorpanle = ref(false);
const errorPanleMsg = ref("");
const showLoading = ref(false);
const handleUpdateShowRating = (value: boolean) => {
    showLoading.value = value;
};
const gameID = ref();
const showRatingCard = (gameId: number) => {
    showLoading.value = true;
    gameID.value = gameId;
};
const isFavorites = ref(false);

const changeFavorites = () => {
    isFavorites.value = !isFavorites.value;
};
</script>

<template>
    <div ref="scrollContainer" @scroll="handleScroll" class="game">
        <p v-if="showErrorpanle" class="error-msg">{{ errorPanleMsg }}</p>
        <div v-if="showLoading" class="loading-overlay">
            <RatingCard
                :ID="gameID"
                @updateShowRating="handleUpdateShowRating"
                style="margin-left: 200px; margin-top: -20px"
            />
        </div>
        <div class="section-header">
            <div style="display: flex; align-items: center">
                <p
                    :style="{
                        color: globalTheme === 'dark' ? '#FFF' : 'black',
                    }"
                    style="
                        font-size: 23px;
                        font-family: yousu-title-black;
                        margin-left: 30px;
                        margin-top: 30px;
                    "
                >
                    {{ tabs[currentTab].title }}
                </p>
                <div style="display: flex; margin-left: 20px; margin-top: 30px">
                    <button
                        v-for="(tab, key) in tabs"
                        :key="key"
                        @click="switchTab(key as ListType)"
                        :style="{
                            color: currentTab === key ? '#409eff' : '#8a8a8a',
                            fontWeight: currentTab === key ? 'bold' : 'normal',
                            textDecoration:
                                currentTab === key ? 'underline' : 'none',
                            marginRight: '15px',
                            background: 'none',
                            border: 'none',
                            cursor: 'pointer',
                        }"
                    >
                        {{ tab.title }}
                    </button>
                </div>
            </div>
        </div>

        <div class="gard">
            <div v-for="(item, index) in gamesList" class="card">
                <div style="display: flex">
                    <p
                        :style="{
                            color: globalTheme === 'dark' ? '#CCC' : 'black',
                        }"
                        class="rank-index"
                    >
                        {{ index + 1 }}
                    </p>
                    <img
                        alt="cover"
                        class="games-img"
                        :src="item.gameImageUrl"
                    />
                    <div style="width: 90%">
                        <!-- 名字 -->
                        <div
                            style="display: flex; align-items: center"
                            class="text-with-tags app-title"
                        >
                            <div style="display: flex; align-items: center">
                                <svg
                                    v-if="item.gameScore >= 9"
                                    t="1739285518314"
                                    class="icon"
                                    viewBox="0 0 1024 1024"
                                    version="1.1"
                                    xmlns="http://www.w3.org/2000/svg"
                                    p-id="12697"
                                    width="13"
                                    height="13"
                                >
                                    <path
                                        d="M491.4176 935.1168L393.216 983.04a46.9504 46.9504 0 0 1-62.1056-20.224l-51.2-96.4608a47.0016 47.0016 0 0 0-33.3824-24.2176l-107.52-18.8928a46.9504 46.9504 0 0 1-38.4-52.7872l15.36-108.1344a46.9504 46.9504 0 0 0-12.8-39.168L27.392 544.6656a46.9504 46.9504 0 0 1 0-65.28l75.9296-78.5408a46.8992 46.8992 0 0 0 12.6976-39.168L100.6592 253.44a46.9504 46.9504 0 0 1 38.4-52.7872l107.5712-18.8928a47.0016 47.0016 0 0 0 33.3824-24.2176l51.2-96.4608A46.8992 46.8992 0 0 1 393.2672 40.9088l98.1504 47.9744c12.9536 6.3488 28.16 6.3488 41.1648 0L630.784 40.96a46.9504 46.9504 0 0 1 62.1056 20.224l51.2 96.4608c6.8096 12.8 19.0976 21.76 33.3824 24.2176l107.52 18.8928a46.9504 46.9504 0 0 1 38.4 52.7872l-15.36 108.1344c-1.9968 14.336 2.7136 28.7744 12.8 39.168l75.8784 78.5408a46.9504 46.9504 0 0 1 0 65.28l-75.9296 78.5408a46.8992 46.8992 0 0 0-12.6976 39.168l15.3088 108.1344a46.9504 46.9504 0 0 1-38.4 52.7872l-107.52 18.8928a47.0016 47.0016 0 0 0-33.3824 24.2176l-51.2 96.4608a46.9504 46.9504 0 0 1-62.1056 20.224l-98.1504-47.9744a46.8992 46.8992 0 0 0-41.1648 0z"
                                        fill="#F5CF41"
                                        p-id="12698"
                                    ></path>
                                    <path
                                        d="M512 864a352 352 0 1 1 0-704 352 352 0 0 1 0 704z m0-46.9504a305.0496 305.0496 0 1 0 0-610.0992 305.0496 305.0496 0 0 0 0 610.0992z"
                                        fill="#EEC437"
                                        p-id="12699"
                                    ></path>
                                    <path
                                        d="M344.832 373.8624l65.9456 83.968a9.3696 9.3696 0 0 0 14.7968 0l79.0528-100.7104a9.3696 9.3696 0 0 1 14.7968 0l79.0016 100.6592a9.3696 9.3696 0 0 0 14.7456 0l66.0992-83.968a9.3696 9.3696 0 0 1 16.6912 6.912l-27.4432 235.776H355.584l-27.4944-235.7248a9.3696 9.3696 0 0 1 16.7424-6.912z"
                                        fill="#FFF2A0"
                                        p-id="12700"
                                    ></path>
                                    <path
                                        d="M370.4832 646.4h283.0336c8.2432 0 14.8992 6.656 14.8992 14.9504v17.1008c0 8.192-6.656 14.8992-14.8992 14.8992H370.4832a14.9504 14.9504 0 0 1-14.8992-14.8992v-17.1008c0-8.2944 6.656-14.9504 14.8992-14.9504z"
                                        fill="#FBE975"
                                        p-id="12701"
                                    ></path>
                                </svg>
                                <svg
                                    v-if="
                                        item.gameScore >= 8.5 &&
                                        item.gameScore < 9
                                    "
                                    t="1739286658891"
                                    class="icon"
                                    viewBox="0 0 1024 1024"
                                    version="1.1"
                                    xmlns="http://www.w3.org/2000/svg"
                                    p-id="5750"
                                    width="13"
                                    height="13"
                                >
                                    <path
                                        d="M491.4176 935.1168L393.216 983.04a46.9504 46.9504 0 0 1-62.1056-20.224l-51.2-96.4608a47.0016 47.0016 0 0 0-33.3824-24.2176l-107.52-18.8928a46.9504 46.9504 0 0 1-38.4-52.7872l15.36-108.1344a46.9504 46.9504 0 0 0-12.8-39.168L27.392 544.6656a46.9504 46.9504 0 0 1 0-65.28l75.9296-78.5408a46.8992 46.8992 0 0 0 12.6976-39.168L100.6592 253.44a46.9504 46.9504 0 0 1 38.4-52.7872l107.5712-18.8928a47.0016 47.0016 0 0 0 33.3824-24.2176l51.2-96.4608A46.8992 46.8992 0 0 1 393.2672 40.9088l98.1504 47.9744c12.9536 6.3488 28.16 6.3488 41.1648 0L630.784 40.96a46.9504 46.9504 0 0 1 62.1056 20.224l51.2 96.4608c6.8096 12.8 19.0976 21.76 33.3824 24.2176l107.52 18.8928a46.9504 46.9504 0 0 1 38.4 52.7872l-15.36 108.1344c-1.9968 14.336 2.7136 28.7744 12.8 39.168l75.8784 78.5408a46.9504 46.9504 0 0 1 0 65.28l-75.9296 78.5408a46.8992 46.8992 0 0 0-12.6976 39.168l15.3088 108.1344a46.9504 46.9504 0 0 1-38.4 52.7872l-107.52 18.8928a47.0016 47.0016 0 0 0-33.3824 24.2176l-51.2 96.4608a46.9504 46.9504 0 0 1-62.1056 20.224l-98.1504-47.9744a46.8992 46.8992 0 0 0-41.1648 0z"
                                        fill="#E19449"
                                        p-id="5751"
                                    ></path>
                                    <path
                                        d="M512 864a352 352 0 1 1 0-704 352 352 0 0 1 0 704z m0-46.9504a305.0496 305.0496 0 1 0 0-610.0992 305.0496 305.0496 0 0 0 0 610.0992z"
                                        fill="#D4863D"
                                        p-id="5752"
                                    ></path>
                                    <path
                                        d="M344.832 373.8624l65.9456 83.968a9.3696 9.3696 0 0 0 14.7968 0l79.0528-100.7104a9.3696 9.3696 0 0 1 14.7968 0l79.0016 100.6592a9.3696 9.3696 0 0 0 14.7456 0l66.0992-83.968a9.3696 9.3696 0 0 1 16.6912 6.912l-27.4432 235.776H355.584l-27.4944-235.7248a9.3696 9.3696 0 0 1 16.7424-6.912z"
                                        fill="#FFDCA6"
                                        p-id="5753"
                                    ></path>
                                    <path
                                        d="M370.4832 646.4h283.0336c8.2432 0 14.8992 6.656 14.8992 14.9504v17.1008c0 8.192-6.656 14.8992-14.8992 14.8992H370.4832a14.9504 14.9504 0 0 1-14.8992-14.8992v-17.1008c0-8.2944 6.656-14.9504 14.8992-14.9504z"
                                        fill="#F2C57E"
                                        p-id="5754"
                                    ></path>
                                </svg>
                                <span
                                    :style="{
                                        color:
                                            globalTheme === 'dark'
                                                ? '#CCC'
                                                : 'black',
                                    }"
                                    class="text text-default--size"
                                    >{{ item.gameName }}</span
                                >
                            </div>
                            <button
                                :style="{ backgroundColor: colorsRandom() }"
                                style="
                                    margin: 2px 4px;
                                    padding: 2px 8px;
                                    border-radius: 12px;
                                    font-size: 12px;
                                    color: white;
                                    border: none;
                                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                                    transition: all 0.2s;
                                    display: inline-flex;
                                    align-items: center;
                                    cursor: default;
                                    white-space: nowrap;
                                "
                                v-for="itemGamePlatforms in item.gamePlatforms"
                            >
                                {{ itemGamePlatforms }}
                            </button>
                        </div>
                        <!-- 分数-类别 -->
                        <div style="margin-top: 2px" class="flex-center--y">
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
                                    {{ item.gameScore }}
                                </div>
                            </div>

                            <div
                                class="tap-label-tag-group flex tap-ellipsis group--adjust game-cell__tags"
                            >
                                <a
                                    v-for="itemCategories in item.gameCategories"
                                    class="tap-router tap-router__prefetched tap-label-tag tap-label-tag--small text-hover tap-label-tag--left-point"
                                    itemprop="genre"
                                    >{{ itemCategories }}</a
                                >
                            </div>
                        </div>
                        <!-- 开发商 -->
                        <div style="display: flex">
                            <div style="margin-top: 3px; margin-left: 13px">
                                <svg
                                    t="1739267667128"
                                    class="icon"
                                    viewBox="0 0 1024 1024"
                                    version="1.1"
                                    xmlns="http://www.w3.org/2000/svg"
                                    p-id="10399"
                                    data-spm-anchor-id="a313x.search_index.0.i4.11fe3a81a67csu"
                                    width="13"
                                    height="13"
                                >
                                    <path
                                        d="M513.641383 777.624706c-4.327562 0-8.487302-1.430582-11.715833-4.025687l-66.756403-53.628408c-0.487094-0.338714-2.137687-0.893346-3.328815-0.893346l-84.58649 13.887288c-0.937348 0.153496-1.899256 0.233314-2.862187 0.233314-7.644097 0-14.888082-4.903683-17.613146-11.921517l-31.000038-79.827093c-0.338714-0.722454-1.785669-2.153036-2.560312-2.505054l-80.069617-30.196743c-8.101515-3.058662-13.21293-11.81407-11.888771-20.36584l13.066597-84.627423c0.068562-0.793062-0.468674-2.758834-0.964978-3.450589l-54.243415-66.189492c-5.485945-6.695492-5.536087-16.832364-0.11461-23.579021l53.630455-66.757426c0.455371-0.652869 0.971118-2.619664 0.889253-3.465938l-13.882172-84.446297c-1.403976-8.54256 3.623527-17.347087 11.692297-20.478404l79.823-30.997991c0.722454-0.338714 2.153036-1.784646 2.503007-2.559289l30.201859-80.07064c2.688225-7.122211 9.952676-12.094456 17.672498-12.094456 0.900509 0 1.806135 0.069585 2.688225 0.205685l84.630493 13.065574c1.230014-0.00307 2.921539-0.585331 3.447519-0.961908l66.191538-54.244438c3.255137-2.667759 7.470135-4.136204 11.866259-4.136204 4.325515 0 8.483208 1.427512 11.712763 4.02057l66.75845 53.629431c0.491187 0.342808 2.137687 0.895393 3.325745 0.895393l84.58649-13.886265c0.937348-0.154519 1.902326-0.232291 2.862187-0.232291 7.644097 0 14.888082 4.90266 17.613146 11.921517l31.000038 79.825047c0.338714 0.723477 1.782599 2.153036 2.558265 2.50403l80.07371 30.201859c8.096399 3.055592 13.205766 11.806907 11.888771 20.358677l-13.066597 84.632539c-0.068562 0.797156 0.466628 2.756787 0.961908 3.447519l54.245462 66.191538c5.483898 6.693446 5.53404 16.82827 0.116657 23.575952l-53.632501 66.76152c-0.457418 0.654916-0.971118 2.619664-0.889253 3.463891l13.880125 84.449367c1.403976 8.540514-3.620457 17.342993-11.692297 20.47738l-79.81993 30.996968c-0.722454 0.338714-2.153036 1.782599-2.503007 2.555196l-30.203906 80.07371c-2.681062 7.120165-9.948583 12.094456-17.668405 12.094456-0.905626 0-1.810229-0.068562-2.695389-0.205685l-84.625376-13.06455c-1.232061 0.002047-2.923586 0.585331-3.449565 0.961908l-66.189492 54.243415C522.252505 776.154215 518.037507 777.624706 513.641383 777.624706zM431.841356 698.009438c5.845125 0 12.32777 2.171456 16.518209 5.53404l65.272609 52.43728 64.753793-53.06559c5.037736-4.123924 13.44829-6.469342 19.976984-5.454222l82.737376 12.771885 29.547966-78.332043c2.352581-6.245238 8.344039-12.294001 14.56881-14.710027l78.039377-30.309306-13.576203-82.611509c-1.083681-6.588045 1.081635-14.823614 5.26184-20.027126l52.432163-65.267493-53.06559-64.753793c-4.233418-5.166673-6.476505-13.380752-5.456269-19.976984l12.773931-82.737376-78.332043-29.546943c-6.247284-2.355651-12.296047-8.347109-14.71412-14.570857l-30.30726-78.042447-82.611509 13.57825c-6.489808 1.064238-14.975063-1.201361-20.027126-5.263887l-65.265446-52.430116-64.753793 53.064566c-5.033643 4.122901-13.455453 6.462179-19.974937 5.456269l-82.741469-12.773931-29.543873 78.332043c-2.358721 6.248308-8.350179 12.297071-14.570857 14.713097l-78.042447 30.30726 13.57825 82.611509c1.081635 6.583952-1.081635 14.817474-5.259794 20.023032l-52.435233 65.271586 53.06559 64.754816c4.229325 5.166673 6.471388 13.376659 5.456269 19.96982l-12.773931 82.745563 78.332043 29.54592c6.247284 2.358721 12.296047 8.348132 14.710027 14.56574l30.309306 78.044494 82.613556-13.57825C429.452959 698.103582 430.632831 698.009438 431.841356 698.009438z"
                                        p-id="10400"
                                    ></path>
                                    <path
                                        d="M690.417529 960.78526c-3.427052 0-6.691399-1.675152-8.677637-4.564969L542.712489 754.007823c-3.296069-4.793166-2.082428-11.351536 2.710738-14.648628 4.796236-3.291976 11.356652-2.080382 14.648628 2.710738L685.905772 925.092339l27.019377-107.079784c1.302669-5.157463 6.233981-8.53335 11.514241-7.877411l109.836571 13.658068L720.585619 653.812843c-3.234671-4.837168-1.936095-11.377118 2.899026-14.611789 4.835122-3.232624 11.379165-1.934049 14.611789 2.899026l126.501112 189.133591c2.293229 3.427052 2.375094 7.875365 0.210801 11.384281-2.162246 3.508917-6.167466 5.420453-10.265808 4.926196l-123.465986-15.35471-30.442336 120.638591c-1.013073 4.01443-4.279467 7.060813-8.353248 7.792477C691.658799 960.730001 691.035606 960.78526 690.417529 960.78526z"
                                        p-id="10401"
                                        data-spm-anchor-id="a313x.search_index.0.i3.11fe3a81a67csu"
                                        class="selected"
                                        fill="#d81e06"
                                    ></path>
                                    <path
                                        d="M349.234963 960.78526c-0.6191 0-1.24127-0.055259-1.863441-0.164752-4.073782-0.731664-7.340175-3.781116-8.353248-7.792477l-30.439266-120.638591-123.468033 15.35471c-4.103458 0.496304-8.105608-1.417279-10.265808-4.926196-2.164293-3.508917-2.082428-7.957229 0.210801-11.384281l126.501112-189.133591c3.232624-4.837168 9.776667-6.135744 14.611789-2.899026 4.835122 3.234671 6.132674 9.774621 2.899026 14.611789L205.376531 823.793212l109.839641-13.658068c5.278213-0.655939 10.213619 2.725064 11.514241 7.877411l27.01733 107.079784 125.833917-183.021384c3.294023-4.79112 9.854439-6.002714 14.648628-2.710738 4.793166 3.296069 6.007831 9.854439 2.710738 14.648628L357.912599 956.220291C355.925339 959.107037 352.660992 960.78526 349.234963 960.78526z"
                                        p-id="10402"
                                        data-spm-anchor-id="a313x.search_index.0.i5.11fe3a81a67csu"
                                        class="selected"
                                        fill="#d81e06"
                                    ></path>
                                    <path
                                        d="M508.509503 588.165704c-92.391247 0-167.55718-75.165933-167.55718-167.558204s75.165933-167.558204 167.55718-167.558204c92.393293 0 167.559227 75.165933 167.559227 167.558204S600.902796 588.165704 508.509503 588.165704zM508.509503 274.116101c-80.773651 0-146.489352 65.714677-146.489352 146.491399s65.715701 146.491399 146.489352 146.491399c80.775698 0 146.492422-65.714677 146.492422-146.491399S589.286224 274.116101 508.509503 274.116101z"
                                        p-id="10403"
                                        data-spm-anchor-id="a313x.search_index.0.i2.11fe3a81a67csu"
                                        class="selected"
                                        fill="#d81e06"
                                    ></path>
                                </svg>
                            </div>
                            <span
                                style="
                                    font-family: Arial, Helvetica, sans-serif;
                                    font-size: 13px;
                                    margin-top: 1px;
                                    color: #8a8a8a;
                                "
                                >{{ item.gameDeveloper }} ·著</span
                            >
                            <span
                                style="
                                    margin-left: 8px;
                                    font-family: Arial, Helvetica, sans-serif;
                                    font-size: 13px;
                                    margin-top: 1px;
                                    color: #8a8a8a;
                                "
                                >{{ item.gameUsers }} 评价</span
                            >
                        </div>

                        <!-- 简介 -->
                        <div>
                            <p class="description">
                                {{ item.gameDescription }}
                            </p>
                        </div>
                    </div>

                    <div style="display: flex">
                        <Favorites
                            v-if="!isFavorites"
                            @click="changeFavorites()"
                            style="margin-top: 10px; margin-right: -1px"
                        />
                        <FavoritesTrue
                            v-if="isFavorites"
                            @click="changeFavorites()"
                            style="margin-top: 10px; margin-right: -1px"
                        />
                        <Like
                            @click="showRatingCard(item.id)"
                            style="margin-right: 10px; margin-top: 10px"
                        />
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.game {
    overflow-y: auto;
    height: 100vh;
}
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
    width: 92%;
    margin-left: 30px;
    margin-bottom: 20px;
    z-index: 1;
    overflow: visible;
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

    min-width: 100px;
    max-width: 100px;
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

.description {
    color: #666;
    font-size: 0.95rem;
    display: -webkit-box; /* 必须，开启弹性盒子布局 */
    -webkit-line-clamp: 2; /* 设置显示的行数为 6 行 */
    line-clamp: 2;
    -webkit-box-orient: vertical; /* 必须，设置盒子方向为垂直 */
    overflow: hidden; /* 必须，隐藏多余的内容 */
    text-overflow: ellipsis; /* 添加省略号 */
    max-height: calc(1.4em * 4); /* 设置高度为 6 行高度 */
    font-family: wenquanyimihei;
    font-size: 11px;
    margin-top: 5px !important;
    margin-left: 15px;
    margin-right: 40px;
}

/* Loading 遮罩层 */
.loading-overlay {
    position: fixed;
    top: 0;
    right: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(58, 58, 58, 0.8);
    /* 半透明背景 */
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 2;
    /* 确保遮罩层在最上层 */
}
</style>
