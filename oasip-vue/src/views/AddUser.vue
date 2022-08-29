<script setup>
import { ref, onBeforeMount } from 'vue';
import AddUserForm from '../components/AddUserForm.vue'
import MatchPassword from '../components/MatchPassword.vue';
const url = import.meta.env.PROD ?  import.meta.env.VITE_API_URL : 'http://localhost:8080/api';

const users = ref([])
const getUsers = async () => {
  const res = await fetch(`${url}/users`)
  if(res.status === 200) {
    users.value = await res.json()
  } else console.log('cannot get users')
}

onBeforeMount( async () => {
  await getUsers()
})

const newestUser = ref({})
const cancelform = () => {
  newestUser.value = {}
}

const createNewUser = async (newUser) => {
  const isUnique = checkUnique(newUser.name, newUser.email)
  if(isUnique) {
    const res = await fetch(`${url}/users`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json'
      },
      body: JSON.stringify({name : newUser.name , email : newUser.email , role : newUser.role , password : newUser.password  })
    })
    if (res.status === 201) {
      alert('Added successfully')
    } else console.log('error, cannot be added')
    cancelform()
  }
}

const match = async (login) => {
  const res = await fetch(`${url}/match`, {
    method: 'POST',
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify({email : login.email , password : login.password})
  })
  if (res.status === 200) {
    alert('Password Matched')
  }else if (res.status === 401){
    alert('Password does not matched') 
  }else if (res.status === 404){
    alert('A user with the specified email does not exist')
  }
  else console.log('error')
  cancelform()
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
  <div>
    <div>
      <add-user-form @addUser = "createNewUser" />
    </div>
    <div>
      <match-password @login="match"/>
    </div>
  </div>
</template>
 
<style>
</style>