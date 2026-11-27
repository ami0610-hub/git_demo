package com.example.crs.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * CourseのDBアクセス窓口。CourseControllerとCrsApplicationから利用される。
 * JpaRepositoryの基本CRUDに加え、メソッド名からタイトル・コード検索を自動生成する。
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

	// タイトル完全一致検索。Spring Data JPAがメソッド名からクエリを生成する。
	List<Course> findByTitle(String title);

	// コード完全一致検索。
	List<Course> findByCode(String code);

	// タイトル部分一致検索。CourseControllerのGET /api/courses?title=...で使う。
	List<Course> findByTitleContaining(String title);

	
}

