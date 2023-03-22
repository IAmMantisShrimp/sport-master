package com.example.backstage.config.security.handler;

import com.example.backstage.config.security.service.UserDetailsServiceImpl;
import com.example.backstage.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author HuaRunSheng
 * @description token认证过滤器，在接口访问前进行过滤
 * @date 2022/5/18 10:23
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenUtil tokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    /**
     * 请求前后去请求头信息token
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1.获取token
        String header=request.getHeader(tokenHeader);
        //2.判断token是否存在
        if (null!=header && header.startsWith(tokenHead)){
            //拿到token主体
            String token = header.substring(tokenHead.length());
            //根据token主体获取用户名
            String username=tokenUtil.getUsernameByToken(token);
            //System.out.println("getContext: "+SecurityContextHolder.getContext());
            //System.out.println("getAuthentication:"+SecurityContextHolder.getContext().getAuthentication());
            //token存在,当没有登录信息
            if (null != username && null == SecurityContextHolder.getContext().getAuthentication()){
                //没有登录信息,直接登录
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("userDetails: "+userDetails);
                System.out.println("getAuthorities: "+userDetails.getAuthorities());
                //判断token是否有效
                if (!tokenUtil.isExpiration(token) && username.equals(userDetails.getUsername())){
                    // 刷新security中的用户信息.
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        // 过滤器放行
        filterChain.doFilter(request,response);
    }
}
