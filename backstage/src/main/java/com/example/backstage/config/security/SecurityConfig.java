package com.example.backstage.config.security;

import com.example.backstage.config.security.contents.SecurityContentsUtil;
import com.example.backstage.config.security.handler.JwtAccessDeniedHandler;
import com.example.backstage.config.security.handler.JwtAuthenticationEntryPoint;
import com.example.backstage.config.security.handler.JwtAuthenticationFilter;
import com.example.backstage.config.security.service.UserDetailsServiceImpl;
import com.example.backstage.entity.SysUser;
import com.example.backstage.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @description: 权限基本配置
 * @author: HuaRunSheng
 * @date: 2022/5/17 15:16
 */
@Configuration
@EnableWebSecurity
//全局方法,权限测试
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 加密算法
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 自定义登录逻辑配置,也即是配置到security中进行认证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }
    /**
     * 一般用来配置白名单(没有权限也可以访问资源)
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                // 可以是String,也可以是字符数组
                .mvcMatchers(SecurityContentsUtil.WHITE_LIST);
    }

    /**
     * 核心配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //1.使用jwt,首先关闭跨域攻击
        http.csrf().disable();
        //2.关闭session,前端传jwt过来
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //3.请求头需要进行认证之后才能访问,除白名单以外的资源
        http.authorizeRequests().anyRequest().authenticated();
        //4.关闭缓存
        http.headers().cacheControl();
        //5.token过滤器,校验token
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //6.没有登录,没有权限访问资源自定义返回结果
        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler);

    }
}
