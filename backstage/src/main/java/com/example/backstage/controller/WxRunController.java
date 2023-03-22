package com.example.backstage.controller;

import com.example.backstage.entity.WxRun;
import com.example.backstage.result.Result;
import com.example.backstage.service.WxRunService;
import com.example.backstage.util.DateUtil;
import com.example.backstage.util.mini.DecryptUtil;
import com.example.backstage.util.SecurityUtil;
import com.example.backstage.vo.RunVo;
import com.example.backstage.vo.StepVo;
import com.example.backstage.vo.WxVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 微信步数
 * 卡路里统计
 * @author ajie
 * @createTime 2021年06月20日 19:21:00
 */
@RestController
@RequestMapping("/calories")
public class WxRunController {

    @Autowired
    private WxRunService runService;

    @ApiOperation(value = "第一次解密获取的数据")
    @PostMapping("/decrypt")
    public Result getRunStep(@RequestBody WxVo wxVo) {
        byte[] decrypt = DecryptUtil.decrypt(Base64.decodeBase64(wxVo.getEncryptedData()), Base64.decodeBase64(wxVo.getSessionKey()), Base64.decodeBase64(wxVo.getIv()));
        if (decrypt != null && decrypt.length > 0) {
            String info = new String(decrypt, StandardCharsets.UTF_8);
            JSONObject object = JSON.parseObject(info);
            //获取微信步数
            String stepInfoList = object.getString("stepInfoList");
            List<StepVo> steps = JSON.parseArray(stepInfoList, StepVo.class);
            WxRun wxRun = new WxRun();
            wxRun.setUserId(SecurityUtil.getUserId());
            for (StepVo step : steps) {
                String stampToDate = DateUtil.stampToDate(step.getTimestamp()*1000);
                Result run = runService.findByDate(stampToDate);
                WxRun data = (WxRun)run.getData();
                if (data == null) {
                    wxRun.setRunDate(stampToDate);
                    wxRun.setRunStep(step.getStep());
                    runService.insert(wxRun);
                } else {
                    //不为空并且步数不等的情况
                    if (!step.getStep().equals(data.getRunStep())) {
                        wxRun.setRunDate(stampToDate);
                        wxRun.setRunStep(step.getStep());
                        runService.update(wxRun);
                    }
                }
            }
        }
        //默认查询当前时间的
        return runService.findRun(DateUtil.getNowDate());
    }

    @ApiOperation(value = "根据时间时间获取用户信息")
    @PostMapping("/getRunInfo")
    public Result getRunInfo(RunVo runVo) {
        runVo.setUserId(SecurityUtil.getUserId());
        return runService.getRunInfo(runVo);
    }
}
