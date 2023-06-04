package com.ssang.gtd;

import com.ssang.gtd.docs.IntegrationRestDocsTests;
import com.ssang.gtd.entity.Collect;
import com.ssang.gtd.entity.Member;
import com.ssang.gtd.things.dto.collect.CollectCreateDto;
import com.ssang.gtd.things.dto.collect.CollectionUpdateDto;
import com.ssang.gtd.things.dto.matcol.MatColCreateDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static com.ssang.gtd.jwt.JwtConstants.TOKEN_HEADER_PREFIX;
import static com.ssang.gtd.things.BoardType.MAT_COLLECTION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ThingsControllerTest extends IntegrationRestDocsTests {

    @DisplayName("Collect 게시글 작성")
    @Transactional
    @Test
    public void collect_post() throws Exception {
        CollectCreateDto.CollectCreateRequest dto = new CollectCreateDto.CollectCreateRequest("디폴트 타입 테스트", Member.builder().id(1L).build());

        mockMvc.perform(
                        post("/api/collection")
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createJson(dto)))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                relaxedResponseFields(
                                        //subsectionWithPath("data").description("info of collect"),
                                        fieldWithPath("data.id").description("게시글 번호"),
                                        fieldWithPath("data.content").description("내용"),
                                        fieldWithPath("data.type").optional().description("타입"),
                                        fieldWithPath("data.member.id").description("ID")
                                )
                        )
                )
        ;
    }
    @DisplayName("Collect 게시글 단건 조회")
    @Transactional
    @Test
    public void collect_get() throws Exception {

        mockMvc.perform(
                        get("/api/collection/{id}",1L)
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
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
    @Test
    public void collect_list() throws Exception {

        mockMvc.perform(
                        get("/api/collection")
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                relaxedResponseFields(
                                        fieldWithPath("data[].id").description("게시글 번호"),
                                        fieldWithPath("data[].content").description("내용"),
                                        fieldWithPath("data[].type").description("타입"),
                                        fieldWithPath("data[].modifiedDate").description("수정일"),
                                        fieldWithPath("data[].createdDate").description("작성일"),
                                        fieldWithPath("data[].member.id").description("고객 번호")
                                )
                        )
                )
        ;
    }

    @DisplayName("Collect 게시글 수정")
    @Transactional
    @Test
    public void collect_put() throws Exception {
        CollectionUpdateDto.CollectUpdateRequest dto = new CollectionUpdateDto.CollectUpdateRequest(1L,"사이드 프로젝트 수정하기",Member.builder().id(1L).build(),null);

        mockMvc.perform(
                        put("/api/collection")
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
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
                                        fieldWithPath("data.member.userName").description("유저ID")
                                )
                        )
                )
        ;
    }

    @DisplayName("Collect 게시글 삭제")
    @Transactional
    @Test
    public void collect_delete() throws Exception {

        mockMvc.perform(
                        delete("/api/collection/{id}",6L)
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("id").description("Member ID")
                                )
                        )
                )
        ;
    }
    @DisplayName("MAT_COLLECTION 등록")
    @Transactional
    @Test
    public void material() throws Exception {
        Member member = Member.builder().id(1L).build();
        Collect collect = Collect.builder().id(1L).content("사이드 프로젝트").type(MAT_COLLECTION).build();
        MatColCreateDto.MatColCreateRequest dto = new MatColCreateDto.MatColCreateRequest("파일 업로드", "업로드", null,collect,member);

        String dtoJson = createJson(dto);
        MockMultipartFile matcol = new MockMultipartFile("matcol", "matcol", "application/json", dtoJson.getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(
                        multipart("/api/material")
                                .file(matcol)
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken()))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestParts(
                                        partWithName("matcol").description(".")
                                )
                        )
                )
        ;
    }

    @DisplayName("MAT_COLLECTION 등록(파일 업로드)")
    //@Transactional
    @Test
    public void material_file() throws Exception {
        Member member = Member.builder().id(1L).build();
        Collect collect = Collect.builder().id(1L).content("사이드 프로젝트").type(MAT_COLLECTION).build();
        MatColCreateDto.MatColCreateRequest dto = new MatColCreateDto.MatColCreateRequest("파일 업로드", "업로드", null,collect,member);

        String dtoJson = createJson(dto);
        MockMultipartFile matcol = new MockMultipartFile("matcol", "matcol", "application/json", dtoJson.getBytes(StandardCharsets.UTF_8));

        MockMultipartFile files = new MockMultipartFile(
                "files",
                "imagefile.png",
                "image/png",
                "<<png data>>".getBytes());

        mockMvc.perform(
                        multipart("/api/material")
                                .file(files)
                                .file(matcol)
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken()))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestParts(
                                        partWithName("files").description("파일"),
                                        partWithName("matcol").description(".")
                                )
                        )
                )
        ;
    }
}
