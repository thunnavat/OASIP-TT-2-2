<script setup>
import { ref } from 'vue'
defineEmits(['login'])
const login = ref({
  email: '',
  password: ''
})

const modal = ref(true)
</script>
 
<template>
  <!-- <div>
    <span>Email : </span>
    <input type="text"  maxlength="51" class=" border-2 border-black text-black ml-8 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed	" v-model="login.email" > <br>
    <span>Password : </span>
    <input type="password"   maxlength="15" autocomplete="on" class="border-2 border-black text-black ml-1 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed	" v-model="login.password" @keyup.enter=" $emit('login', {email: login.email, password: login.password})" > <br>  
    <button class="mt-6  border border-solid bg-white  border-yellow-800 hover:bg-black hover:text-white font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3"
    @click=" $emit('login', {email: login.email, password: login.password}) ">Login</button>
  </div> -->
<div>
          <div class="modal" v-if="modal">
          <div class="modal-body">
          <h4 style="text-align: center " class="mb-4 font-bold">Sign In</h4>
          <span>Email : </span>
    <input type="text"  maxlength="51" class=" border-2 border-black text-black ml-16 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed	" v-model="login.email" > <br>
    <span>Password : </span>
    <input type="password"   maxlength="15" autocomplete="on" class="border-2 border-black text-black ml-2 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed	" v-model="login.password" @keyup.enter=" $emit('login', {email: login.email, password: login.password})" > <br>  
    <button class="mt-6  border border-solid bg-white  border-yellow-800 hover:bg-black hover:text-white font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3"
    @click=" $emit('login', {email: login.email, password: login.password}) ">Login</button>
        </div>
        </div>
</div>
</template>
 
<style>
.modal {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 32px;
  }
.modal-body {
  background-color: rgb(70, 112, 153);
  padding: 70px;
  border-radius: 0.375rem;
}
.modal button {
  margin-top: 10px;
  margin-right: 4px;
  margin-left: 20px;
}
</style>