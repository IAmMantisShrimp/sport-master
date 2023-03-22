package com.example.backstage.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author 阿杰
 * @create 2021-04-20 14:14
 */
@Data
public class Order {
    /**
     * 由雪花算法生成的订单ID
     */
    private Long id;
    /**
     * 商品编号
     */
    private Integer goodsId;
    /**
     * 下单时间
     */
    private Date createTime;
    /**
     * 下单用户
     */
    private Integer userId;
    /**
     * 状态0待付款、1已付款、2已取消、3已发货、4已收货
     */
    private Integer state;
    /**
     * 备注
     */
    private String remark;
}
