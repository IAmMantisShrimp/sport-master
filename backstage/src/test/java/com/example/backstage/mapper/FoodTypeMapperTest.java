package com.example.backstage.mapper;

import com.example.backstage.entity.FoodType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author HuaRunSheng
 * @date 2022/11/11 13:47
 * @description :
 */
@SpringBootTest
class FoodTypeMapperTest {

    @Resource
    private FoodTypeMapper foodTypeMapper;
    @Test
    void typeAll(){
        System.out.println("typeAll: " + foodTypeMapper.typeAll());
    }
    @Test
    void findByTitle(){
        System.out.println("findByTitle: " + foodTypeMapper.findByTitle("主食类"));
    }
    //@Test
    //void findPage(){
    //    System.out.println(foodTypeMapper.findPage("水果"));
    //}
    @Test
    void findPageLimit(){
        //System.out.println(foodTypeMapper.findPage( 0 ,5));
    }
    @Test
    void insert() {
        FoodType foodType= new FoodType();
        foodType.setTitle("测试");
        foodTypeMapper.insert(foodType);
    }

    @Test
    void update(){
        FoodType foodType= new FoodType();
        foodType.setId(13L);
        foodType.setTitle("测试update");
        foodTypeMapper.update(foodType);
    }
    @Test
    void delete(){

        foodTypeMapper.delete(13L);
    }
}