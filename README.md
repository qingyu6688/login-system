# Enterprise Authentication System (企业级认证系统)

基于 Spring Boot 3 + Vue 3 的现代化企业级认证系统，提供安全、高效、美观的身份管理解决方案。

## 🌟 项目亮点

*   **极速体验**: 采用 Vue 3 + Vite 构建，页面秒开，操作流畅。
*   **美观设计**: 深度定制 Tailwind CSS 样式，Glassmorphism 玻璃拟态风格，配备流体动画与微交互。
*   **安全可靠**: 集成 Spring Security + JWT 无状态认证，密码高强度加密，防止 SQL 注入与 XSS 攻击。
*   **灵活登录**: 支持 **用户名** 或 **手机号** 双重登录模式，自动识别账号类型。
*   **用户中心**: 支持个人资料管理、头像上传（即时预览 + 数据库记录）、密码修改等功能。
*   **日志审计**: 全方位记录登录日志，包括 IP 地址、归属地、浏览器、操作系统等信息。

## 🛠️ 技术栈

### 🎨 前端 (Frontend)
*   **核心框架**: Vue 3 (Composition API)
*   **构建工具**: Vite 5
*   **UI 组件库**: Element Plus
*   **样式库**: Tailwind CSS
*   **状态管理**: Pinia
*   **路由管理**: Vue Router 4
*   **网络请求**: Axios (封装拦截器，统一错误处理)
*   **图标库**: Element Plus Icons

### ⚙️ 后端 (Backend)
*   **核心框架**: Spring Boot 3.2
*   **ORM 框架**: MyBatis-Plus
*   **数据库**: MySQL 8.0
*   **缓存**: Redis
*   **安全框架**: Spring Security
*   **认证机制**: JWT (JSON Web Token)
*   **工具库**: Hutool (验证码、工具类), Lombok
*   **接口文档**: Knife4j (Swagger 3增强版)

## 📁 目录结构

```
login-system/
├── backend/                # 后端项目 (Maven)
│   ├── src/main/java       # Java 源码
│   │   └── com/example/login
│   │       ├── config      # 核心配置 (Security, MyBatis, Swagger)
│   │       ├── controller  # 接口控制器 (Auth, User, File)
│   │       ├── entity      # 数据库实体
│   │       ├── service     # 业务逻辑
│   │       └── ...
│   └── src/main/resources
│       ├── application.yml # 配置文件
│       └── sql/schema.sql  # 数据库初始化脚本
│
├── frontend/               # 前端项目 (Node.js)
│   ├── src/
│   │   ├── api/            # 接口封装
│   │   ├── layout/         # 布局组件
│   │   ├── stores/         # Pinia 状态管理
│   │   ├── views/          # 页面视图 (Login, Register, Profile)
│   │   └── ...
│   └── vite.config.js      # Vite 配置
│
└── README.md               # 项目说明文档
```

## 🚀 快速开始

### 1. 环境准备
*   JDK 17+
*   Node.js 18+
*   MySQL 8.0
*   Redis 6+

### 2. 数据库设置
1.  创建数据库 `login_db`。
2.  执行 `backend/src/main/resources/sql/schema.sql` 中的 SQL 脚本初始化表结构。

### 3. 后端启动
```bash
cd backend
# 修改 application.yml 中的数据库和 Redis 配置
mvn clean package
java -jar target/login-0.0.1-SNAPSHOT.jar
```
服务默认启动在 `http://localhost:8080`。

### 4. 前端启动
```bash
cd frontend
npm install
npm run dev
```
访问 `http://localhost:5173` 即可体验。

## 🔑 核心功能演示

### 1. 登录与注册
*   双因子验证（验证码）。
*   手机号唯一性校验。
*   密码强度策略（最少8位，需包含字母/数字）。

### 2. 个人中心
*   编辑昵称、邮箱、手机号。
*   **头像上传**: 支持本地预览，自动保存至服务器 `/uploads` 目录并更新数据库记录。

### 3. 首页
*   响应式导航栏，显示实时登录状态。
*   炫酷的 Hero 区域与特性展示。

## 📄 接口文档
启动后端后，访问 `http://localhost:8080/doc.html` 查看完整的在线接口文档。

## 🤝 贡献
欢迎提交 Issue 和 Pull Request！

## 📄 许可
MIT License
