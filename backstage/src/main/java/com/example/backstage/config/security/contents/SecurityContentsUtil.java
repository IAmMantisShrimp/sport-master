package com.example.backstage.config.security.contents;

/**
 * @author HuaRunSheng
 * @date 2022/5/19 11:44
 * @description :
 */
public class SecurityContentsUtil {
    // 白名单
    public static final String[] WHITE_LIST={
            //后端接口
            "/test/hello",
            "/test/login",
            "/user/login",
            "/user/forgot",
            "/user/code/*",
            "/mini/login",
            "/mini/*",

            // swagger相关
            "/swagger-ui.html",
            "/**/api-docs",
            "/webjars/**",
            "/swagger-resources/**",
            "/doc.html",
            "/doc.html/**",
            "/favicon.ico",

            "/v2/**",
            "/configuration/ui",
            "/configuration/security",
            "/csrf/**",

            "/",
            //增加srs的过滤名单 2020-6-2 dev-api/v1/srs/
            "/srs/v1/**",
            "/druid/**",
            "/*/api-docs",
    };
}
