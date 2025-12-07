<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock, Message, Iphone } from '@element-plus/icons-vue'
import { register } from '../api/auth'
import { ElMessage } from 'element-plus'

const router = useRouter()
const loading = ref(false)
const registerFormRef = ref(null)

const registerForm = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  email: '',
  mobile: ''
})

const validatePass2 = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== registerForm.password) {
    callback(new Error('两次输入密码不一致!'))
  } else {
    callback()
  }
}

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 8, max: 20, message: '长度在 8 到 20 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { validator: validatePass2, trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
  ],
  mobile: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ]
}

const handleRegister = async (formEl) => {
  if (!formEl) return
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      loading.value = true
      try {
        const { confirmPassword, ...data } = registerForm
        await register(data)
        ElMessage.success('注册成功，请登录')
        router.push('/login')
      } catch (error) {
        // Error handled globally
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

    <div class="w-full max-w-[460px] bg-white/80 backdrop-blur-xl rounded-2xl shadow-2xl p-8 z-10 border border-white/50 transition-all duration-300 hover:shadow-cyan-100 scale-in-center">
      <div class="text-center mb-8">
        <h2 class="text-3xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-gray-900 to-gray-600">创建新账号</h2>
        <p class="text-gray-500 mt-2 text-sm font-medium tracking-wide">Join Enterprise System</p>
      </div>

      <el-form
        ref="registerFormRef"
        :model="registerForm"
        :rules="rules"
        size="large"
        label-width="0"
        class="space-y-5"
      >
        <el-form-item prop="username">
          <el-input 
            v-model="registerForm.username" 
            placeholder="用户名"
            :prefix-icon="User"
            class="!rounded-xl custom-input"
          />
        </el-form-item>
        
        <el-form-item prop="email">
          <el-input 
            v-model="registerForm.email" 
            placeholder="电子邮箱"
            :prefix-icon="Message"
            class="custom-input"
          />
        </el-form-item>

        <el-form-item prop="mobile">
          <el-input 
            v-model="registerForm.mobile" 
            placeholder="手机号码"
            :prefix-icon="Iphone"
            class="custom-input"
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input 
            v-model="registerForm.password" 
            type="password" 
            placeholder="密码 (8-20位)"
            :prefix-icon="Lock"
            show-password
            class="custom-input"
          />
        </el-form-item>

        <el-form-item prop="confirmPassword">
          <el-input 
            v-model="registerForm.confirmPassword" 
            type="password" 
            placeholder="确认密码"
            :prefix-icon="Lock"
            show-password
            class="custom-input"
          />
        </el-form-item>

        <div class="flex items-center text-sm px-1">
          <el-checkbox label="我已阅读并同意" class="!text-gray-500 font-medium mr-1" />
          <a href="#" class="text-blue-600 hover:text-blue-500 font-bold hover:underline">服务协议</a> 
          <span class="mx-1 text-gray-400">&</span>
          <a href="#" class="text-blue-600 hover:text-blue-500 font-bold hover:underline">隐私政策</a>
        </div>

        <el-button 
          type="primary" 
          class="w-full !rounded-xl !h-12 !text-lg !font-bold tracking-wide !bg-gradient-to-r !from-blue-600 !to-cyan-500 !border-0 hover:!shadow-lg hover:!shadow-blue-500/40 transition-all duration-300 hover:!opacity-90 hover:!-translate-y-0.5" 
          :loading="loading"
          @click="handleRegister(registerFormRef)"
        >
          立即注册
        </el-button>

        <div class="relative mt-6">
           <div class="absolute inset-0 flex items-center">
            <span class="w-full border-t border-gray-200"></span>
          </div>
          <div class="relative flex justify-center text-sm">
             <span class="px-2 bg-transparent bg-white/0 text-gray-400">已有账号?</span>
             <router-link to="/login" class="text-blue-600 hover:text-blue-500 font-bold ml-1 hover:underline decoration-2 underline-offset-4">
              直接登录
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
