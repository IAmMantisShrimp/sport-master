package com.example.backstage.util.mini;

import com.example.backstage.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author HuaRunSheng
 * @date 2022/11/16 14:58
 * @description :
 */
public class GenerateUser {
    public static SysUser GenerateUser(String openId){
        SysUser user=new SysUser();
        user.setUsername(getName());
        user.setPassword("$2a$10$jhJnii7OjMHDY4Q86BIkf.cIPpdGCdxiaiUXCGcGP9CZj2THIydXy");
        user.setOpenId(openId);
        return user;
    }
    public static String getName() {
        int rand= (int) (Math.random()*100);
        return "WX_"+rand+"_"+getTimeStr();
    }
    public static String getTimeStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date());
    }

}
