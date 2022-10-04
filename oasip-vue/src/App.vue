<script setup>
import router from './router';
import { onBeforeMount , ref } from 'vue';
import jwt_decode from "jwt-decode";

const token = localStorage.getItem('token')
const url = import.meta.env.PROD ?  import.meta.env.VITE_API_URL : '/api';
const logout = () => { 
  localStorage.clear();
  router.replace({name: 'Login'})
    setTimeout(() => {
      router.go(0)
    })
 }
 //  Testing area
// ต้องใช้ npm install jwt-decode นะ
const decode = token !== null ? jwt_decode(token) : ""
let refreshtoken = ref('') 

// เปรียบวันหมดอายุกับปัจจุบัน
const refresh = async () => {
  console.log('this is refresh function')
  const res = await fetch(`${url}/refresh`, {
    method: 'GET',
    headers: {
      'Authorization': token,
      'isRefreshToken': 'true'
    }
  })
  if (res.status === 200) {
    console.log('succeed')
    refreshtoken.value = await res.json()
    localStorage.setItem('refreshtoken', 'Bearer ' + refreshtoken.value.token)
  }
  else {
    console.log('cannot get token')
  }
}
  const checkexpired = () => {
    if(Date.now() >= decode.exp * 1000){
    refresh()
    }
  else{
    console.log('didnot')
  }
}
onBeforeMount(() => {
  checkexpired()
})

</script>
 
<template>
<div>  
  <div id="header">
    <nav id="nav-control" class="flex items-center justify-between flex-wrap p-6 bg-black	">
      <router-link :to="{name:'Home'}">
        <span class="iconify" data-icon="noto:bear" style="width: 50px; height: 50px;"></span>
      </router-link>
      <div class="w-full block flex-grow sm:flex sm:items-center sm:w-auto">
        <div class="text-sm sm:flex-grow font-medium">
          <router-link :to="{name:'Home'}" class="block mt-4 sm:inline-block sm:mt-0 text-white hover:text-gray-300 ml-4">Home</router-link>
          <router-link :to="{name:'EventCategory'}" class="block mt-4 sm:inline-block sm:mt-0 text-white hover:text-gray-300 ml-4">EventCategory</router-link>
          <router-link :to="{name:'User'}" class="block mt-4 sm:inline-block sm:mt-0 text-white hover:text-gray-300 ml-4">User</router-link>
          <router-link :to="{name:'AboutUs'}" class="block mt-4 sm:inline-block sm:mt-0 text-white hover:text-gray-300 ml-4">About Us</router-link>
          <router-link :to="{name:'AddUser'}" class="absolute right-36 mt-4 sm:inline-block sm:mt-0 text-white hover:text-gray-300 ml-4 ">Add User</router-link>
          <router-link v-if="token === null" :to="{name:'Login'}" class="absolute right-16 mt-4 sm:inline-block sm:mt-0 text-white hover:text-gray-300 ml-4 ">Login</router-link>
          <div v-else class="w-full block flex-grow sm:flex sm:items-center sm:w-auto ">
            <button @click="logout" class=" absolute right-16 mt-4 mb-5 sm:inline-block sm:mt-0 text-white hover:text-gray-300 ml-4 text-sm sm:flex-grow font-medium"> Sign Out </button>
          </div>     
        </div> 
      </div>
    </nav>
  </div>

  <router-view> </router-view> 
</div>
</template>
 
<style>

</style>