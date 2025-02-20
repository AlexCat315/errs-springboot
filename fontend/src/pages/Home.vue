<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

// 全屏滚动相关
const sections = ref(['hero', 'features', 'recommendations', 'ai', 'screenshots', 'download'])
const activeSection = ref(0)
let isScrolling = false
let wheelListener = null;
const scrollTimeout = ref(1200)
let lastScrollTime = 0; // 记录上次滚动的时间戳

const handleWheel = (e) => {
  e.preventDefault();
  if (isScrolling) return;

  const currentTime = Date.now();
  if (currentTime - lastScrollTime < scrollTimeout.value) {
    return; // 节流：如果在 timeout 时间内再次滚动，则忽略
  }

  let delta = Math.sign(e.deltaY);
  let newIndex = activeSection.value;

  if (delta > 0) {
    newIndex = Math.min(activeSection.value + 1, sections.value.length - 1);
  } else if (delta < 0) {
    newIndex = Math.max(activeSection.value - 1, 0);
  }

  if (newIndex !== activeSection.value) {
    isScrolling = true;
    activeSection.value = newIndex;
    document.getElementById(sections.value[newIndex]).scrollIntoView({
      behavior: 'smooth'
    });
    lastScrollTime = currentTime; // 更新上次滚动时间
    setTimeout(() => { isScrolling = false }, scrollTimeout.value)
  }
}

const scrollToSection = (index) => {
  activeSection.value = index
  document.getElementById(sections.value[index]).scrollIntoView({
    behavior: 'smooth'
  })
}

// 动态图标及功能特性（保留原有逻辑，并增加动效）
const platformIcons = ref([
  'src/assets/img/home/windows.png',
  'src/assets/img/home/macOS.png',
  'src/assets/img/home/linux.png'
])
const features = ref([
  {
    title: '全平台支持',
    desc: '原生支持 Linux/Windows/macOS 系统',
    icon: platformIcons.value[0],
    bg: '#212121', // 黑色背景
    currentIconIndex: 0
  },
  {
    title: '智能推荐',
    desc: '接入深度推理 AI 模型 DeepSeek，提供精准的个性化推荐',
    icon: 'src/assets/img/home/deepseek.png',
    bg: '#212121' // 黑色背景
  },
  {
    title: '多类型评分',
    desc: '支持电影、书籍、音乐等文娱内容评分',
    icon: 'src/assets/img/home/评分.png',
    bg: '#212121' // 黑色背景
  }
])
onMounted(() => {
  // 轮询切换平台图标
  const platformFeature = features.value[0]
  setInterval(() => {
    platformFeature.currentIconIndex = (platformFeature.currentIconIndex + 1) % platformIcons.value.length
    platformFeature.icon = platformIcons.value[platformFeature.currentIconIndex]
  }, 4000)

  wheelListener = (e) => handleWheel(e);
  window.addEventListener('wheel', wheelListener, { passive: false })
})
onUnmounted(() => {
  if (wheelListener) {
    window.removeEventListener('wheel', wheelListener);
  }
})

// 推荐类型数据（支持电影、音乐、书籍、游戏）
const mediaTypes = ref([
  {
    type: '电影',
    icon: 'src/assets/img/home/电影.png',
    examples: ['肖申克的救赎', '星际穿越', '霸王别姬'],
    color: '#ff6b6b'
  },
  {
    type: '音乐',
    icon: 'src/assets/img/home/音乐.png',
    examples: ['古典交响乐', '独立摇滚', '电子音乐'],
    color: '#4ecdc4'
  },
  {
    type: '书籍',
    icon: 'src/assets/img/home/book.png',
    examples: ['百年孤独', '三体', '人类简史'],
    color: '#45b7d1'
  },
  {
    type: '游戏',
    icon: 'src/assets/img/home/游戏.png',
    examples: ['塞尔达传说', '原神', '巫师3'],
    color: '#96ceb4'
  }
])

