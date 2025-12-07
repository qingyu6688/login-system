<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElementPlus, User, SwitchButton } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

// If avatar is null, use default
const getAvatar = (avatar) => {
    return avatar ? (avatar.startsWith('http') ? avatar : 'http://localhost:8080' + avatar) : ''
}

const handleLogout = () => {
    ElMessageBox.confirm('确定要退出登录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        userStore.logout()
        router.push('/login')
        ElMessage.success('已退出登录')
    })
}

const handleProfile = () => {
    router.push('/profile')
}
</script>

<template>
    <nav class="fixed w-full z-50 bg-white/80 backdrop-blur-md border-b border-gray-100 transition-all duration-300">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex justify-between items-center h-20">
                <!-- Logo -->
                <div class="flex items-center gap-3 cursor-pointer" @click="router.push('/')">
                    <div
                        class="w-10 h-10 rounded-xl bg-gradient-to-tr from-blue-600 to-cyan-400 flex items-center justify-center shadow-lg shadow-blue-500/30">
                        <el-icon class="text-white text-xl font-bold">
                            <ElementPlus />
                        </el-icon>
                    </div>
                    <span
                        class="text-xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-gray-900 to-gray-600">
                        企业级认证系统
                    </span>
                </div>

                <!-- Desktop Menu -->
                <div class="hidden md:flex items-center space-x-8">
                    <router-link to="/"
                        class="text-gray-500 hover:text-blue-600 font-medium transition-colors">首页</router-link>
                    <a href="#" class="text-gray-500 hover:text-blue-600 font-medium transition-colors">功能特性</a>
                    <a href="#" class="text-gray-500 hover:text-blue-600 font-medium transition-colors">关于我们</a>
                </div>

                <!-- User Profile / Actions -->
                <div class="flex items-center gap-4">
                    <div class="hidden md:flex flex-col items-end mr-2">
                        <span class="text-sm font-bold text-gray-800">{{ userStore.user?.username || '用户' }}</span>
                        <span class="text-xs text-green-500 font-medium flex items-center gap-1">
                            <span class="w-1.5 h-1.5 rounded-full bg-green-500 animate-pulse"></span> 在线
                        </span>
                    </div>
                    <el-dropdown trigger="click">
                        <el-avatar :size="40" :src="getAvatar(userStore.user?.avatar)"
                            class="cursor-pointer !bg-gradient-to-tr !from-blue-500 !to-cyan-400 hover:scale-105 transition-transform border-2 border-white shadow-md">
                            {{ !userStore.user?.avatar && userStore.user?.username ?
                                userStore.user.username.charAt(0).toUpperCase() : '' }}
                        </el-avatar>
                        <template #dropdown>
                            <el-dropdown-menu class="!p-2 !rounded-xl">
                                <el-dropdown-item :icon="User" @click="handleProfile">个人中心</el-dropdown-item>
                                <el-dropdown-item :icon="SwitchButton" divided @click="handleLogout"
                                    class="!text-red-500">退出登录</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </div>
            </div>
        </div>
    </nav>
</template>
