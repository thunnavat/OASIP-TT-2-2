<script setup>
import { ref } from 'vue';
import AddUserForm from '../components/AddUserForm.vue'
import {users as usersObj} from '../untils/untils.js'

const token = localStorage.getItem('token')
const newestUser = ref({})
const cancelform = () => {
  newestUser.value = {}
}

const createNewUser = async (newUser) => {
  await usersObj.createUser(newUser)
  cancelform()
}

</script>
 
<template>
  <div>
    <div v-if="token === null" class="text-xl">
    You have to 
    <router-link :to="{name:'Login'}" class="text-blue-700 font-semibold underline">Login</router-link>
    First
  </div>
    <div v-else>
      <add-user-form @addUser = "createNewUser" />
    </div>
  </div>
</template>
 
<style>
</style>