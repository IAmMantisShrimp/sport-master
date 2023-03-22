package com.example.backstage.util.mini;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author HuaRunSheng
 * @date 2022/11/15 11:11
 * @description :
 */
public class GenerateLoginUrl {
    public static String getUrl(String code, String appid, String secret) {
        // https://mp.weixin.qq.com/wxopen/devprofile?action=get_profile&token=164113089&lang=zh_CN
        //GET https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?";
        return requestUrl + "appid=" + appid
                        + "&secret=" + secret
                        + "&js_code=" + code
                        + "&grant_type=authorization_code";
    }
}
