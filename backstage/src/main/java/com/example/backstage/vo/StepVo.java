package com.example.backstage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ajie
 * @createTime 2021年06月20日 20:59:00
 */
@Data
public class StepVo {
    @ApiModelProperty("步数")
    private Long step;

    @ApiModelProperty("时间戳")
    private Long timestamp;
}
