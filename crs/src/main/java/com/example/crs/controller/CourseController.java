package com.example.crs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Dependency Injection
import org.springframework.beans.factory.annotation.Autowired;  

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crs.model.Course;
import com.example.crs.model.CourseRepository;

/**
 * コースAPIのHTTPリクエストを受け付けるコントローラー。
 * CourseRepositoryでCourseを検索・保存・削除し、VueのHelloWorld.vueがGET /api/coursesを利用する。
 */
@CrossOrigin(origins = "http://localhost:5173") // used for vue
@RestController
@RequestMapping("/api")
public class CourseController {

	// SpringがCourseRepositoryの実装を注入する。実装と永続化処理はSpring Data JPAが提供する。
	@Autowired
	CourseRepository courseRepository;

	// APIの稼働確認用。/api/へのGETに応答する。
	@GetMapping("/")
	public String home()
	{
		return "API is working";
	}
	// GET /api/courses。title指定時は部分一致検索し、結果がCourseの配列としてJSON化される。
	@GetMapping("/courses")
	public ResponseEntity<List<Course>> getAllCourses(@RequestParam(required = false) String title) {
		try {
			List<Course> courses = new ArrayList<Course>();

			if (title == null)
				courseRepository.findAll().forEach(courses::add);
				
			else
				courseRepository.findByTitleContaining(title).forEach(courses::add);

			if (courses.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(courses, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// GET /api/courses/{id}。CourseRepositoryでID検索し、存在しなければ404を返す。
	@GetMapping("/courses/{id}")
	public ResponseEntity<Course> getCourseById(@PathVariable("id") long id) {
		Optional<Course> courseData = courseRepository.findById(id);

		if (courseData.isPresent()) {
			return new ResponseEntity<>(courseData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// POST /api/courses。受け取ったcode/titleからCourseを作成して保存する。
	@PostMapping("/courses")
	public ResponseEntity<Course> createTutorial(@RequestBody Course course) {
		try {
			Course _course = courseRepository.save(new Course(course.getCode(), course.getTitle()));
			return new ResponseEntity<>(_course, HttpStatus.CREATED);//201
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// PUT /api/courses/{id}。既存Courseのcode/titleを更新する。
	@PutMapping("/courses/{id}")
	public ResponseEntity<Course> updateCourse(@PathVariable("id") long id, @RequestBody Course course) {
		Optional<Course> courseData = courseRepository.findById(id);

		if (courseData.isPresent()) {
			Course _course = courseData.get();
			_course.setCode(course.getCode());
			_course.setTitle(course.getTitle());
			return new ResponseEntity<>(courseRepository.save(_course), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// DELETE /api/courses/{id}。指定IDのCourseを削除する。
	@DeleteMapping("/courses/{id}")
	public ResponseEntity<HttpStatus> deleteCourse(@PathVariable("id") long id) {
		try {
			courseRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// DELETE /api/courses。CourseRepositoryに保存された全Courseを削除する。
	@DeleteMapping("/courses")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		try {
			courseRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
