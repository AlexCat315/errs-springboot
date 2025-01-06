<script lang="js" setup>

</script>


<template>
  <div style="
        max-width: 100%;
        height: 100vh;
        overflow: hidden;
        display: flex;
        font-family: system-ui;
        position: relative;">
    <!-- 背景图片 -->
    <el-image fit="cover" src="../../assets/resources/login/login.jpg"
              style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: -1;"/>
    <!-- 登录卡片 -->
    <div>
      <div class="login_name">文娱推荐与评分系统 (管理端)</div>
      <el-card class="message-card">
        <template #header>
          <div class="card-header">
                        <span style="font-family: 'yousu-title-black',serif; font-size: 25px; color: #3d405b;">
                            系统公告
                        </span>
          </div>
        </template>
        <div class="block text-center">
          <el-carousel height="200px" motion-blur>
            <el-carousel-item>
              <!--列排列-->
              <div class="content-message-list">
                <el-link :underline="false" class="content-message">文娱推荐与评分系统管理端使用指南</el-link>
                <el-link :underline="false" class="content-message">进一步优化系统可用度公告</el-link>
              </div>

            </el-carousel-item>
            <el-carousel-item>
              <div class="content-message-list">
              <el-link :underline="false" class="content-message">
                关于系统安全，请阅读《文娱推荐与评分系统安全说明书》
              </el-link>
              </div>
            </el-carousel-item>
            <el-carousel-item>
              <div class="content-message-list">
              <el-link :underline="false" class="content-message">
                进一步加强安全防范意识，落实安全准则
              </el-link>
              </div>
            </el-carousel-item>
          </el-carousel>
        </div>
      </el-card>
    </div>

    <!-- 插槽用于放置不同页面的内容 -->
    <slot name="right-card"></slot>
  </div>
</template>


<style scoped>
.right-card {
  width: 400px;
  margin-left: 80px;
  margin-top: 60px;
  margin-bottom: 60px;
  z-index: 10;
  background-color: rgba(255, 255, 255, 0.2);
  /* 半透明背景 */
  backdrop-filter: blur(50px);
  /* 磨砂效果 */
  border-radius: 10px;
  /* 圆角边框 */
  padding: 20px;
  /* 内边距 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  /* 卡片阴影 */
  border: 1px solid rgba(255, 255, 255, 0.3);
  /* 淡白色边框 */
}

.message-card {
  width: 700px;
  height: 280px;
  z-index: auto;
  margin-left: 100px;
  margin-top: 50px;
  background-color: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(3px);
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.3);
}

.login_name {
  margin-top: 200px;
  margin-left: 100px;
  display: inline-block;
  font-size: 60px;
  line-height: 1em;
  color: #e07a5f;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  font-family: 'yousu-title-black', serif;
}

.content-message {
  font-size: 16px;
  font-family: 'LXGWMarkerGothic-Regular', serif;
  display: block;
  margin-top: 13px;


}

.content-message-list{
/* 从上到下,列排列 */
  display: flex !important;
  flex-direction: column !important;
}

.demonstration {
  color: var(--el-text-color-secondary);
}

.el-carousel__item h3 {
  color: transparent;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: transparent;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: transparent;
}
</style>