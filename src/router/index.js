import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import( '../views/Login.vue')
  },
  {
    path: '/',
    name: 'home',
    component: () => import( '../views/Home.vue'),
    children: [
      {
        path: 'employee',
        name: 'Employee',
        component: () => import( '../views/Employee.vue')
      },
    ]
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
