import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            component: () => import('../layout/index.vue'),
            meta: { requiresAuth: true },
            children: [
                {
                    path: '',
                    name: 'home',
                    component: () => import('../views/Home.vue')
                },
                {
                    path: 'profile',
                    name: 'profile',
                    component: () => import('../views/Profile.vue')
                }
            ]
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('../views/Login.vue')
        },
        {
            path: '/register',
            name: 'register',
            component: () => import('../views/Register.vue')
        },
        {
            path: '/forgot-password',
            name: 'forgot-password',
            component: () => import('../views/ForgotPassword.vue')
        }
    ]
})

router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token') // Check storage used by store
    // Simple check: if route requires auth and no token, redirect to login
    if (to.meta.requiresAuth && !token) {
        next('/login')
    } else {
        next()
    }
})

export default router
