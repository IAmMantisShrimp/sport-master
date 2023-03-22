package com.example.backstage.service.implement;

import com.example.backstage.entity.SysPermission;
import com.example.backstage.mapper.SysPermissionMapper;
import com.example.backstage.service.SysPermissionService;
import com.example.backstage.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HuaRunSheng
 * @date 2022/10/24 11:36
 * @description :
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public ResultUtil findById(String id) {
        SysPermission user = sysPermissionMapper.findById(id);
        if (user!=null)
            return ResultUtil.success("查询Id为"+id+"的权限成功", user);
        else
            return ResultUtil.fail("无法查找到Id="+id+"的用户");
    }

    @Override
    public ResultUtil findAll() {
        return ResultUtil.success("查找所有用户成功", sysPermissionMapper.findAll());
    }

    @Override
    public ResultUtil findPage(Integer pageNo, Integer pageSize) {
        return ResultUtil.success("分页查询权限成功", sysPermissionMapper.findPage(pageNo, pageSize));
    }

    @Override
    public ResultUtil findByInfo(String info) {
        return ResultUtil.success("查找与"+info+"相关的权限成功", sysPermissionMapper.findByInfo(info));
    }

    @Override
    public ResultUtil insert(SysPermission sysPermission) {
        sysPermissionMapper.insert(sysPermission);
        return ResultUtil.success("插入权限成功");
    }

    @Override
    public ResultUtil update(SysPermission sysPermission) {
        sysPermissionMapper.update(sysPermission);
        return ResultUtil.success("修改权限成功");
    }

    @Override
    public ResultUtil delete(int id) {
        sysPermissionMapper.delete(id);
        return ResultUtil.success("删除权限成功");
    }

    @Override
    public ResultUtil getCount() {
        return ResultUtil.success("获取所有数目成功", sysPermissionMapper.getCount());
    }

    @Override
    public ResultUtil getInfo() {
        Map<String, Object> info=new HashMap<>();
        info.put("count", sysPermissionMapper.getCount());
        info.put("permissionList", sysPermissionMapper.findPage(0, 5));
        return ResultUtil.success("获取权限信息成功", info);
    }


}
