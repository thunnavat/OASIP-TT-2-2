<script setup>
import {ref , onBeforeMount} from 'vue'
import EditEventCategory from '../components/EditEventCategory.vue';
import EventCategoryList from '../components/EventCategoryList.vue';
import {eventCategories as eventCategoriesObj} from '../untils/untils.js'

const eventCategories = ref([])
const editMode = ref(false)
const token = localStorage.getItem('token')
const refreshtoken = localStorage.getItem('refreshtoken')
const newestEventCategory = ref({}) 

onBeforeMount(async () => {
  eventCategories.value = await eventCategoriesObj.getEventCategories()
})

const cancelform = () => {
  newestEventCategory.value = {}
  editMode.value = false
}

const toEditMode = (editEventCategory) => {
  newestEventCategory.value = editEventCategory
  editMode.value = true
}

const updateEventCategory = async (updateEventCategory) => {
  await eventCategoriesObj.updateEventCategory(updateEventCategory)
  eventCategories.value = await eventCategoriesObj.getEventCategories()
  cancelform()
}

</script>
 
<template>
<div>
  <div v-if="token === null" class="text-xl">
    You have to
    <router-link :to="{name:'Login'}" class="text-blue-700 font-semibold underline">Login</router-link>
    First
  </div>
  <div v-else>
    <div>
      <edit-event-category
      v-show="editMode"
      :eventCategory="newestEventCategory"
      :eventCategories = "eventCategories"
      @updateEventCategory = updateEventCategory
      />
    </div>
    <div>
      <event-category-list :eventCategories="eventCategories" @editEventCategory="toEditMode"/>
    </div>
  </div>
</div>
</template>
 
<style>

</style>