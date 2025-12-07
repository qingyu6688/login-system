<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { Message, Lock } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()
const step = ref(1) // 1: Verify Identity, 2: Reset Password, 3: Success
const loading = ref(false)
const timer = ref(0)

const form = reactive({
  email: '',
  code: '',
  newPassword: '',
  confirmPassword: ''
})

const sendCode = () => {
  if (!form.email) return ElMessage.warning('请先输入邮箱')
  // Mock sending code
  ElMessage.success('验证码已发送 (模拟)')
  timer.value = 60
  const interval = setInterval(() => {
    timer.value--
    if (timer.value <= 0) clearInterval(interval)
  }, 1000)
}

const handleNext = () => {
  if (step.value === 1) {
    if (!form.code) return ElMessage.warning('请输入验证码')
    step.value = 2
  } else if (step.value === 2) {
    if (!form.newPassword) return ElMessage.warning('请输入新密码')
    if (form.newPassword !== form.confirmPassword) return ElMessage.warning('两次密码不一致')
    loading.value = true
    setTimeout(() => {
      loading.value = false
      step.value = 3
    }, 1000)
  }
}
</script>

<template>
  <div class="min-h-screen w-full flex items-center justify-center relative overflow-hidden bg-[#e0f2fe]">
    <!-- Animated Decoration Circles -->
    <div class="absolute top-[-10%] left-[-10%] w-[500px] h-[500px] rounded-full bg-blue-400 opacity-20 blur-[100px] animate-float"></div>
    <div class="absolute bottom-[-10%] right-[-10%] w-[600px] h-[600px] rounded-full bg-cyan-400 opacity-20 blur-[120px] animate-float-delay"></div>

    <div class="w-full max-w-[500px] bg-white/80 backdrop-blur-xl rounded-2xl shadow-2xl p-8 z-10 border border-white/50 transition-all duration-300 hover:shadow-cyan-100 scale-in-center">
      <div class="text-center mb-8">
        <h2 class="text-3xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-gray-900 to-gray-600">找回密码</h2>
        <p class="text-gray-500 mt-2 text-sm font-medium tracking-wide">Reset Your Password</p>
      </div>

      <el-steps :active="step" finish-status="success" simple class="mb-8 !bg-transparent !p-0">
        <el-step title="验证身份" />
        <el-step title="重置密码" />
      </el-steps>

      <!-- Step 1: Verify Identity -->
      <div v-if="step === 1" class="space-y-6">
        <el-input 
          v-model="form.email" 
          placeholder="请输入注册邮箱"
          :prefix-icon="Message"
          size="large"
          class="custom-input"
        >
          <template #append>
            <el-button @click="sendCode" :disabled="timer > 0" class="!text-blue-500 !font-semibold">
              {{ timer > 0 ? `${timer}s` : '发送验证码' }}
            </el-button>
          </template>
        </el-input>
        
        <el-input 
          v-model="form.code" 
          placeholder="请输入验证码" 
          size="large"
          class="custom-input"
        />
        
        <el-button 
          type="primary" 
          class="w-full !rounded-xl !h-12 !text-lg !font-bold tracking-wide !bg-gradient-to-r !from-blue-600 !to-cyan-500 !border-0 hover:!shadow-lg hover:!shadow-blue-500/40 transition-all duration-300 hover:!opacity-90 hover:!-translate-y-0.5" 
          @click="handleNext"
        >
          下一步
        </el-button>
      </div>

      <!-- Step 2: Reset Password -->
      <div v-if="step === 2" class="space-y-6">
        <el-input 
          v-model="form.newPassword" 
          type="password" 
          placeholder="新密码"
          :prefix-icon="Lock"
          show-password
          size="large"
          class="custom-input"
        />
        <el-input 
          v-model="form.confirmPassword" 
          type="password" 
          placeholder="确认新密码"
          :prefix-icon="Lock"
          show-password
          size="large"
          class="custom-input"
        />
        <el-button 
           type="primary" 
           class="w-full !rounded-xl !h-12 !text-lg !font-bold tracking-wide !bg-gradient-to-r !from-blue-600 !to-cyan-500 !border-0 hover:!shadow-lg hover:!shadow-blue-500/40 transition-all duration-300 hover:!opacity-90 hover:!-translate-y-0.5" 
           :loading="loading" 
           @click="handleNext"
        >
          确认重置
        </el-button>
      </div>

      <!-- Step 3: Success -->
      <div v-if="step === 3" class="text-center">
        <el-result
          icon="success"
          title="重置成功"
          sub-title="您的密码已成功更新，请重新登录"
          class="!pt-2"
        >
          <template #extra>
            <el-button type="primary" class="!rounded-lg !px-8" @click="router.push('/login')">返回登录</el-button>
          </template>
        </el-result>
      </div>

      <div class="mt-8 text-center text-sm">
        <router-link to="/login" class="text-gray-400 hover:text-blue-500 transition-colors duration-300">
          返回登录
        </router-link>
      </div>
    </div>
  </div>
</template>
