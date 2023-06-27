package com.ssang.gtd;

import com.ssang.gtd.docs.DocumentLinkGenerator;
import com.ssang.gtd.docs.IntegrationRestDocsTests;
import com.ssang.gtd.domain.user.domain.Member;
import com.ssang.gtd.domain.user.dto.member.MemberCreateDto;
import com.ssang.gtd.domain.user.dto.member.MemberUpdateDto;
import com.ssang.gtd.global.enums.Gender;
import com.ssang.gtd.global.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.transaction.annotation.Transactional;

import static com.ssang.gtd.global.jwt.JwtConstants.TOKEN_HEADER_PREFIX;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// optional과 커스텀해서 넣은 constraints를 명시해서 테스트를 작성
class MemberControllerTest extends IntegrationRestDocsTests {
    @DisplayName("회원 가입 테스트")
    @Test
    //@Rollback(false)
    public void member_create() throws Exception {

        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);

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
                                        fieldWithPath("email").description("이메일"),//.attributes(field("constraints", "길이 30 이하")),
                                        fieldWithPath("name").description("이름"),
                                        fieldWithPath("userName").description("유저ID"),
                                        fieldWithPath("password").description("비밀번호"),
                                        fieldWithPath("role").optional().description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.ROLE)),
                                        fieldWithPath("gender").optional().description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.MEMBER_SEX))
                                        //fieldWithPath("status").description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.MEMBER_STATUS))
                                )
                        )
                )
        ;
    }

    @DisplayName("회원 조회 단건")
    @Test
    public void member_get() throws Exception {
        //given
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);
        Member member = saveUser(req.toServiceDto());

        mockMvc.perform(
                        get("/api/member/{id}", member.getId())
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("id").description("Member ID")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("data.name").description("이름"),
                                        fieldWithPath("data.userName").description("유저ID"),
                                        fieldWithPath("data.status").optional().description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.MEMBER_STATUS)),
                                        fieldWithPath("data.role").optional().description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.ROLE)),
                                        fieldWithPath("data.gender").optional().description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.MEMBER_SEX)),
                                        fieldWithPath("data.email").description("이메일"),
                                        fieldWithPath("data.createDate").description("생성일"),
                                        fieldWithPath("data.modifiedDate").description("수정일"),
                                        fieldWithPath("data.nickname").description("소셜 닉네임"),
                                        fieldWithPath("data.profileImg").description("소셜 프로필 이미지"),
                                        fieldWithPath("data.socialType").description("소셜 유형")
                                )
                        )
                )
        ;
    }
    @DisplayName("회원 조회 리스트")
    @Test
    public void member_list() throws Exception {
        //given
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);
        MemberCreateDto.MemberCreateRequest req2 = new MemberCreateDto.MemberCreateRequest("테스트네임4", "이상준", pwd+1, "0"+email, Role.USER, Gender.MALE);
        saveUser(req.toServiceDto());
        saveUser(req2.toServiceDto());

        mockMvc.perform(
                        get("/api/member")
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                relaxedResponseFields(
                                        subsectionWithPath("[]").type(JsonFieldType.ARRAY).description("info of member"),
                                        fieldWithPath("[].name").optional().description("이름"),
                                        fieldWithPath("[].userName").optional().description("유저ID"),
                                        fieldWithPath("[].status").optional().description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.MEMBER_STATUS)),
                                        fieldWithPath("[].role").optional().description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.ROLE)),
                                        fieldWithPath("[].gender").optional().description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.MEMBER_SEX)),
                                        fieldWithPath("[].email").optional().description("이메일"),
                                        fieldWithPath("[].createDate").description("생성일"),
                                        fieldWithPath("[].modifiedDate").description("수정일"),
                                        fieldWithPath("[].nickname").optional().description("소셜 닉네임"),
                                        fieldWithPath("[].profileImg").optional().description("소셜 프로필 이미지"),
                                        fieldWithPath("[].socialType").optional().description("소셜 유형")
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
    //@Rollback(false)
    public void member_modify() throws Exception {
        //given
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);
        MemberUpdateDto.MemberUpdateRequest dto = new MemberUpdateDto.MemberUpdateRequest("수정테스트네임", "손석구", "1q2w3e@@", "test0@test.com", Role.USER);
        saveUser(req.toServiceDto());

        mockMvc.perform(
                        put("/api/member")
                                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createJson(dto)))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("email").description("이메일"),//.attributes(field("constraints", "길이 30 이하")),
                                        fieldWithPath("name").optional().description("이름"),
                                        fieldWithPath("userName").optional().description("유저ID"),
                                        fieldWithPath("password").description("비밀번호"),
                                        fieldWithPath("role").optional().description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.ROLE))
                                        //fieldWithPath("status").description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.MEMBER_STATUS))
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("data.userName").description("유저ID"),
                                        fieldWithPath("data.name").description("이름"),
                                        fieldWithPath("data.email").description("이메일"),
                                        fieldWithPath("data.role").description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.ROLE))
                                )
                        )
                )
        ;
    }
    @DisplayName("회원 삭제 테스트")
    @Test
    @Transactional
    public void member_delete() throws Exception {
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);
        saveUser(req.toServiceDto());

        mockMvc.perform(
                        delete("/api/member/{id}",1L)
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