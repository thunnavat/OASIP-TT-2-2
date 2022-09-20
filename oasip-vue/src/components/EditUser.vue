<script setup>
import { computed , ref} from 'vue';

defineEmits(['updateUser' , 'cancelform'])
const props = defineProps ({
  user: {
    type : Object ,
    require: true
  }
})

const editUser = computed(() => {
  return {
    id: props.user.id,
    name: props.user.name,
    email: props.user.email,
    role: props.user.role
  }
})

const roles = ["admin" , "lecturer" , "student"]
const showNameError = ref(false)
const showEmailError = ref(false)
const nameErrorMsg = ref()
const emailErrorMsg = ref()

const checkName = () => {
  if(editUser.value.name.length > 100){
    nameErrorMsg.value = 'Name cannot exceed 100 characters'
    showNameError.value = true
  }
  else if(editUser.value.name.length <= 0){
    nameErrorMsg.value = 'Name cannot be null'
    showNameError.value = true
  }
}
const checkEmail = () => {
  if(editUser.value.email.length > 50){
    emailErrorMsg.value = 'Email cannot exceed 50 characters'
    showEmailError.value = true
  }
  else if(editUser.value.email.length <= 0){
    emailErrorMsg.value = 'Email cannot be null'
    showEmailError.value = true
  }
  else if(String(editUser.value.email)
    .toLowerCase()
    .match(
     /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    ) === null){
      emailErrorMsg.value = 'Email is in invalid format'
      showEmailError.value = true
    }
}
</script>

<template>

<div>
  <h1 class="text-xl font-bold mb-3 ml-4">Edit user</h1>
<div class="ml-2 mb-5">
    <span v-show="showNameError" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded absolute mx-20"> {{ nameErrorMsg }} <button @click="showNameError = false">x</button></span>
    <span>Name : </span>
    <input type="text"  maxlength="101" class="border-2 border-black text-black ml-1 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed	" v-model="editUser.name" :onchange="checkName" @click="showNameError = false">
    <span v-show="showEmailError" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded absolute mx-20"> {{ emailErrorMsg }} <button @click="showEmailError = false">x</button></span> &nbsp;  
    <span>Email : </span>
    <input type="text"  maxlength="51" class="border-2 border-black text-black ml-1 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed	" v-model="editUser.email" :onchange="checkEmail" @click="showEmailError = false"> <br>
  <div>
    <select v-model="editUser.role">
        <option v-for="(role , index) in roles" :key="index" :value="role">{{ role }}</option>
      </select>
  </div>
</div>
<button class=" ml-3 mb-4  border border-solid bg-white  border-yellow-800 hover:bg-black hover:text-white font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3"
@click=" $emit('updateUser' , {id: editUser.id, name: editUser.name , email: editUser.email , role: editUser.role })">Save</button>
<button class=" ml-3 mb-4  border border-solid bg-white  border-yellow-800 hover:bg-black hover:text-white font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3"
@click=" $emit('cancelform')">cancel</button>
</div>

</template>
 
<style>

</style>