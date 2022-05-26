package com.security.securityjwt.config.security;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtTokenDTO {

    private String accessToken;
    private String refreshToken;

}
