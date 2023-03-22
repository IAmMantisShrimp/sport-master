package com.example.backstage.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: HuaRunSheng
 * @date: 2022/5/17 9:29
 */
@Data
public class SysMenu implements Serializable {
    private int id;
    private String path;
    private String icon;
    private String title;
    private String component;
    private int parentId;
    private List<SysMenu> menus;
}
