import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as loginApi } from '../api/auth'

import { getUserInfo } from '../api/user'

export const useUserStore = defineStore('user', () => {
    const token = ref(localStorage.getItem('token') || '')
    const user = ref(JSON.parse(localStorage.getItem('user')) || null)

    function setToken(newToken) {
        token.value = newToken
        localStorage.setItem('token', newToken)
    }

    function setUser(newUser) {
        user.value = newUser
        localStorage.setItem('user', JSON.stringify(newUser))
    }

    async function login(loginForm) {
        try {
            const res = await loginApi(loginForm)
            if (res.token) {
                setToken(res.token)
                // Initial set from login response if available, else fetch
                // The login API response structure: { token: "...", username: "...", role: "..." }
                setUser({ username: res.username, role: res.role, avatar: res.avatar })
                return res
            }
        } catch (error) {
            throw error
        }
    }

    async function fetchUser() {
        try {
            const res = await getUserInfo()
            setUser(res)
            return res
        } catch (error) {
            console.error('Fetch user info failed', error)
        }
    }

    function logout() {
        token.value = ''
        user.value = null
        localStorage.removeItem('token')
        localStorage.removeItem('user')
    }

    return { token, user, setToken, setUser, logout, login, fetchUser }
})
