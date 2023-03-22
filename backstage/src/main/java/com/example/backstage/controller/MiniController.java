package com.example.backstage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backstage.entity.SysUser;
import com.example.backstage.result.Result;
import com.example.backstage.service.SysUserService;
import com.example.backstage.util.StringUtils;
import com.example.backstage.util.mini.GenerateLoginUrl;
import com.example.backstage.util.mini.HttpClientUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.poifs.filesystem.EntryUtils;
import org.ehcache.config.units.EntryUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author HuaRunSheng
 * @date 2022/11/14 21:28
 * @description : 小程序接口
 */
@RestController
@RequestMapping("/mini")
public class MiniController {
    @Value("${mini.secret}")
    private String secret;
    @Value("${mini.appid}")
    private String appid;
    @Resource
    private SysUserService sysUserService;

    /**
     * 小程序登录,app.js部分
     * @param code
     * @return
     */
    @GetMapping("/login")
    public Result login(String code){
        //System.out.println("code: " + code);
        if (StringUtils.isEmpty(code)){
            return Result.fail("登录失败, 请联系管理员");
        }
        String url = GenerateLoginUrl.getUrl(code, appid, secret);
        String resultString;
        CloseableHttpResponse response = HttpClientUtil.doGet(url);
        // 判断返回状态是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            try {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
                JSONObject jsonObject = JSON.parseObject(resultString);
                System.out.println("微信小程序，获取 openid result: "+ resultString);
                String openid = jsonObject.getString("openid");
                System.out.println("openid: "+ openid);
                // 根据openid判断是否为新用户
                Result result = sysUserService.miniLogin(openid);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (response != null) {
                        response.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.fail("登录失败");
    }

    @PostMapping("/update/info")
    public Result updateInfo(@RequestBody SysUser user){

        System.out.println("user: "+user);

        return sysUserService.updateByOpenId(user);
    }
}
