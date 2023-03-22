package com.example.backstage.service;

import com.example.backstage.entity.SysPermission;
import com.example.backstage.util.ResultUtil;

import java.util.List;

/**
 * @author HuaRunSheng
 * @date 2022/10/24 11:22
 * @description :
 */
public interface SysPermissionService {
    // 根据Id查询权限信息
    ResultUtil findById(String id);
    // 查找所有
    ResultUtil findAll();

    /**
     * 分页查询
     * @param pageNo: 第几页
     * @param pageSize: 一页有几条数据
     * @return : 返回权限列表
     */
    ResultUtil findPage(Integer pageNo, Integer pageSize);

    /**
     * 模糊查询
     * @param info: 要查询的信息
     * @return
     */
    ResultUtil findByInfo(String info);

    // 添加权限
    ResultUtil insert(SysPermission sysPermission);
    // 更改权限
    ResultUtil update(SysPermission sysPermission);
    // 删除权限
    ResultUtil delete(int id);
    // 查询数据库数目
    ResultUtil getCount();
    // 获取信息
    ResultUtil getInfo();
}
