<script setup>
import { onBeforeMount , ref} from 'vue'
import {useRoute} from 'vue-router'
import jwt_decode from "jwt-decode"
import {authentication as authenticationObj} from './untils/untils.js'
import router from './router'
import TutorialIcon from './components/TutorialIcon.vue'
import Tutorial from './components/Tutorial.vue'

const route = useRoute()
const token = localStorage.getItem('token')
const refreshToken = localStorage.getItem('refreshToken')

const logout = () => { 
  authenticationObj.logout()
 }

const tokenDecode = token !== null ? jwt_decode(token) : ""
const refreshTokenDecode = refreshToken !== null ? jwt_decode(refreshToken) : ""

const refresh = async () => {
  await authenticationObj.refresh()
}

const checkExpired = () => {
  if (refreshTokenDecode !== "") {
    if (Date.now() >= refreshTokenDecode.exp * 1000) {
      alert('Time out please login again')
      localStorage.clear()
      router.replace({name: 'Login'})
      setTimeout(() => {
        router.go(0)
      })
    }
  } else if (tokenDecode !== "" && Date.now() >= tokenDecode.exp * 1000) {
    refresh()
  } else console.log('Token is not expired') 
}

onBeforeMount(() => {
  checkExpired()
})


const showTutorial = ref(false)
const closeTutorial = (t) => {
  showTutorial.value = t
}
</script>
 
<template>
<div>  
  <div id="header">
    <nav id="nav-control" class="flex items-center justify-between flex-wrap p-6 bg-black	">
      <router-link :to="{name:'Home', params:{id:'home'}}">
        <span class="iconify" data-icon="noto:bear" style="width: 50px; height: 50px;"></span>
      </router-link>
      <div class="w-full block flex-grow sm:flex sm:items-center sm:w-auto">
        <div class="text-sm sm:flex-grow font-medium">
          <router-link :to="{name:'Home', params:{id:'home'}}" class="block mt-4 sm:inline-block sm:mt-0 text-white hover:text-gray-300 ml-4">Home</router-link>
          <router-link :to="{name:'EventCategory', params:{id:'eventcategory'}}" class="block mt-4 sm:inline-block sm:mt-0 text-white hover:text-gray-300 ml-4">EventCategory</router-link>
          <router-link :to="{name:'User', params:{id:'user'}}" class="block mt-4 sm:inline-block sm:mt-0 text-white hover:text-gray-300 ml-4">User</router-link>
          <router-link :to="{name:'AboutUs', params:{id:'about_us'}}" class="block mt-4 sm:inline-block sm:mt-0 text-white hover:text-gray-300 ml-4">About Us</router-link>
          <span v-show="token !== null"> <TutorialIcon @click="showTutorial = true" class="absolute right-60 mt-4 sm:inline-block sm:mt-0 text-white hover:text-gray-300 hover:cursor-pointer ml-4" /> </span>
          <router-link :to="{name:'AddUser', params:{id:'adduser'}}" class="absolute right-36 mt-4 sm:inline-block sm:mt-0 text-white hover:text-gray-300 ml-4 ">Add User</router-link>
          <router-link v-if="token === null" :to="{id:'Login'}" class="absolute right-16 mt-4 sm:inline-block sm:mt-0 text-white hover:text-gray-300 ml-4 ">Login</router-link>
          <div v-else class="w-full block flex-grow sm:flex sm:items-center sm:w-auto ">
            <button @click="logout" class=" absolute right-16 mt-4 mb-5 sm:inline-block sm:mt-0 text-white hover:text-gray-300 ml-4 text-sm sm:flex-grow font-medium"> Sign Out </button>
          </div>     
        </div> 
      </div>
    </nav>
  </div>
  <div v-show="showTutorial">
    <tutorial @close="closeTutorial" :routes="route.params.id" :role="tokenDecode.role" /></div>
  <div class="bg-zinc-200">
      <router-view> </router-view> 
  </div>
</div>
</template>
 
<style></style>