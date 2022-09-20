<script setup>
import { ref } from 'vue'
defineEmits(['login'])
const login = ref({
  email: '',
  password: ''
})
</script>
 
<template>
  <div>
    <span>Email : </span>
    <input type="text"  maxlength="51" class=" border-2 border-black text-black ml-8 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed	" v-model="login.email" > <br>
    <span>Password : </span>
    <input type="password"  maxlength="15" autocomplete="on" class="border-2 border-black text-black ml-1 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed	" v-model="login.password" > <br>  
    <button class="mt-6  border border-solid bg-white  border-yellow-800 hover:bg-black hover:text-white font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3"
    @click=" $emit('login', {email: login.email, password: login.password}) ">Login</button>
  </div>
</template>
 
<style>

</style>