package com.security.securityjwt.config.security;


import com.security.securityjwt.config.exception.UserException;
import com.security.securityjwt.config.exception.UserExceptionResult;
import com.security.securityjwt.domain.token.Token;
import com.security.securityjwt.domain.token.TokenRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Component
public class JwtTokenProvider {


    private static String KEY = "happy";


    // 토큰 유효 시간 30분
    private final long tokenValidTime = 20 * 1000L;

    private final Map<String,TokenUtil> tokenKeyMap;

    private final UserDetailsService userDetailsService;

    private final TokenRepository tokenRepository;

    // 객체 초기화, secretKey를 Base64로 인코딩 한다.
@PostConstruct
    protected void init() {
        KEY = Base64.getEncoder().encodeToString(KEY.getBytes());

    }

    // JWT 토큰 생성
    public String issueToken(String userPk, List<String> roles,String tokenRole){
        return tokenKeyMap.get(tokenRole).createToken(userPk,roles,KEY);
    }


    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody().getSubject();

    }

    public Claims getTokenInfo(String token,String tokenRole) {
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값"
    public JwtTokenDTO resolveToken(HttpServletRequest request){
        return JwtTokenDTO.builder().accessToken(request.getHeader("X-ACCESS-TOKEN"))
                .refreshToken(request.getHeader("X-REFRASH-TOKEN")).build();
    }

    public Object getTokenType(String token) {
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody().get("tokenType");
    }


    // 토큰의 유효성 + 만료일자 확인
    public boolean validateAccessToken(String jwtToken) {
        try {

            Jws<Claims> claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (ExpiredJwtException e){
            System.out.println("토큰만료 예외 = " + e);
            return false;
        }
        catch (Exception e) {
            System.out.println("JwtTokenProvider.validateToken");
            return false;
        }
    }

    public boolean validateRefrashToken(String jwtToken){
        try {

            Jws<Claims> claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwtToken);

            return !claims.getBody().getExpiration().before(new Date()) && this.getTokenType(jwtToken).equals("refrash");


        }catch (ExpiredJwtException e){
            System.out.println("토큰만료 예외 = " + e);
            return false;
        }
        catch (Exception e) {
            System.out.println("JwtTokenProvider.validateToken");
            return false;
        }
    }



}
