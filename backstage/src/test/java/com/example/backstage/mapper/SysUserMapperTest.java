package com.example.backstage.mapper;

import com.example.backstage.entity.SysUser;
import com.example.backstage.vo.UserLoginVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author HuaRunSheng
 * @date 2022/11/3 12:40
 * @description :
 */
@SpringBootTest
class SysUserMapperTest {
    @Resource
    private SysUserMapper sysUserMapper;
    // 密码加密
    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    void findAll() {
    }

    @Test
    void findByUsername() {
        System.out.println(sysUserMapper.findByUsername("root"));
    }

    @Test
    void findRoles() {
    }

    @Test
    void findMenus() {
    }

    @Test
    void findPermission() {
    }

    @Test
    void findPage() {
        System.out.println(sysUserMapper.findPage(0, 5));
    }

    @Test
    void getCount() {
        System.out.println("getCount: " + sysUserMapper.getCount());
    }

    @Test
    void findUserByNameVo() {
        System.out.println("findUserByNameVo(r): " + sysUserMapper.findUserByNameVo("r"));
    }

    @Test
    void insert() {
        for (int i = 2; i < 20; i++) {
            SysUser user = new SysUser();
            user.setUsername("test" + i);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setNickName("测试" + i);
            user.setSex(1);
            user.setStatus(false);
            user.setAdmin(false);
            sysUserMapper.insert(user);
        }
    }

    @Test
    void update() {
        SysUser user = sysUserMapper.findUserById(3);
        System.out.println("user: " + user);
        user.setUsername("测试更改");
        user.setNickName("testChange");
        sysUserMapper.update(user);
    }

    @Test
    void delete() {
        sysUserMapper.delete(21);
    }
    @Test
    void updatePassword() {
        UserLoginVo user=new UserLoginVo();
        user.setId(4);
        user.setPassword("123456");
        sysUserMapper.updatePasswordById(user);
    }
    @Test
    void getPhoneNumber(){
        System.out.println("getPhoneNumber: " + sysUserMapper.getPhoneNumber("root"));
    }

}