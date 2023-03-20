package com.ssang.gtd;

import com.ssang.gtd.things.CollectDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class GtdApplication {

	public static void main(String[] args) {
		SpringApplication.run(GtdApplication.class, args);
	}

}

