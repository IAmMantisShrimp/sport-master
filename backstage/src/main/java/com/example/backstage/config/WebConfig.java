//package com.example.backstage.config;
//
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @description: 前端跨域问题处理
// * @author: HuaRunSheng
// * @date: 2022/5/16 11:31
// */
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry
//                //允许访问的路径
//                .addMapping("/**")
//                .allowCredentials(true)
//                //配置请求来源
//                .allowedOrigins("http://localhost:8080")
//                //允许跨域访问的方法
//                .allowedMethods("GET","POST","DELETE","PUT","OPTION")
//                //是否允许请求头
//                .allowCredentials(true)
//                //最大响应时间
//                .maxAge(3600);
//    }
//}
