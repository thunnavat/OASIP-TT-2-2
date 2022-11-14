<script setup>
import MatchPassword from '../components/MatchPassword.vue';
import {authentication as authenticationObj} from '../untils/untils.js'

const login = async (authenticationRequest) => {
  await authenticationObj.login(authenticationRequest)
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