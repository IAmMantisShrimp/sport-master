package com.example.backstage.service;

import com.example.backstage.entity.WxRun;
import com.example.backstage.result.Result;
import com.example.backstage.vo.RunVo;

/**
 * @author ajie
 * @createTime 2021年06月20日 18:53:00
 */
public interface WxRunService {
    /**
     * 添加微信运动信息
     * @param wxRun
     * @return
     */
    Result insert(WxRun wxRun);

    /**
     * 更新本日步数
     * @param wxRun
     * @return
     */
    Result update(WxRun wxRun);

    /**
     * 查询微信运动信息
     * 传递一个时间 查询4个周的运动数据
     * @return
     */
    Result findRun(String date);

    /**
     * 根据时间查询是否有数据
     * @param stampToDate
     * @return
     */
    Result findByDate(String stampToDate);

    Result getRunInfo(RunVo runVo);
}
