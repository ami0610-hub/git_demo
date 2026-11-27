package com.example.crs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * コース1件を表すJPAエンティティ。
 * CourseRepositoryがこの型を永続化し、CourseControllerがAPIレスポンスとしてVueへ返す。
 */
@Entity
@Table(name = "courses")
public class Course {

	// DB上の主キー。新規Courseの保存時はJPAがIDを採番する。
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	// コースコード。Vueのコース一覧ではcode列に表示される。
	@Column(name = "code")
	private String code;

	// コース名。CourseRepositoryのタイトル検索にも使われる。
	@Column(name = "title")
	private String title;

	public Course() {
	}

	public Course(String code, String title) {
		this.code = code;
		this.title = title;
	}

	public long getId() {
		return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

	}

