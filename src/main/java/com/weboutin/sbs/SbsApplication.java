package com.weboutin.sbs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.weboutin.sbs.mappers")
public class SbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbsApplication.class, args);
	}

}
