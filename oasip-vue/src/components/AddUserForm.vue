<script setup>
import { ref } from 'vue'

const showNameError = ref(false)
const showEmailError = ref(false)

defineEmits(['addUser'])

const newUser = ref({
    name: '',
    email: '',
    role: 'student'
  })


const checkError = () => {
  if(newUser.value.name !== undefined && newUser.value.name.length > 100){
    showNameError.value = true
  }
  if(newUser.value.email !== undefined && newUser.value.email.length > 50){
    showEmailError.value = true
  }
}
const roles = ["admin" , "lecturer" , "student"]


</script>
 
<template>

<div>
<h1 class="text-xl font-bold mb-3 ml-4">Add user</h1>
<span v-show="showNameError" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded absolute mx-20">Name cannot exceed 100 characters <button @click="showNameError = false">x</button></span>
<span>Name : </span>
<input type="text"  maxlength="101" class="border-2 border-black text-black ml-1 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed	" v-model="newUser.name" :onchange="checkError"> 
<span v-show="showEmailError" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded absolute mx-20">Email cannot exceed 50 characters <button @click="showEmailError = false">x</button></span>
<span>Email : </span>
<input type="text"  maxlength="51" class="border-2 border-black text-black ml-1 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed	" v-model="newUser.email" :onchange="checkError"> 
<div v-for="(role , index) in roles" :key="index"> 
  <input type="radio" :value="role" name="selectedrole" v-model="newUser.role">
  <label>{{ role }}</label>
</div>
<button class=" ml-3 mb-4  border border-solid bg-white  border-yellow-800 hover:bg-black hover:text-white font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3"
@click="$emit('addUser' , {name: newUser.name , email: newUser.email , role: newUser.role})">Create User</button>
</div>

</template>
 
<style>

</style>