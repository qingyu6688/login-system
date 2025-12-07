import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        // rewrite: (path) => path.replace(/^\/api/, '') 
        // Backend controller is @RequestMapping("/api/auth"), so we might NOT want to rewrite /api if backend expects it.
        // My AuthController has @RequestMapping("/api/auth").
        // robustly, if I send /api/auth/login from frontend:
        // proxy -> http://localhost:8080/api/auth/login.
        // Matches Controller. So NO rewrite needed.
      }
    }
  }
})
