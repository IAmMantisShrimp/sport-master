package com.example.backstage.entity;

import lombok.Data;

/**
 * 运动表
 * @Author 阿杰
 * @create 2021-04-15 14:50
 */
@Data
public class Sport {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 运动名称
     */
    private String name;
    /**
     * 适用年龄
     */
    private String applicableAge;
    /**
     * 身体收益部位
     */
    private String beneficialPosition;
    /**
     * 运动科普
     */
    private String introduction;

}
