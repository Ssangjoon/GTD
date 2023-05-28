package com.ssang.gtd;

import com.ssang.gtd.docs.AbstractRestDocsTests;
import com.ssang.gtd.entity.Member;
import com.ssang.gtd.oauth2.Role;
import com.ssang.gtd.oauth2.SocialType;
import com.ssang.gtd.test.Gender;
import com.ssang.gtd.test.MemberStatus;
import com.ssang.gtd.user.dto.MemberCreateDto;
import com.ssang.gtd.user.dto.MemberServiceDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.ssang.gtd.config.RestDocsConfig.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// optional과 커스텀해서 넣은 constraints를 명시해서 테스트를 작성
class MemberControllerTest2 extends AbstractRestDocsTests {

//    @Test
//    public void member_get() throws Exception {
//
//        mockMvc.perform(
//                        get("/member/{id}", 1L)
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(
//                        restDocs.document(
//                                pathParameters(
//                                        parameterWithName("id").description("Member ID")
//                                ),
//                                responseFields(
//                                        fieldWithPath("id").description("ID"),
//                                        fieldWithPath("name").description("name"),
//                                        fieldWithPath("createDate").description("createDate"),
//                                        fieldWithPath("modifiedDate").description("modifiedDate"),
//                                        fieldWithPath("userName").description("userName"),
//                                        fieldWithPath("password").description("password"),
//                                        fieldWithPath("status").description("status"),
//                                        fieldWithPath("role").description("role"),
//                                        fieldWithPath("socialType").description("socialType"),
//                                        fieldWithPath("socialId").description("socialId"),
//                                        fieldWithPath("refreshToken").description("refreshToken"),
//                                        fieldWithPath("imageUrl").description("imageUrl"),
//                                        fieldWithPath("gender").description("gender"),
//                                        fieldWithPath("email").description("email")
//                                )
//                        )
//                )
//        ;
//    }

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

    @DisplayName("회원 가입 테스트")
    @Test
    public void member_create() throws Exception {

        Member member = Member.builder()
                .email("SsangJoon@gmail.com")
                .status(MemberStatus.NORMAL)
                .id(1L)
                .gender(Gender.MALE)
                .password("12345")
                .role(Role.USER)
                .userName("as;ldfkj;sldf")
                .name("s;ldfkjsf")
                .socialType(SocialType.GOOGLE)
                .build();

        //MemberCreateDto.MemberCreateResponse response = new MemberCreateDto.MemberCreateResponse(MemberCreateDto.MemberCreateData.create(member));
        // given
        doReturn(member).when(memberService).post(any(MemberServiceDto.class));
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트이름", "테스트유저이름", "1", "test@test.com", Role.USER, Gender.MALE);

        //when
        mockMvc.perform(
                        post("/joinUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createJson(req)))
                .andExpect(status().isOk())
                //then
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("email").description("email").attributes(field("constraints", "길이 30 이하")),
                                        fieldWithPath("name").description("name"),
                                        fieldWithPath("userName").description("userName"),
                                        fieldWithPath("password").description("password"),
                                        fieldWithPath("role").description("role"),
                                        fieldWithPath("gender").description("gender")
                                        //fieldWithPath("status").description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.MEMBER_STATUS))
                                )
                        )
                )
        ;
    }
//
//    @Test
//    public void member_modify() throws Exception {
//        // given
//        //MemberModificationRequest dto = MemberModificationRequest.builder().age(1).build();
//        Member member = Member.builder()
//                .email("SsangJoon@gmail.com")
//                .status(MemberStatus.NORMAL)
//                .build();
//        given(memberRepository.findById(ArgumentMatchers.any())).willReturn(Optional.of(member));
//
//        mockMvc.perform(
//                        patch("/api/members/{id}", 1)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(createJson(member)))
//                .andExpect(status().isOk())
//                .andDo(
//                        restDocs.document(
//                                pathParameters(
//                                        parameterWithName("id").description("Member ID")
//                                ),
//                                requestFields(
//                                        fieldWithPath("age").description("age")
//                                )
//                        )
//                )
//        ;
//    }

}