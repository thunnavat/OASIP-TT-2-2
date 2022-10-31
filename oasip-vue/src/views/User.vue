<script setup>
import { ref , onBeforeMount} from 'vue'
import UserList from '../components/UserList.vue'
import UserDetail from '../components/UserDetail.vue'
import EditUser from '../components/EditUser.vue'
import {users as usersObj} from '../untils/untils.js'

const users = ref([])
const emptyMsg = 'No Users'
const user = ref([])
const token = localStorage.getItem('token')
const currentUser = ref({})
const newestUser = ref({})
const editMode = ref(false)

const getUsers = async () => {
  users.value = await usersObj.getUsers()
}

onBeforeMount( async () => {
  await getUsers()
  user.value = users.value
})

const getUserDetail = (user) => {
    currentUser.value = user
    isModal.value = true
}

const toEditMode = (currentUser) => {
  newestUser.value = currentUser
  editMode.value = true
}

const updateUser = async (updateUser) =>{
  const isUnique = checkUnique(updateUser.name, updateUser.email)
  if(isUnique) {
    users.value = await usersObj.updateUser(updateUser)
    cancelform()
  }
}

const cancelform = () => {
  newestUser.value = {}
  editMode.value = false
}

const checkUnique = ((name, email) => {
  const repeatName = users.value.filter((user) => user.name === name)
  const repeatEmail = users.value.filter((user) => user.email === email)
  if(repeatName.length > 0) {
    alert('Name has already use')
    return false
  } else if(repeatEmail.length > 0) {
    alert('Email has already use')
    return false
  } else {
    return true
  }
})

const removeUser = async (userId) => {
  users.value = await usersObj.deleteUser(userId)
}

const isModal = ref(false)
const closeModal = (e) => {
  isModal.value = e
}

</script>
 
<template>
   <div v-if="token === null" class="text-xl">
    You have to 
    <router-link :to="{name:'Login'}" class="text-blue-700 font-semibold underline">Login</router-link>
    First
  </div>
  <div v-else>
    <div class="box-border p-4">
      <div class="font-semibold flex justify-center items-center text-black box-content bg-[#c4c4c4] h-96" v-if="users.length === 0 ">
        <span>{{ emptyMsg }}</span>
      </div>
      <div v-else>
        <div v-if="editMode === true">
          <edit-user :user="newestUser"  @updateUser="updateUser" @cancelform="cancelform"/>
        </div>
        <!-- <span>{{ users }}</span> -->
        <user-list :users="users" @detail="getUserDetail" @deleteUser="removeUser" @editUser="toEditMode"/>
        
      </div>
      <div v-if="isModal">
        <user-detail :user="currentUser" @close="closeModal" />
      </div>      </div>
    
  </div>
  </template>

<style>

</style>                  



      


