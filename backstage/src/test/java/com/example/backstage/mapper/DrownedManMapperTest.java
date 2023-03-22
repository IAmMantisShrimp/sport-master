package com.example.backstage.mapper;

import com.alibaba.excel.EasyExcel;
import com.example.backstage.entity.DrownedMan;
import com.example.backstage.util.DrownedManUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author HuaRunSheng
 * @date 2022/10/26 17:43
 * @description :
 */
@SpringBootTest
class DrownedManMapperTest {

    @Resource
    private DrownedManMapper drownedManMapper;
    @Test
    void insert() {
        //for (int i = 0; i < 29000; i++) {
        //    DrownedMan drownedMan = DrownedManUtil.generateDrownedMan();
        //    //System.out.println(drownedMan);
        //    drownedManMapper.insert(drownedMan);
        //}

    }
    @Test
    void selectList(){
        // 从数据库读取数据
        List<DrownedMan> drownedMEN = drownedManMapper.selectList();
        System.out.println(drownedMEN.size());
        //System.out.println(drownedMEN.get(1));
        //for (int i = 1; i < 10; i++) {
        //    System.out.println(drownedMEN.get(i));
        //}
        //定义文件输出位置
        FileOutputStream outputStream = null;
        try {
            // 新建一个xlsx文件
            outputStream = new FileOutputStream(new File("E:/GOOGLE_download/user1.xlsx"));
            // 将数据库数据写入到文件中
            EasyExcel.write(outputStream, DrownedMan.class).sheet("用户信息").doWrite(drownedMEN);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //EasyExcel.write(outputStream, DrownedMan.class).sheet("用户信息").doWrite(drownedMEN);

    }

}