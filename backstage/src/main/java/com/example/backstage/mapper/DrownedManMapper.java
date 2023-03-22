package com.example.backstage.mapper;

import com.example.backstage.entity.DrownedMan;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author HuaRunSheng
 * @date 2022/10/26 17:39
 * @description :
 */
@Mapper
public interface DrownedManMapper {
    void insert(DrownedMan drownedMan);
    List<DrownedMan> selectList();
}
