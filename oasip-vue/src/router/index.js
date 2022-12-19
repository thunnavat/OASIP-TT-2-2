import { createRouter, createWebHistory } from 'vue-router'
import  Home  from  '../views/Home.vue'
import AboutUs from '../views/AboutUs.vue'
import EventCategory from '../views/EventCategory.vue'
import  NotFound  from '../views/NotFound.vue'
import User from '../views/User.vue'
import AddUser from '../views/AddUser.vue'
import Login from '../views/Login.vue'
import jwt_decode from 'jwt-decode'

const history = createWebHistory(import.meta.env.BASE_URL)
const routes = [  {path:'/Home' , name:'Home' , component:Home} ,
                  {path:'/about_us' , name:'AboutUs' , component:AboutUs},
                  {path:'/eventcategory' , name:'EventCategory' , component:EventCategory},
                  {path: '/user' , name:'User' , component:User},
                  {path: '/adduser' , name:'AddUser' , component:AddUser},
                  {path:'/', name:'Login', component:Login},
                  {path: '/:catchNotMatchPath(.*)' , name: 'NotFound' , component: NotFound}]

const router = createRouter({ history, routes })
  
router.beforeEach( (to) => {
const token = localStorage.getItem('token')
const tokenDecode = token !== null ? jwt_decode(token) : ''
  if (to.name === "Login" && tokenDecode){
    return {name : "Home"}
  } 
})

export default router