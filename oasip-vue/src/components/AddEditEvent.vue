<script setup>
import { ref, computed } from 'vue'
import {events as eventsObj} from '../untils/untils.js'
import jwt_decode from "jwt-decode"
import dayjs from 'dayjs'
import utc from 'dayjs/plugin/utc'
dayjs.extend(utc)

defineEmits(['addEvent','cancel','updateEvent'])
const props = defineProps ({
  event: {
    type : Object ,
    default : {}
  },
  eventCategories: {
    type: Array,
    require: true
  }
})
const newEvent = computed(() => {
  return {
    id: props.event.id,
    bookingName: props.event.bookingName ,
    eventStartTime: props.event.eventStartTime ,
    eventDuration: props.event.eventDuration ,
    bookingEmail: props.event.bookingEmail ,
    eventNotes: props.event.eventNotes,
    eventCategoryId: props.event.eventCategoryId
  }
})

const token = localStorage.getItem('token')
const refreshToken = localStorage.getItem('refreshToken')
const tokenDecode = token !== null ? jwt_decode(token) : ""
const refreshTokenDecode = refreshToken !== null ? jwt_decode(refreshToken) : ""
const selectedEventCategory = ref(0)
const startTime = ref(dayjs().format('YYYY-MM-DDTHH:mm'))
const past = ref(false)
const noCategory = ref(false)
const noStartTime = ref(false)
const showNotes = ref(false)
const isDisabled = ref(false)
const editShow = ref(false)
const noValidEmail = ref(false)
const checkEmailNull = ref(false)
const noName = ref(false)
const isOverlap = ref(false)
const checkDate = ref(false)
const descErrMsg = ref(false)
const eventsByCategoryAndDate = ref([])
const fileName = ref()
const currentFile = ref()

const findDuration = () => {
  noCategory.value = false
  const eventCategory = props.eventCategories.find(eventCategory => eventCategory.id === selectedEventCategory.value)
  newEvent.value.eventCategoryId = selectedEventCategory.value
  newEvent.value.eventDuration = eventCategory.eventDuration;
} 

const changeStartTime = () => {
  noStartTime.value = false
  isOverlap.value = false
  showNotes.value = false
  editShow.value = false
  showNotes.value = false
  checkDate.value = true
  if(newEvent.value.id === undefined) {
    if(startTime.value <= dayjs().format('YYYY-MM-DDTHH:mm')) {
      past.value = true
      isDisabled.value = true
    } else {
      newEvent.value.eventStartTime = startTime.value
      past.value = false
      isDisabled.value = false
    } 
  } else {
    if(newEvent.value.eventStartTime <= dayjs().format('YYYY-MM-DDTHH:mm')){
      past.value = true
      isDisabled.value = true
    }
    else {
      past.value = false
      isDisabled.value = false
    }
  }
}

const getEventsByCategoryAndDate = async (eventCategoryId, startDateMidNightTime) => {
  eventsByCategoryAndDate.value = await eventsObj.getEventsByCategoryAndDate(eventCategoryId, startDateMidNightTime)
}

const checkOverlapTime = async (eventCategoryId, startDateMidNightTime) => {
  if(newEvent.value.eventCategoryId === undefined && newEvent.value.eventStartTime === undefined) {
    noCategory.value = true
    noStartTime.value = true
  } else if(newEvent.value.eventStartTime === undefined) {
    noStartTime.value = true
  } else if(newEvent.value.eventCategoryId === undefined) {
    noCategory.value = true;
  } else {
    await getEventsByCategoryAndDate(eventCategoryId, startDateMidNightTime)
    isOverlap.value = eventsByCategoryAndDate.value.some(e => ((e.eventStartTime < dayjs(newEvent.value.eventStartTime).utc().format() && (dayjs(e.eventStartTime).utc().add(e.eventDuration, 'm').format()) > dayjs(newEvent.value.eventStartTime).utc().format()) ||
      (e.eventStartTime >= dayjs(newEvent.value.eventStartTime).utc().format() && e.eventStartTime < (dayjs(newEvent.value.eventStartTime).add(newEvent.value.eventDuration, 'm').utc().format()))))
    if(isOverlap.value === false && past.value === false){
      if(refreshToken !== null) {
        newEvent.value.bookingEmail = refreshTokenDecode.sub
      } else if(token !== null) {
        newEvent.value.bookingEmail = tokenDecode.sub
      }
      
      showNotes.value = true
      editShow.value = !editShow.value
      checkDate.value = !checkDate.value
    }
  }
}

