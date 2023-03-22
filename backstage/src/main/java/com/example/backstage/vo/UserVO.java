package com.example.backstage.vo;

import lombok.Data;

/**
 * @author HuaRunSheng
 * @date 2022/11/3 10:35
 * @description :
 */
@Data
public class UserVO {
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
}
