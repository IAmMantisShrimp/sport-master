package com.example.backstage.controller;

import com.example.backstage.constant.MessageConstant;
import com.example.backstage.entity.Food;
import com.example.backstage.entity.FoodType;
import com.example.backstage.result.QueryInfo;
import com.example.backstage.result.Result;
import com.example.backstage.service.FoodService;
import com.example.backstage.util.EasyExcelUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ajie
 * @createTime 2021年06月14日 19:08:00
 */
@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @ApiOperation(value = "通过excel批量导入")
    @PostMapping("/batchImport")
    public Result batchImport(@RequestParam("file") MultipartFile file) {
        try {
            List<Food> list = EasyExcelUtil.readExcel(file.getInputStream(), Food.class);
            return foodService.batchImport(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(MessageConstant.FILE_READER_FAIL);
        }
    }

    @ApiOperation(value = "添加菜品分类")
    @PostMapping("/type/insert")
    public Result insertType(@RequestBody FoodType foodType) {
        return foodService.insertType(foodType);
    }

    @ApiOperation(value = "删除菜品分类")
    @DeleteMapping("/type/delete/{id}")
    public Result deleteType(@PathVariable Long id) {
        return foodService.deleteType(id);
    }

    @ApiOperation(value = "修改菜品分类")
    @PostMapping("/type/update")
    public Result updateType(@RequestBody FoodType foodType) {
        return foodService.updateType(foodType);
    }
    @ApiOperation(value = "分页查询菜品分类及其菜品信息")
    @PostMapping("/type/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        return foodService.findPage(queryInfo);
    }

    @GetMapping("/typeAll")
    public Result typeAll() {
        return foodService.typeAll();
    }

    @PostMapping("/findPage")
    public Result findFoodPage(@RequestBody QueryInfo queryInfo) {
        return foodService.findFoodPage(queryInfo);
    }

    @ApiOperation(value = "添加菜品")
    @PostMapping("/insert")
    public Result insert(@RequestBody Food food) {
        return foodService.insert(food);
    }

    @ApiOperation(value = "修改菜品")
    @PostMapping("/update")
    public Result update(@RequestBody Food food) {
        return foodService.update(food);
    }

    @ApiOperation(value = "删除菜品")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return foodService.delete(id);
    }

    @ApiOperation(value = "根据食物类别查询食物")
    @PostMapping("/typeId")
    public Result findFoodByTypeId(@RequestBody QueryInfo queryInfo) {
        return foodService.findFoodByTypeId(queryInfo);
    }
}
