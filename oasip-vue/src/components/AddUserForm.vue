<script setup>
import { ref } from 'vue'

const showNameError = ref(false)
const showEmailError = ref(false)
const nameErrorMsg = ref()
const emailErrorMsg = ref()

const checkpassword = ref()
const check = () => {
  if(newUser.value.password.length < 8 || newUser.value.password.length > 14){
    alert('The password must be between 8 to 14 characters')
    return false
  }
  else if(checkpassword.value !== newUser.value.password){
    checkpassword.value = ''
    alert('The password does not match')
    return false
  }
  else if(checkpassword.value === newUser.value.password){
    return true
  }
}

defineEmits(['addUser'])

const newUser = ref({
    name: '',
    email: '',
    password: '',
    role: 'student'
  })


const checkName = () => {
  if(newUser.value.name.length > 100){
    nameErrorMsg.value = 'Name cannot exceed 100 characters'
    showNameError.value = true
  }
  else if(newUser.value.name.length <= 0){
    nameErrorMsg.value = 'Name cannot be null'
    showNameError.value = true
  }
}
const checkEmail = () => {
  if(newUser.value.email.length > 50){
    emailErrorMsg.value = 'Email cannot exceed 50 characters'
    showEmailError.value = true
  }
  else if(newUser.value.email.length <= 0){
    emailErrorMsg.value = 'Email cannot be null'
    showEmailError.value = true
  }
  else if(String(newUser.value.email)
    .toLowerCase()
    .match(
     /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    ) === null){
      emailErrorMsg.value = 'Email is in invalid format'
      showEmailError.value = true
    }
}
const roles = ["admin" , "lecturer" , "student"]


</script>
 
<template>

<div>
  {{newUser.role}}
<h1 class="text-xl font-bold mb-3 ml-4">Add user</h1>
<div class="ml-2 mb-5">
  <form>
    <span v-show="showNameError" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded absolute mx-20"> {{ nameErrorMsg }} <button @click="showNameError = false">x</button></span>
    <span>Name : </span>
    <input type="text"  maxlength="101" class="border-2 border-black text-black ml-1 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed	" v-model="newUser.name" :onchange="checkName" @click="showNameError = false">
    <span v-show="showEmailError" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded absolute mx-20"> {{ emailErrorMsg }} <button @click="showEmailError = false">x</button></span> &nbsp;  
    <span>Email : </span>
    <input type="text"  maxlength="51" class="border-2 border-black text-black ml-1 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed	" v-model="newUser.email" :onchange="checkEmail" @click="showEmailError = false"> <br>
    <span>Password (Must be between 8 and 14 characters) : </span>
    <input type="password"  maxlength="15" autocomplete="on" class="border-2 border-black text-black ml-1 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed	" v-model="newUser.password" > <br>  
    <span>Retype Password : </span>
    <input type="password"  maxlength="15" autocomplete="on" class="border-2 border-black text-black ml-1 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed	" v-model="checkpassword" > 
    <div > 
      <select v-model="newUser.role">
        <option v-for="(role , index) in roles" :key="index" :value="role">{{ role }}</option>
      </select>
    </div>

  </form>
  <button class=" ml-3 mb-4  border border-solid bg-white  border-yellow-800 hover:bg-black hover:text-white font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3"
      @click="check() === false ? '' : $emit('addUser' , {name: newUser.name , email: newUser.email , role: newUser.role , password: newUser.password})">Create User
    </button>
</div>

</div>

</template>
 
<style>

</style>