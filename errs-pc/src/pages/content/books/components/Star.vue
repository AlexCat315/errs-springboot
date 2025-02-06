<template>
  <div>
    <p class="cleanfloat starFive" :class="starClass">
      <!-- 使用 v-for 渲染整颗星星 -->
      <span v-for="(_, index) in totalStars" :key="index" class="org_star">R</span>

      <!-- 渲染半颗星星 -->
      <span v-if="halfStars" class="org_star" :data-content="'R'"></span>

      <!-- 渲染剩余的空白星星 -->
      <span v-for="(_, index) in remainingStars" :key="index + totalStars" class="org_star">R</span>

    </p>
  </div>
</template>

<script lang="ts" setup>
import { defineProps, computed } from 'vue';

const props = defineProps({
  score: {
    type: Number,
    required: true
  }
});

// 将10分制的评分转换为5分制
const starScore = computed(() => props.score / 2);

// 计算显示的整颗星星数量
const totalStars = computed(() => Math.floor(starScore.value));

// 判断是否有半颗星星
const halfStars = computed(() => starScore.value % 1 >= 0.5);

// 计算剩余的空白星星数量
const remainingStars = computed(() => 5 - totalStars.value - (halfStars.value ? 1 : 0));

// 根据评分计算starClass
const starClass = computed(() => {
  if (starScore.value >= 4.76) return 'sF1';
  if (starScore.value >= 3.76) return 'sF2';
  if (starScore.value >= 2.76) return 'sF3';
  return '';
});
</script>

<style scoped>
.cleanfloat::after {
  display: block;
  clear: both;
  content: "";
  visibility: hidden;
  height: 0;
}

* {
  margin: 0;
  padding: 0;
}

/* 字体路径，注意修改为你自己的路径 */
@font-face {
  font-family: 'AlluraRegular';
  src: url('src/assets/ttf/WebSymbols-Regular.otf');
}

.starFive span {
  display: block;
  float: left;
  font-size: 17px;
  font-family: 'AlluraRegular';
  text-align: center;
  color: #888;
  width: 27px;
  height: 33px;
  line-height: 33px;
  margin-right: 5px;
  position: relative;
  overflow: hidden;
  white-space: pre;
}

.starFive span:before {
  position: absolute;
  left: 0;
  top: 0;
  display: block;
  width: 50%;
  content: attr(data-content);
  overflow: hidden;
  color: #f63;
}

.sF1 span:before {
  width: 70%;
}

.sF2 span:before {
  width: 50%;
}

.sF3 span:before {
  width: 40%;
}

.starFive .org_star {
  color: #f63;
}

.starFive b {
  font-weight: normal;
  line-height: 40px;
  font-size: 25px;
  color: #888;
  margin-left: 10px;
}
</style>
