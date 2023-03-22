package com.example.backstage.mapper;

import com.example.backstage.entity.SysPermission;
import com.example.backstage.service.SysPermissionService;
import com.example.backstage.util.ResultUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author HuaRunSheng
 * @date 2022/10/24 10:25
 * @description :
 */
@SpringBootTest
class SysPermissionMapperTest {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Test
    void findById() {
        System.out.println("result: =====>>  "+sysPermissionMapper.findById("1"));
    }
    @Test
    void findAll() {
        System.out.println("result: =====>>  "+sysPermissionMapper.findAll());
    }
    @Test
    void findPage() {
        //System.out.println("result: =====>>  "+sysPermissionMapper.findPage(1, 5));
    }
    @Test
    void findByInfo() {
        System.out.println("result: =====>>  "+sysPermissionMapper.findByInfo("User"));
    }
    @Test
    void insert() {
        SysPermission sysPermission=new SysPermission();
        sysPermission.setCode("AddRole");
        sysPermission.setLabel("添加角色");
        sysPermissionMapper.insert(sysPermission);
    }
    @Test
    void update() {
        SysPermission sysPermission=new SysPermission();
        sysPermission.setId(8);
        sysPermission.setCode("Add");
        sysPermission.setLabel("添加");
        sysPermissionMapper.update(sysPermission);
    }
    @Test
    void delete() {
        sysPermissionMapper.delete(8);
    }

    @Test
    void getCount(){
        System.out.println("数目: "+sysPermissionMapper.getCount());
    }
    @Test
    void getInfo(){
        ResultUtil info = sysPermissionService.getInfo();
        Map data = (Map) info.getData();
        System.out.println("count: " + data.get("count"));
        System.out.println("permissionList: " + data.get("permissionList"));
        System.out.println(data);
    }
}