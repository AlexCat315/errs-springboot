# 智能文娱推荐与评分系统

该项目是一个后端服务基于 SpringBoot 构建的智能文娱推荐与评分系统。

管理端前端采用 Vue.js + TypeScript + Vite 构建，提供用户管理、文娱信息可视化及管理、评分管理等功能。

用户端采用 Tauri + Vue.js + TypeScript + Vite 构建，提供用户信息可视化、评分管理等功能、DeepSeek问答推荐、文娱信息推荐、微信扫码登录等功能。

项目架构

项目采用前后端分离架构，前端采用 Vue.js + TypeScript + Vite 构建，后端采用 SpringBoot 构建。前端和后端通过 RESTful API 进行通信。

前端项目目录结构

    src
    ├── assets
    ├── components
    ├── pages
    ├── plugins
    ├── router
    ├── store
    ├── styles
    ├── utils
    ├── App.vue
    ├── main.ts
    └── vite.config.ts

后端项目目录结构

    pom.xml
    src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── soms
    │   │           └── soms
    │   │               ├── config
    │   │               ├── controller
    │   │               ├── entity
    │   │               ├── exception
    │   │               ├── repository
    │   │               ├── service
    │   │               └── SomsApplication.java
    │   └── resources
    │       ├── application.yml
    │       ├── log4j2.xml
    │       └── static
    └── test
        └── java
            └── com
                └── soms
                    └── soms
                        └── controller
                            └── SomsControllerTest.java




项目技术栈

    前端：Vue.js + TypeScript + Vite
    后端：SpringBoot
    数据库：MySQL
    构建工具：Maven
    版本管理：Git
    服务器：Tomcat
    前端框架：Element-Plus
    后端框架：Spring Boot
    安全框架：Spring Security
    日志框架：Log4j2
    代码风格检查：Checkstyle
    代码质量分析：SonarQube
    依赖管理：Maven
    数据库连接池：HikariCP
    数据库连接工具：JDBC
    数据库驱动：MySQL Connector/J
    前端构建工具：Vite
    前端组件库：Element-Plus
    前端路由：Vue Router
    前端状态管理：Vuex
    前端请求库：Axios
    前端图标库：Element-Plus Icon
    前端主题：Element-Plus Theme
    前端布局：Element-Plus Layout
    前端表单验证：Element-Plus Form
    前端图表库：Echarts
    请确保本机安装node.js(推荐使用20+)
    确保本机拥有Rust环境。

项目使用
1. 克隆项目


``` bash
git clone https://github.com/AlexCat315/errs-springboot.git
```




部署和运行

    导入项目：将项目导入到 IDEA或Vscode。
    运行npm install导入依赖和运行Maven导入依赖。
    运行SomsApplication.java启动项目。
    访问项目：在浏览器中访问 http://localhost:12345 以查看项目运行情况。

注意事项

    确保本机拥有node.js。
    确保node.js能够适配依赖。
    确保 MySQL 数据库的用户名和密码正确。
    确保数据库名称和表结构与项目要求一致。
    确保端口号 12345 未被占用。
    确保本机拥有Rust环境。
    若遇到依赖问题，检查 Maven 配置并重新导入依赖。
    若遇到数据库问题，检查数据库配置。
    若遇到端口问题，检查端口是否被占用。

贡献指南

欢迎任何形式的贡献，包括但不限于：

    提交代码
    报告 Bug
    提出新功能建议

联系方式

若有任何问题或建议，请通过 GitHub Issues 进行反馈。
