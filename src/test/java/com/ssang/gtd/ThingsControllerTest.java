package com.ssang.gtd;

import com.ssang.gtd.docs.DocumentLinkGenerator;
import com.ssang.gtd.docs.IntegrationRestDocsTests;
import com.ssang.gtd.domain.things.dto.collect.CollectCreateDto;
import com.ssang.gtd.domain.things.dto.collect.CollectionUpdateDto;
import com.ssang.gtd.domain.user.domain.MemberSocial;
import com.ssang.gtd.domain.user.dto.member.MemberCreateDto;
import com.ssang.gtd.global.utils.enums.Gender;
import com.ssang.gtd.global.utils.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.ssang.gtd.global.jwt.JwtConstants.TOKEN_HEADER_PREFIX;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ThingsControllerTest extends IntegrationRestDocsTests {

    @DisplayName("Collect 게시글 작성")
    //@Rollback(false)
    @Test
    public void collect_post() throws Exception {
        //given
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);

        CollectCreateDto.CollectCreateRequest dto = new CollectCreateDto.CollectCreateRequest("디폴트 타입 테스트", new MemberSocial(saveUser(req.toServiceDto()).getId()));

        mockMvc.perform(
                        post("/api/collection")
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createJson(dto)))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                relaxedRequestFields(
                                        fieldWithPath("content").description("내용"),
                                        fieldWithPath("member.id").description("작성자")
                                ),
                                relaxedResponseFields(
                                        //subsectionWithPath("data").description("info of collect"),
                                        fieldWithPath("data.id").description("게시글 번호"),
                                        fieldWithPath("data.content").description("내용"),
                                        fieldWithPath("data.type").optional().description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.BOARD_TYPE)),
                                        fieldWithPath("data.member.id").description("ID")
                                )
                        )
                )
        ;
    }
    @DisplayName("Collect 게시글 단건 조회")
    //@Rollback(false)
    @Test
    public void collect_get() throws Exception {
        // given
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);
        CollectCreateDto.CollectCreateRequest dto = new CollectCreateDto.CollectCreateRequest("디폴트 타입 테스트", new MemberSocial(saveUser(req.toServiceDto()).getId()));
        Long id = saveCollect(dto.toServiceDto()).getId();

        mockMvc.perform(
                        get("/api/collection/{id}",id)
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
                                        fieldWithPath("type").description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.BOARD_TYPE))
                                )
                        )
                )
        ;
    }
    @DisplayName("Collect 게시글 조회")
    @Test
    public void collect_list() throws Exception {
        // given
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);
        CollectCreateDto.CollectCreateRequest dto = new CollectCreateDto.CollectCreateRequest("디폴트 타입 테스트", new MemberSocial(saveUser(req.toServiceDto()).getId()));
        saveCollect(dto.toServiceDto());
        saveCollect(dto.toServiceDto());

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
                                        fieldWithPath("data[].type").description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.BOARD_TYPE)),
                                        fieldWithPath("data[].modifiedDate").description("수정일"),
                                        fieldWithPath("data[].createdDate").description("작성일"),
                                        fieldWithPath("data[].member.id").description("고객 번호")
                                )
                        )
                )
        ;
    }

    @DisplayName("Collect 게시글 수정")
    //@Rollback(false)
    @Test
    public void collect_put() throws Exception {
        // given
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);
        Long userId = saveUser(req.toServiceDto()).getId();
        CollectCreateDto.CollectCreateRequest dto = new CollectCreateDto.CollectCreateRequest("디폴트 타입 테스트", new MemberSocial(userId));
        Long collectId = saveCollect(dto.toServiceDto()).getId();

        CollectionUpdateDto.CollectUpdateRequest updateDto = new CollectionUpdateDto.CollectUpdateRequest(collectId,"사이드 프로젝트 수정하기",new MemberSocial(userId),null);

        mockMvc.perform(
                        put("/api/collection")
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createJson(updateDto)))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                relaxedRequestFields(
                                        fieldWithPath("id").description("게시글 번호"),
                                        fieldWithPath("content").description("수정 내용"),
                                        fieldWithPath("member.id").description("작성자"),
                                        fieldWithPath("type").optional().description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.BOARD_TYPE))
                                ),
                                relaxedResponseFields(
                                        subsectionWithPath("data").description("info of collect"),
                                        fieldWithPath("data.id").description("게시글 번호"),
                                        fieldWithPath("data.content").description("내용"),
                                        fieldWithPath("data.type").description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.BOARD_TYPE)),
                                        fieldWithPath("data.member.id").description("ID")
                                )
                        )
                )
        ;
    }

    @DisplayName("Collect 게시글 삭제")
    //@Rollback(false)
    @Test
    public void collect_delete() throws Exception {
        // given
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);
        CollectCreateDto.CollectCreateRequest dto = new CollectCreateDto.CollectCreateRequest("디폴트 타입 테스트", new MemberSocial(saveUser(req.toServiceDto()).getId()));
        Long id = saveCollect(dto.toServiceDto()).getId();

        mockMvc.perform(
                        delete("/api/collection/{id}",id)
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


}
