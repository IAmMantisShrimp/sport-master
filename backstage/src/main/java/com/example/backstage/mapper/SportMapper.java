package com.example.backstage.mapper;

import com.example.backstage.entity.Sport;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author 阿杰
 * @create 2021-04-01 11:04
 */
@Mapper
public interface SportMapper {
    /**
     * 数据查询
     * @param queryString 根据字符串查询
     * @return
     */
    Page<Sport> findPage(String queryString);

    /**
     * 修改运动信息
     * @param sport 运动参数
     * @return
     */
    int update(Sport sport);

    /**
     * 根据运动编号查询运动信息
     * @param id
     * @return
     */
    Sport findById(Integer id);

    /**
     * 添加运动
     * @param sport
     * @return
     */
    int insert(Sport sport);

    /**
     * 根据运动名称查询运动信息
     * @param name
     * @return
     */
    Sport findByName(String name);

    /**
     * 根据编号删除运动信息
     * @param id
     * @return
     */
    int delete(Integer id);
}
