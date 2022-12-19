import router from '../router'

const url = import.meta.env.PROD ?  import.meta.env.VITE_API_URL : '/api'
const token  = localStorage.getItem('token')
const refreshToken = localStorage.getItem('refreshToken')

export const events = {
  getFile: async (filePath) => {
    const res = await fetch(`${url}/files/download?filePath=${filePath}`, {
      method: 'GET',
      headers: {
        'Authorization': refreshToken !== null ? refreshToken : token
      }
    })     
    if (res.status === 200) {
      return await res.json()
    } else console.log('Error, cannot get files')
  },
  getEvents: async () => {
    const res = await fetch(`${url}/events`, {
      method: 'GET',
      headers: {
        'Authorization': refreshToken !== null ? refreshToken : token
      }
    })
    if (res.status === 200) {
      return await res.json()
    } else console.log('Error, cannot get events')
  },

  getEventsByDay: async (dateTimeMidnight) => {
    const res = await fetch(`${url}/events/day?dateTimeMidnight=${dateTimeMidnight}`, {
      method: 'GET',
      headers: {
        'Authorization': refreshToken !== null ? refreshToken : token
      }
    })
    if (res.status === 200) {
      return await res.json()
    } else console.log('Error, cannot get events by day')
  },

  getEventsByCategory: async (categoryId) => {
    const res = await fetch(`${url}/events/category/${categoryId}`, {
      method: 'GET',
      headers: {
        'Authorization': refreshToken !== null ? refreshToken : token
      }
    })
    if (res.status === 200) {
      return await res.json()
    } else console.log('Error, cannot get events by category')
  },

  getEventsByUpcomingTime: async (eventStartTime) => {
    const res = await fetch(`${url}/events/upcoming?eventStartTime=${eventStartTime}`, {
      method: 'GET',
      headers: {
        'Authorization': refreshToken !== null ? refreshToken : token
      }
    })
    if (res.status === 200) {
      return await res.json()
    } else console.log('Error, cannot get events by upcoming time')
  },

  getEventsByPastTime: async (eventStartTime) => {
    const res = await fetch(`${url}/events/past?eventStartTime=${eventStartTime}`, {
      method: 'GET',
      headers: {
        'Authorization': refreshToken !== null ? refreshToken : token
      }
    })
    if (res.status === 200) {
      return await res.json()
    } else console.log('Error, cannot get events by upcoming time')
  },

  getEventsByCategoryAndDate: async(eventCategoryId, startDateMidNightTime) => {
    const res = await fetch(`${url}/events/categoryDate?eventCategoryId=${eventCategoryId}&startDateMidNightTime=${startDateMidNightTime}`, {
      method: 'GET',
      headers: {
        'Authorization': refreshToken !== null ? refreshToken : token
      }
    })
    if (res.status === 200) {
      return await res.json()
    } else console.log('Error, cannot get events by category and date')
  },

  createEvent: async (newEvent) => {
    const res = await fetch(`${url}/events`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json',
        'Authorization': refreshToken !== null ? refreshToken : token
      },
      body: newEvent
    })
    if (res.status === 201) {
      alert('Added Successfully')
    } else console.log('Error, cannot create new event')
  },


  updateEvent: async (updateEvent) => {
    const res = await fetch(`${url}/events/${updateEvent.id}`, {
      method: 'PUT',
      headers: {
        'content-type': 'application/json',
        'Authorization': refreshToken !== null ? refreshToken : token
      },
      body: JSON.stringify({eventStartTime: updateEvent.eventStartTime, eventNotes: updateEvent.eventNotes})
    })
    if (res.status === 200) {
      alert('Updated Successfully')
    } else console.log('Error, cannot update event')
  },

  deleteEvent: async (eventId) => {
    if (confirm(`Do you want to delete event-id: ${eventId} `) === true) {
      const res = await fetch(`${url}/events/${eventId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': refreshToken !== null ? refreshToken : token
        }
      })
      if (res.status === 200) {
        
      } else console.log('Error, cannot delete event')
    } else console.log(`Cancel to delete event-id: ${eventId}`)
  }
}

export const eventCategories = {
  getEventCategories: async () => {
    const res = await fetch(`${url}/eventCategories`, {
      method: 'GET',
      headers: {
        'Authorization': refreshToken !== null ? refreshToken : token
      }
    })
    if (res.status === 200) {
      return await res.json()
    } else console.log('Error, cannot get event categories')
  },

  updateEventCategory: async (updateEventCategory) => {
    const res = await fetch(`${url}/eventCategories/${updateEventCategory.id}`, {
      method: 'PUT',
      headers: {
        'content-type': 'application/json',
        'Authorization': refreshToken !== null ? refreshToken : token
      },
      body: JSON.stringify({eventCategoryName: updateEventCategory.eventCategoryName, 
        eventCategoryDescription: updateEventCategory.eventCategoryDescription, eventDuration: updateEventCategory.eventDuration})
    })
    if (res.status === 200) {
      alert('Updated Successfully')
    } else console.log('Error, cannot update event category')
  }
}

export const users = {
  getUsers: async () => {
    const res = await fetch(`${url}/users`, {
      method: 'GET',
      headers: {
        'Authorization': refreshToken !== null ? refreshToken : token
      }
    })
    if (res.status === 200) {
      return await res.json()
    } else console.log('Errorm, cannot get users')
  },

  createUser: async (newUser) => {
    const res = await fetch(`${url}/users`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json',
        'Authorization': refreshToken !== null ? refreshToken : token
      },
      body: JSON.stringify({name: newUser.name, email: newUser.email, role: newUser.role, password: newUser.password})
    })
    if (res.status === 201) {
      alert('Added successfully')
    } else if (res.status === 400) {
      const errorMessage = await res.json()
      alert(errorMessage.message)
    } else console.log('Error, cannot create new user')
  },

  updateUser: async (updateUser) => {
    const res = await fetch(`${url}/users/${updateUser.id}`, {
      method: 'PUT',
      headers: {
        'content-type': 'application/json',
        'Authorization': refreshToken !== null ? refreshToken : token
      },
      body: JSON.stringify({name: updateUser.name, email: updateUser.email, role: updateUser.role})
    })
    if(res.status === 200) {
      alert('Updated successfully')
    } else console.log('Error, cannot update user')
  },

  deleteUser: async (userId) => {
    if (confirm(`Do you want to delete user-id: ${userId} `) === true) {
      const res = await fetch(`${url}/users/${userId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': refreshToken !== null ? refreshToken : token
        }
      })
      if (res.status === 200) {

      } else console.log('Error, cannot delete user')
    } else console.log(`Cancel to delete user-id: ${userId}`)
  }
}

