package com.security.securityjwt.config.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserExceptionResult {


    DUPLICATED_MEMBERSHIP_REGISTER(HttpStatus.BAD_REQUEST, "Duplicated Membership Register Request"),
    INVALID_VERIFICATION(HttpStatus.UNAUTHORIZED, "유효하지 않은 인증입니다."),
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR,"Unknown Exception"),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND,"E-MAIL Not found" ),
    PASSWORD_NOT_EQUAL(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지않습니다."),
    NOT_MEMBERSHIP_OWNER(HttpStatus.BAD_REQUEST, "Not a membership owner"),

    ACCESS_DENIED(HttpStatus.FORBIDDEN,"접근권한이 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;



}
