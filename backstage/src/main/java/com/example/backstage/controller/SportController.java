package com.example.backstage.controller;

import com.example.backstage.entity.Sport;
import com.example.backstage.result.QueryInfo;
import com.example.backstage.result.Result;
import com.example.backstage.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 阿杰
 * @create 2021-04-01 11:20
 */
@RestController
@RequestMapping("/sport/")
public class SportController {

    @Autowired
    private SportService sportService;

    @PostMapping("/findPage")
    public Result findAll(@RequestBody QueryInfo queryInfo) {
        return sportService.findPage(queryInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Sport sport) {
        return sportService.add(sport);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody Sport sport) {
        return sportService.edit(sport);
    }

    @PostMapping("delete")
    public Result delete(Integer id) {
        return sportService.delete(id);
    }
}
