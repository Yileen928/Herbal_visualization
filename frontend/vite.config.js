import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  define: {
    __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: 'false'
  },
  assetsInclude: ['**/*.fbx'],
  server: {
    host: true,
    proxy: {
      '/api': {
        target: 'http://121.4.116.71:10010',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})
