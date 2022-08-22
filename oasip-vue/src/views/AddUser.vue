<script setup>
import AddUserForm from '../components/AddUserForm.vue'
import {ref} from 'vue'
const url = import.meta.env.PROD ?  import.meta.env.VITE_API_URL : 'http://localhost:8080/api';

const createNewUser = async (newUser) => {
  const res = await fetch(`${url}/users`, {
    method: 'POST',
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify({name : newUser.name , email : newUser.email , role : newUser.role  })
  })
  if (res.status === 201) {
    alert('Added successfully')
  } else console.log('error, cannot be added')
  cancelform()
}

const cancelform = () => {
  newestUser.value = {}
}
</script>
 
<template>
<add-user-form @addUser = "createNewUser"/>
></template>
 
<style>
</style>