<script setup>
import dayjs from 'dayjs'

defineEmits(['close'])
defineProps({
  user : {
    type: Object
  }
})
</script>
 
<template>
  <div class="modal-mask">
      <div class="modal-wrapper">
          <div class="modal-container bg-[#855B52]">
              <div class="modal-header text-lg font-bold text-white"  >
                  <h3> Detail </h3>
              </div>
              <div class="modal-body text-white ">
          <p class="font-medium">NAME: {{ user.name }}</p>  
          <p class="font-medium">EMAIL: {{ user.email }}</p>
          <p class="font-medium">ROLE: {{ user.role }}</p>
          <p class="font-medium">CREATED ON: {{ user.createdOn }}</p>
          <p class="font-medium">UPDATED ON: {{ user.updatedOn }}</p>
          </div>
              <div class=" text-black flex justify-end">
                  <button class=" ml-3 mb-4  border border-solid bg-white  border-yellow-800 hover:bg-yellow-800 hover:text-black font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3"
                  @click="$emit('close', false)">Close</button>
              </div>
          </div>
      </div>
  </div>
</template>
 
<style scoped>
.modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: table;
    transition: opacity 0.3s ease;
}

.modal-wrapper {
    display: table-cell;
    vertical-align: middle;
}

.modal-container {
    width: 400px;
    margin: 0px auto;
    padding: 20px 30px;
    border-radius: 2px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
}

.modal-body {
    margin: 20px 0;
}

</style>