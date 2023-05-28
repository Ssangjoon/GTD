package com.ssang.gtd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssang.gtd.config.SecurityConfig;
import com.ssang.gtd.docs.CommonDocController;
import com.ssang.gtd.security.CustomAuthorizationFilter;
import com.ssang.gtd.user.controller.MemberController;
import com.ssang.gtd.user.dao.MemberRepository;
import com.ssang.gtd.user.service.MemberService;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
// 컨트롤러 테스트를 위한 어노테이션
// MockMvc 객체가 자동으로 생성되며 ControllerAdvice나 Filter, Interceptor 등
// 웹과 관련된 빈들만 생성해 @SpringBootTest에 비해 가볍다
@WebMvcTest(
    // 테스트할 컨트롤러를 지정한다.
    controllers = {
        MemberController.class,
        CommonDocController.class
    },
    //excludeAutoConfiguration = SecurityAutoConfiguration.class,
    excludeFilters =
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
            classes = {
            SecurityConfig.class,
            CustomAuthorizationFilter.class
        })
)
public class ControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired protected MockMvc mockMvc;

    //@WebMvcTest는 스프링부트가 제공하는 테스트 환경이므로 @Mock과 @Spy 대신 각각 @MockBean과 @SpyBean을 사용해주어야 한다.
    @MockBean
    protected MemberRepository memberRepository;
    @MockBean
    protected MemberService memberService;


    protected String createJson(Object dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }
}
