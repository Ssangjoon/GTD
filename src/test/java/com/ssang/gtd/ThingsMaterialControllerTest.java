package com.ssang.gtd;

import com.ssang.gtd.docs.IntegrationRestDocsTests;
import com.ssang.gtd.domain.things.domain.Collect;
import com.ssang.gtd.domain.things.domain.MatCol;
import com.ssang.gtd.domain.things.dto.collect.CollectCreateDto;
import com.ssang.gtd.domain.things.dto.matcol.MatColCreateDto;
import com.ssang.gtd.domain.user.domain.MemberSocial;
import com.ssang.gtd.domain.user.dto.member.MemberCreateDto;
import com.ssang.gtd.global.enums.Gender;
import com.ssang.gtd.global.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.ssang.gtd.global.jwt.JwtConstants.TOKEN_HEADER_PREFIX;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ThingsMaterialControllerTest extends IntegrationRestDocsTests {
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
    @DisplayName("MAT_COLLECTION (프로젝트 등록)")
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
    @DisplayName("MAT_COLLECTION (구체화 게시글 단건 조회)")
    //@Rollback(false)
    @Test
    public void material_get() throws Exception {
        // given
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);
        MemberSocial member = saveUser(req.toServiceDto()); // 테스트 유저 생성
        CollectCreateDto.CollectCreateRequest dto = new CollectCreateDto.CollectCreateRequest("디폴트 타입 테스트", new MemberSocial(member.getId()));
        Collect collect = saveCollect(dto.toServiceDto()); // 테스트 게시글 생성
        MatColCreateDto.MatColCreateRequest createRequestreq = new MatColCreateDto.MatColCreateRequest("파일 업로드", "차근 차근 하나씩", null,collect,member,null);
        MatCol matCol = saveMatCol(createRequestreq.toServiceDto()); // 저장

            mockMvc.perform(
                        get("/api/material/{id}",matCol.getId())
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
                    )
                .andExpect(status().isOk())
//                .andDo(
//                        restDocs.document(
//                                requestParts(
//    //                                        partWithName("files").description("파일")
//                                        partWithName("materialCollection").description("목표, 절차, 날짜 등을 기재한다")
//                                )
//                        )
//                )
            ;
    }
}
