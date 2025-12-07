<script setup>
import { useRouter } from 'vue-router'
import { ArrowRight, Connection, Link, User } from '@element-plus/icons-vue'

const router = useRouter()

const scrollToFeatures = () => {
  document.getElementById('features').scrollIntoView({ behavior: 'smooth' })
}
</script>

<template>
  <div class="min-h-full font-sans selection:bg-blue-100 selection:text-blue-900">

    <!-- Hero Section -->
    <section class="relative pt-32 pb-20 lg:pt-48 lg:pb-32 overflow-hidden">
      <!-- Background Blobs -->
      <div
        class="absolute top-0 right-0 -z-10 w-[800px] h-[800px] bg-gradient-to-b from-blue-50 to-cyan-50 rounded-full blur-3xl opacity-60 translate-x-1/3 -translate-y-1/4 animate-float-slow">
      </div>
      <div
        class="absolute bottom-0 left-0 -z-10 w-[600px] h-[600px] bg-gradient-to-tr from-cyan-50 to-blue-50 rounded-full blur-3xl opacity-60 -translate-x-1/3 translate-y-1/4 animate-float-delay">
      </div>

      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center relative z-10">
        <div
          class="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-blue-50 border border-blue-100 text-blue-600 text-sm font-semibold mb-8 animate-fade-in-up">
          <span class="relative flex h-2 w-2">
            <span class="animate-ping absolute inline-flex h-full w-full rounded-full bg-blue-400 opacity-75"></span>
            <span class="relative inline-flex rounded-full h-2 w-2 bg-blue-500"></span>
          </span>
          全新 2.0 版本已上线
        </div>

        <h1
          class="text-5xl lg:text-7xl font-bold text-gray-900 tracking-tight mb-8 leading-tight animate-fade-in-up delay-100">
          构建 <span
            class="text-transparent bg-clip-text bg-gradient-to-r from-blue-600 via-cyan-500 to-blue-600 bg-300% animate-gradient">下一代</span>
          <br class="hidden md:block" />
          企业级安全认证平台
        </h1>

        <p class="text-xl text-gray-500 mb-12 max-w-2xl mx-auto leading-relaxed animate-fade-in-up delay-200">
          为您提供安全、高效、极简的身份管理服务。采用最新的技术栈，重新定义企业数字化转型的安全基石。
        </p>

        <div class="flex flex-col sm:flex-row gap-4 justify-center animate-fade-in-up delay-300">
          <button @click="scrollToFeatures"
            class="px-8 py-4 rounded-xl bg-gray-900 text-white font-bold text-lg hover:bg-gray-800 hover:-translate-y-1 transition-all shadow-xl shadow-gray-900/20 flex items-center justify-center gap-2 group">
            探索功能
            <el-icon class="group-hover:translate-x-1 transition-transform">
              <ArrowRight />
            </el-icon>
          </button>
          <button
            class="px-8 py-4 rounded-xl bg-white border border-gray-200 text-gray-700 font-bold text-lg hover:bg-gray-50 hover:border-gray-300 hover:-translate-y-1 transition-all shadow-sm">
            查看文档
          </button>
        </div>
      </div>
    </section>

    <!-- Features Section -->
    <section id="features" class="py-24 bg-gray-50/50">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <h2 class="text-3xl font-bold text-gray-900 mb-4">核心优势</h2>
          <p class="text-gray-500 max-w-2xl mx-auto">不仅仅是登录系统，更是您值得信赖的安全伙伴。</p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
          <!-- Feature 1 -->
          <div
            class="bg-white p-8 rounded-3xl shadow-sm hover:shadow-xl hover:shadow-blue-500/10 transition-all duration-300 group border border-gray-100">
            <div
              class="w-14 h-14 rounded-2xl bg-blue-50 text-blue-600 flex items-center justify-center text-2xl mb-6 group-hover:scale-110 transition-transform duration-300">
              <el-icon>
                <Connection />
              </el-icon>
            </div>
            <h3 class="text-xl font-bold text-gray-900 mb-3">极速接入</h3>
            <p class="text-gray-500 leading-relaxed">
              基于标准协议开发，提供完善的 SDK 和 API 文档，让您的业务系统在几分钟内拥有强大的认证能力。
            </p>
          </div>

          <!-- Feature 2 -->
          <div
            class="bg-white p-8 rounded-3xl shadow-sm hover:shadow-xl hover:shadow-cyan-500/10 transition-all duration-300 group border border-gray-100">
            <div
              class="w-14 h-14 rounded-2xl bg-cyan-50 text-cyan-600 flex items-center justify-center text-2xl mb-6 group-hover:scale-110 transition-transform duration-300">
              <el-icon>
                <User />
              </el-icon>
            </div>
            <h3 class="text-xl font-bold text-gray-900 mb-3">安全合规</h3>
            <p class="text-gray-500 leading-relaxed">
              内置多重加密、异地登录检测、风控拦截机制，全方位保障您的企业数据和用户信息安全。
            </p>
          </div>

          <!-- Feature 3 -->
          <div
            class="bg-white p-8 rounded-3xl shadow-sm hover:shadow-xl hover:shadow-blue-500/10 transition-all duration-300 group border border-gray-100">
            <div
              class="w-14 h-14 rounded-2xl bg-blue-50 text-blue-600 flex items-center justify-center text-2xl mb-6 group-hover:scale-110 transition-transform duration-300">
              <el-icon>
                <Link />
              </el-icon>
            </div>
            <h3 class="text-xl font-bold text-gray-900 mb-3">统一管理</h3>
            <p class="text-gray-500 leading-relaxed">
              打破信息孤岛，实现多应用单点登录 (SSO)，统一的组织架构和权限配置，极大地降低运维成本。
            </p>
          </div>
        </div>
      </div>
    </section>

  </div>
</template>

<style scoped>
@keyframes float-slow {
  0% {
    transform: translateY(0px);
  }

  50% {
    transform: translateY(-30px);
  }

  100% {
    transform: translateY(0px);
  }
}

.animate-float-slow {
  animation: float-slow 15s ease-in-out infinite;
}

@keyframes fade-in-up {
  0% {
    opacity: 0;
    transform: translateY(20px);
  }

  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-fade-in-up {
  opacity: 0;
  /* Starts hidden */
  animation: fade-in-up 0.8s ease-out forwards;
}

.delay-100 {
  animation-delay: 0.1s;
}

.delay-200 {
  animation-delay: 0.2s;
}

.delay-300 {
  animation-delay: 0.3s;
}

.bg-300% {
  background-size: 300% auto;
}

@keyframes gradient {
  0% {
    background-position: 0% 50%;
  }

  50% {
    background-position: 100% 50%;
  }

  100% {
    background-position: 0% 50%;
  }
}

.animate-gradient {
  animation: gradient 6s ease infinite;
}
</style>