// 截图预览数据
const screenshots = ref([
  {
    img: 'src/assets/img/home/show/探索.png',
    title: '个性化推荐页面',
    desc: '智能内容推荐'
  },
  {
    img: 'src/assets/img/home/show/rating.png',
    title: '评分详情界面',
    desc: '直观的评分体系与评论系统'
  },
  {
    img: 'src/assets/img/home/show/ReovAi.png',
    title: 'AI 问答展示',
    desc: '最好的 AI，最优的答案'
  },
  {
    img: 'src/assets/img/home/show/游戏.png',
    title: '游戏展示',
    desc: '最热门的游戏推荐'
  },
  {
    img: 'src/assets/img/home/show/音乐.png',
    title: '音乐展示',
    desc: '最热门的音乐推荐'
  },
  {
    img: 'src/assets/img/home/show/Book.png',
    title: '书籍展示',
    desc: '最热门的书籍推荐'
  },
  {
    img: 'src/assets/img/home/show/电影.png',
    title: '电影展示',
    desc: '最热门的电影推荐'
  },
  {
    img: 'src/assets/img/home/show/搜索.png',
    title: '搜索',
    desc: '智慧赋能，探索无限可能'
  },
  {
    img: 'src/assets/img/home/show/设置.png',
    title: '设置',
    desc: '一键设置，轻松定制'
  },
])
const downloads = ref([
  {
    os: 'Windows',
    osType: 'windows',
    icon: 'src/assets/img/home/windows.png'
  },
  {
    os: 'macOS',
    osType: 'macos',
    icon: 'src/assets/img/home/macOS.png'
  },
  {
    os: 'Linux',
    osType: 'linux',
    icon: 'src/assets/img/home/linux.png'
  }
])
const handleDownload = (osType) => {
  // 直接创建链接触发下载
  const url = `http://localhost:12345/api/download/${osType}`;
  const link = document.createElement('a');
  link.href = url;
  link.style.display = 'none';
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};

const sectionGradients = ref([
  'linear-gradient(135deg, #2c3e50 0%, #3498db 100%)', // hero
  'linear-gradient(135deg, #3498db 0%, #9b59b6 100%)', // features
  'linear-gradient(135deg, #9b59b6 0%, #e74c3c 100%)', // recommendations
  'linear-gradient(135deg, #e74c3c 0%, #f39c12 100%)', // ai
  'linear-gradient(135deg, #f39c12 0%, #1abc9c 100%)', // screenshots
  'linear-gradient(135deg, #1abc9c 0%, #2ecc71 100%)'  // download
])
</script>

