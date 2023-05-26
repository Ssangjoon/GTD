package com.ssang.gtd;

import com.ssang.gtd.docs.AbstractRestDocsTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// optional과 커스텀해서 넣은 constraints를 명시해서 테스트를 작성
class MemberControllerTest2 extends AbstractRestDocsTests {
    @Test
    @WithMockUser
    void RestDocsTest() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/member/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

//    @Test
//    public void member_get() throws Exception {
//        Member member = Member.builder()
//                .email("SsangJoon@gmail.com")
//                .status(MemberStatus.NORMAL)
//                .build();
//        given(memberRepository.findById(ArgumentMatchers.any())).willReturn(Optional.of(member));
//
//        mockMvc.perform(
//                        get("/api/members/{id}", 1L)
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andDo(
//                        restDocs.document(
//                                pathParameters(
//                                        parameterWithName("id").description("Member ID")
//                                ),
//                                responseFields(
//                                        fieldWithPath("id").description("ID"),
//                                        fieldWithPath("age").description("age"),
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
//                        get("/api/members")
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


//    @Test
//    public void member_create() throws Exception {
//        Member member = Member.builder()
//                .email("SsangJoon@gmail.com")
//                .status(MemberStatus.NORMAL)
//                .build();
//
//        mockMvc.perform(
//                        post("/member")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(createJson(member)))
//                .andExpect(status().isOk())
//                .andDo(
//                        restDocs.document(
//                                requestFields(
//                                        fieldWithPath("age").description("age").attributes(field("constraints", "길이 10 이하")),
//                                        fieldWithPath("email").description("email").attributes(field("constraints", "길이 30 이하")),
//                                        fieldWithPath("status").description(DocumentLinkGenerator.generateLinkCode(DocumentLinkGenerator.DocUrl.MEMBER_STATUS))
//                                )
//                        )
//                )
//        ;
//    }
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