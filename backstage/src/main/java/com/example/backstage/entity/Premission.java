package com.example.backstage.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 阿杰
 * @create 2021-04-24 10:24
 */
@Data
public class Premission {
    private Integer id;

    @ApiModelProperty("权限名称")
    private String name;

    @ApiModelProperty("权限标识")
    private String premission;
}
