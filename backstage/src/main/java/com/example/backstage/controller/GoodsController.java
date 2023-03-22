package com.example.backstage.controller;

import com.example.backstage.constant.MessageConstant;
import com.example.backstage.entity.Goods;
import com.example.backstage.result.QueryInfo;
import com.example.backstage.result.Result;
import com.example.backstage.service.GoodsService;
import com.example.backstage.util.EasyExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author 阿杰
 * @create 2021-04-20 15:18
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @ApiOperation(value = "分页查询商品商品信息", httpMethod = "POST")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        return goodsService.findPage(queryInfo);
    }

    @PostMapping("/batchImport")
    @ApiOperation(value = "通过excel导入添加商品信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods", value = "从浏览器上传的Excel文档", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Integer")
    })
    public Result batchImport(@RequestParam("goods") MultipartFile file, Integer userId) {
        try {
            List<Goods> list = EasyExcelUtil.readExcel(file.getInputStream(), Goods.class);
            return goodsService.batchImport(list, userId);
        } catch (Exception e) {
            return new Result(false, MessageConstant.FILE_READER_FAIL);
        }
    }

    @ApiOperation(value = "Excel导出所有的商品信息", httpMethod = "GET")
    @ApiImplicitParam(name = "response", value = "响应给客户端的输出流(用于输出excel文档)")
    @GetMapping("/batchExport")
    public void batchExport(HttpServletResponse response) throws IOException {
        List<Goods> list = goodsService.findAll();
        //代表的时Excel文件类型
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=export.xlsx");
        // 这里需要设置不关闭流
        OutputStream outputStream = response.getOutputStream();
        EasyExcel.write(outputStream)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("商品列表")
                .head(Goods.class)
                .doWrite(list);
        outputStream.flush();
        outputStream.close();
    }


}
