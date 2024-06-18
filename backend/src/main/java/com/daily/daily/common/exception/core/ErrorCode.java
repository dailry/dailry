package com.daily.daily.common.exception.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_LOGIN_INFO(400, "로그인 정보가 올바르지 않습니다.", ErrorType.AUTH),

    ALL_AUTH_TOKEN_EXPIRED(401, "엑세스토큰과 리프레시 토큰이 모두 만료되었습니다. 재 로그인이 필요합니다.", ErrorType.AUTH),

    UNAUTHORIZED_ACCESS(403, "접근권한이 없는 리소스에 접근하였습니다.", ErrorType.AUTH),

    CERT_NUMBER_EXPIRED(401, "(이메일 등록 등) 인증번호가 만료되었습니다.", ErrorType.AUTH),

    CERT_NUMBER_UNMATCHED(401, "(이메일 등록 등) 인증번호가 올바르지 않습니다.", ErrorType.AUTH),

    INVALID_PASSWORD_RESET_TOKEN(401, "비밀번호 찾기 토큰이 올바르지 않습니다.", ErrorType.AUTH),

    PASSWORD_UNMATCHED(401, "(마이페이지에서 비밀번호 변경시) 현재 비밀번호가 올바르지 않습니다.", ErrorType.AUTH),



    FILE_CONTENT_TYPE_UNMATCHED(400, "파일 형식이 올바르지 않습니다.", ErrorType.FILE),

    FILE_UPLOAD_FAILURE(500, "파일 업로드에 실패하였습니다. 서버에서 예기치 못한 에러가 발생하였습니다.", ErrorType.FILE),



    DAILRY_NOT_FOUND(404, "해당 다일리를 찾을 수 없습니다.", ErrorType.DAILRY),

    DAILRY_PAGE_NOT_FOUND(404, "해당 다일리 페이지를 찾을 수 없습니다.", ErrorType.DAILRY),

    DAILRY_THUMBNAIL_NOT_FOUND(404, "해당 다일리 페이지의 썸네일을 찾을 수 없습니다.", ErrorType.DAILRY),



    DUPLICATED_EMAIL(409, "이미 사용중인 이메일입니다.", ErrorType.MEMBER),

    DUPLICATED_NICKNAME(409, "이미 사용중인 닉네임 입니다.", ErrorType.MEMBER),

    DUPLICATED_USERNAME(409, "이미 사용중인 아이디 입니다.", ErrorType.MEMBER),

    MEMBER_NOT_FOUND(404, "해당 회원을 찾을 수 없습니다.", ErrorType.MEMBER),

    INVALID_EMAIL(400, "(아이디/비밀번호 찾기시 이메일을 입력할 때) 올바르지 않은 이메일입니다. ", ErrorType.MEMBER),



    POST_NOT_FOUND(404, "해당 게시글을 찾을 수 없습니다.", ErrorType.COMMUNITY),

    ALRADY_LIKE_POST(409, "(좋아요를 누른 게시글에 다시 좋아요를 요청할 경우) 이미 좋아요를 누른 게시글입니다.", ErrorType.COMMUNITY),

    NOT_PREVIOUSLY_LIKE(409, "(좋아요를 누르지 않은 게시글에 좋아요 취소를 요청할 경우) 좋아요를 취소할 수 없습니다.", ErrorType.COMMUNITY),

    COMMENT_NOT_FOUND(404, "해당 댓글을 찾을 수 없습니다.", ErrorType.COMMUNITY);

    private final int statusCode;
    private final String msg;
    private final ErrorType errorType;
}
