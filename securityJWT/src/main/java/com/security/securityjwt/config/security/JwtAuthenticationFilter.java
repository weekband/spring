package com.security.securityjwt.config.security;

import com.security.securityjwt.config.exception.UserException;
import com.security.securityjwt.config.exception.UserExceptionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;



@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        if (token != null && jwtTokenProvider.validateToken(token)) {

            //토큰이 유효하면 토큰으로부터 유저 정보를 가져옵니다.
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext에 authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("token = " + token);
            System.out.println("JwtAuthenticationFilter.doFilter");
        }

        chain.doFilter(request,response);

    }
}
