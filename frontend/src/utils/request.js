import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../stores/user'

const service = axios.create({
    baseURL: '/api', // Proxy will handle this
    timeout: 15000
})

// Request interceptor
service.interceptors.request.use(
    config => {
        const userStore = useUserStore()
        if (userStore.token) {
            config.headers['Authorization'] = `Bearer ${userStore.token}`
        }
        return config
    },
    error => {
        return Promise.reject(error)
    }
)

// Response interceptor
service.interceptors.response.use(
    response => {
        return response.data
    },
    error => {
        const msg = error.response?.data?.message || 'Request Failed'
        ElMessage.error(msg)
        return Promise.reject(error)
    }
)

export default service
