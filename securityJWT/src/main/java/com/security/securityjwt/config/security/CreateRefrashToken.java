package com.security.securityjwt.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component(value = "refrash")
public class CreateRefrashToken implements TokenUtil{


    // refrash token 발급만료기한 1주일
    private final long tokenValidTime = 7 * 24 * 60 * 60 * 1000L;



    @Override
    public String createToken(String userPk, List<String> roles,String key) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("roles",roles); // 정보는 key / value 쌍으로 저장된다.
        claims.put("tokenType","refrash");


        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now)  // 토큰 발생 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) //set Expire Time
                .signWith(SignatureAlgorithm.HS256,key) // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }


}
