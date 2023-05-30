package com.ssang.gtd.docs;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

class CommonDocControllerTestAbstract extends IntegrationRestDocsTests {

//    @Test
//    public void errorSample() throws Exception {
//        CommonDocController.SampleRequest sampleRequest = new CommonDocController.SampleRequest("name","hhh.naver");
//        mockMvc.perform(
//                        post("/test/error")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(sampleRequest))
//                )
//                .andExpect(status().isBadRequest())
//                .andDo(
//                        restDocs.document(
//                                responseFields(
//                                        fieldWithPath("message").description("에러 메시지"),
//                                        fieldWithPath("code").description("Error Code"),
//                                        fieldWithPath("errors").description("Error 값 배열 값"),
//                                        fieldWithPath("errors[0].field").description("문제 있는 필드"),
//                                        fieldWithPath("errors[0].value").description("문제가 있는 값"),
//                                        fieldWithPath("errors[0].reason").description("문재가 있는 이유")
//                                )
//                        )
//                )
//        ;
//    }

//    @Test
//    public void enums() throws Exception {
//        //요청
//        ResultActions result = this.mockMvc.perform(
//                get("/test/enums")
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//
//        // 결과값
//        MvcResult mvcResult = result.andReturn();
//
//        // 데이터 파싱
//        EnumDocs enumDocs = getData(mvcResult);
//
//
//        // 문서화 진행
//        result.andExpect(status().isOk())
//                .andDo(restDocs.document(
//                        customResponseFields("custom-response", beneathPath("data.memberStatus").withSubsectionId("memberStatus"), // (2)
//                                attributes(key("title").value("memberStatus")),
//                                enumConvertFieldDescriptor((enumDocs.getMemberStatus()))
//                        ),
//                        customResponseFields("custom-response", beneathPath("data.sex").withSubsectionId("sex"), // (2)
//                                attributes(key("title").value("sex")),
//                                enumConvertFieldDescriptor((enumDocs.getSex()))
//                        )
//                ));
//    }

    // 커스텀 템플릿 사용을 위한 함수
    public static CustomResponseFieldsSnippet customResponseFields
    (String type,
     PayloadSubsectionExtractor<?> subsectionExtractor,
     Map<String, Object> attributes, FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(type, subsectionExtractor, Arrays.asList(descriptors), attributes
                , true);
    }

    // Map으로 넘어온 enumValue를 fieldWithPath로 변경하여 리턴
    private static FieldDescriptor[] enumConvertFieldDescriptor(Map<String, String> enumValues) {
        return enumValues.entrySet().stream()
                .map(x -> fieldWithPath(x.getKey()).description(x.getValue()))
                .toArray(FieldDescriptor[]::new);
    }

    // mvc result 데이터 파싱
    private EnumDocs getData(MvcResult result) throws IOException {
        ApiResponseDto<EnumDocs> apiResponseDto = objectMapper
                .readValue(result.getResponse().getContentAsByteArray(),
                        new TypeReference<ApiResponseDto<EnumDocs>>() {}
                );
        return apiResponseDto.getData();
    }


}