package com.daily.daily.common.exception.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_LOGIN_INFO(400, "로그인 정보가 올바르지 않습니다.", ErrorType.AUTH, ""),

    ALL_AUTH_TOKEN_EXPIRED(401, "엑세스토큰과 리프레시 토큰이 모두 만료되었습니다. 재 로그인이 필요합니다.", ErrorType.AUTH, ""),

    UNAUTHORIZED_ACCESS(403, "접근권한이 없는 리소스에 접근하였습니다.", ErrorType.AUTH, ""),

    CERT_NUMBER_EXPIRED(401, "인증번호가 만료되었습니다.", ErrorType.AUTH,
            "(이메일 등록과정 등) 인증번호가 만료되었을 경우 반환됩니다."),

    CERT_NUMBER_UNMATCHED(401, "인증번호가 올바르지 않습니다.", ErrorType.AUTH,
            "(이메일 등록과정 등) 사용자가 입력한 인증번호가 올바르지 않을경우 반환됩니다."),

    INVALID_PASSWORD_RESET_TOKEN(401, "비밀번호 찾기 토큰이 올바르지 않습니다.", ErrorType.AUTH, ""),

    PASSWORD_UNMATCHED(401, "현재 비밀번호가 올바르지 않습니다.", ErrorType.AUTH,
            "마이페이지에서 비밀번호 변경할 때, 현재 비밀번호가 올바르지 않을때 반환됩니다."),



    DUPLICATED_EMAIL(409, "이미 사용중인 이메일입니다.", ErrorType.MEMBER, ""),

    DUPLICATED_NICKNAME(409, "이미 사용중인 닉네임 입니다.", ErrorType.MEMBER, ""),

    DUPLICATED_USERNAME(409, "이미 사용중인 아이디 입니다.", ErrorType.MEMBER, ""),

    MEMBER_NOT_FOUND(404, "해당 회원을 찾을 수 없습니다.", ErrorType.MEMBER, ""),

    INVALID_EMAIL(400, "올바르지 않은 이메일입니다.", ErrorType.MEMBER,
            "아이디/비밀번호 찾기시  유효하지 않은 이메일(소셜로그인 유저, 등록되지 않은 이메일)을 입력할 때 반환됩니다."),




    DAILRY_NOT_FOUND(404, "해당 다일리를 찾을 수 없습니다.", ErrorType.DAILRY, ""),

    DAILRY_PAGE_NOT_FOUND(404, "해당 다일리 페이지를 찾을 수 없습니다.", ErrorType.DAILRY, ""),

    DAILRY_THUMBNAIL_NOT_FOUND(404, "해당 다일리 페이지의 썸네일을 찾을 수 없습니다.", ErrorType.DAILRY, ""),




    POST_NOT_FOUND(404, "해당 게시글을 찾을 수 없습니다.", ErrorType.COMMUNITY, ""),

    ALRADY_LIKE_POST(409, "이미 좋아요를 누른 게시글입니다.", ErrorType.COMMUNITY, "좋아요를 누른 게시글에 다시 좋아요를 요청할 경우 반환됩니다."),

    NOT_PREVIOUSLY_LIKE(409, "좋아요를 취소할 수 없습니다.", ErrorType.COMMUNITY, "좋아요를 누르지 않은 게시글에 좋아요 취소를 요청할 경우 반환됩니다."),

    COMMENT_NOT_FOUND(404, "해당 댓글을 찾을 수 없습니다.", ErrorType.COMMUNITY, ""),



    FILE_CONTENT_TYPE_UNMATCHED(400, "파일 형식이 올바르지 않습니다.", ErrorType.FILE, ""),

    FILE_UPLOAD_FAILURE(500, "파일 업로드에 실패하였습니다. 서버에서 예기치 못한 에러가 발생하였습니다.", ErrorType.FILE, "");



    private final Integer statusCode;
    private final String msg;
    private final ErrorType errorType;
    private final String description;

    public String getDescription() {
        if (description.isEmpty()) return msg;
        return description;
    }

    public static List<ErrorCode> getErrorCodes(ErrorType errorType) {
        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getErrorType() == errorType)
                .collect(Collectors.toList());
    }
}
