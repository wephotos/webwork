import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/Home.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    children: [
      {
        path: '/user',
        name: 'User',
        component: () => import('../views/User.vue')
      },
      {
        path: '/role',
        name: 'Role',
        component: () => import('../views/Role.vue')
      },
      {
        path: '/resource',
        name: 'Resource',
        component: () => import('../views/Resource.vue')
      },
      {
        path: '/dictionary',
        name: 'Dictionary',
        component: () => import('../views/Dictionary.vue')
      },
      {
        path: '/config',
        name: 'Config',
        component: () => import('../views/Config.vue')
      }
    ],
    redirect: '/user'
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/client',
    name: 'Client',
    component: () => import('../views/Client.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
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
