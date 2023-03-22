package com.example.backstage.controller;

import com.example.backstage.entity.SysPermission;
import com.example.backstage.entity.SysUser;
import com.example.backstage.result.Result;
import com.example.backstage.service.SysUserService;
import com.example.backstage.util.MailUtils;
import com.example.backstage.util.ResultUtil;
import com.example.backstage.util.SecurityUtil;
import com.example.backstage.vo.UserLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * @description:
 * @author: HuaRunSheng
 * @date: 2022/5/16 17:13
 */
@RestController
// swagger借口说明
@Api(value = "登录接口")
@RequestMapping("/user")
public class LoginController {
    @Autowired
    SysUserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "登录接口")
    public ResultUtil login(@RequestBody UserLoginVo userLoginVo) {
        return userService.login(userLoginVo);
    }

    @ApiOperation("获取用户基本信息")
    @GetMapping("/getInfo")
    public ResultUtil getUserInfo() {
        return ResultUtil.success("获取用户信息成功", SecurityUtil.getUser());
    }

    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public ResultUtil logout() {
        return userService.logout();
    }

    @ApiOperation("忘记密码")
    @PostMapping("/forgot")
    public ResultUtil forgot(@RequestBody UserLoginVo userLoginVo) {
        return userService.forget(userLoginVo);
    }
    @ApiOperation("发送验证码")
    @GetMapping("/code/{username}")
    public ResultUtil forgot(@PathVariable String username) {
        return userService.sendVerificationCode(username);
    }
    @GetMapping("/initial")
    public ResultUtil initial() {
        return userService.initial();
    }


    /**
     * 分页查询数据 /findPage?pageNo=1&pageSize=5
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping("/findPage")
    public ResultUtil findPage(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return userService.findPage(pageNo, pageSize);
    }

    /**
     * method: "get",
     * url: "/back/permission/findById/"+id,
     * @param id
     * @return
     */
    //@GetMapping("/findById/{id}")
    //public ResultUtil findById(@PathVariable String id){
    //    return userService.;
    //}

    /**
     * 根据name模糊查询: /findByNameVo?name="****"
     *
     * @param name
     * @return
     */
    @PostMapping("/findByNameVo")
    public ResultUtil findByInfo(@RequestParam String name) {
        return userService.findByUsernameVo(name);
    }

    @PostMapping("/insert")
    public ResultUtil insert(@RequestBody SysUser sysUser) {

        return userService.insert(sysUser);
    }

    @PostMapping("/update")
    public ResultUtil update(@RequestBody SysUser sysUser) {
        return userService.update(sysUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResultUtil delete(@PathVariable Integer id) {

        return userService.delete(id);
    }

    @GetMapping("/runLogin")
    public Result runLogin(String code) {
        return userService.runLogin(code);
    }

}