const validateEmail = () => {
  checkEmailNull.value = false
    if(String(newEvent.value.bookingEmail)
    .toLowerCase()
    .match(
     /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    ) === null && newEvent.value.bookingEmail !== undefined){
      noValidEmail.value = true
    }
  else{
    noValidEmail.value = false
  }
  
};
const check = () => {
  validateEmail()
  if(newEvent.value.bookingName === undefined && newEvent.value.bookingEmail === undefined){
    isDisabled.value = true
    checkEmailNull.value = true
    noName.value = true
  }
  else if(newEvent.value.bookingName === undefined || newEvent.value.bookingName === '' || newEvent.value.bookingName.length > 100){
    isDisabled.value = true
    noName.value = true
  }
  else if(newEvent.value.bookingEmail === undefined || newEvent.value.bookingEmail === ''){
    isDisabled.value = true
    checkEmailNull.value = true
  }
  else if(noValidEmail.value === true){
    isDisabled.value = true
  }
  else{
    isDisabled.value = false
    noName.value = false
    checkEmailNull.value = false
  }
}

const checkName = () => {
  if(newEvent.value.bookingName === undefined || newEvent.value.bookingName === '' || newEvent.value.bookingName.length > 100){
    isDisabled.value = true
    noName.value = true
  }else{
    isDisabled.value = false
    noName.value = false
  }
}
const clear = () => {
  selectedEventCategory.value = 0
  startTime.value = dayjs().format('YYYY-MM-DDTHH:mm')
  showNotes.value = false
  editShow.value = false
  noName.value = false
  checkEmailNull.value = false
  noValidEmail.value = false
}

const checkDesc = () => {
  if(newEvent.value.eventNotes.length > 500){
    descErrMsg.value = true
  }else{
    descErrMsg.value = false
  }
}


const updateFile = (e) => {
  const file = e.target.files[0]
  if((file.size / 1048576) > 10){
    alert('The file size cannot be larger than 10 MB')
  }
  else{
    currentFile.value = file
    fileName.value = currentFile.value.name
  }
}

const removeFile = () => {
  currentFile.value = undefined
  fileName.value = currentFile.value
}
let formData = new FormData()

const uploadFile = (event) => {
  if (currentFile.value !== undefined){
  formData.append("file", currentFile.value)
  formData.append("bookingName", event.bookingName)
  formData.append("bookingEmail", event.bookingEmail)
  formData.append("eventCategoryId", event.eventCategoryId)
  formData.append("eventStartTime", dayjs(event.eventStartTime).utc().format())
  formData.append("eventNotes", event.eventNotes == undefined ? '' : event.eventNotes)
  }
  else{
  formData.append("bookingName", event.bookingName)
  formData.append("bookingEmail", event.bookingEmail)
  formData.append("eventCategoryId", event.eventCategoryId)
  formData.append("eventStartTime", dayjs(event.eventStartTime).utc().format())
  formData.append("eventNotes", event.eventNotes == undefined ? '' : event.eventNotes)
  }
}

</script>

