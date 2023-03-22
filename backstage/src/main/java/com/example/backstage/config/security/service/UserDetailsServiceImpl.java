package com.example.backstage.config.security.service;

import com.example.backstage.entity.SysRole;
import com.example.backstage.entity.SysUser;
import com.example.backstage.mapper.SysUserMapper;
import com.example.backstage.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HuaRunSheng
 * @description 实现UserDetailsService接口实现是定义登录逻辑,重写loadUserByUsername登录方法
 * @date 2022/5/18 10:55
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.在mapper中自定义登录,根据用户名获取用户信息
        SysUser user = userMapper.findByUsername(username);

        if (redisUtil.hasKey("userInfo_"+username)){
            System.out.println("redis中取");
            // 缓存中存在用户信息,直接从redis中取出
            user = (SysUser) redisUtil.getValue("userInfo_"+username);
        }else{
            System.out.println("数据库中取");
            if (user==null){
                System.out.println("用户名或密码错误");
                return null;
                //throw new RuntimeException("用户名或密码错误");
            }

            // 设置用户的角色信息
            user.setRoles(userMapper.findRoles(user.getId()));
            //if (user.isAdmin()){
            //    List<SysRole> list=new ArrayList<>();
            //    SysRole sysRole = new SysRole();
            //    sysRole.setCode("admin");
            //    list.add(sysRole);
            //    user.setRoles(list);
            //}else{
            //    user.setRoles(userMapper.findRoles(user.getId()));
            //}
            // 将userInfo存储到缓存中
            redisUtil.setValueTime("userInfo_"+username, user, 5);
        }
        return user;
    }
}
