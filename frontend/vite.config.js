//vite.config.js

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  define: {
    // 添加Vue功能标志定义，避免警告
    __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: 'false'
  },
  assetsInclude: ['**/*.fbx','**/*.json'], // 确保FBX文件被正确处理
  server: {
    host: true
  }
})
