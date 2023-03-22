package com.example.backstage.util;

import com.example.backstage.entity.SysRole;
import com.example.backstage.entity.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;

/**
 * @author HuaRunSheng
 * @date 2022/5/22 10:38
 * @description :获取当前登录用户的基本信息
 */
public class SecurityUtil {
    /**
     * @return 从security主体信息中获取用户信息
     * getPrincipal():获取主体,主要的
     */
    public static SysUser getUser(){
        //if (!User.getRoles().isEmpty()){
        //    for (SysRole role : User.getRoles()) {
        //        // 超级管理员
        //        if (role.getCode().equals(InfoValue.Role.SUPER_ADMIN.name())){
        //            return User;
        //        }
        //        // 普通管理员,屏蔽密码
        //        else if (role.getCode().equals(InfoValue.Role.ADMIN.name())){
        //            User.setPassword("********");
        //            return User;
        //        }
        //    }
        //
        //}
        //// 如果只是普通用户,只返回基本信息
        //User.setPassword("");
        //if (User.getRoles() == null){
        //    ArrayList<SysRole> sysRoles = new ArrayList<SysRole>();
        //    sysRoles.add(InfoValue.sampleRole);
        //    User.setRoles(sysRoles);
        //}
        //User.setOpenId("");
        return (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getUsername(){
        return getUser().getUsername();
    }
    public static int getUserId(){
        return getUser().getId();
    }

}
