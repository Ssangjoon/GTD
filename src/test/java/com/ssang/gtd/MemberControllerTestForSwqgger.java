package com.ssang.gtd;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.ssang.gtd.docs.IntegrationRestDocsTests;
import com.ssang.gtd.user.dto.member.MemberCreateDto;
import com.ssang.gtd.utils.enums.Gender;
import com.ssang.gtd.utils.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.ssang.gtd.jwt.JwtConstants.TOKEN_HEADER_PREFIX;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;

public class MemberControllerTestForSwqgger extends IntegrationRestDocsTests {


    @Test
    @DisplayName("관심사 목록 리스트 조회")
    void getInterestAll() throws Exception {
        //given
        MemberCreateDto.MemberCreateRequest req = new MemberCreateDto.MemberCreateRequest("테스트네임3", "손석구", pwd, email, Role.USER, Gender.MALE);
        MemberCreateDto.MemberCreateRequest req2 = new MemberCreateDto.MemberCreateRequest("테스트네임4", "이상준", pwd+1, "0"+email, Role.USER, Gender.MALE);
        saveUser(req.toServiceDto());
        saveUser(req2.toServiceDto());

        //then
        ResultActions resultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/member")
                .header(HttpHeaders.AUTHORIZATION,TOKEN_HEADER_PREFIX + getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcRestDocumentationWrapper.document("interest-docs",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(
                    ResourceSnippetParameters.builder()
                        .tag("회원") //
                        .description("회원 리스트 조회")
                        .requestFields()
                        .responseFields(
                            subsectionWithPath("[]").type(JsonFieldType.ARRAY).description("info of member"),
                            fieldWithPath("[].name").optional().description("이름"),
                            fieldWithPath("[].userName").optional().description("유저ID"),
                            fieldWithPath("[].status").optional().description("상태"),
                            fieldWithPath("[].role").optional().description("역할"),
                            fieldWithPath("[].gender").optional().description("성별"),
                            fieldWithPath("[].email").optional().description("이메일"),
                            fieldWithPath("[].createDate").description("생성일"),
                            fieldWithPath("[].modifiedDate").description("수정일"),
                            fieldWithPath("[].nickname").optional().description("소셜 닉네임"),
                            fieldWithPath("[].profileImg").optional().description("소셜 프로필 이미지"),
                            fieldWithPath("[].socialType").optional().description("소셜 유형")
                        )
                        .responseSchema(Schema.schema("IdealTypeResponse"))
                        .build()
                )
            )
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
