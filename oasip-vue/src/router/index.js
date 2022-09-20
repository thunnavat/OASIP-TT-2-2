import { createRouter, createWebHistory } from 'vue-router'
import  Home  from  '../views/Home.vue'
import AboutUs from '../views/AboutUs.vue'
import EventCategory from '../views/EventCategory.vue'
import  NotFound  from '../views/NotFound.vue'
import User from '../views/User.vue'
import AddUser from '../views/AddUser.vue'
import Login from '../views/Login.vue'

const history = createWebHistory(import.meta.env.BASE_URL)
const routes = [  {path:'/' , name:'Home' , component:Home} ,
                  {path:'/about_us' , name:'AboutUs' , component:AboutUs},
                  {path:'/eventcategory' , name:'EventCategory' , component:EventCategory},
                  {path: '/user' , name:'User' , component:User},
                  {path: '/adduser' , name:'AddUser' , component:AddUser},
                  {path:'/login', name:'Login', component:Login},
                  {path: '/:catchNotMatchPath(.*)' , name: 'NotFound' , component: NotFound}]


const router = createRouter({ history, routes })
export default router