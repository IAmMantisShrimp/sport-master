package com.example.backstage.service.implement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backstage.config.security.service.UserDetailsServiceImpl;
import com.example.backstage.constant.MessageConstant;
import com.example.backstage.entity.SysUser;
import com.example.backstage.mapper.SysUserMapper;
import com.example.backstage.result.Result;
import com.example.backstage.service.SysUserService;
import com.example.backstage.util.*;
import com.example.backstage.util.mini.GenerateUser;
import com.example.backstage.util.mini.HttpUtil;
import com.example.backstage.vo.MailVo;
import com.example.backstage.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: HuaRunSheng
 * @date: 2022/5/16 17:33
 */
@Service
public class SysUserServiceImplement implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;
    // 使用自定义的UserDetailsService
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    // 加密算法
    @Autowired
    PasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${qiniu.imageDomain}")
    private String imageDomain;

    @Value("${mini.appid}")
    private String appid;

    @Value("${mini.secret}")
    private String secret;


    @Autowired
    TokenUtil tokenUtil;
    @Resource
    private MailUtils mailUtils;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public ResultUtil findAll() {
        List<SysUser> userList = sysUserMapper.findAll();
        handlerImage(userList);
        return new ResultUtil(true, "获取所有用户", userList);
    }

    @Override
    public ResultUtil login(UserLoginVo loginVo) {
        if (loginVo.getUsername().equals("")) {
            return ResultUtil.fail("账号为空...");
        } else if (loginVo.getPassword().equals("")) {
            return ResultUtil.fail("密码为空...");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginVo.getUsername());
        if (userDetails == null) {
            return ResultUtil.fail("无此用户...");
        } else if (!passwordEncoder.matches(loginVo.getPassword(), userDetails.getPassword())) {
            return ResultUtil.fail("密码输入错误...");
        }
        if (userDetails.isEnabled()) {
            return ResultUtil.fail("该账号已禁用,请联系管理员.");
        }
        // 登录信息,密码,认证信息(权限数据)
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        //给容器加入数据,即设置token
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 根据登录信息jwt获取token
        String token = tokenUtil.generateToken(userDetails);
        Map<String, String> map = new HashMap<>();
        map.put("tokenHead", tokenHead);
        map.put("token", token);
        return ResultUtil.success("登录成功", map);
    }

    @Override
    public SysUser findByUsername(String username) {
        SysUser user = sysUserMapper.findByUsername(username);
        handlerImage(user);
        return user;
    }

    @Override
    public ResultUtil logout() {
        // 将认证信息token清空
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return ResultUtil.success("退出成功");
    }

    /**
     * 忘记密码,用邮箱
     * @param userLoginVo
     * @return
     */
    @Override
    public ResultUtil forget(UserLoginVo userLoginVo) {
        // 判断验证码
        if (userLoginVo.getVerificationCode()!=null){
            if (redisUtil.hasKey(userLoginVo.getMailUrl())){
                // 获取验证码
                String code = (String) redisUtil.getValue(userLoginVo.getMailUrl());
                if (userLoginVo.getVerificationCode().equals(code)){
                    // 密码加密
                    userLoginVo.setPassword(passwordEncoder.encode(userLoginVo.getPassword()));
                    // 修改密码
                    sysUserMapper.updatePasswordByName(userLoginVo);
                    return ResultUtil.success("修改密码成功!");
                }else{
                    return ResultUtil.fail("验证码错误!");
                }
            }else{
                return ResultUtil.fail("验证码已经失效!");
            }

        }
        return ResultUtil.fail("验证码错误!");
    }

    /**
     * 用邮箱发送验证码
     * @param username
     * @return
     */
    @Override
    public ResultUtil sendVerificationCode(String username) {
        // 生成验证码
        String code = getVerificationCode();
        // 获取邮件地址
        String mailUrl = sysUserMapper.getPhoneNumber(username);
        // 将验证码放入到缓存中
        redisUtil.setValueTime(mailUrl, code, 5);
        // 内容为Html格式
        MailVo mail=new MailVo();
        mail.setHtml(true);
        mail.setReceivers(new String[]{mailUrl});
        mail.setSubject("重新设置密码");
        mail.setContent("您的验证码为:" + code+"\n有效时间为5分钟.");
        mailUtils.sendMail(mail);
        return ResultUtil.success("发送验证码成功!", mailUrl);
    }


    /**
     * 随机产生一个六位数的验证码
     */
    public String getVerificationCode() {
        StringBuilder productNum = new StringBuilder("1");
        for (int i = 0; i < 6; i++) {
            //每次循环都从0~9挑选一个随机数
            productNum.append((int) (Math.random() * 10));
        }
        return productNum.toString();
    }

    /**
     * 初始化
     * @return
     */
    @Override
    public ResultUtil initial() {
        List<SysUser> users = sysUserMapper.findPage(0, 5);
        handlerImage(users);
        Map<String, Object> map = new HashMap<>();
        map.put("count", sysUserMapper.getCount());
        map.put("userList", users);

        return ResultUtil.success("初始化成功", map);
    }

    /**
     * 按页数查找
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public ResultUtil findPage(Integer pageNo, Integer pageSize) {
        List<SysUser> users = sysUserMapper.findPage((pageNo - 1) * pageSize, pageSize);
        handlerImage(users);
        return ResultUtil.success(String.format("查找分页数据 pageNo=%d, pageSize=%d 成功", pageNo, pageSize), users);
    }

    @Override
    public ResultUtil findByUsernameVo(String name) {
        List<SysUser> users = sysUserMapper.findUserByNameVo(name);
        handlerImage(users);
        return ResultUtil.success(String.format("模糊查询 name=%s 成功", name), users);
    }


    @Override
    public ResultUtil update(SysUser user) {
        //user.setPassword("");
        sysUserMapper.update(user);
        return ResultUtil.success(String.format("修改用户名为 %s 的用户成功", user.getUsername()), null);
    }

    /**
     * 插入数据后总数会变化
     * @param user
     * @return
     */
    @Override
    public ResultUtil insert(SysUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        sysUserMapper.insert(user);
        return ResultUtil.success(String.format("插入用户名为 %s 的用户成功",
                user.getUsername()), sysUserMapper.getCount());
    }

    /**
     * 删除数据后总数会变化
     * @param id
     * @return
     */
    @Override
    public ResultUtil delete(Integer id) {
        sysUserMapper.delete(id);
        return ResultUtil.success(String.format("删除 id=%d 的用户成功", id), sysUserMapper.getCount());
    }

    @Override
    public ResultUtil getCount() {
        return ResultUtil.success("获取总条目成功", sysUserMapper.getCount());
    }

    /**
     * 微信小程序登录,
     * @param openid: 用户微信id
     * @return
     */
    @Override
    public Result miniLogin(String openid) {
        // 当第一次登录时保存,未做

        UserDetails userDetails = userDetailsService.loadUserByUsername(openid);
        if (userDetails == null){
            System.out.println("新建一个用户, openId： "+openid);
            SysUser sysUser = GenerateUser.GenerateUser(openid);
            System.out.println("User: " + sysUser);
            sysUser.setOpenId(openid);
            sysUserMapper.insert(sysUser);
            userDetails = userDetailsService.loadUserByUsername(openid);
        }
        // 登录信息,密码,认证信息(权限数据)
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        //给容器加入数据,即设置token
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 根据登录信息jwt获取token
        String token = tokenUtil.generateToken(userDetails);
        Map<String, Object> map = new HashMap<>();
        map.put("tokenHead", tokenHead);
        map.put("token", token);
        map.put("userInfo", userDetails);
        map.put("openid", openid);
        return Result.success("登录成功", map);
    }

    @Override
    public Result updateByOpenId(SysUser user) {
        if (StringUtils.isEmpty(user.getOpenId())){
            return Result.fail("请传递小程序唯一标识!");
        }
        sysUserMapper.updateByOpenId(user);
        SysUser sysUser = sysUserMapper.findUserByOpenId(user.getOpenId());
        handlerImage(sysUser);
        return Result.success("updateByOpenId 更新成功!", sysUser);
    }

    /**
     * 因为图片有存在自己的文件服务器的,也有在互联网上的,
     * 在互联网上的,不处理,在自己服务器上的加上自己的域名
     * @param user
     */
    public void handlerImage(SysUser user){
        String avatar = user.getAvatar();
        if (avatar != null && !"".equals(avatar)){
            if (!avatar.startsWith("http")){
                avatar=imageDomain+avatar;
                user.setAvatar(avatar);
            }
        }
    }
    public void handlerImage(List<SysUser> users){
        for (SysUser user : users) {
            handlerImage(user);
        }
    }

    @Override
    public Result runLogin(String code) {
        //访问微信url
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        //添加参数
        Map<String, String> map = new HashMap<>(16);
        map.put("appid", appid);
        map.put("secret", secret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        try {
            //2. 根据请求码获取用户openId
            String response = HttpUtil.getResponse(url, map);
            //将字符串转为Json
            JSONObject object = JSON.parseObject(response);
            //得到sessionKey
            String sessionKey = object.getString("session_key");
            return Result.success(MessageConstant.LOGIN_SUCCESS, sessionKey);
        } catch (Exception e) {
            return Result.fail("登录失败");
        }
    }
}
