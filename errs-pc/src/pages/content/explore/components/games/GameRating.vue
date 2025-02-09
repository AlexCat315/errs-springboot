<script lang="ts" setup>


// 定义接收的评分属性
const props = defineProps<{
  rating: number;
}>();

// 计算完整的星星数量
const fullStars = Math.floor(props.rating);
const hasHalfStar = props.rating % 1 >= 0.5;
</script>

<template>
  <div class="rating">
    <span
      v-for="index in 5"
      :key="index"
      :class="{ full: index <= fullStars, half: index === fullStars && hasHalfStar }"
      class="star"
    >
      ★
    </span>
    <span class="rating-text">{{ props.rating }}/5</span>
  </div>
</template>

<style scoped>
.star {
  font-size: 1.2rem;
  color: #ddd;
  position: relative;
}

.star.full {
  color: #ffd700;
}

.star.half::before {
  content: '★';
  position: absolute;
  left: 0;
  width: 50%;
  overflow: hidden;
  color: #ffd700;
}

.rating-text {
  margin-left: 0.5rem;
  font-size: 0.9rem;
  color: var(--text-secondary);
}
</style>
