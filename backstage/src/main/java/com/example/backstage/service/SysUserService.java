package com.example.backstage.service;

import com.example.backstage.entity.SysUser;
import com.example.backstage.result.Result;
import com.example.backstage.util.ResultUtil;
import com.example.backstage.vo.UserLoginVo;
import com.example.backstage.vo.UserVO;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: HuaRunSheng
 * @date: 2022/5/16 17:32
 */

public interface SysUserService {
    // 查找所有用户
    ResultUtil findAll();
    // 登录判断
    ResultUtil login(UserLoginVo loginVo);
    // 根据用户名返回用户信息
    SysUser findByUsername(String username);
    //退出登录
    ResultUtil logout();
    // 忘记密码
    ResultUtil forget(UserLoginVo userLoginVo);
    // 发送验证码
    ResultUtil sendVerificationCode(String username);
    // 页面初始化
    ResultUtil initial();
    // 分页
    ResultUtil findPage(Integer pageNo, Integer pageSize);
    // 根据name模糊查询
    ResultUtil findByUsernameVo(String name);
    // 修改
    ResultUtil update(SysUser user);
    // 插入
    ResultUtil insert(SysUser user);
    // 删除
    ResultUtil delete(Integer id);
    // 删除
    ResultUtil getCount();
    // 小程序登录
    Result miniLogin(String openid);

    Result updateByOpenId(SysUser user);

    Result runLogin(String code);
}
