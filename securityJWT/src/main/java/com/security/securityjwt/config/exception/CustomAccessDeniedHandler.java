package com.security.securityjwt.config.exception;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.simple.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        UserExceptionResult exceptionCode;
        exceptionCode = UserExceptionResult.ACCESS_DENIED;
        setResponse(response, exceptionCode);
    }

    private void setResponse(HttpServletResponse response, UserExceptionResult exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        JSONObject responseJson = new JSONObject();
        responseJson.put("message", exceptionCode.getMessage());
        responseJson.put("ErrorCode", exceptionCode.getHttpStatus());

        response.getWriter().print(responseJson);
    }
}
