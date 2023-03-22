package com.example.backstage.controller;

import com.example.backstage.entity.SysPermission;
import com.example.backstage.mapper.SysPermissionMapper;
import com.example.backstage.service.SysPermissionService;
import com.example.backstage.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author HuaRunSheng
 * @date 2022/10/24 11:50
 * @description :
 */

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private SysPermissionService sysPermissionService;
    @PostMapping("/findByInfo")
    public ResultUtil findByInfo(@RequestParam String info){
        return sysPermissionService.findByInfo(info);
    }
    @GetMapping("/findAll")
    public ResultUtil findAll(){
        return sysPermissionService.findAll();
    }

    /**
     * 分页查询数据 /findPage?pageNo=1&pageSize=5
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping("/findPage")
    public ResultUtil findPage(@RequestParam  Integer pageNo, @RequestParam  Integer pageSize){
        return sysPermissionService.findPage(pageNo, pageSize);
    }
    /**
     * method: "get",
     * url: "/back/permission/findById/"+id,
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResultUtil findById(@PathVariable String id){
        return sysPermissionService.findById(id);
    }
    @PostMapping("/insert")
    public ResultUtil insert(@RequestBody SysPermission sysPermission){
        sysPermissionService.insert(sysPermission);
        return sysPermissionService.getCount();
    }
    @PostMapping("/update")
    public ResultUtil update(@RequestBody SysPermission sysPermission){
        return sysPermissionService.update(sysPermission);
    }

    @DeleteMapping("/delete/{id}")
    public ResultUtil delete(@PathVariable Integer id){
        sysPermissionService.delete(id);
        return sysPermissionService.getCount();
    }

    @GetMapping("/getInfo")
    public ResultUtil getInfo(){
        return sysPermissionService.getInfo();
    }

}