<template>
<div>
  <div >
    <h1 class="text-xl font-bold mb-3 ml-4">ADD NEW EVENT</h1>
    <span>
      
    <span v-show="noCategory" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded absolute mx-24 top-40 ">Category must not be empty <button @click="noCategory = false">x</button></span>
      <span class="font-bold ml-4"> Category : </span> 
      <select v-if="newEvent.id === undefined" class="border-2  border-black text-black ml-1 mt-2 bg-zinc-300" v-model="selectedEventCategory" :onchange="findDuration">
        <option v-for="eventCategory in eventCategories" :key="eventCategory.id" :value="eventCategory.id">{{ eventCategory.eventCategoryName }}</option>
      </select>
      <select v-else disabled class="border-2  border-black text-black ml-1 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed" v-model="newEvent.eventCategoryId" :onchange="findDuration">
        <option v-for="eventCategory in eventCategories" :key="eventCategory.id" :value="eventCategory.id">{{ eventCategory.eventCategoryName }}</option>
      </select>
    </span>
    <span v-show="past" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded absolute mx-20 top-40 ">The selected date is not avaliable time <button @click="past = false , isDisabled = false">x</button></span>
    <span v-show="noStartTime" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded absolute mx-20 top-40 ">Please Enter Start Time <button @click=" noStartTime = false">x</button></span>
    <span v-show="isOverlap" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded absolute mx-20 top-40 ">Start time is overlap <button @click="isOverlap = false">x</button></span>
    <span class="font-bold"> StartTime : </span> <input v-if="newEvent.id === undefined" type="datetime-local"  class="text-black border-2 border-black bg-zinc-300 "  min="2022-04-19T00:01" v-model="startTime" :onchange="changeStartTime ">
                                                  <input v-else type="datetime-local" class="text-black border-2 border-black bg-zinc-300 "  min="2022-04-19T00:01" v-model="newEvent.eventStartTime" :onchange="changeStartTime ">
    <span class="font-bold"> Duration : </span> <input disabled type="text" :value="newEvent.eventDuration" class="text-black border-2 border-black bg-zinc-300 opacity-50 hover:cursor-not-allowed">
    <button v-show="(!editShow && newEvent.id === undefined) || checkDate" @click="checkOverlapTime(newEvent.eventCategoryId, dayjs(newEvent.eventStartTime).startOf('day').utc().format())" class="text-white bg-black mr-4 border border-solid hover:bg-[#855B52]  active:bg-cyan-600 font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3 ml-4">Next</button>
    </div>
    <div v-show="(showNotes || newEvent.id > 0) && !checkDate">

    <p class="ml-4 "> <span class="font-bold"> Name : </span>  <input type="text" :disabled="newEvent.id > 0" maxlength="101" class="border-2 border-black text-black ml-1 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed	" v-model="newEvent.bookingName" @click="noName = false" :onchange="checkName"> 
    <span v-show="noName" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mx-2 ">Name must be between 1 to 100 characters <button @click=" noName = false">x</button></span>
      <span class="font-bold"> Email : </span> <input type="email" :disabled="(refreshToken !== null || token !== null) || (newEvent.id > 0)" maxlength="100"  class="border-2 border-black text-black ml-1 mt-2 bg-zinc-300 disabled:opacity-50 disabled:hover:cursor-not-allowed" v-model="newEvent.bookingEmail"  :onchange="check">
          <span v-show="noValidEmail" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded absolute mx-20 right">Not a valid Email Address format <button @click=" noValidEmail = false">x</button></span>
          <span v-show="checkEmailNull" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded absolute mx-20 right">Email Address must not be empty  <button @click=" checkEmailNull = false">x</button></span>
    </p>
    <p class="ml-4"> <span class="font-bold"> Notes : </span> <br> 
    <span v-show="descErrMsg" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded absolute mx-20 right">Description must be between 0 to 500 characters  <button @click=" descErrMsg = false">x</button></span>
    <textarea rows="4" maxlength="501" cols="180" class="border-2 border-black bg-zinc-300" v-model="newEvent.eventNotes" :onchange="checkDesc"></textarea> <br>
    <div>
    <p><span class="font-bold">Upload File(optional)</span></p><br/>
    <label for="fileItem" class="text-white bg-black mr-4 border border-solid hover:bg-[#855B52]  active:bg-cyan-600 font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3 hover:cursor-pointer">
      > Upload File</label><br/>
    <input type="file" id="fileItem" :onchange="updateFile" class="hidden" /><br/>
    <p v-show="fileName">
      <span class="font-bold">Selected File : </span> {{fileName}}&nbsp;
      <button class="text-white bg-red-600  hover:bg-red-700  active:bg-red-900 font-bold uppercase text-sm py-1 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3 hover:cursor-pointer" @click="removeFile">Remove File</button>
    </p>
    </div>
    <div>
      <button :disabled="isDisabled" v-if="newEvent.id > 0" @click="changeStartTime() , isDisabled === true ? '' : $emit('updateEvent', {id: newEvent.id, eventStartTime: dayjs(newEvent.eventStartTime).utc().format(), eventNotes: newEvent.eventNotes}) " class="text-white bg-black mr-4 border border-solid hover:bg-[#855B52]  active:bg-cyan-600 font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3
      disabled:opacity-50 disabled:hover:cursor-not-allowed">
      Save</button>
      <button :disabled="isDisabled && (noName || checkEmailNull)" v-else  @click= "check() , isDisabled === true ? '' :  clear(), uploadFile(newEvent), $emit('addEvent', formData)"  class="text-white bg-black mr-4 border border-solid hover:bg-[#855B52]  active:bg-cyan-600 font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3 disabled:opacity-50 disabled:hover:cursor-not-allowed">
      Add</button>
      <button @click="$emit('cancel') , clear()" class="text-white bg-black mr-4 border border-solid hover:bg-[#855B52]  active:bg-cyan-600 font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3">Cancel</button>
    </div>
    </p>
  </div> 
</div>
</template>
 
<style>

</style>