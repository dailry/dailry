package com.daily.daily.testutil.document;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

public interface ApiDocumentUtils {
    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
                modifyUris()
                        .scheme("https")
                        .host("docs.api.com")
                        .removePort(),
                prettyPrint());
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(modifyHeaders().remove("Vary")
                        .remove("Content-Length")
                        .remove("X-Content-Type-Options")
                        .remove("X-XSS-Protection")
                        .remove("Cache-Control")
                        .remove("Pragma")
                        .remove("Expires")
                        .remove("X-Frame-Options"),
                prettyPrint());
    }}

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