<template>
  <main class="scroll-container">
    <!-- 导航指示器 -->
    <div class="nav-dots">
      <div v-for="(_, index) in sections" :key="index" :class="['dot', { active: activeSection === index }]"
        @click="scrollToSection(index)"></div>
    </div>

    <!-- 首屏 Hero -->
    <section id="hero" class="full-screen hero-section" :style="{ background: sectionGradients[0] }">
      <div class="section-content">
        <h1 class="logo">Revo<span>Cat</span></h1>
        <p class="slogan">您的智能文娱导航专家</p>
        <div class="scroll-hint">
          <span>向下探索</span>
          <div class="mouse"></div>
        </div>
      </div>
    </section>

    <!-- 功能特性 -->
    <section id="features" class="full-screen features-section" :style="{ background: sectionGradients[1] }">
      <div class="section-content">
        <h2>核心功能</h2>
        <div class="features-grid">
          <div v-for="(feat, index) in features" :key="index" class="feature-card"
            :style="{ backgroundColor: feat.bg }">
            <div class="animated-icon">
              <img :src="feat.icon" :alt="feat.title">
            </div>
            <h3>{{ feat.title }}</h3>
            <p style="color: #ddd;">{{ feat.desc }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 推荐类型 -->
    <section id="recommendations" class="full-screen types-section" :style="{ background: sectionGradients[2] }">
      <div class="section-content">
        <h2>支持推荐类型</h2>
        <div class="media-grid">
          <div v-for="(media, index) in mediaTypes" :key="index" class="media-card"
            :style="{ '--hover-color': media.color }">
            <img :src="media.icon" :alt="media.type">
            <h3>{{ media.type }}</h3>
            <ul>
              <li v-for="(example, i) in media.examples" :key="i">{{ example }}</li>
            </ul>
          </div>
        </div>
      </div>
    </section>

    <!-- DeepSeek AI 集成 -->
    <section id="ai" class="full-screen ai-section" :style="{ background: sectionGradients[3] }">
      <div class="section-content ai-content">
        <div class="ai-demo">
          <img src="src/assets/img/home/ai-demo.gif" alt="AI 演示">
        </div>
        <div class="ai-description">
          <h2>DeepSeek AI 深度集成</h2>
          <ul>
            <li>🎯 智能内容理解与匹配</li>
            <li>💡 个性化推荐引擎</li>
            <li>🤖 自然语言交互推荐</li>
            <li>📈 持续学习优化算法</li>
          </ul>
        </div>
      </div>
    </section>

    <!-- 截图预览 Section（应用界面预览） -->
    <section id="screenshots" class="full-screen screenshots-section" :style="{ background: sectionGradients[4] }">
      <div class="section-content">
        <h2>应用界面预览</h2>
        <div class="gallery">
          <div v-for="(screen, index) in screenshots" :key="index" class="screenshot-item">
            <img :src="screen.img" :alt="screen.title" class="screenshot-img">
            <div class="screenshot-info">
              <h3>{{ screen.title }}</h3>
              <p>{{ screen.desc }}</p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 下载区域 -->
    <section id="download" class="full-screen download-section" :style="{ background: sectionGradients[5] }">
      <div class="section-content">
        <h2>立即体验</h2>
        <div class="download-grid">
          <a v-for="(item, index) in downloads" :key="index" class="download-card"
            @click.prevent="handleDownload(item.osType)">
            <img :src="item.icon" :alt="item.os">
            <div class="os-info">
              <span>{{ item.os }}</span>
              <small>最新版本 v0.1.1</small>
            </div>
          </a>
        </div>
      </div>
    </section>
  </main>
</template>

<style scoped>
/* 整体滚动容器 */
.scroll-container {
  height: 100vh;
  overflow-y: scroll;
  scroll-snap-type: y mandatory;
  scroll-behavior: smooth;
  position: relative;
}

/* 每个全屏 Section */
.full-screen {
  height: 100vh;
  scroll-snap-align: start;
  padding: 4rem;
  box-sizing: border-box;
  position: relative;
  color: #fff;
  /* 默认文字颜色改为白色 */
  background-size: 200% 200%;
  /* Larger background size for animation */
  transition: background 0.8s ease;
  /* Keep transition for other background changes if needed, and increase duration for smoother transition */
}


/* 导航指示器 */
.nav-dots {
  position: fixed;
  right: 2rem;
  top: 50%;
  transform: translateY(-50%);
  z-index: 100;
}

.dot {
  width: 12px;
  height: 12px;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 50%;
  margin: 1rem 0;
  cursor: pointer;
  transition: all 0.3s ease;
}

.dot.active {
  background: #fff;
  transform: scale(1.4);
}

/* 通用内容动效 */
@keyframes fadeInSlide {
  from {
    opacity: 0;
    transform: translateY(20px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.section-content {
  animation: fadeInSlide 1s ease-out;
  text-align: center;
  width: 100%;
}

/* Hero 页面 */
.hero-section {
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  position: relative;
}

.logo {
  font-size: 4.5rem;
  animation: float 3s ease-in-out infinite;
}

.logo span {
  color: #ff7f50;
}

.slogan {
  font-size: 1.5rem;
  margin-top: 1rem;
}

.scroll-hint {
  margin-top: 3rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
}

.mouse {
  width: 24px;
  height: 40px;
  border: 2px solid #fff;
  border-radius: 12px;
  position: relative;
}

.mouse::after {
  content: '';
  position: absolute;
  top: 6px;
  left: 50%;
  transform: translateX(-50%);
  width: 4px;
  height: 4px;
  background: #fff;
  border-radius: 50%;
  animation: scroll 1.5s infinite;
}

/* LOGO 浮动动画 */
@keyframes float {

  0%,
  100% {
    transform: translateY(0);
  }

  50% {
    transform: translateY(-15px);
  }
}

/* 鼠标滚动动画 */
@keyframes scroll {
  0% {
    opacity: 1;
  }

  100% {
    opacity: 0;
    transform: translateY(10px);
  }
}

/* 功能特性 Section */
.features-section {
  color: #fff;
  /* 文字颜色为白色 */
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
  margin-top: 2rem;
}

.feature-card {
  padding: 1.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  /* 阴影颜色调整 */
  transition: transform 0.3s ease;
}

.feature-card:hover {
  transform: translateY(-10px);
}

.animated-icon img {
  width: 64px;
  height: 64px;
  margin-bottom: 1rem;
}

/* 推荐类型 Section */
.types-section {
  color: #fff;
  /* 文字颜色为白色 */
}

.media-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 2rem;
  margin-top: 2rem;
}

.media-card {
  padding: 1.5rem;
  border-radius: 12px;
  background: #212121;
  /* 深色卡片背景 */
  transition: transform 0.3s, box-shadow 0.3s;
}

.media-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.5);
  /* 阴影颜色调整 */
}

.media-card img {
  width: 48px;
  height: 48px;
  margin-bottom: 1rem;
}

/* AI 集成 Section */
.ai-section {
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
}

.ai-content {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
  gap: 2rem;
}

.ai-demo img {
  width: 300px;
  border-radius: 12px;
}

.ai-description ul {
  list-style: none;
  padding: 0;
  text-align: left;
}

.ai-description li {
  margin: 0.5rem 0;
  font-size: 1.1rem;
}

/* 截图预览 Section */
.screenshots-section {
  color: #fff;
  padding: 4rem 2rem;
}

.gallery {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  margin-top: 2rem;
}

.screenshot-item {
  position: relative;
  overflow: hidden;
  border-radius: 16px;
  transition: transform 0.4s cubic-bezier(0.23, 1, 0.32, 1),
    box-shadow 0.4s cubic-bezier(0.23, 1, 0.32, 1);
  perspective: 1000px;
}

.screenshot-item:hover {
  transform: scale(1.08) rotateY(2deg);
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.5);
  /* 阴影颜色调整 */
}

