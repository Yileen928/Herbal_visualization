import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Achievement from '../views/Achievement.vue'
import Ancient from '../views/Ancient.vue'
import Map from '../views/Map.vue'
import HerbalProduct from '../views/HerbalProduct.vue'
import Hci from '../views/Hci.vue'
import TimeHistory from '../views/TimeHistory.vue'
import Companies from '../views/Companies.vue'
import Yunnanbaiyao from '../views/Yunnanbaiyao.vue'
import Competitor from '../views/Competitor.vue'
import Herbal from '../views/Herbal.vue'
import TenAchievement from '../views/tenachievement.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
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
  },
  {
    path: '/time-history',
    name: 'TimeHistory',
    component: TimeHistory
  },
  {
    path: '/companies',
    name: 'Companies',
    component: Companies
  },
  {
    path: '/yunnanbaiyao',
    name: 'Yunnanbaiyao',
    component: Yunnanbaiyao
  },
  {
    path: '/competitor',
    name: 'Competitor',
    component: Competitor
  },
  {
    path: '/ten-achievement',
    name: 'TenAchievement',
    component: TenAchievement
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})


export default router