package com.ssang.gtd;

import com.ssang.gtd.docs.DocumentLinkGenerator;
import com.ssang.gtd.user.dao.MemberSocialTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GtdApplicationTests {

	@Autowired
    MemberSocialTypeRepository socialEntityRepository;

	@Test
	void contextLoads() {
		System.out.println("======================================");
		System.out.println(PathRequest.toStaticResources().atCommonLocations());
		System.out.println(PathRequest.toStaticResources());
		System.out.println("======================================");
	}

	@Test
	void test(){
		System.out.println("======================================");
		System.out.println(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.MEMBER_STATUS));
		System.out.println("======================================");

	}

}
