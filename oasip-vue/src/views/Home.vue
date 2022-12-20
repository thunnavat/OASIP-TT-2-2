<script setup>
import { ref, onBeforeMount } from 'vue'
import EventList from '../components/EventList.vue'
import ShowDetail from '../components/ShowDetail.vue'
import dayjs from 'dayjs'
import AddEditEvent from '../components/AddEditEvent.vue'
import {
  events as eventsObj,
  eventCategories as eventCategoriesObj
} from '../untils/untils.js'
import jwt_decode from 'jwt-decode'

const events = ref([])
const eventCategories = ref([])
const eventViews = ['ALL', 'DAY', 'CATEGORY', 'UPCOMING', 'PAST']
const selectedView = ref('ALL')
const selectedDate = ref(dayjs().format('YYYY-MM-DD'))
const selectedCategory = ref(1)
const emptyMsg = ref('EMPTY')
const clickForBooking = ref(false)
const newestEvent = ref({})
const token = localStorage.getItem('token')
const isModal = ref(false)
const currentEvent = ref({})
const tokenDecode = token !== null ? jwt_decode(token) : ''

onBeforeMount(async () => {
  eventCategories.value = await eventCategoriesObj.getEventCategories()
  if (token !== null) {
    events.value = await eventsObj.getEvents()
  }
})

const cancelform = () => {
  newestEvent.value = {}
  clickForBooking.value = false
}

const createNewEvent = async (newEvent) => {
  await eventsObj.createEvent(newEvent)
  events.value = await eventsObj.getEvents()
  cancelform()
}

const toEditMode = (editEvent) => {
  newestEvent.value = editEvent
  newestEvent.value.eventStartTime = dayjs(editEvent.eventStartTime).format(
    'YYYY-MM-DDTHH:mm'
  )
  newestEvent.value.eventCategoryId = editEvent.eventCategory.id
  clickForBooking.value = true
}

const updateEvent = async (updateEvent) => {
  await eventsObj.updateEvent(updateEvent)
  events.value = await eventsObj.getEvents()
  cancelform()
}

const deleteEvent = async (eventId) => {
  await eventsObj.deleteEvent(eventId)
  events.value = await eventsObj.getEvents()
}

const getDetail = (event) => {
  currentEvent.value = event
  isModal.value = true
}

const closeModal = (e) => {
  isModal.value = e
}

const change = async () => {
  if (selectedView.value === 'ALL') {
    events.value = await eventsObj.getEvents()
    emptyMsg.value = 'EMPTY'
  } else if (selectedView.value === 'CATEGORY') {
    events.value = await eventsObj.getEventsByCategory(selectedCategory.value)
    emptyMsg.value = 'No Scheduled Events'
  } else if (selectedView.value === 'UPCOMING') {
    events.value = await eventsObj.getEventsByUpcomingTime(
      dayjs().utc().format()
    )
    emptyMsg.value = 'No On-Going or Upcoming Events'
  } else if (selectedView.value === 'PAST') {
    events.value = await eventsObj.getEventsByPastTime(dayjs().utc().format())
    emptyMsg.value = 'No Past Events'
  } else if (selectedView.value === 'DAY') {
    events.value = await eventsObj.getEventsByDay(
      dayjs(selectedDate.value).startOf('day').utc().format()
    )
    emptyMsg.value = 'No Scheduled Events'
  }
}

const unique = events.value.filter((item, i, ar) => ar.indexOf(item) === i)
console.log(unique)
</script>

<template>
  <div>
    <div class="mt-4 flex justify-end mr-4">
      <button
        @click="clickForBooking = !clickForBooking"
        class="text-white bg-black mr-4 border border-solid hover:bg-[#855B52] active:bg-cyan-600 font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3"
      >
        BOOKING
      </button>
      <!-- Select Bar -->
      <div v-show="token !== null">
        <select
          id="select-bar"
          class="select ml-4 mb-6 mt-3 mr-4 text-black bg-blue-300 rounded font-bold"
          v-model="selectedView"
          :onchange="change"
        >
          <option
            v-for="(eventView, index) in eventViews"
            :key="index"
            class="font-bold"
          >
            {{ eventView }}
          </option>
        </select>
        <!-- User Select Specific day or category -->
        <div class="absolute top-48 right-5">
          <input
            type="date"
            v-show="selectedView === 'DAY'"
            v-model="selectedDate"
            :onchange="change"
          />
          <select
            v-show="selectedView === 'CATEGORY'"
            id="select-bar"
            v-model="selectedCategory"
            :onchange="change"
          >
            <option
              v-for="eventCategory in eventCategories"
              :key="eventCategory.id"
              :value="eventCategory.id"
              class="font-bold"
            >
              {{ eventCategory.eventCategoryName }}
            </option>
          </select>
        </div>
      </div>
    </div>

    <!-- Show Add Event -->
    <div>
      <add-edit-event
        v-show="clickForBooking"
        @addEvent="createNewEvent"
        :event="newestEvent"
        :eventCategories="eventCategories"
        @cancel="cancelform"
        @updateEvent="updateEvent"
      />
    </div>
    <!-- Show Detail -->
    <div v-if="isModal">
      <show-detail :event="currentEvent" @close="closeModal" />
    </div>
    <!-- Show Event List -->
    <div v-show="token !== null">
      <h2 class="text-xl font-bold ml-4 mt-4">EVENT LISTS</h2>
      <div class="box-border p-4 border-t-8 border-black">
        <div
          class="font-semibold flex justify-center items-center text-black box-content bg-[#c4c4c4] h-96"
          v-if="events.length === 0"
        >
          <span>{{ emptyMsg }}</span>
        </div>
        <div v-else>
          <event-list
            :events="events"
            :role="tokenDecode.role"
            @detail="getDetail"
            @deleteEvent="deleteEvent"
            @editEvent="toEditMode"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
