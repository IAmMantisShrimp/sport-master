package com.example.backstage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: HuaRunSheng
 * @date: 2022/5/16 17:17
 */
@Data
public class SysUser implements Serializable, UserDetails {
    //ApiModelProperty: swagger注释用
    @ApiModelProperty("主键")
    private int id;
    //登录名
    @ApiModelProperty("登录名")
    private String username;
    //密码
    private String password;
    //昵称
    private String nickName;
    //性别(0男,1女,2未知)
    private int sex;
    //用户头像
    private String avatar;
    //地址
    private String address;
    //微信小程序open_id,每个用户对应一个,且唯一
    private String openId;
    //状态是否禁用
    private boolean status;
    //状态是否禁用
    private boolean admin;
    //电话号码
    private String phoneNumber;
    private List<SysRole> roles;

    /**
     * 权限数据
     * @return
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list=new ArrayList<>();
        roles.forEach(item->list.add(new SimpleGrantedAuthority("ROLE_"+item.getCode())));
        //list.add(new SimpleGrantedAuthority("ROLE_admin"));
        return list;
    }

    /**
     * 账号是否过期
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * 账号是否被锁定
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }
    // 是否被禁用
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return status;
    }
}
