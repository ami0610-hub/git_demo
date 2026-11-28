<template>
  
  <!-- Full-screen background wrapper -->
  <div class="login-container">
    <!-- Centered login form card -->
    <div class="edit-form">
      <h4>Login</h4>
      <form @submit.prevent="login">
        <div style="margin: 10px 0;">
	<label style="margin-right: 15px;">
		<input 
	                	  type="radio" 
			  name="role" 
			  value="STUDENT" 
			  v-model="studentLoginRequest.role" 
		/> Student
	</label>
	<label>
		<input 
			  type="radio" 
			  name="role" 
			  value="FACULTY" 
			  v-model="studentLoginRequest.role" 
		/> Faculty
	</label>
</div>

        <div>
          <label for="studentId">Student ID: </label>
          <input type="text" id="studentId" v-model="studentLoginRequest.userId"/>
        </div>
        <div>
          <label for="password">Password: </label>
          <input type="password" id="password" v-model="studentLoginRequest.password"/>
        </div>        
        <!-- Button group to place buttons side-by-side -->
        <div class="button-group">
          <button type="submit">Login</button>
          <!-- Changed type to "button" to prevent form auto-submission -->
          <button type="button" @click="openCreateModal" class="create-btn">Create</button>
        </div>
      </form> 
      <p v-if="message" style="color: red;">{{ message }}</p>
    </div>
    <!-- NEW: Create Student Info Modal Pop-up -->
    <div v-if="showModal" class="modal-overlay">
      <div class="modal-card">
        <h4>Create Student Account</h4>
        <form @submit.prevent="submitRegistration">
          <div>
            <label for="newStudentId">Student ID: </label>
            <input type="text" id="newStudentId" v-model="newStudent.studentId" required />
          </div>
          <div>
            <label for="newPassword">Password: </label>
            <input type="password" id="newPassword" v-model="newStudent.password" required />
          </div>
          <div>
            <label for="studentName">Full Name: </label>
            <input type="text" id="studentName" v-model="newStudent.name" required />
          </div>
          <div>
            <label for="studentEmail">Email: </label>
            <input type="email" id="studentEmail" v-model="newStudent.email" required />
          </div>
          <!-- Modal Action Buttons -->
          <div class="button-group">
            <button type="submit" class="submit-btn">Submit</button>
            <button type="button" @click="closeCreateModal" class="cancel-btn">Cancel</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>



import LoginService from "../services/LoginService";
export default {
  name: "studentLogin",
  data() {
    return {
      studentLoginRequest: {
        userId: "",
        password: "",
        role: "STUDENT" 
      },
      message: "",

      showModal: false,

      newStudent: {
        studentId: "",
        password: "",
        name: "",
        email: ""
      }
    };
  },
  methods: {

    login() {
      LoginService.login(this.studentLoginRequest)
        .then(response => {
          console.log("1. Raw response:", response);
          const resData = response.data || {}; //  

          if (!resData || (typeof resData === 'object' && Object.keys(resData).length === 0)) {
            this.message = "Backend returned 200 OK, but response body is empty!";
            return;
          }
		  
         const currentRole = resData.role || this.studentLoginRequest.role;
          const currentUserId = resData.userId || this.studentLoginRequest.userId;
          localStorage.setItem('userId', currentUserId);
          localStorage.setItem('userRole', currentRole);

          // if (currentRole === 'STUDENT') {
          //   localStorage.setItem('sid', resData.id);
          //   }

          alert("Login success! Role: " + currentRole);
         if (currentRole === 'FACULTY') {
            this.$router.push('/faculty');
          } else {
            this.$router.push('/helloworld');
          }        
})
        .catch(e => {
          this.studentLoginRequest.userId = "";
          this.studentLoginRequest.password = "";
          this.message = e.response?.data?.message || "Login failed, check internet or credential";
          console.error(e);
        });
    },


    openCreateModal() {
      this.showModal = true;
    },    

    closeCreateModal() {
      this.showModal = false;
      this.newStudent = { studentId: "", password: "", name: "", email: "" };
    },    
    // NEW: Handle the registration logic
    


    submitRegistration() {
      LoginService.register(this.newStudent)
        .then(response => {
          alert(response.data.message);
          this.closeCreateModal();
        })
        .catch(error => {
          if (error.response && error.response.data.message) {
            alert(error.response.data.message);
          } else {
            alert("Registration failed. Please try again.");
          }
        });
    }
  },
  mounted() {
    this.message = "";
  }
};

</script>
<style scoped>
/* Main wrapper setting the full-screen view and background picture */
.login-container {
  width: 100vw;
  height: 100vh;
  background-image: url('../assets/background.png'); 
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}
/* Form card layout */
.edit-form {
  max-width: 320px;
  width: 100%;
  padding: 25px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 8px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);
}
.edit-form div, .modal-card div {
  margin-bottom: 12px;
  display: flex;
  flex-direction: column; /* Stacks labels above input fields for cleaner look */
}
.edit-form label, .modal-card label {
  font-size: 0.9rem;
  margin-bottom: 4px;
  font-weight: bold;
}
.edit-form input, .modal-card input {
  padding: 6px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
/* Aligns buttons inline with equal distribution spacing */
.button-group {
  display: flex;
  gap: 10px;
  margin-top: 15px;
}
.button-group button {
  flex: 1;
  padding: 8px;
  cursor: pointer;
  border-radius: 4px;
}
.create-btn {
  background-color: #e0e0e0;
  border: 1px solid #ccc;
}
/* NEW: Full-screen semi-transparent overlay for the modal pop-up */
.modal-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5); /* Dims the background image */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100; /* Keeps the modal on top of everything else */
}
/* NEW: Centered Pop-up Form Card */
.modal-card {
  max-width: 360px;
  width: 100%;
  padding: 25px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.3);
}
.submit-btn {
  background-color: #4CAF50;
  color: white;
  border: none;
}
.cancel-btn {
  background-color: #f44336;
  color: white;
  border: none;
}
</style>
