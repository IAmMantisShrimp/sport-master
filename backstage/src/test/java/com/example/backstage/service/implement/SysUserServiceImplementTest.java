package com.example.backstage.service.implement;

import com.example.backstage.service.SysUserService;
import com.example.backstage.util.InfoValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description:
 * @author: HuaRunSheng
 * @date: 2022/5/16 17:37
 */
@SpringBootTest
class SysUserServiceImplementTest {

    @Autowired
    SysUserService sysUserService;
    @Test
    void findAll() {
        System.out.println(sysUserService.findAll());
    }
    @Test
    void infoValueTest(){
        System.out.println(InfoValue.Role.ADMIN.getClass());
        System.out.println(InfoValue.Role.ADMIN);
        System.out.println(InfoValue.Role.ADMIN.name());
        System.out.println(InfoValue.Role.ADMIN.name().getClass());
    }
}