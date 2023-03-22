package com.example.backstage.mapper;

import com.example.backstage.entity.BigDataUser;
import com.example.backstage.util.RandomPhoneNum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author HuaRunSheng
 * @date 2022/10/26 15:47
 * @description :
 */
@SpringBootTest
class BigDataUserMapperTest {
    @Resource
    private BigDataUserMapper bigDataUserMapper;
    @Test
    void findUserById() {
        System.out.println(bigDataUserMapper.findUserById(1));
    }
    @Test
    void insert(){
        for (int i = 0; i < 100000; i++) {
            BigDataUser user = RandomPhoneNum.getUser();
            System.out.println(user);
            bigDataUserMapper.insert(user);
        }
    }
}