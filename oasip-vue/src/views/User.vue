<script setup>
import { ref , onBeforeMount} from 'vue'
import UserList from '../components/UserList.vue'
import UserDetail from '../components/UserDetail.vue'
import EditUser from '../components/EditUser.vue'

const url = import.meta.env.PROD ?  import.meta.env.VITE_API_URL : '/api';
const users = ref([])
const emptyMsg = 'No Users'
const user = ref([])
const token = localStorage.getItem('token')
const refreshtoken = localStorage.getItem('refreshtoken')
const getUsers = async () => {
  const res = await fetch(`${url}/users`, {
    method: 'GET',
    headers: {
      'Authorization': token
    }
  })
  if(res.status === 200) {
    users.value = await res.json()
  }
  else if(res.status === 401){

  } else console.log('cannot get users')
}

onBeforeMount( async () => {
  await getUsers()
  user.value = users.value
})

const currentUser = ref({})
const getUserDetail = (u) => {
    currentUser.value = u
    isModal.value = true
}

const newestUser = ref({})

const editMode = ref(false)
const toEditMode = (currentUser) => {
  newestUser.value = currentUser
  editMode.value = true
}

const removeUser = async (deleteUserId) => {
  if (confirm(`Do you want to delete user-id: ${deleteUserId} `) === true) {
    const res = await fetch(`${url}/users/${deleteUserId}` , {
      method:'DELETE'
  })
  if(res.status === 200 ){
      users.value = users.value.filter((user) => user.id !== deleteUserId)
    } else console.log('Error , cannot delete user')
  } else {
    console.log('cancel')
  }
}
const isModal = ref(false)
const closeModal = (e) => {
  isModal.value = e
}

const updateUser = async (updateUser) =>{
  const isUnique = checkUnique(updateUser.name, updateUser.email)
  if(isUnique) {
    const res = await fetch(`${url}/users/${updateUser.id}`, {
      method: 'PUT',
      headers: {
        'content-type': 'application/json'
      },
      body: JSON.stringify({
        name: updateUser.name, email: updateUser.email, role: updateUser.role
      })
    })
    if (res.status === 200) {
      const editedUser = await res.json()
      users.value = users.value.map((user) => user.id === editedUser.id ? {...user, name: editedUser.name, email: editedUser.email, role: editedUser.role} : user) 
      alert('Updated successfully')  
    } else console.log('Cannot update')
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
      </div>
      <!--  -->
    </div>
    
  </div>
  </template>

<style>

</style>                  



      


