package com.ssang.gtd;

import com.ssang.gtd.docs.DocumentLinkGenerator;
import com.ssang.gtd.docs.IntegrationRestDocsTests;
import com.ssang.gtd.entity.Collect;
import com.ssang.gtd.entity.MemberSocial;
import com.ssang.gtd.things.dto.collect.CollectCreateDto;
import com.ssang.gtd.things.dto.collect.CollectionUpdateDto;
import com.ssang.gtd.things.dto.matcol.MatColCreateDto;
import com.ssang.gtd.user.dto.member.MemberCreateDto;
import com.ssang.gtd.utils.enums.Gender;
import com.ssang.gtd.utils.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.ssang.gtd.jwt.JwtConstants.TOKEN_HEADER_PREFIX;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
    @DisplayName("MAT_COLLECTION 등록")
    //@Rollback(false)
    @Test
    public void material() throws Exception {
        // given
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);
        MemberSocial member = saveUser(req.toServiceDto()); // 테스트 유저 생성
        CollectCreateDto.CollectCreateRequest dto = new CollectCreateDto.CollectCreateRequest("디폴트 타입 테스트", new MemberSocial(member.getId()));
        Collect collect = saveCollect(dto.toServiceDto()); // 테스트 게시글 생성


        MatColCreateDto.MatColCreateRequest createRequestreq = new MatColCreateDto.MatColCreateRequest("파일 업로드", "차근 차근 하나씩", null,collect,member,null);
        String dtoJson = createJson(createRequestreq);

        MockMultipartFile materialCollection = new MockMultipartFile("materialCollection", "materialCollection", "application/json", dtoJson.getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(
                        multipart("/api/material")
                                .file(materialCollection)
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken()))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestParts(
                                        partWithName("materialCollection").description("목표, 절차, 날짜 등을 기재한다")
                                )
                        )
                )
        ;
    }

    @DisplayName("MAT_COLLECTION 등록(파일 업로드)")
    //@Rollback(false)
    @Test
    public void material_file() throws Exception {
        // given
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);
        MemberSocial member = saveUser(req.toServiceDto()); // 테스트 유저 생성
        CollectCreateDto.CollectCreateRequest dto = new CollectCreateDto.CollectCreateRequest("디폴트 타입 테스트", new MemberSocial(member.getId()));
        Collect collect = saveCollect(dto.toServiceDto()); // 테스트 게시글 생성


        MatColCreateDto.MatColCreateRequest createRequestreq = new MatColCreateDto.MatColCreateRequest("파일 업로드", "차근 차근 하나씩", null,collect,member,null);
        String dtoJson = createJson(createRequestreq);

        MockMultipartFile materialCollection = new MockMultipartFile("materialCollection", "materialCollection", "application/json", dtoJson.getBytes(StandardCharsets.UTF_8));

        MockMultipartFile files = new MockMultipartFile(
                "files",
                "imagefile.png",
                "image/png",
                "<<png data>>".getBytes());

        mockMvc.perform(
                        multipart("/api/material")
                                .file(files)
                                .file(materialCollection)
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
                                .content(dtoJson))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestParts(
                                        partWithName("files").description("파일"),
                                        partWithName("materialCollection").description("목표, 절차, 날짜 등을 기재한다")
                                )
                        )
                )
        ;
    }

    @DisplayName("MAT_COLLECTION(목표일 등록)")
    //@Rollback(false)
    @Test
    public void material_calender() throws Exception {
        // given
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);
        MemberSocial member = saveUser(req.toServiceDto()); // 테스트 유저 생성
        CollectCreateDto.CollectCreateRequest dto = new CollectCreateDto.CollectCreateRequest("디폴트 타입 테스트", new MemberSocial(member.getId()));
        Collect collect = saveCollect(dto.toServiceDto()); // 테스트 게시글 생성
        MatColCreateDto.MatColCreateRequest createRequestreq = new MatColCreateDto.MatColCreateRequest("파일 업로드", "차근 차근 하나씩", LocalDate.of(2023,7,1),collect,member,null);
        String dtoJson = createJson(createRequestreq);

        MockMultipartFile materialCollection = new MockMultipartFile("materialCollection", "materialCollection", "application/json", dtoJson.getBytes(StandardCharsets.UTF_8));
//
//        MockMultipartFile files = new MockMultipartFile(
//                "files",
//                "imagefile.png",
//                "image/png",
//                "<<png data>>".getBytes());

        mockMvc.perform(
                        multipart("/api/material")
//                                .file(files)
                                .file(materialCollection)
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
                                .content(dtoJson))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestParts(
//                                        partWithName("files").description("파일")
                                        partWithName("materialCollection").description("목표, 절차, 날짜 등을 기재한다")
                                )
                        )
                )
        ;
    }
    @DisplayName("MAT_COLLECTION (프로젝트 타입)")
    //@Rollback(false)
    @Test
    public void material_type() throws Exception {
        // given
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);
        MemberSocial member = saveUser(req.toServiceDto()); // 테스트 유저 생성
        CollectCreateDto.CollectCreateRequest dto = new CollectCreateDto.CollectCreateRequest("디폴트 타입 테스트", new MemberSocial(member.getId()));
        Collect collect = saveCollect(dto.toServiceDto()); // 테스트 게시글 생성

        List<String> testList = new ArrayList<>();
        String testdata = "테스트 데이터";
        int cycle = 5;
        for (int i = 0; i < cycle; i++) {
            testList.add(testdata);
        }
        MatColCreateDto.MatColCreateRequest createRequestreq = new MatColCreateDto.MatColCreateRequest("파일 업로드", "차근 차근 하나씩", null,collect,member,testList);
        String dtoJson = createJson(createRequestreq);
        MockMultipartFile materialCollection = new MockMultipartFile("materialCollection", "materialCollection", "application/json", dtoJson.getBytes(StandardCharsets.UTF_8));
//        Long MatCollectId = saveMatCol(createRequestreq.toServiceDto()).getId(); // 테스트 구체화 목록 생성

            mockMvc.perform(
                        multipart("/api/material")
    //                                .file(files)
                                .file(materialCollection)
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
                                .content(dtoJson))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestParts(
    //                                        partWithName("files").description("파일")
                                        partWithName("materialCollection").description("목표, 절차, 날짜 등을 기재한다")
                                )
                        )
                )
            ;
    }

}
