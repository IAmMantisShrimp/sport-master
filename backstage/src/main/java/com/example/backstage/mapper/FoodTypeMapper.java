package com.example.backstage.mapper;


import com.example.backstage.entity.FoodType;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ajie
 * @createTime 2021年06月14日 18:57:00
 */
@Mapper
public interface FoodTypeMapper {

    void insert(FoodType foodType);

    void delete(Long id);

    void update(FoodType foodType);

    Page<FoodType> findPage(String queryString);

    FoodType findByTitle(String title);
    @Select("select * from food_type where id=#{id}")
    FoodType findByIdFoodType(@Param("id") Long id);

    List<FoodType> typeAll();
}
