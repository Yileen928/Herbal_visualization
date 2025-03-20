import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Achievement from '../views/Achievement.vue'
import Ancient from '../views/Ancient.vue'
import Herbal from '../views/Herbal.vue'
import Map from '../views/Map.vue'
import HerbalProduct from '../views/HerbalProduct.vue'
import Hci from '../views/Hci.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/achievement',
    name: 'Achievement',
    component: Achievement
  },
  {
    path: '/ancient',
    name: 'Ancient',
    component: Ancient
  },
  {
    path: '/herbal',
    name: 'Herbal',
    component: Herbal
  },
  {
    path: '/map',
    name: 'Map',
    component: Map
  },
  {
    path: '/herbal-product',
    name: 'HerbalProduct',
    component: HerbalProduct
  },
  {
    path: '/hci',
    name: 'Hci',
    component: Hci
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router