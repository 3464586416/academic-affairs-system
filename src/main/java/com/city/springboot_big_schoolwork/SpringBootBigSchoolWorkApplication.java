package com.city.springboot_big_schoolwork;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.city.springboot_big_schoolwork.mapper")
public class SpringBootBigSchoolWorkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBigSchoolWorkApplication.class, args);
	}

}
