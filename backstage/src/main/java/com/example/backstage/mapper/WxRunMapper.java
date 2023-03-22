package com.example.backstage.mapper;

import com.example.backstage.entity.WxRun;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ajie
 * @createTime 2021年06月20日 18:36:00
 */
public interface WxRunMapper {
    /**
     * 添加微信运动信息
     * @param wxRun
     */
    void insert(WxRun wxRun);

    /**
     * 更改微信运动信息
     * @param wxRun
     */
    void update(WxRun wxRun);

    /**
     * 根据用户ID以及日期
     * @return
     */
    List<WxRun> findByTime(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("userId") Integer userId);

    /**
     * 根据日期查询
     * @param date
     * @return
     */
    WxRun findByDate(String date);
}
