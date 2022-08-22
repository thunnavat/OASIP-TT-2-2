<script setup>
import { ref , onBeforeMount} from 'vue'
import UserList from '../components/UserList.vue'
import UserDetail from '../components/UserDetail.vue'

const url = import.meta.env.PROD ?  import.meta.env.VITE_API_URL : 'http://localhost:8080/api';
const users = ref([])
const emptyMsg = 'No Users'
const user = ref([])

const getUsers = async () => {
  const res = await fetch(`${url}/users`)
  if(res.status === 200) {
    users.value = await res.json()
  } else console.log('cannot get users')
}

onBeforeMount( async () => {
  getUsers()
  user.value = users.value
})

const currentUser = ref({})
const getUserDetail = (u) => {
    currentUser.value = u
    isModal.value = true
}

const newestUser = ref({})
const toEditMode = (currentUser) => {
  newestUser.value = currentUser
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

</script>
 
<template>

<div class="box-border p-4">
<div class="font-semibold flex justify-center items-center text-black box-content bg-[#c4c4c4] h-96" v-if="users.length === 0">
        <span>{{ emptyMsg }}</span>
      </div>
<div v-else>
  <!-- <span>{{ users }}</span> -->
  <user-list :users="users" @detail="getUserDetail" @deleteUser="removeUser" @editUser="toEditMode"/>
  
</div>
<div v-if="isModal">
  <user-detail :user="currentUser" @close="closeModal" />
  </div>
<!--  -->


</div>

</template>

<style>

</style>                  



      


