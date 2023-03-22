package com.example.backstage.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: HuaRunSheng
 * @date: 2022/5/17 9:28
 */
@Data
public class SysPermission implements Serializable {
    private int id;
    private String label;
    private String code;

}
