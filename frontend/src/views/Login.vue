<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { User, Lock } from '@element-plus/icons-vue' // Check if Key icon exists or use generic
import { ElMessage } from 'element-plus'
import { getCaptcha } from '../api/auth'

const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const loginFormRef = ref(null)

const loginForm = reactive({
  username: '',
  password: '',
  captchaCode: '',
  captchaUuid: ''
})
const captchaImage = ref('')

const refreshCaptcha = async () => {
  try {
    const data = await getCaptcha()
    // Backend returns Map: {uuid: "...", image: "..."}
    // Interceptor returns response.data
    loginForm.captchaUuid = data.uuid
    captchaImage.value = data.image
  } catch (e) {
    console.error(e)
  }
}

onMounted(() => {
  refreshCaptcha()
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  captchaCode: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { min: 4, max: 4, message: '4位验证码', trigger: 'blur' }
  ]
}

const handleLogin = async (formEl) => {
  if (!formEl) return
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      loading.value = true
      try {
        await userStore.login(loginForm)
        ElMessage.success('登录成功')
        router.push('/')
      } catch (error) {
        // Error is handled by interceptor, but we need to refresh captcha on failure
        refreshCaptcha()
        loginForm.captchaCode = '' // Clear user input
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<template>
  <div class="min-h-screen w-full flex items-center justify-center relative overflow-hidden bg-[#e0f2fe]">
    <!-- Animated Decoration Circles -->
    <div class="absolute top-[-10%] left-[-10%] w-[500px] h-[500px] rounded-full bg-blue-400 opacity-20 blur-[100px] animate-float"></div>
    <div class="absolute bottom-[-10%] right-[-10%] w-[600px] h-[600px] rounded-full bg-cyan-400 opacity-20 blur-[120px] animate-float-delay"></div>
    
    <div class="w-full max-w-[420px] bg-white/80 backdrop-blur-xl rounded-2xl shadow-2xl p-8 z-10 border border-white/50 transition-all duration-300 hover:shadow-cyan-100 scale-in-center">
      <div class="text-center mb-10">
        <div class="inline-flex items-center justify-center w-16 h-16 rounded-full bg-gradient-to-tr from-blue-500 to-cyan-400 mb-4 shadow-lg shadow-blue-500/30">
          <el-icon class="text-white text-3xl"><User /></el-icon>
        </div>
        <h2 class="text-3xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-gray-900 to-gray-600">欢迎回来</h2>
        <p class="text-gray-500 mt-2 text-sm font-medium tracking-wide">Enterprise Unified Authentication</p>
      </div>

      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="rules"
        size="large"
        class="space-y-6"
        @keyup.enter="handleLogin(loginFormRef)"
      >
        <el-form-item prop="username">
          <el-input 
            v-model="loginForm.username" 
            placeholder="用户名 / 手机号"
            :prefix-icon="User"
            class="!rounded-xl custom-input"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input 
            v-model="loginForm.password" 
            type="password" 
            placeholder="请输入密码"
            :prefix-icon="Lock"
            show-password
            class="custom-input"
          />
        </el-form-item>

        <el-form-item prop="captchaCode">
          <div class="flex gap-4 w-full">
            <el-input 
              v-model="loginForm.captchaCode" 
              placeholder="验证码"
              :prefix-icon="Lock"
              class="custom-input flex-1"
              @keyup.enter="handleLogin(loginFormRef)"
            />
            <div 
              class="h-10 rounded-xl overflow-hidden cursor-pointer border border-gray-200 shadow-sm hover:shadow-md transition-all bg-white"
              @click="refreshCaptcha"
              title="点击刷新"
            >
              <img v-if="captchaImage" :src="captchaImage" class="h-full w-auto object-contain" />
            </div>
          </div>
        </el-form-item>

        <div class="flex justify-between items-center text-sm px-1">
          <el-checkbox label="记住我" class="!text-gray-500 font-medium" />
          <router-link to="/forgot-password" class="text-blue-600 hover:text-blue-500 font-medium hover:underline decoration-2 underline-offset-4">忘记密码?</router-link>
        </div>

        <el-button 
          type="primary" 
          class="w-full !rounded-xl !h-12 !text-lg !font-bold tracking-wide !bg-gradient-to-r !from-blue-600 !to-cyan-500 !border-0 hover:!shadow-lg hover:!shadow-blue-500/40 transition-all duration-300 hover:!opacity-90 hover:!-translate-y-0.5" 
          :loading="loading"
          @click="handleLogin(loginFormRef)"
        >
          立即登录
        </el-button>

        <div class="relative mt-8">
          <div class="absolute inset-0 flex items-center">
            <span class="w-full border-t border-gray-200"></span>
          </div>
          <div class="relative flex justify-center text-sm">
            <span class="px-2 bg-transparent bg-white/0 text-gray-400">没有账号?</span>
            <router-link to="/register" class="text-blue-600 hover:text-blue-500 font-bold ml-1 hover:underline decoration-2 underline-offset-4">
              注册新用户
            </router-link>
          </div>
        </div>
      </el-form>
    </div>
    
    <div class="absolute bottom-6 text-center w-full z-10 text-gray-400 text-xs font-medium tracking-wider">
      © 2025 Antigravity Enterprise System. All Rights Reserved.
    </div>
  </div>
</template>

<style scoped>
:deep(.el-input__wrapper) {
  background-color: #f9fafb;
  box-shadow: 0 0 0 1px #e5e7eb inset;
  padding: 8px 15px;
  border-radius: 12px;
  transition: all 0.3s ease;
}
:deep(.el-input__wrapper.is-focus) {
  background-color: white;
  box-shadow: 0 0 0 2px #3b82f6 inset !important;
}

/* Float Animation */
@keyframes float {
  0% { transform: translateY(0px) rotate(0deg); }
  50% { transform: translateY(-20px) rotate(2deg); }
  100% { transform: translateY(0px) rotate(0deg); }
}
.animate-float {
  animation: float 8s ease-in-out infinite;
}
.animate-float-delay {
  animation: float 10s ease-in-out infinite;
  animation-delay: 2s;
}

/* Scale In Animation */
@keyframes scale-in {
  0% { opacity: 0; transform: scale(0.95); }
  100% { opacity: 1; transform: scale(1); }
}
.scale-in-center {
  animation: scale-in 0.5s cubic-bezier(0.250, 0.460, 0.450, 0.940) both;
}
</style>
