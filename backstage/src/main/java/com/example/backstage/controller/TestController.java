package com.example.backstage.controller;

import com.example.backstage.entity.DrownedMan;
import com.example.backstage.mapper.DrownedManMapper;
import com.example.backstage.service.SysUserService;
//import com.example.backstage.util.ExcelUtil;
import com.example.backstage.util.ResultUtil;
import com.example.backstage.vo.UserLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: HuaRunSheng
 * @date: 2022/5/15 23:04
 */
@RestController
@RequestMapping("/test")
@Api(value = "接口测试")
public class TestController {
    @Autowired
    SysUserService userService;
    @Resource
    private DrownedManMapper drownedManMapper;
    //@PreAuthorize("hasAnyRole('admin')")
    @ApiOperation(value="测试test")
    @RequestMapping("/hello")
    public ResultUtil test(HttpServletRequest request, HttpServletResponse response){
        List<DrownedMan> drownedMEN = drownedManMapper.selectList();
        //创建excel表头
        List<String> column = new ArrayList<>();
        column.add("序号");
        column.add("姓名");
        column.add("性别");
        column.add("年龄");
        column.add("溺水时间");
        column.add("溺水地点");
        column.add("是否会游泳");
        column.add("是否死亡");
//表头对应的数据
        List<Map<String, Object>> data = new ArrayList<>();

        //遍历获取到的需要导出的数据，k要和表头一样
        for (int i = 1; i < drownedMEN.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            DrownedMan user = drownedMEN.get(i);
            dataMap.put("序号", user.getId());
            dataMap.put("姓名", user.getName());
            dataMap.put("性别", user.getSex());
            dataMap.put("年龄", user.getAge());
            dataMap.put("溺水时间", user.getDrowningTime());
            dataMap.put("溺水地点", user.getSurrounding());
            dataMap.put("是否会游泳", user.getCanSwim());
            dataMap.put("是否死亡", user.getDeath());

            data.add(dataMap);
        }

        //调用导出工具类
        //ExcelUtil.exportExcel("溺水信息表", column, data, request, response);
        return ResultUtil.success("hello");
    }
    @PostMapping("/login")
    public ResultUtil login(@RequestBody UserLoginVo userLoginVo){
        return userService.login(userLoginVo);
    }
}
