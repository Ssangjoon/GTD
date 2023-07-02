package com.ssang.gtd;

import com.ssang.gtd.docs.IntegrationRestDocsTests;
import com.ssang.gtd.domain.user.domain.MemberSocial;
import com.ssang.gtd.domain.user.dto.member.MemberCreateDto;
import com.ssang.gtd.global.enums.Gender;
import com.ssang.gtd.global.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.ssang.gtd.global.jwt.JwtConstants.TOKEN_HEADER_PREFIX;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NoticeControllerTest extends IntegrationRestDocsTests {
    private MemberSocial member;
    @BeforeEach
    public void setUp(){
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);
        member = saveUser(req.toServiceDto()); // 테스트 유저 생성
    }

    @DisplayName("Notice ")
    //@Rollback(false)
    @Test
    public void notice() throws Exception {
        // given
        mockMvc.perform(
                        get("/api/notification/connect")
//                                .file(materialCollection)
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
                                .accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE)))
                .andExpect(status().isOk())
//                .andDo(
////                        restDocs.document(
////                                requestParts(
////                                        partWithName("materialCollection").description("목표, 절차, 날짜 등을 기재한다")
////                                )
////                        )
//                )
        ;
    }
}
