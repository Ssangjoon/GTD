package com.ssang.gtd;

import com.ssang.gtd.docs.AbstractRestDocsTests;
import com.ssang.gtd.user.dto.LoginReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTest extends AbstractRestDocsTests {
    @DisplayName("로그인")
    @Test
    public void login() throws Exception {
        LoginReq login = new LoginReq("굿데브상준3","12345");

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
                                        fieldWithPath("userName").description("userName"),
                                        fieldWithPath("password").description("password")
                                )
                        )
                )
        ;
    }
    @DisplayName("로그아웃")
    //@Test
    public void logout() throws Exception {

        // given


        //when
        mockMvc.perform(
                        post("/api/logout")
                                .header(AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLqtb_rjbDruIzsg4HspIAzIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE2ODUzNDQyNjB9.I59rrdfR3mkcOC0kaiffK3Uil-ZarrRrHt5xSbaZhS0")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //then
//                .andDo(
//                        restDocs.document(
//                                requestFields(
//                                )
//                        )
//                )
        ;
    }

//    @DisplayName("로그인")
//    @Test
//    public void login(){
//
//        mockMvc.perform(
//                get("/api/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(createJson())
//        )
//    }
}
