package com.example.backstage.config.security.handler;

import com.example.backstage.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description :当用户未登录和token过期的情况下访问资源
 * @author :HuaRunSheng
 * @date :2022/5/18 9:58
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(401);
        System.out.println("response.setStatus(401);");
        response.setCharacterEncoding("UTF-8");
        // 返回的数据格式
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(ResultUtil.fail("您尚未登录,请登录后操作.")));
        writer.flush();
        writer.close();
    }
}
