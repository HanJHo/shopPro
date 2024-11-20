package com.example.shoppro.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Log4j2
public class CustomAuthenticationEntryPoint implements
        AuthenticationEntryPoint {
    //http request header에
    //XMLHttpRequest라는 값이 세팅되어 요청이 옴
    // 인증되지 않은 사용자가 ajax로 리소스를 요청할 경우
    //Unauthorized 에러 발생

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info(request.getHeader("x-requested-with"));
        if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }else {
            response.sendRedirect("/members/login");
        }

    }



}
