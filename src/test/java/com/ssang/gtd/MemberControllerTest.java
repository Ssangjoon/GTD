package com.ssang.gtd;

import com.ssang.gtd.docs.IntegrationRestDocsTests;
import com.ssang.gtd.oauth2.Role;
import com.ssang.gtd.test.Gender;
import com.ssang.gtd.user.dto.member.MemberCreateDto;
import com.ssang.gtd.user.dto.member.MemberUpdateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static com.ssang.gtd.config.RestDocsConfig.field;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// optional과 커스텀해서 넣은 constraints를 명시해서 테스트를 작성
class MemberControllerTest extends IntegrationRestDocsTests {
    @DisplayName("회원 조회 단건")
    @Test
    public void member_get() throws Exception {

        mockMvc.perform(
                        get("/api/member/{id}", 1L)
                                .header(HttpHeaders.AUTHORIZATION,"Bearer " + getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("id").description("Member ID")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("id").description("ID"),
                                        fieldWithPath("name").description("이름"),
                                        fieldWithPath("createDate").description("생성일"),
                                        fieldWithPath("modifiedDate").description("수정일"),
                                        fieldWithPath("userName").description("유저ID"),
                                        fieldWithPath("status").description("사용가능계정여부"),
                                        fieldWithPath("role").description("권한"),
                                        fieldWithPath("socialType").optional().description("socialType"),
                                        fieldWithPath("socialId").optional().description("socialId"),
                                        fieldWithPath("refreshToken").optional().description("refreshToken"),
                                        fieldWithPath("imageUrl").optional().description("imageUrl"),
                                        fieldWithPath("gender").description("성별"),
                                        fieldWithPath("email").description("이메일")
                                )
                        )
                )
        ;
    }
    @DisplayName("회원 조회 리스트")
    @Test
    public void member_list() throws Exception {

        mockMvc.perform(
                        get("/api/member")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer " + getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                relaxedResponseFields(
                                        fieldWithPath("[].id").description("ID"),
                                        fieldWithPath("[].name").description("이름"),
                                        fieldWithPath("[].createDate").description("생성일"),
                                        fieldWithPath("[].modifiedDate").description("수정일"),
                                        fieldWithPath("[].userName").description("유저ID"),
                                        fieldWithPath("[].status").description("사용가능계정여부"),
                                        fieldWithPath("[].role").description("권한"),
                                        fieldWithPath("[].socialType").optional().description("socialType"),
                                        fieldWithPath("[].socialId").optional().description("socialId"),
                                        fieldWithPath("[].refreshToken").optional().description("refreshToken"),
                                        fieldWithPath("[].imageUrl").optional().description("imageUrl"),
                                        fieldWithPath("[].gender").description("성별"),
                                        fieldWithPath("[].email").description("이메일")
                                )
                        )
                )
        ;
    }

    @DisplayName("회원 가입 테스트")
    @Test
    @Transactional
    public void member_create() throws Exception {

        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임2", "테스트네임2", "12345", "test@test.com", Role.USER, Gender.MALE);

        //when
        mockMvc.perform(
                        post("/api/joinUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createJson(req)))
                .andExpect(status().isOk())
                //then
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("email").description("이메일").attributes(field("constraints", "길이 30 이하")),
                                        fieldWithPath("name").description("이름"),
                                        fieldWithPath("userName").description("유저ID"),
                                        fieldWithPath("password").description("비밀번호"),
                                        fieldWithPath("role").description("권한"),
                                        fieldWithPath("gender").description("성별")
                                        //fieldWithPath("status").description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.MEMBER_STATUS))
                                )
                        )
                )
        ;
    }

//    @Test
//    public void member_page_test() throws Exception {
//        Member member = Member.builder()
//                .email("SsangJoon@gmail.com")
//                .status(MemberStatus.NORMAL)
//                .build();
//        PageImpl<Member> memberPage = new PageImpl<>(List.of(member), PageRequest.of(0, 10), 1);
//        given(memberRepository.findAll(ArgumentMatchers.any(Pageable.class))).willReturn(memberPage);
//
//        mockMvc.perform(
//                        get("/members")
//                                .param("size", "10")
//                                .param("page", "0")
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(
//                        restDocs.document(
//                                requestParameters(
//                                        parameterWithName("size").optional().description("size"),
//                                        parameterWithName("page").optional().description("page")
//                                )
//                        )
//                )
//        ;
//    }

    @DisplayName("회원 수정 테스트")
    @Test
    @Transactional
    public void member_modify() throws Exception {
        MemberUpdateDto.MemberUpdateRequest dto = new MemberUpdateDto.MemberUpdateRequest("테스트유저이름", "이상준", "12345", "test@test.com", Role.USER);

        mockMvc.perform(
                        put("/api/member")
                                .header(HttpHeaders.AUTHORIZATION,"Bearer " + getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createJson(dto)))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                relaxedResponseFields(
                                        fieldWithPath("data.userName").description("유저ID"),
                                        fieldWithPath("data.name").description("이름"),
                                        fieldWithPath("data.email").description("이메일"),
                                        fieldWithPath("data.role").description("권한")
                                )
                        )
                )
        ;
    }
    @DisplayName("회원 삭제 테스트")
    @Test
    @Transactional
    public void member_delete() throws Exception {

        mockMvc.perform(
                        delete("/api/member/{id}",10L)
                                .header(HttpHeaders.AUTHORIZATION,"Bearer " + getAccessToken())
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