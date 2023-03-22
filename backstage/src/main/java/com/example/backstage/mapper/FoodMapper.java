package com.example.backstage.mapper;


import com.example.backstage.entity.Food;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ajie
 * @createTime 2021年06月14日 19:33:00
 */
@Mapper
public interface FoodMapper {

    /**
     * 分页查询菜品信息
     * @param queryString
     * @return
     */
    Page<Food> findPage(String queryString);

    /**
     * 根据菜品名称查询
     * @param title
     * @return
     */
    Food findByTitle(String title);

    @Select("select * from  food where id=#{id}")
    Food findById(@Param("id") String id);

    void updateImage(Food food);
    /**
     * 添加菜品
     * @param food
     */
    void insert(Food food);

    /**
     * 删除菜品
     * @param id
     */
    void delete(Long id);

    /**
     * 更新菜品
     * @param food
     */
    void update(Food food);

    /**
     * 根据分类ID查询食物列表
     * @param id
     * @return
     */
    Page<Food> findByTypeId(String id);
}
