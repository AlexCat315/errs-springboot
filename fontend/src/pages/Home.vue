<script setup lang="ts">
import { ref } from 'vue'

const features = ref([
  {
    title: '全平台支持',
    desc: '原生支持Linux/Windows/macOS系统',
    icon: '💻'
  },
  {
    title: '智能推荐',
    desc: '基于深度学习的个性化推荐引擎',
    icon: '🧠'
  },
  {
    title: '多类型评分',
    desc: '支持电影/书籍/音乐等文娱内容评分',
    icon: '⭐'
  }
])

const downloads = ref([
  { os: 'Windows', icon: '🪟', link: '#' },
  { os: 'macOS', icon: '🍎', link: '#' },
  { os: 'Linux', icon: '🐧', link: '#' }
])
</script>

<template>
  <div class="container">
    <!-- Hero Section -->
    <section class="hero">
      <h1 class="logo">Revo<span>Cat</span></h1>
      <p class="slogan">发现属于你的文娱世界</p>
      <div class="cta-buttons">
        <a 
          v-for="(item, index) in downloads"
          :key="index"
          :href="item.link"
          class="download-btn"
        >
          {{ item.icon }} 下载 {{ item.os }} 版
        </a>
      </div>
    </section>

    <!-- Features -->
    <section class="features">
      <div 
        v-for="(feat, index) in features"
        :key="index"
        class="feature-card"
      >
        <div class="icon">{{ feat.icon }}</div>
        <h3>{{ feat.title }}</h3>
        <p>{{ feat.desc }}</p>
      </div>
    </section>

    <!-- Screenshots -->
    <section class="screenshots">
      <h2>应用界面预览</h2>
      <div class="gallery">
        <!-- <img src="@/assets/screen1.jpg" alt="界面截图">
        <img src="@/assets/screen2.jpg" alt="界面截图">
        <img src="@/assets/screen3.jpg" alt="界面截图"> -->
      </div>
    </section>
  </div>
</template>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

.hero {
  text-align: center;
  padding: 4rem 0;
}

.logo {
  font-size: 4rem;
  color: #6d5dfc;
  margin-bottom: 1rem;
}

.logo span {
  color: #ff7f50;
}

.slogan {
  font-size: 1.5rem;
  color: #666;
  margin-bottom: 2rem;
}

.cta-buttons {
  display: flex;
  gap: 1rem;
  justify-content: center;
}

.download-btn {
  background: #6d5dfc;
  color: white;
  padding: 1rem 2rem;
  border-radius: 8px;
  text-decoration: none;
  transition: transform 0.2s;
}

.download-btn:hover {
  transform: translateY(-2px);
}

.features {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  padding: 4rem 0;
}

.feature-card {
  text-align: center;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
}

.feature-card .icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.screenshots {
  margin-top: 4rem;
}

.gallery {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1rem;
  margin-top: 2rem;
}

.gallery img {
  width: 100%;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
}

@media (max-width: 768px) {
  .cta-buttons {
    flex-direction: column;
  }
  
  .gallery {
    grid-template-columns: 1fr;
  }
}
</style>