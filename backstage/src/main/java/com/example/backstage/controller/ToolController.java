package com.example.backstage.controller;

import com.example.backstage.util.QiNiuUtils;
import com.example.backstage.util.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import java.io.IOException;

/**
 * @author HuaRunSheng
 * @date 2022/11/6 22:17
 * @description :
 */
@RestController
@RequestMapping("/tool")
public class ToolController {
    @Resource
    private QiNiuUtils qiNiuUtils;
    @PostMapping("/upload")
    public ResultUtil upload(@RequestBody MultipartFile file) throws IOException {
        String url = qiNiuUtils.upload(file.getInputStream(), file.getOriginalFilename());
        return ResultUtil.success("文件上传成功", url);

    }
}
