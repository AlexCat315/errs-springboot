<template>
  <div class="movie-card">
    <div class="card-header">
      <h2 class="title">观影评价</h2>
      <div class="rating">
        <span class="prompt">评分：</span>
        <div class="stars">
          <button
            v-for="star in 5"
            :key="star"
            class="star"
            :class="{ 'active': currentRating >= star }"
            @click="rate(star)"
            @mouseover="hoverRating = star"
            @mouseleave="hoverRating = 0"
          >
            ★
          </button>
        </div>
        <span class="score">{{ currentRating || hoverRating || '0.0' }}</span>
      </div>
    </div>

    <div class="card-body">
      <textarea
        v-model="comment"
        class="comment-input"
        placeholder="写下你的观后感..."
        rows="4"
      ></textarea>
      
      <button 
        class="submit-btn"
        :disabled="!currentRating"
        @click="submitReview"
      >
        🎬 提交评价
      </button>
    </div>

    <div class="reviews" v-if="comments.length">
      <div 
        v-for="(review, index) in comments"
        :key="index"
        class="review-item"
      >
        <div class="user-info">
          <img :src="review.avatar" class="avatar" alt="用户头像">
          <div class="meta">
            <span class="username">{{ review.username }}</span>
            <div class="rating">
              <span class="stars">{{ '★'.repeat(review.rating) }}</span>
              <span class="time">{{ formatTime(review.timestamp) }}</span>
            </div>
          </div>
        </div>
        <p class="content">{{ review.content }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'

interface Review {
  username: string
  avatar: string
  rating: number
  content: string
  timestamp: number
}

const currentRating = ref(0)
const hoverRating = ref(0)
const comment = ref('')
const comments = reactive<Review[]>([
  {
    username: '影迷小王',
    avatar: 'https://via.placeholder.com/40/00ff7f/ffffff?text=W',
    rating: 4,
    content: '特效震撼，剧情紧凑，值得二刷！',
    timestamp: Date.now() - 3600000
  }
])

const rate = (stars: number) => {
  currentRating.value = stars
}

const submitReview = () => {
  if (!currentRating.value || !comment.value.trim()) return

  comments.unshift({
    username: '当前用户',
    avatar: 'https://via.placeholder.com/40/ff69b4/ffffff?text=U',
    rating: currentRating.value,
    content: comment.value.trim(),
    timestamp: Date.now()
  })

  comment.value = ''
  currentRating.value = 0
}

const formatTime = (timestamp: number) => {
  return new Date(timestamp).toLocaleString('zh-CN', {
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}
</script>

<style scoped>
.movie-card {
  max-width: 700px;
  margin: 2rem auto;
  background: #fff;
  border-radius: 15px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.1);
  padding: 2rem;
  font-family: 'Helvetica Neue', Arial, sans-serif;
}

.card-header {
  border-bottom: 2px solid #f0f0f0;
  padding-bottom: 1.5rem;
  margin-bottom: 1.5rem;
}

.title {
  color: #2c3e50;
  font-size: 1.8rem;
  margin-bottom: 1.2rem;
}

.rating {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.stars {
  display: flex;
}

.star {
  font-size: 2rem;
  color: #ddd;
  cursor: pointer;
  transition: all 0.2s;
  background: none;
  border: none;
  padding: 0 0.2rem;
}

.star.active {
  color: #ffd700;
  transform: scale(1.1);
}

.star:hover {
  color: #ffd700;
  transform: scale(1.2);
}

.score {
  font-size: 1.4rem;
  color: #ff6b6b;
  font-weight: bold;
}

.comment-input {
  width: 90%;
  padding: 1rem;
  border: 2px solid #eee;
  border-radius: 8px;
  margin: 1rem 0;
  resize: vertical;
  transition: border-color 0.3s;
}

.comment-input:focus {
  outline: none;
  border-color: #3498db;
}

.submit-btn {
  background: linear-gradient(135deg, #3498db, #6c5ce7);
  color: white;
  padding: 0.8rem 2rem;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1rem;
  transition: transform 0.2s, opacity 0.2s;
}

.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.submit-btn:not(:disabled):hover {
  transform: translateY(-2px);
}

.reviews {
  margin-top: 2rem;
}

.review-item {
  background: #f9f9f9;
  border-radius: 10px;
  padding: 1.2rem;
  margin-bottom: 1.5rem;
  animation: slideIn 0.3s ease-out;
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.user-info {
  display: flex;
  align-items: center;
  margin-bottom: 0.8rem;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 1rem;
}

.meta {
  flex: 1;
}

.username {
  font-weight: bold;
  color: #2c3e50;
}

.time {
  color: #95a5a6;
  font-size: 0.9rem;
  margin-left: 1rem;
}

.content {
  color: #34495e;
  line-height: 1.6;
  margin: 0;
}
</style>