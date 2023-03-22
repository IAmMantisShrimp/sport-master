package com.example.backstage.mapper;

import com.example.backstage.entity.BigDataUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author HuaRunSheng
 * @date 2022/10/26 15:42
 * @description :
 */
@Mapper
public interface BigDataUserMapper {
    BigDataUser findUserById(Integer id);
    void insert(BigDataUser bigDataUser);
}
