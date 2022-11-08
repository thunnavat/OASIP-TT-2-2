<script setup>
import { ref } from 'vue';
import AddUserForm from '../components/AddUserForm.vue'
import {users as usersObj} from '../untils/untils.js'
import jwt_decode from "jwt-decode"

const token = localStorage.getItem('token')
const tokenDecode = token !== null ? jwt_decode(token) : ""
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
  <div class="container">
    <div  class="signup">
      <div v-if="token === null" class="text-xl ">
    You have to 
    <router-link :to="{name:'Login'}" class="text-blue-700 font-semibold underline ">Login</router-link>
    First
  </div>
  <div v-else-if="tokenDecode.role !== 'ADMIN'">
    <span class="form-group">You do not have permission to access this page</span>
  </div>
  <div v-else class="form-group">
    <add-user-form @addUser = "createNewUser" />
  </div>
</div>
</div>
</template>
 
<style>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 40px;
  margin-left: 150px;
}
.signup {
  width: 700px;
  height: 460px;
  position: relative;
  overflow: hidden;
  background-color: rgb(39, 33, 33);
  box-shadow: 10px 10px 60px rgba(176, 92, 92, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 5px;
}
.form-group {
  display: flex;
  flex-direction: column;
  margin: 0 0 20px 0;
  font-size: .9em;
  color: pink;
}
</style>