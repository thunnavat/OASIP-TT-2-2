<script setup>
import { ref, onBeforeMount } from 'vue';
import AddUserForm from '../components/AddUserForm.vue'
const url = import.meta.env.PROD ?  import.meta.env.VITE_API_URL : '/api';

const newestUser = ref({})
const cancelform = () => {
  newestUser.value = {}
}

const createNewUser = async (newUser) => {
  const res = await fetch(`${url}/users`, {
    method: 'POST',
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify({name : newUser.name , email : newUser.email , role : newUser.role , password : newUser.password  })
  })
  if (res.status === 201) {
    alert('Added successfully')
  } else if (res.status === 400) {
    const errorMessage = await res.json()
    alert(errorMessage.message)
  } else console.log('error, cannot be added')
  cancelform()
}

</script>
 
<template>
  <div>
    <div>
      <add-user-form @addUser = "createNewUser" />
    </div>
  </div>
</template>
 
<style>
</style>