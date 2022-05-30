package com.security.securityjwt.domain.token;


import com.security.securityjwt.config.exception.UserException;
import com.security.securityjwt.config.exception.UserExceptionResult;
import com.security.securityjwt.config.security.JwtTokenProvider;
import com.security.securityjwt.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Transactional
    public String tokenUpdate(String token, User member) {
        Token findRefrashToken = tokenRepository.findByRefrashToken(token)
                .orElseThrow(()->new UserException(UserExceptionResult.REFRASH_TOKEN_INVALID_VERIFICATION));

        String refrash = jwtTokenProvider.issueToken(member.getUsername(), member.getRoles(), "refrash");
        findRefrashToken.setRefrashToken(refrash);
        tokenRepository.save(findRefrashToken);

        return refrash;

    }



}
