package com.example.backstage.service.implement;

import com.example.backstage.entity.Food;
import com.example.backstage.entity.FoodType;
import com.example.backstage.entity.Goods;
import com.example.backstage.mapper.FoodMapper;
import com.example.backstage.mapper.FoodTypeMapper;
import com.example.backstage.result.PageResult;
import com.example.backstage.result.QueryInfo;
import com.example.backstage.result.Result;
import com.example.backstage.service.FoodService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ajie
 * @createTime 2021年06月14日 19:08:00
 */
@Service
@Slf4j
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodTypeMapper foodTypeMapper;

    @Autowired
    private FoodMapper foodMapper;
    @Value("${qiniu.imageDomain}")
    private String imageDomain;
    @Override
    public Result delete(Long id) {
        try {
            foodMapper.delete(id);
            return Result.success("食物删除成功");
        } catch (Exception e) {
            log.info("程序异常 --> {}", e.getMessage());
            return Result.fail("食物删除失败");
        }
    }

    @Override
    public Result update(Food food) {
        try {
            foodMapper.update(food);
            return Result.success("食物修改成功");
        } catch (Exception e) {
            log.info("程序异常 --> {}", e.getMessage());
            return Result.fail("食物修改失败");
        }
    }

    @Override
    public Result insert(Food food) {
        try {
            foodMapper.insert(food);
            return Result.success("食物添加成功");
        } catch (Exception e) {
            log.info("程序异常 --> {}", e.getMessage());
            return Result.fail("食物添加失败");
        }
    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        try {
            PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
            Page<FoodType> page = foodTypeMapper.findPage(queryInfo.getQueryString());
            List<FoodType> foodTypes = page.getResult();
            handlerFoodTypeImage(foodTypes);
            return Result.success("菜品分类查询成功", new PageResult(foodTypes, page.getTotal()));
        } catch (Exception e) {
            log.info("程序异常 --> {}", e.getMessage());
            return Result.fail("菜品分类查询失败");
        }
    }

    @Override
    public Result findFoodPage(QueryInfo queryInfo) {
        try {
            PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
            Page<Food> page = foodMapper.findPage(queryInfo.getQueryString());
            List<Food> foods = page.getResult();
            handlerImage(foods);
            return Result.success("菜品查询成功", new PageResult(foods, page.getTotal()));
        } catch (Exception e) {
            log.info("程序异常 --> {}", e.getMessage());
            return Result.fail("菜品查询失败");
        }
    }

    @Override
    public Result updateType(FoodType foodType) {
        try {
            foodTypeMapper.update(foodType);
            return Result.success("菜品分类更新成功");
        } catch (Exception e) {
            log.info("程序异常 --> {}", e.getMessage());
            return Result.fail("菜品分类修改失败");
        }
    }

    @Override
    public Result deleteType(Long id) {
        try {
            foodTypeMapper.delete(id);
            return Result.success("菜品分类删除成功");
        } catch (Exception e) {
            log.info("程序异常 --> {}", e.getMessage());
            return Result.fail("菜品分类删除失败");
        }
    }

    @Override
    public Result insertType(FoodType foodType) {
        try {
            foodTypeMapper.insert(foodType);
            return Result.success("菜品分类添加成功");
        } catch (Exception e) {
            log.info("程序异常 --> {}", e.getMessage());
            return Result.fail("菜品分类添加失败");
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result batchImport(List<Food> list) {
        //批量导入
        try {
            //1. 读取分类
            for (Food food : list) {
                FoodType foodType = foodTypeMapper.findByTitle(food.getTypeTitle());
                Food title = foodMapper.findByTitle(food.getTitle());
                if (null != foodType) {
                    //修改
                    if (title != null) {
                        foodMapper.update(food);
                    } else {
                        //添加
                        food.setTypeId(foodType.getId());
                        foodMapper.insert(food);
                    }
                }
            }
            return Result.success("批量导入成功！");
        } catch (Exception e) {
            log.info("程序异常 --> {}", e.getMessage());
            return Result.fail("批量导入失败");
        }
    }

    @Override
    public Result typeAll() {
        try {
            List<FoodType> foodTypes = foodTypeMapper.typeAll();
            handlerFoodTypeImage(foodTypes);
            return Result.success("分类查询成功！", foodTypes);
        } catch (Exception e) {
            log.info("程序异常 --> {}", e.getMessage());
            return Result.fail("分类查询失败");
        }
    }

    @Override
    public Result findFoodByTypeId(QueryInfo queryInfo) {
        try {
            PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
            String queryString = queryInfo.getQueryString();
            Page<Food> foods = foodMapper.findByTypeId(queryString);
            List<Food> result = foods.getResult();
            handlerImage(result);
            return Result.success("食物查询成功！", new PageResult(result, foods.getTotal()));
        } catch (Exception e) {
            log.info("程序异常 --> {}", e.getMessage());
            return Result.fail("食物查询失败");
        }
    }

    /**
     * 因为图片有存在自己的文件服务器的,也有在互联网上的,
     * 在互联网上的,不处理,在自己服务器上的加上自己的域名
     * @param food
     */
    public void handlerImage(Food food){
        String avatar = food.getImageUrls();
        if (avatar != null && !"".equals(avatar)){
            if (!avatar.startsWith("http")){
                avatar=imageDomain+avatar;
                food.setImageUrls(avatar);
            }
        }
    }
    public void handlerImage(List<Food> foods){
        for (Food food : foods) {
            handlerImage(food);
        }
    }
    public void handlerImage(FoodType foodType){
        String avatar = foodType.getIcon();
        if (avatar != null && !"".equals(avatar)){
            if (!avatar.startsWith("http")){
                avatar=imageDomain+avatar;
                foodType.setIcon(avatar);
            }
        }
    }
    public void handlerFoodTypeImage(List<FoodType> foodTypes){
        for (FoodType foodType : foodTypes) {
            handlerImage(foodType);
        }
    }
}
