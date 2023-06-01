package com.ssang.gtd;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GtdApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("======================================");
		System.out.println(PathRequest.toStaticResources().atCommonLocations());
		System.out.println(PathRequest.toStaticResources());
		System.out.println("======================================");
	}

}