.screenshot-img {
  width: 100%;
  display: block;
  border-radius: 16px;
  transition: transform 0.4s ease;
}

.screenshot-item:hover .screenshot-img {
  transform: scale(1.05);
}

.screenshot-info {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0, 0, 0, 0.7);
  /* 略微调整透明度 */
  color: #fff;
  padding: 1rem;
  transform: translateY(100%);
  transition: transform 0.4s ease;
  backdrop-filter: blur(4px);
  text-shadow: 0 1px 3px rgba(0, 0, 0, 0.8);
}

.screenshot-item:hover .screenshot-info {
  transform: translateY(0);
}

/* 下载 Section */
.download-section {
  color: #fff;
}

.download-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 2rem;
  margin-top: 2rem;
  justify-items: center;
}

.download-card {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border-radius: 15px;
  padding: 2rem;
  text-decoration: none;
  color: inherit;
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.download-card:hover {
  background: rgba(255, 255, 255, 0.2);
  transform: scale(1.05);
  /* 鼠标设为手型 */
  cursor: pointer;
}

.download-card img {
  width: 48px;
  height: 48px;
  margin-bottom: 1rem;
}

.os-info span {
  font-size: 1.2rem;
  font-weight: bold;
}

.os-info small {
  font-size: 0.9rem;
  opacity: 0.8;
}

/* 移动端响应 */
@media (max-width: 768px) {
  .full-screen {
    padding: 2rem;
    height: auto;
    min-height: 100vh;
  }

  .logo {
    font-size: 3rem;
  }

  .features-grid,
  .media-grid,
  .download-grid,
  .gallery {
    grid-template-columns: 1fr;
  }

  .ai-content {
    flex-direction: column;
  }
}

/* 截图预览 Section */
.screenshots-section {
  padding: 4rem 2rem;
}

.gallery {
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  margin-top: 2rem;
}

.screenshot-item {
  border-radius: 16px;
  transition: transform 0.4s cubic-bezier(0.23, 1, 0.32, 1),
    box-shadow 0.4s cubic-bezier(0.23, 1, 0.32, 1);
}

.screenshot-img {
  border-radius: 16px;
}

@media screen and (min-width: 768px) {
  img[alt="AI 演示"] {
    width: 700px;
    /* 在大屏幕上设置固定宽度为 700px */
  }
}
</style>
