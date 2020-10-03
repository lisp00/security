package org.zerock.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class WebAuthenticationEntryPoint implements AuthenticationEntryPoint {
 
    @SuppressWarnings("unchecked")
	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException, ServletException {
        // status를 401 에러로 지정
    	log.info(":::::::AuthenticationEntryPoint::::::" + authEx);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // json 리턴 및 한글깨짐 수정.
        response.setContentType("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        String message = "잘못된 접근입니다";
        json.put("code", "9999");
        json.put("message", message);
 
        PrintWriter out = response.getWriter();
        out.print(json);
        
        response.sendRedirect("/customLogin");
    }
}
