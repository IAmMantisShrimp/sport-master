package com.example.backstage.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: HuaRunSheng
 * @date: 2022/5/17 9:27
 */
@Data
public class SysRole implements Serializable {
    // 主键
    private int id;
    // 标签名称
    private String label;
    // 标签值
    private String code;
    // 角色对应的菜单列表
    private List<SysMenu> menus;
    // 数据权限
    private List<SysPermission> permissions;

}
