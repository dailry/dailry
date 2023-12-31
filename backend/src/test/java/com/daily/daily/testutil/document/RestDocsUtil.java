package com.daily.daily.testutil.document;

import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.snippet.Snippet;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

public class RestDocsUtil {
    private static OperationRequestPreprocessor customRequestPreprocessor() {
        return preprocessRequest(
                modifyHeaders()
                        .remove("Content-Length")
                        .remove("X-CSRF-TOKEN")
                        .remove("Host"),
                prettyPrint());
    }

    private static OperationResponsePreprocessor customResponsePreprocessor() {
        return preprocessResponse(modifyHeaders().remove("Vary")
                        .remove("Content-Length")
                        .remove("X-Content-Type-Options")
                        .remove("X-XSS-Protection")
                        .remove("Cache-Control")
                        .remove("Pragma")
                        .remove("Expires")
                        .remove("X-Frame-Options"),
                prettyPrint());
    }

    public static RestDocumentationResultHandler document(String identifier, Snippet... snippets) {
        return MockMvcRestDocumentation.document(
                identifier,
                customRequestPreprocessor(),
                customResponsePreprocessor(),
                snippets);
    }
}

//HTTP/1.1 201 Created
//Vary: Origin
//Vary: Access-Control-Request-Method
//Vary: Access-Control-Request-Headers
//Content-Type: application/json;charset=UTF-8
//X-Content-Type-Options: nosniff
//X-XSS-Protection: 0
//Cache-Control: no-cache, no-store, max-age=0, must-revalidate
//Pragma: no-cache
//Expires: 0
//X-Frame-Options: DENY
//Content-Length: 97