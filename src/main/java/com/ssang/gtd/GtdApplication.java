package com.ssang.gtd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableAspectJAutoProxy
@SpringBootApplication
@EnableJpaAuditing
public class GtdApplication {
	public static void main(String[] args) {
		SpringApplication.run(GtdApplication.class, args);
	}

}

