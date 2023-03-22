package com.example.backstage.mapper;

import com.example.backstage.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author HuaRunSheng
 * @date 2022/10/24 10:12
 * @description :
 */
@Mapper
public interface SysPermissionMapper {

    // 根据Id查询权限信息
    SysPermission findById(String id);
    // 查找所有
    List<SysPermission> findAll();

    /**
     * 分页查询
     * @param pageNo: 第几页
     * @param pageSize: 一页有几条数据
     * @return : 返回权限列表
     */
    List<SysPermission> findPage(Integer pageNo, Integer pageSize);

    /**
     * 模糊查询
     * @param info: 要查询的信息
     * @return
     */
    List<SysPermission> findByInfo(String info);

    // 添加权限
    void insert(SysPermission sysPermission);
    // 更改权限
    void update(SysPermission sysPermission);
    // 删除权限
    void delete(int id);
    // 获取总数
    int getCount();
}
