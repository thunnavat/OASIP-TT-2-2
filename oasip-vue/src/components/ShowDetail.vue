<script setup>
import dayjs from 'dayjs'
import {ref} from 'vue'
import {events as eventsObj} from '../untils/untils.js'

defineEmits(['close'])
const props = defineProps({
    event: {
       type: Object
        }
})

// const fileName = ref()

// const updateFile = () => {
//   console.log("running")
// }

// const removeFile = () => {
//   currentFile.value = undefined
//   fileName.value = currentFile.value
// }

const file = ref()
const download = async(filePath) => {
  file.value = await eventsObj.getFile(filePath)
  
  // const reader = new FileReader();

  // reader.addEventListener("load", () => {
  //   // convert image file to base64 string
  //   preview.src = reader.result;
  // }, false);

  // if (file) {
  //   reader.readAsDataURL(file);
  // }

}

</script>
 
<template>
<div class="modal-mask">
    <div class="modal-wrapper">
        <div class="modal-container bg-[#855B52]">
            <div class="modal-header text-lg font-bold text-white"  >
                <h3> Detail </h3>
            </div>
            <div class="modal-body text-white ">
        <p class="font-medium">START TIME: {{ dayjs(event.eventStartTime).format('DD MMMM YYYY HH.mm') }}</p>  
        <p class="font-medium">DURATION: {{ event.eventDuration }} mins</p>
        <p class="font-medium">CATEGORY: {{ event.eventCategory.eventCategoryName }}</p>
        <p class="font-medium">NAME: {{ event.bookingName }}</p>
        <p class="font-medium">EMAIL: {{ event.bookingEmail }}</p>
        <p class="font-medium">NOTE: {{ event.eventNotes }}</p> <br />
        <div v-show="event.fileAttachment !== null">
          <span class="text-lg font-sans italic">You Have 1 File Attachment</span><br />
          <a class="hover:cursor-pointer text-gray-300 font-bold underline hover:text-gray-700" :href="file" @click="download(event.fileAttachment)" download>Download</a>
        </div>
        <!-- <div v-show="event.fileAttachment === null">
          <p><span class="font-bold">Upload File(optional)</span></p><br/>
          <input type="file" id="fileItem" :onchange="updateFile" />

        <p v-show="fileName">
          <span class="font-bold">Selected File : </span> {{fileName}}&nbsp;
          <button class="text-white bg-red-600  hover:bg-red-700  active:bg-red-900 font-bold uppercase text-sm py-1 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3 hover:cursor-pointer" @click="removeFile">Remove File</button>
         </p> -->
        <!-- </div> -->
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