package com.security.securityjwt.config.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
    @RequiredArgsConstructor
public class UserException extends RuntimeException {

    private final UserExceptionResult errorResult;

}
