package com.ssang.gtd;

import com.ssang.gtd.docs.IntegrationRestDocsTests;
import com.ssang.gtd.entity.Member;
import com.ssang.gtd.things.dto.collect.CollectCreateDto;
import com.ssang.gtd.things.dto.collect.CollectionUpdateDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ThingsControllerTest extends IntegrationRestDocsTests {

    @DisplayName("Collect 게시글 작성")
    //@Transactional
    @Test
    public void collect_post() throws Exception {
        CollectCreateDto.CollectCreateRequest dto = new CollectCreateDto.CollectCreateRequest("사이드 프로젝트 마무리하기", Member.builder().id(1L).build());

        mockMvc.perform(
                        post("/api/collection")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer " + getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createJson(dto)))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                relaxedResponseFields(
                                        subsectionWithPath("data").description("info of collect"),
                                        fieldWithPath("data.id").description("게시글 번호"),
                                        fieldWithPath("data.content").description("내용"),
                                        fieldWithPath("data.type").description("타입"),
                                        fieldWithPath("data.member.id").description("ID"),
                                        fieldWithPath("data.member.name").description("이름"),
                                        fieldWithPath("data.member.createDate").description("생성일"),
                                        fieldWithPath("data.member.modifiedDate").description("수정일"),
                                        fieldWithPath("data.member.userName").description("유저ID")
                                )
                        )
                )
        ;
    }
    @DisplayName("Collect 게시글 조회")
    @Transactional
    @Test
    public void collect_get() throws Exception {
        CollectCreateDto.CollectCreateRequest dto = new CollectCreateDto.CollectCreateRequest("사이드 프로젝트 마무리하기", Member.builder().id(1L).build());

        mockMvc.perform(
                        get("/api/collection/{id}",6L)
                                .header(HttpHeaders.AUTHORIZATION,"Bearer " + getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("id").description("Member ID")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("createDate").description("작성일"),
                                        fieldWithPath("modifiedDate").description("수정일"),
                                        fieldWithPath("id").description("게시글 번호"),
                                        fieldWithPath("content").description("내용"),
                                        fieldWithPath("type").description("타입")
                                )
                        )
                )
        ;
    }
    @DisplayName("Collect 게시글 조회")
    @Transactional
    @Test
    public void collect_list() throws Exception {
        CollectCreateDto.CollectCreateRequest dto = new CollectCreateDto.CollectCreateRequest("사이드 프로젝트 마무리하기", Member.builder().id(1L).build());

        mockMvc.perform(
                        get("/api/collection")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer " + getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                relaxedResponseFields(
                                        fieldWithPath("[].createDate").description("작성일"),
                                        fieldWithPath("[].modifiedDate").description("수정일"),
                                        fieldWithPath("[].id").description("게시글 번호"),
                                        fieldWithPath("[].content").description("내용"),
                                        fieldWithPath("[].type").description("타입")
                                )
                        )
                )
        ;
    }

    @DisplayName("Collect 게시글 수정")
    @Transactional
    @Test
    public void collect_put() throws Exception {
        CollectionUpdateDto.CollectUpdateRequest dto = new CollectionUpdateDto.CollectUpdateRequest(6L,"사이드 프로젝트 수정하기",Member.builder().id(3L).build(),"");

        mockMvc.perform(
                        put("/api/collection")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer " + getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createJson(dto)))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                relaxedResponseFields(
                                        subsectionWithPath("data").description("info of collect"),
                                        fieldWithPath("data.id").description("게시글 번호"),
                                        fieldWithPath("data.content").description("내용"),
                                        fieldWithPath("data.type").description("타입"),
                                        fieldWithPath("data.member.id").description("ID"),
                                        fieldWithPath("data.member.name").description("이름"),
                                        fieldWithPath("data.member.createDate").description("생성일"),
                                        fieldWithPath("data.member.modifiedDate").description("수정일"),
                                        fieldWithPath("data.member.userName").description("유저ID")
                                )
                        )
                )
        ;
    }
}
