package com.example.backstage.util;

import lombok.Data;

/**
 * @description:
 * @author: HuaRunSheng
 * @date: 2022/5/16 12:02
 */
@Data
public class QueryInfo {
    // 第几页
    private Integer pageNumber;
    // 一页多少条数据
    private Integer pageSize;
    // 查询的内容
    private String queryString;
}
