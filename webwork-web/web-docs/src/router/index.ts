import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import DocList from '../views/DocList.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'DocList',
    component: DocList
  },
  {
    path: '/doc-view',
    name: 'DocView',
    component: () => import('../views/DocView.vue')
  },
  {
    path: '/doc-edit',
    name: 'DocEdit',
    component: () => import('../views/DocEdit.vue')
  },
  {
    path: '/login',
    name: 'Login',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/:catchAll(.*)',
    name: '404',
    component: () => import('../views/NotFound.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
