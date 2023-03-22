package com.example.backstage.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author HuaRunSheng
 * @date 2022/10/26 16:10
 * @description :
 */
@Data
public class DrownedMan {
    @ExcelProperty(value = "序号")
    private Integer id;
    @ExcelProperty(value = "姓名")
    private String name;
    @ExcelProperty(value = "性别")
    private Integer sex;
    @ExcelProperty(value = "年龄")
    private Integer age;
    @ExcelProperty(value = "溺水时间")
    private Date drowningTime;
    @ExcelProperty(value = "环境")
    private String surrounding;
    @ExcelProperty(value = "是否会游泳")
    private Integer canSwim;
    @ExcelProperty(value = "是否死亡")
    private Integer death;
}
