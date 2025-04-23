import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Achievement from '../views/Achievement.vue'
import Ancient from '../views/Ancient.vue'
import Map from '../views/Map.vue'
import HerbalProduct from '../views/HerbalProduct.vue'
import Hci from '../views/Hci.vue'
import TimeHistory from '../views/TimeHistory.vue'
import Corona from '../views/Corona.vue'
import Companies from '../views/companies.vue'
import Yunnanbaiyao from '../views/Yunnanbaiyao.vue'
import Competitor from '../views/Competitor.vue'
import Herbal from '../views/Herbal.vue'  // 添加这行导入语句

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
    path: '/corona',
    name: 'Corona',
    component: Corona
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
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router