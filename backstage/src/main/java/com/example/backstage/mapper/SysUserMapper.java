package com.example.backstage.mapper;

import com.example.backstage.entity.*;
import com.example.backstage.vo.UserLoginVo;
import com.example.backstage.vo.UserVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: HuaRunSheng
 * @date: 2022/5/16 17:25
 */
@Mapper
public interface SysUserMapper {
    /**
     * 查询所有用户
     * @return
     */
    List<SysUser> findAll();

    @Select("select * from easyuser where id = #{userId}")
    EasyUser findById(@Param("userId") Integer userId);
    /**
     * 根据username查找用户
     * @param username
     * @return
     */
    SysUser findByUsername(String username);

    /**
     * 根据用户id查找角色信息
     * @param id
     * @return
     */
    List<SysRole> findRoles(int id);

    /**
     * 根据用户id查找menus
     * @param id
     * @return
     */
    List<SysMenu> findMenus(int id);

    /**
     * 根据用户id查找权限
     * @param id
     * @return
     */
    List<SysPermission> findPermission(int id);

    /**
     * 分页查询
     * @param pageNo: 第几页
     * @param pageSize: 一页有几条数据
     * @return : 返回用户列表
     */
    List<SysUser> findPage(Integer pageNo, Integer pageSize);

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    SysUser findUserById(Integer id);
    SysUser findUserByOpenId(String openId);
    /**
     * 获取用户数目
     * @return
     */
    Integer getCount();

    /**
     * 根据name模糊查询用户
     * @param name
     * @return
     */
    List<SysUser> findUserByNameVo(String name);

    /**
     * 增加用户
     */
    void insert(SysUser user);

    /**
     * 微信小程序新建一个用户,用户信息只有openid
     * @param openid
     */
    @Insert("insert into sys_user(open_id) values (#{open_id})")
    void insertOpenid(@Param("open_id")String openid);
    /**
     * 根据id删除用户
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改用户
     * @param user
     */
    void update(SysUser user);

    /**
     * 修改密码
     * @param userLoginVo
     */
    void updatePasswordById(UserLoginVo userLoginVo);

    /**
     * 修改密码
     * @param userLoginVo
     */
    void updatePasswordByName(UserLoginVo userLoginVo);

    String getPhoneNumber(String username);

    /**
     * 根据openid更改用户
     * @param user
     */
    void updateByOpenId(SysUser user);
}
