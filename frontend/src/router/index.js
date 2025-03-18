import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Ancient from '../views/Ancient.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/ancient',
    name: 'Ancient',
    component: Ancient
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router