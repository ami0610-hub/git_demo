package com.example.crs;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.crs.model.Course;
import com.example.crs.model.CourseRepository;

/**
 * Spring Bootアプリケーションの起動クラス。
 * CourseRepositoryを使って起動時にサンプルコースを登録し、CourseControllerが返す初期データを用意する。
 */
@SpringBootApplication
public class CrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsApplication.class, args);
	}

	// Spring起動後に一度実行される初期化処理。CourseはJPAを通じてH2データベースへ保存する。
	@Bean
	ApplicationRunner init(CourseRepository repository) {
		//lambda 
		return args -> {

			repository.save(new Course("CSIS2175", "ADVANCED INTEGRATED SOFTWARE DEVELOPMENT"));
			repository.save(new Course("CSIS3275", "SOFTWARE ENGINEERING"));
			repository.save(new Course("CSIS1190", "EXCEL FOR BUSINESS"));
			repository.findAll().forEach(System.out::println);
		};
	}

}

