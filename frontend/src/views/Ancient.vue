<template>
  <div class="ancient">
    <div class="ancient-background">
      <h1>古籍页面</h1>
      <div class="search-section">
        <input v-model="searchQuery" placeholder="请输入药材名称" />
        <button @click="searchHerbal">查询</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const searchQuery = ref('')

const searchHerbal = () => {
  // 这里可以添加查询验证
  if (searchQuery.value) {
    router.push({
      path: '/herbal',
      query: { name: searchQuery.value }
    })
  }
}
</script>