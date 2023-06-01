package com.ssang.gtd.docs;

import com.ssang.gtd.ControllerTest;
import com.ssang.gtd.config.RestDocsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@Disabled
// Config를 추가해주는 코드입니다.
@Import(RestDocsConfig.class)
// 앞선 코드에서는 @AutoConfigureRestDocs로 자동으로 주입시켰지만, 이제 중복 작업을 제거하기 위해서는 직접 MockMvc를 커스텀해서 주입해야한다.
// 따라서 자동 주입이 아니라 필요한 것들을 가져와서 주입하기 위해 사용하는 코드.
@ExtendWith(RestDocumentationExtension.class)
public class IntegrationRestDocsTests extends ControllerTest {

    @Autowired
    protected RestDocumentationResultHandler restDocs;

//    @Autowired
//    protected CustomAuthenticationFilter customAuthenticationFilter;

    @BeforeEach
    void setUp(final WebApplicationContext context,
               final RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))  // rest docs 설정 주입
                .alwaysDo(MockMvcResultHandlers.print()) // andDo(print()) 코드 포함
                .alwaysDo(restDocs) // pretty 패턴과 문서 디렉토리 명 정해준것 적용
                .apply(springSecurity())
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
                .build();
    }
}
