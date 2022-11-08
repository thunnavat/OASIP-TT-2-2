<script setup>
import dayjs from 'dayjs'
import utc from 'dayjs/plugin/utc'
import {ref} from 'vue'
dayjs.extend(utc)

defineEmits(['detail','deleteEvent','editEvent'])
const props = defineProps({
  events : {
   type: Array,
   default: []
  },
  role : {
    type: String
  }
})
const count = () => {
  // let uniqueEmail = props.events.filter((bookingEmail , index) => {
  //   return props.events.indexOf(bookingEmail) === index
  // })
  // console.log(uniqueEmail)
  for(let i = 0; i < props.events.length ; i++){
    const currentEmail = props.events[i].bookingEmail
    let count = 0
    for(let i = 0; i < props.events.length; i++){
      if(currentEmail == props.events[i].bookingEmail){
        count++
      }
    }
    console.log(count)
    if(numOfEmail.value.includes(currentEmail)){
      console.log('a')
    }else{
      console.log('b')
    }
    console.log(numOfEmail.currentEmail)
    numOfEmail.value.push({currentEmail , count})
    console.log(numOfEmail.value)
  }
}
const countEvent = ref(false)
const numOfEmail = ref([])
console.log(props.events[1].bookingEmail)

// const counteve = props.events.reduce((count,val) => {
//   count[val] = (count[val] || 0) +1 
//   return count;
// }, {} );
// console.log(counteve)
</script>
 
<template>
  <div v-show="role === 'ADMIN'">
  <input type="checkbox" v-model="countEvent" @click="count"/> &nbsp;
  <label>Show number of event by emails</label>
  </div>
  <div v-show="countEvent == true">
    <div v-for ="(num , index) in numOfEmail" :key="index" >
      {{ num.currentEmail }} {{ num.count }} 
    </div>
  </div>
<div >
  <div class="grid grid-cols-1 gap-1 2xl:grid-cols-4 xl:grid-cols-3 lg:grid-cols-2 bg-[#c4c4c4] p-4"> 
    <div v-for="event in events" :key="event.id"  class="bg-white m-4 px-4 pt-2 pb-4 leading-8 border-4 rounded border-stone-900 text-sm">
      <ul>
        <li class="flex justify-end font-bold ">
          <span class="text-[#878787] mr-1">CATEGORY: </span>{{ event.eventCategory.eventCategoryName }}
        </li>
        <li class="font-bold">
          <span class="text-[#878787]">NAME: </span>{{ event.bookingName }}
        </li>
        <li class="font-bold">
          <span class="text-[#878787]">START TIME: </span>{{ dayjs(event.eventStartTime).format('DD MMMM YYYY HH.mm') }}
        </li>  
        <li class="font-bold">
          <span class="text-[#878787] mr-1">DURATION: </span><span class="mr-28">{{ event.eventDuration }} mins</span>  
        </li>
        <li class="relative mt-6">
          <button @click="$emit('deleteEvent', event.id)" class="text-white bg-black mr-4 border border-solid hover:bg-[#855B52]  active:bg-cyan-600 font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3">
            DELETE</button>
            <button @click="$emit('editEvent', event)" class="text-white bg-black mr-4 border border-solid hover:bg-[#855B52]  active:bg-cyan-600 font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3">
            EDIT</button>
          <span @click="$emit('detail' , event)" class="grid justify-end leading-3 justify-items-center absolute bottom-0 right-0 hover:cursor-pointer">
            <span class="iconify" data-icon="noto:bear" style="width: 38px; height: 38px;"></span>
            <span class="text-[#878787] font-bold underline hover:text-black" style="font-size: 11px;">MORE DETAILS</span>
          </span>
        </li>
      </ul>
    </div>
  </div>
</div>
</template>
 
<style>

</style>