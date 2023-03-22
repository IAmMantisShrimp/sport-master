package com.example.backstage.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** JSON Web Token:https://blog.csdn.net/sinat_29774479/article/details/89884500
 * @description: Token工具类,用于生成token
 * 用户登录拿到token,然后访问系统资源
 * @author: HuaRunSheng
 * @date: 2022/5/17 10:56
 */
@Component
public class TokenUtil {
    //  读取配置文件中的jwt.secret值
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;
    // 生成token
    public String generateToken(UserDetails userDetails){
        Map<String, Object> map=new HashMap<>(2);
        map.put("username",userDetails.getUsername());
        // 创建时间
        map.put("created",new Date());
        return this.generateJwt(map);
    }
    public String generateJwt(Map<String, Object> map){
        //System.out.println("map: "+map);
        // 生成加密信息
        return Jwts.builder()
                .setClaims(map)
                // 加密
                .signWith(SignatureAlgorithm.HS512,secret)
                // 设置过期时间
                .setExpiration(new Date(System.currentTimeMillis()+expiration*1000))
                // 生成jwt
                .compact();
    }

    /**
     * 获取token的主体,即generateToken的map(未加密)部分
     * @param token
     * @return
     */
    public Claims getTokenBody(String token){
        // 解密
        try {
            return Jwts.parser()
                    // 输入密钥
                    .setSigningKey(secret)
                    // 解密
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            return null;
        }

    }

    /**
     * 根据token得到用户名
     * @param token
     * @return
     */
    public String getUsernameByToken(String token){
        /**
         * header={alg=HS512},body={exp=1652869860, created=1652868060488, username=root},signature=rIRMVQUsKew406Mb0jNMMWt-b4rmdPWqX94kM2apVqqev1qkQIb_1rNHMW9rQKwn4F-B7SGK9S7E0uAhZkC5bA
         * claims：  {exp=1652869860, created=1652868060488, username=root}
         */
        Claims claims=this.getTokenBody(token);
        //System.out.println("token："+token);
        //System.out.println("claims："+claims);
        //System.out.println(claims.get("username"));
        return this.getTokenBody(token).get("username").toString();
    }

    /**
     * 根据token判断当前时间内,该token是否过期
     * @param token
     * @return
     */
    public boolean isExpiration(String token){
        Claims tokenBody = this.getTokenBody(token);
        System.out.println("tokenBody: "+tokenBody);
        Date expiration = tokenBody.getExpiration();
        //System.out.println(expiration);
        //System.out.println(new Date());
        //System.out.println(expiration.before(new Date()));
        return expiration.before(new Date());
    }

    /**
     * 更新token
     * @param token
     * @return
     */
    public String refreshToken(String token){
        Claims claims=this.getTokenBody(token);
        claims.setExpiration(new Date());
        return this.generateJwt(claims);
    }

    /**
     * 验证token是否有效
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameByToken(token);
        return username.equals(userDetails.getUsername()) && !isExpiration(token);
    }
}
