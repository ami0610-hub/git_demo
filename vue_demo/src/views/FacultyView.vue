<template>
  <div class="container">
    <div class="header">
      <h1>Faculty Course Management</h1>
      <button @click="logout" class="btn-secondary">Logout</button>
    </div>

    <!-- Add Course Form -->
    <div class="card">
      <h3>Add a New Course</h3>
      <form @submit.prevent="createCourse" class="course-form">
        <input 
          v-model="newCode" 
          placeholder="Course Code (e.g., CSIS 3275)" 
          required 
        />
        <input 
          v-model="newTitle" 
          placeholder="Course Title (e.g., Software Engineering)" 
          required 
        />
        <button type="submit" class="btn-primary">Add Course</button>
      </form>
      <p v-if="message" class="status-msg">{{ message }}</p>
    </div>

    <!-- Course List Table (Reused from Lab 6) -->
    <h2>Existing Courses</h2>
    <table v-if="courses.length > 0" class="course-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Course Code</th>
          <th>Course Title</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="course in courses" :key="course.id">
          <td>{{ course.id }}</td>
          <td>{{ course.code }}</td>
          <td>{{ course.title }}</td>
        </tr>
      </tbody>
    </table>
    <p v-else>No courses available.</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const courses = ref([])
const newCode = ref('')
const newTitle = ref('')
const message = ref('')

// Load courses automatically when page opens
const fetchCourses = async () => {
  try {
    const res = await fetch('http://localhost:8080/api/courses')
    if (res.ok && res.status !== 204) {
      courses.value = await res.json()
    }
  } catch (err) {
    console.error('Failed to load courses:', err)
  }
}

// Submit new course via POST request
const createCourse = async () => {
  message.value = ''
  try {
    const res = await fetch('http://localhost:8080/api/courses', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        code: newCode.value,
        title: newTitle.value
      })
    })

    if (res.ok) {
      message.value = 'Course added successfully!'
      newCode.value = ''
      newTitle.value = ''
      await fetchCourses() // Refresh the table immediately
    } else {
      message.value = 'Error adding course.'
    }
  } catch (err) {
    message.value = 'Network error. Check backend connection.'
  }
}

const logout = () => {
  localStorage.clear()
  router.push('/')
}

// Automatically fetch courses on component mount
onMounted(() => {
  fetchCourses()
})
</script>

<style scoped>
.container { max-width: 800px; margin: 20px auto; font-family: sans-serif; }
.header { display: flex; justify-content: space-between; align-items: center; }
.card { background: #f9f9f9; padding: 15px; border-radius: 8px; margin: 20px 0; border: 1px solid #ddd; }
.course-form { display: flex; gap: 10px; }
.course-form input { padding: 8px; flex: 1; border: 1px solid #ccc; border-radius: 4px; }
.btn-primary { padding: 8px 16px; background: #42b883; color: white; border: none; border-radius: 4px; cursor: pointer; }
.btn-secondary { padding: 8px 16px; background: #666; color: white; border: none; border-radius: 4px; cursor: pointer; }
.status-msg { color: #2c7a7b; font-weight: bold; margin-top: 10px; }
.course-table { width: 100%; border-collapse: collapse; margin-top: 10px; }
.course-table th, .course-table td { border: 1px solid #ddd; padding: 10px; text-align: left; }
.course-table th { background-color: #f2f2f2; }
</style>
