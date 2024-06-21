package com.daily.daily.testutil.document.errorcode;

import com.daily.daily.auth.jwt.JwtAuthorizationFilter;
import com.daily.daily.auth.jwt.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.daily.daily.testutil.document.RestDocsUtil.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(value = ErrorCodeController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class),
})
@AutoConfigureRestDocs
public class ErrorCodeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser
    void documentErrorCodes() throws Exception {
        ResultActions action = mockMvc.perform(get("/error-code")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        action.andDo(document("전체에러코드모음", AllErrorCodesSnippet.buildSnippet()));
    }
}
