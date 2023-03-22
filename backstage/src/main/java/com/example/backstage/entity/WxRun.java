package com.example.backstage.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信运动信息
 * @author ajie
 * @createTime 2021年06月20日 18:32:00
 */
@Data
public class WxRun {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("运动用户")
    private Integer userId;

    @ApiModelProperty("运动时间")
    private String runDate;

    @ApiModelProperty("运动步数")
    private Long runStep;
}
