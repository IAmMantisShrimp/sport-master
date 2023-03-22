package com.example.backstage.config.security.handler;

import com.example.backstage.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description  没有权限访问时返回的结果
 * @author HuaRunSheng
 * @date 2022/5/18 10:14
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(403);
        System.out.println("response.setStatus(403);");
        response.setCharacterEncoding("UTF-8");
        // 返回的数据格式
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(ResultUtil.fail("权限不足，请联系管理员。")));
        writer.flush();
        writer.close();
    }
}
