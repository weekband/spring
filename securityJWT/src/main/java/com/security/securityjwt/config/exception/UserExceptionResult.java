package com.security.securityjwt.config.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserExceptionResult {


    DUPLICATED_MEMBERSHIP_REGISTER(HttpStatus.BAD_REQUEST, "Duplicated Membership Register Request"),
    INVALID_VERIFICATION(HttpStatus.UNAUTHORIZED, "유효하지 않은 인증입니다.새로운 인증을 발급받거나 정확히 입력해주세요."),
    REFRASH_TOKEN_INVALID_VERIFICATION(HttpStatus.UNAUTHORIZED, "RefrashToken이 유효하지 않습니다.다시 로그인하여 토큰을 발급받거나 정확히 입력해주세요."),
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR,"Unknown Exception"),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND,"이메일이 존재하지않습니다." ),
    PASSWORD_NOT_EQUAL(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지않습니다."),
    NOT_MEMBERSHIP_OWNER(HttpStatus.BAD_REQUEST, "Not a membership owner"),

    ACCESS_DENIED(HttpStatus.FORBIDDEN,"접근권한이 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;



}
