package com.example.backstage;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.example.backstage.entity.Food;
import com.example.backstage.entity.FoodType;
import com.example.backstage.entity.SysUser;
import com.example.backstage.mapper.FoodMapper;
import com.example.backstage.mapper.FoodTypeMapper;
import com.example.backstage.mapper.SysUserMapper;
import com.example.backstage.service.SysUserService;
import com.example.backstage.util.EasyExcelUtil;
import com.example.backstage.util.MailUtils;
import com.example.backstage.util.TokenUtil;
import com.example.backstage.util.mini.GenerateUser;
import com.example.backstage.vo.MailVo;
import com.example.backstage.vo.UserLoginVo;
import io.jsonwebtoken.Claims;
import jdk.nashorn.internal.parser.Token;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class BackstageApplicationTests {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenUtil tokenUtil;

    @Test
    void generateUser(){
        System.out.println(GenerateUser.GenerateUser("jdkjfaksdjfa"));

    }
    @Test
    void contextLoads() {
        System.out.println(passwordEncoder.encode("123456"));
    }

    @Test
    void jwtTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("Bearer", new String("hhhhhhhhhh"));
        String token = tokenUtil.generateJwt(map);
        token = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NjU5MzYzOTUsImNyZWF0ZWQiOjE2NjU5MTgzOTU3NTQsInVzZXJuYW1lIjoicm9vdCJ9.L-56o2daHeudLdLGo54NX26a_5an9lgFM5jmDG2R0zIzA781qNGE5GT2ZWCKO7gBB9mwWFzDHYqwmMD5SQxjvQ";
        System.out.println("token:" + token);
        Claims tokenBody = tokenUtil.getTokenBody(token);
        System.out.println("tokenBody：" + tokenBody);
        tokenUtil.getUsernameByToken(token);
    }

    @Test
    void upload() {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println((format.format(new Date()) + "fileName"));
    }
    @Resource
    private MailUtils mailUtils;
    @Test
    void mailText(){
        // 纯文本邮件
        MailVo mailVo=new MailVo();
        mailVo.setHtml(false);
        mailVo.setReceivers(new String[]{"1253088506@qq.com"});
        mailVo.setSubject("个人运动管理平台");
        mailVo.setContent("邮件发送测试");
        System.out.println(mailUtils.sendMail(mailVo));

    }
    @Test
    void mailHtml(){
        // 内容为Html格式
        MailVo mail=new MailVo();
        mail.setHtml(true);
        mail.setReceivers(new String[]{"1253088506@qq.com"});
        mail.setSubject("个人运动管理平台");
        mail.setContent("<a href='https://www.bilibili.com' style='color: red'> 邮件发送测试 </a>");
        System.out.println(mailUtils.sendMail(mail));
    }
    @Resource
    private SysUserService sysUserService;
    @Test
    void sendVerificationCode(){
        // 内容为Html格式
        System.out.println(sysUserService.sendVerificationCode("1253088506@qq.com"));

    }
    @Test
    void forget(){
        UserLoginVo user=new UserLoginVo();
        user.setId(3);
        user.setPassword("123456");
        user.setMailUrl("1253088506@qq.com");
        user.setVerificationCode("1171247");
        // 内容为Html格式
        System.out.println(sysUserService.forget(user));
    }
    @Autowired
    private FoodMapper foodMapper;
    @Autowired
    private FoodTypeMapper foodTypeMapper;
    @Test
    void easyExcel(){
        List<FoodType> foodTypes = foodTypeMapper.typeAll();
        System.out.println("foodTypes: " + foodTypes);
        try {
            FileInputStream file = new FileInputStream("F:\\Java\\PracticeProject\\20220515个人运动管理平台\\code\\backstage\\src\\main\\resources\\static\\food.xlsx");
            //ExcelReaderSheetBuilder sheet = EasyExcel.read(file).sheet(0);
            //System.out.println(sheet);
            for (Food food : EasyExcelUtil.readExcel(file, Food.class)) {
                Long foodTypeId = getFoodTypeId(food.getTypeTitle(), foodTypes);
                food.setTypeId(foodTypeId);
                System.out.println("foodTypeId: " + foodTypeId);
                System.out.println("food: "+food);
                foodMapper.insert(food);
                //return;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    Long getFoodTypeId(String foodTypeTitle, List<FoodType> foodTypes) throws Exception {
        for (FoodType foodType : foodTypes) {
            if (foodType.getTitle().equals(foodTypeTitle)){
                return foodType.getId();
            }
        };
        throw new Exception("没有找到Title为 " + foodTypeTitle + "的食物类别");
    }

    @Value("${jwt.secret}")
    private String secret;
    @Test
    void valueTest(){
        System.out.println(secret);
    }
    @Value("${qiniu.imageDomain}")
    private String imageDomain;
    @Resource
    private SysUserMapper sysUserMapper;
    @Test
    void handlerImage(){
        //SysUser userById = sysUserMapper.findUserById(1);
        //handlerImage(userById);

        List<SysUser> users = sysUserMapper.findPage(0, 10);
        handlerImage(users);
        for (SysUser user : users) {
            System.out.println(user.getAvatar());
        }

    }
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
}
