package com.ssang.gtd;

import com.ssang.gtd.entity.MemberSocial;
import com.ssang.gtd.user.dao.MemberSocialTypeRepository;
import com.ssang.gtd.oauth2.SocialType;
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
		MemberSocial test = new MemberSocial("이상준", null, 12,null, SocialType.GOOGLE,"sldkf");
		socialEntityRepository.save(test);
	}

}
