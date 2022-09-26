<script setup>
import router from './router';
const token = localStorage.getItem('token')

const logout = () => { 
  localStorage.clear();
  router.replace({name: 'Login'})
    setTimeout(() => {
      router.go(0)
    })
 }
 //  Testing area
// ต้องใช้ npm install jwt-decode นะ

// import jwt_decode from "jwt-decode";
//const decode = jwt_decode(token) 

// เปรียบวันหมดอายุกับปัจจุบัน

// const checkexpired = () => {
//   if(Date.now() >= decode.exp * 1000){
//     localStorage.clear()
//     router.go(0)
//   }
//   else {
//   }
// }

// const refresh = async () => {
  // const res = await fetch(`${url}/refresh`, {
  //   method: 'POST',
  //   headers: {
  //     'content-type': 'application/json'
  //   },
  //   body: JSON.stringify({token:token})
  // })
  // if (res.status === 200) {
  //   token.value = await res.json()
  //   localStorage.setItem('token', 'Bearer ' + token.value.token)


// 
// 
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