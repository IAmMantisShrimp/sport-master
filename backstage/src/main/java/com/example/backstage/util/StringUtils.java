package com.example.backstage.util;

/**
 * @author HuaRunSheng
 * @date 2022/11/14 21:38
 * @description :字符串判断
 */
public class StringUtils {
    public static boolean isNotEmpty(String str){
        return str != null && !"".equals(str);
    }

    public static boolean isEmpty(String str){
        return str == null || "".equals(str);
    }
}
