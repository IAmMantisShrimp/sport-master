package com.example.backstage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author HuaRunSheng
 * @date 2022/11/9 14:44
 * @description :
 */
@Data
@ApiModel(value = "邮件发送内容")
public class MailVo implements Serializable {
    @ApiModelProperty(value = "是否是HTML格式")
    private boolean html;
    @ApiModelProperty(value = "接收人,可以有多个")
    private String[] receivers;
    @ApiModelProperty(value = "邮件主题")
    private String subject;
    @ApiModelProperty(value = "邮件内容")
    private String content;

}