export const authentication = {
  match: async (authenticationRequest) => {
    const res = await fetch(`${url}/match`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json',
        'Authorization': refreshToken !== null ? refreshToken : token
      },
      body: JSON.stringify({email : authenticationRequest.email , password : authenticationRequest.password})
    })
    if (res.status === 200) {

    } else console.log('')
  },

  login: async (authenticationRequest) => {
    const res = await fetch(`${url}/login`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json'
      },
      body: JSON.stringify({email : authenticationRequest.email , password : authenticationRequest.password})
    })
    if (res.status === 200) {
      const token = await res.json()
      localStorage.setItem('token', 'Bearer ' + token.token)
      alert('Login Successful')
      router.replace({name: 'Home'})
      setTimeout(() => {
        router.go(0)
      })
    } else if (res.status === 401){
      alert('Password Incorrect') 
    } else if (res.status === 404){
      alert('A user with the specified email DOES NOT exist')
    } else console.log('Error, cannot login')
  },

  refresh: async () => {
    const res = await fetch(`${url}/refresh`, {
      method: 'GET',
      headers: {
        'Authorization': token,
        'isRefreshToken': 'true'
      }
    })
    if (res.status === 200) {
      const refreshToken = await res.json()
      localStorage.setItem('refreshToken', 'Bearer ' + refreshToken.token)
      router.replace({name: `${router.currentRoute.value.name}`})
      setTimeout(() => {
        router.go(0)
      })
    } else console.log('Error, cannot get refresh token')
  },

  logout: () => {
    localStorage.clear()
    router.replace({name: 'Login'})
    setTimeout(() => {
      router.go(0)
    })
  }
}