<script setup>
import { ref } from 'vue'
import MatchPassword from '../components/MatchPassword.vue';
import router from '../router';

const url = import.meta.env.PROD ?  import.meta.env.VITE_API_URL : '/api';

const to = router.currentRoute;


// const cancelform = () => {
//   newestUser.value = {}
// }

let token = ref('')
const login = async (login) => {
  const res = await fetch(`${url}/login`, {
    method: 'POST',
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify({email : login.email , password : login.password})
  })
  if (res.status === 200) {
    token.value = await res.json()
    localStorage.setItem('token', 'Bearer ' + token.value.jwttoken)
    alert('Login Successful')
    router.replace({name: 'Home'})
    setTimeout(() => {
      router.go(0)
    });
  }else if (res.status === 401){
    alert('Password Incorrect') 
  }else if (res.status === 404){
    alert('A user with the specified email DOES NOT exist')
  }
  else console.log('error')
  // cancelform()
}
</script>
 
<template>
  <div >  
    <div>
      <div class="mt-12 ml-16">
        <match-password @login="login"/>
      </div>
    </div>
  </div>
  </template>
 
<style>

</style>