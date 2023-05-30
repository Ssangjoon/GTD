package com.ssang.gtd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssang.gtd.user.dao.MemberRepository;
import com.ssang.gtd.user.dto.LoginReq;
import com.ssang.gtd.user.service.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;

//@Disabled
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired protected MockMvc mockMvc;

    @Autowired
    protected MemberRepository memberRepository;
    @Autowired
    protected MemberService memberService;

    protected String createJson(Object dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }
    protected String getAccessToken() throws Exception {
        LoginReq login = new LoginReq("테스트유저이름","1");

        ResultActions perform = mockMvc.perform(post("/api/oauth/token").content(createJson(login)));

        return perform.andReturn().getResponse().getContentAsString();
    }
}
