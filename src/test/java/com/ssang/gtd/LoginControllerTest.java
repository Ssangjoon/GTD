package com.ssang.gtd;

import com.ssang.gtd.docs.IntegrationRestDocsTests;
import com.ssang.gtd.user.dto.LoginReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.Duration;

import static com.ssang.gtd.jwt.JwtConstants.TOKEN_HEADER_PREFIX;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTest extends IntegrationRestDocsTests {
    @DisplayName("로그인")
    @Test
    public void login() throws Exception {
        LoginReq login = new LoginReq("test0@test.com","1q2w3e@@");

        //when
        mockMvc.perform(
                        post("/api/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createJson(login))
                    )
                .andExpect(status().isOk())
                //then
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("email").description("email"),
                                        fieldWithPath("password").description("password")
                                )
                        )
                )
        ;
    }
    @DisplayName("로그아웃")
    @Test
    public void logout() throws Exception {

        //when
        mockMvc.perform(
                        post("/api/logout")
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        //then
//                .andDo(
//                        restDocs.document(
//                                responseFields(
//
//                                )
//                        )
//                )
        ;
    }
    @DisplayName("레프레시 토큰을 통한 액세스 토큰 재발급")
    @Test
    public void refresh() throws Exception {

        //given
        String refreshToken = tokenProvider.generateRefreshToken();
        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLthYzsiqTtirjsnKDsoIDsnbTrpoQiLCJhdXRoIjoiVVNFUiIsImV4cCI6MTY4NTYyNDEyMH0.lIwyXdtPo3VsqCPn3W5vs28Lcg-W1puIjDbFphltoY4";
        String username = "테스트유저이름";
        redisDao.setValues(username, refreshToken, Duration.ofDays(14));

        //when
        mockMvc.perform(
                        post("/api/refresh")
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + accessToken)
                                .header(HttpHeaders.AUTHORIZATION+"-refresh",TOKEN_HEADER_PREFIX + refreshToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
        //then
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("accessToken").description("accessToken"),
                                        fieldWithPath("refreshToken").optional().description("refreshToken")
                                )
                        )
                )
        ;
    }

}
