package com.example.backstage.service;

import com.example.backstage.entity.Goods;
import com.example.backstage.result.QueryInfo;
import com.example.backstage.result.Result;

import java.util.List;

/**
 * @Author 阿杰
 * @create 2021-04-01 11:14
 */
public interface GoodsService {
    /**
     * 分页查询
     * @param queryInfo 查询条件
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 查询所有商品信息
     * @return
     */
    List<Goods> findAll();

    /**
     * 添加商品信息
     * @param goods
     * @return
     */
    Result add(Goods goods);

    /**
     * 修改商品信息
     * @param goods
     * @return
     */
    Result edit(Goods goods);

    /**
     * 根据ID删除商品信息
     * @param id
     * @return
     */
    Result delete(Integer id);

    /**
     * 批量导入数据
     * @param list
     * @param userId
     * @return
     */
    Result batchImport(List<Goods> list, Integer userId);
}
