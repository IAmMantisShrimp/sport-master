package com.example.backstage.util;

import com.example.backstage.entity.SysRole;
import com.example.backstage.entity.SysUser;

/**
 * @author HuaRunSheng
 * @date 2022/10/18 20:55
 * @description :
 */
public class InfoValue {
    public static enum Role{
        SUPER_ADMIN,ADMIN,SAMPLE;
    }
    public static SysRole sampleRole=new SysRole();
    public InfoValue(){
        sampleRole.setId(3);
        sampleRole.setCode("SAMPLE");
        sampleRole.setLabel("普通用户");
    }
}
