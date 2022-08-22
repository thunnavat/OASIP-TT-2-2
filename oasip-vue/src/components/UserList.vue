<script setup>


defineEmits(['detail','deleteUser','editUser'])
defineProps({
  users : {
   type: Array,
   default: []
  }
  })
</script>
 
<template>
<div>
  <div class="grid grid-cols-1 gap-1 bg-[#c4c4c4] p-4"> 
<div v-for="user in users" :key="user.id">
 <ul>
        <li class=" font-bold ">
          <span class="text-[#878787] mr-1">NAME: </span>{{ user.name }}
        </li>
         <li class="font-bold ">
          <span class="text-[#878787] mr-1">E-MAIL: </span>{{ user.email }}
        </li>
         <li class=" font-bold ">
          <span class="text-[#878787] mr-1">ROLE: </span>{{ user.role }}
        </li>
          <span @click="$emit('detail' , user)" class="  hover:cursor-pointer">
            <span class="text-[#212545] font-bold underline hover:text-black" style="font-size: 18px;">DETAILS</span>
          </span>
        <li class="relative  mt-6">
          <button @click="$emit('deleteUser', user.id)" class="text-white bg-black mr-4 border border-solid hover:bg-[#855B52]  active:bg-cyan-600 font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3">
            DELETE</button>
            <button @click="$emit('editUser', user)" class="text-white bg-black mr-4 border border-solid hover:bg-[#855B52]  active:bg-cyan-600 font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3">
            EDIT</button>
        </li>
 </ul>
</div>
</div>
</div>
</template>
 
<style>

</style>