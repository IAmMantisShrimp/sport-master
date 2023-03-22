package com.example.backstage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: HuaRunSheng
 * @date: 2022/5/17 10:09
 */
@Data
@ApiModel(value = "登录参数")
public class UserLoginVo {
    @ApiModelProperty(value = "用户id", dataType = "int")
    private int id;
    @ApiModelProperty(value = "用户名", dataType = "String")
    private String username;
    @ApiModelProperty(value = "密码", dataType = "String")
    private String password;
    @ApiModelProperty(value = "邮箱", dataType = "String")
    private String mailUrl;
    @ApiModelProperty(value = "验证码", dataType = "String")
    private String verificationCode;
}
