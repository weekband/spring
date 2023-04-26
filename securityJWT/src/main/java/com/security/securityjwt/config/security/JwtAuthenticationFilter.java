package com.security.securityjwt.config.security;

import com.security.securityjwt.config.exception.UserException;
import com.security.securityjwt.config.exception.UserExceptionResult;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.json.simple.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        JwtTokenDTO token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        if (token.getAccessToken() != null && jwtTokenProvider.validateAccessToken(token.getAccessToken())) {
            System.out.println("jwtTokenProvider.getTokenType = " + jwtTokenProvider.getTokenType(token.getAccessToken()));
            //토큰이 유효하면 토큰으로부터 유저 정보를 가져옵니다.
            Authentication authentication = jwtTokenProvider.getAuthentication(token.getAccessToken());
            // SecurityContext에 authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("token = " + token);
            System.out.println("JwtAuthenticationFilter.doFilter");
        } else if (token.getAccessToken() != null &&!jwtTokenProvider.validateAccessToken(token.getRefreshToken())) {
            setResponse((HttpServletResponse) response,UserExceptionResult.INVALID_VERIFICATION);
        }


        chain.doFilter(request,response);

    }

    private void setResponse(HttpServletResponse response, UserExceptionResult exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("message", exceptionCode.getMessage());
        responseJson.put("code", exceptionCode.getHttpStatus());

        response.getWriter().print(responseJson);
    }
}
