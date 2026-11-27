
package com.example.crs.controller;

import com.example.crs.model.Student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * VueのStudentLogin.vueから呼ばれる学生ログイン・登録API。
 * Studentを使って学生情報を扱う。現在の学生データはDBではなくプロセス内Mapに保持する。
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class StudentController {

    // 学生IDをキーに学生を保持する簡易インメモリ領域。サーバー再起動で登録データは消える。
    private static final Map<String, Student> studentDatabase =
            new ConcurrentHashMap<>();

    // Vueから動作確認できる初期アカウント。
    static {
        Student defaultStudent = new Student();
        defaultStudent.setStudentId("1001");
        defaultStudent.setPassword("123456");
        defaultStudent.setName("Test Student");
        defaultStudent.setEmail("test@school.edu");
        studentDatabase.put("1001", defaultStudent);
    }

    // POST /api/login。StudentLogin.vueのLoginService.loginが呼び出し、成功時にid/nameを返す。
    @PostMapping("/login")
    //public ResponseEntity<?> login(...) { ... }   
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String studentId = loginRequest.get("studentId");
        String password = loginRequest.get("password");

        // 学生IDとパスワードの未入力を検査する。
        if (studentId == null || studentId.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            
            Map<String, String> error = new HashMap<>();
            error.put("message", "Student ID and Password cannot be empty.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        // 入力された学生IDでStudentを検索する。
        Student student = studentDatabase.get(studentId);

        // パスワードが一致した場合だけ、フロントエンドへ必要な識別情報を返す。
        if (student != null && student.getPassword().equals(password)) {
            // success, assemble the data back to  Vue
            Map<String, Object> response = new HashMap<>();
            response.put("id", student.getStudentId());
            response.put("name", student.getName());
            
            // send 200 OK along with student info
            return ResponseEntity.ok(response);
        } else {
            // login fail
            Map<String, String> error = new HashMap<>();
            error.put("message", "Invalid Student ID or Password");
            
            // send 401 Unauthorized 
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }


    // POST /api/register。StudentLogin.vueの登録モーダルからStudent情報を受け取りMapへ追加する。
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
