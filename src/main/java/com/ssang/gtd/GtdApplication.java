package com.ssang.gtd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableAspectJAutoProxy
@EnableScheduling
@SpringBootApplication
public class GtdApplication {
	public static void main(String[] args) {
		SpringApplication.run(GtdApplication.class, args);
	}

}

