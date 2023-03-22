package com.example.backstage.util;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 分页数据
 * @author: HuaRunSheng
 * @date: 2022/5/16 11:59
 */
@Data
// 排除父类中的@Data
@EqualsAndHashCode(callSuper = true)
public class PageResultUtil extends ResultUtil implements Serializable {
    //总记录数
    private long total;
    //分页数据
    private List<?> list;

    public PageResultUtil(long total, List list) {
        this.setFlag(true);
        this.setMessage("分页查询成功");
        this.total = total;
        this.list = list;
    }
}
