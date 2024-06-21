package com.daily.daily.testutil.document.errorcode;

import com.daily.daily.common.exception.core.ErrorCode;
import com.daily.daily.common.exception.core.ErrorType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.snippet.TemplatedSnippet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * test/resources/org/springframework/restdocs/templates/all-error-codes.snippet 에 대응되는 Snippet 클래스입니다.
 */
public class AllErrorCodesSnippet extends TemplatedSnippet {
    public AllErrorCodesSnippet() {
        super("all-error-codes", null);
    }

    public static AllErrorCodesSnippet buildSnippet() {
        return new AllErrorCodesSnippet();
    }

    @Override
    protected Map<String, Object> createModel(Operation operation) {
        Map<String, Object> model = new HashMap<>();
        model.put("allErrorCodes", generateAllErrorCodes()); //error-codes.snippet의 {{wholeErrorCodes}} 에 대응되는 Map 자료형을 만듭니다. 머스타치 문법 참고.
        return model;
    }

    private List<Object> generateAllErrorCodes() {
        List<Object> allErrorCodes = new ArrayList<>();

        for (ErrorType errorType : ErrorType.values()) {
            Map<String, Object> map = new HashMap<>();

            map.put("errorType", errorType.getDescription());
            map.put("errorCodes", generateErrorCodes(errorType));

            allErrorCodes.add(map);
        }

        return allErrorCodes;
    }

    private List<Object> generateErrorCodes(ErrorType errorType) {
        List<Object> errorsByErrorType = new ArrayList<>();

        for (ErrorCode errorCode : ErrorCode.getErrorCodes(errorType)) {
            Map<String, Object> error = new HashMap<>();

            error.put("code", errorCode.name());
            error.put("httpStatus", errorCode.getStatusCode());
            error.put("description", errorCode.getDescription());

            errorsByErrorType.add(error);
        }

        return errorsByErrorType;
    }
}
