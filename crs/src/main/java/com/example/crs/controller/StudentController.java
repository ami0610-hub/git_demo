
package com.example.crs.controller;

import com.example.crs.model.Student;
import com.example.crs.model.StudentLoginRequest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;





@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class StudentController {


    private static final Map<String, Student> studentDatabase =
            new ConcurrentHashMap<>();


    static {
        Student defaultStudent = new Student();
        defaultStudent.setStudentId("1001");
        defaultStudent.setPassword("123456");
        defaultStudent.setName("Test Student");
        defaultStudent.setEmail("test@school.edu");
        studentDatabase.put("1001", defaultStudent);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody StudentLoginRequest loginRequest) {

        String password = loginRequest.getPassword();
        String userId = loginRequest.getUserId();
     // String password = loginRequest.get("password");
        String role = loginRequest.getRole();

        Map<String, Object> response = new HashMap<>();

  
        

        // 1. Faculty Login
        if ("FACULTY".equals(role)) {

            if ("faculty1".equals(userId) && "123".equals(password)) {

                response.put("status", "success");
                response.put("userId", userId);
                response.put("role", "FACULTY");

                return ResponseEntity.ok(response);

            } else {

                response.put("status", "error");
                response.put("message", "Invalid Faculty credentials.");

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        }

        // 2. Student Login
        if ("STUDENT".equals(role)) {

            Student student = studentDatabase.get(userId);

            if (student != null && student.getPassword().equals(password)) {

                response.put("status", "success");
                response.put("id", student.getStudentId());
                response.put("userId", student.getStudentId());
                response.put("name", student.getName());
                response.put("role", "STUDENT");

                return ResponseEntity.ok(response);

            } else {

                response.put("status", "error");
                response.put("message", "Invalid Student ID or Password");

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        }

        // 3. Invalid Role
        response.put("status", "error");
        response.put("message", "Invalid role.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }



    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody Student newStudent) {
        // validate data, make sure not null
        if (newStudent.getStudentId() == null || ((String) newStudent.getStudentId()).trim().isEmpty() ||
            newStudent.getPassword() == null || ((String) newStudent.getPassword()).trim().isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Student ID and Password cannot be empty.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // check studentId has been created or not
        if (studentDatabase.containsKey(newStudent.getStudentId())) {
            Map<String, String> errorResponse = new HashMap<>();
            // align with  Vue frontend  
            errorResponse.put("message", "Registration failed: Student ID already exists.");
            
            // return 409 Conflict status code
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        // if it is unique StudentID, "insert" into the simulated database
        studentDatabase.put((String) newStudent.getStudentId(), newStudent);

        // successfully registered
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Registration successful!");
        
        // return 201 Created status code
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

}
