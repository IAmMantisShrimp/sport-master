package com.example.backstage.service;

import com.example.backstage.entity.Sport;
import com.example.backstage.result.QueryInfo;
import com.example.backstage.result.Result;

/**
 * @Author 阿杰
 * @create 2021-04-01 11:14
 */
public interface SportService {
    /**
     * 分页查询
     * @param queryInfo 查询条件
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 添加运动信息
     * @param sport
     * @return
     */
    Result add(Sport sport);

    /**
     * 修改运动信息
     * @param sport
     * @return
     */
    Result edit(Sport sport);

    /**
     * 根据ID删除运动信息
     * @param id
     * @return
     */
    Result delete(Integer id);
}
