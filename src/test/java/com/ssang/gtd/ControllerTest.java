package com.ssang.gtd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssang.gtd.domain.things.domain.Collect;
import com.ssang.gtd.domain.things.domain.MatCol;
import com.ssang.gtd.domain.user.domain.MemberSocial;
import com.ssang.gtd.global.jwt.TokenProvider;
import com.ssang.gtd.global.redis.RedisDao;
import com.ssang.gtd.domain.things.dao.CollectRepository;
import com.ssang.gtd.domain.things.dao.MatCollectRepository;
import com.ssang.gtd.domain.things.dto.collect.CollectServiceDto;
import com.ssang.gtd.domain.things.dto.matcol.MatColCreateDto;
import com.ssang.gtd.domain.user.dao.MemberDetialRepository;
import com.ssang.gtd.domain.user.dao.MemberRepository;
import com.ssang.gtd.domain.user.dao.MemberSocialTypeRepository;
import com.ssang.gtd.domain.user.dto.LoginReq;
import com.ssang.gtd.domain.user.dto.member.MemberServiceDto;
import com.ssang.gtd.domain.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

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
    protected CollectRepository collectRepository;
    @Autowired
    protected MatCollectRepository matCollectRepository;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected RedisDao redisDao;
    @Autowired
    protected TokenProvider tokenProvider;
    @Autowired
    protected AuthenticationManager authenticationManager;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected MemberSocialTypeRepository memberSocialTypeRepository;
    @Autowired
    protected MemberDetialRepository memberDetialRepository;
    protected String email= "test0@test.com";
    protected String pwd = "1q2w3e@@";

    protected String createJson(Object dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }
    protected String getAccessToken() throws Exception {
        LoginReq login = new LoginReq("test0@test.com","1q2w3e@@");

        ResultActions perform = mockMvc.perform(post("/api/oauth/token").content(createJson(login)));

        return perform.andReturn().getResponse().getContentAsString();
    }

    public MemberSocial saveUser(MemberServiceDto dto){
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        return memberDetialRepository.save(dto.toMemberEntity());
    }
    public Collect saveCollect(CollectServiceDto dto){
        return collectRepository.save(dto.toEntity());
    }
    public MatCol saveMatCol(MatColCreateDto.MatColServiceDto dto){
        return matCollectRepository.save(dto.toEntity());
    }
}
