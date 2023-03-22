package com.example.backstage.service.implement;

import com.example.backstage.entity.WxRun;
import com.example.backstage.mapper.WxRunMapper;
import com.example.backstage.result.Result;
import com.example.backstage.service.WxRunService;
import com.example.backstage.util.DateUtil;
import com.example.backstage.util.SecurityUtil;
import com.example.backstage.vo.RunVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ajie
 * @createTime 2021年06月20日 18:54:00
 */
@Service
@Slf4j
public class WxRunServiceImpl implements WxRunService {

    @Autowired
    private WxRunMapper runMapper;

    @Override
    public Result insert(WxRun wxRun) {
        try {
            WxRun run = runMapper.findByDate(wxRun.getRunDate());
            if (null == run) {
                runMapper.insert(wxRun);
            }
            return Result.success("微信步数录入成功");
        } catch (Exception e) {
            log.info("程序异常 --> {}", e.getMessage());
            return Result.fail("微信步数录入失败");
        }
    }

    @Override
    public Result update(WxRun wxRun) {
        try {
            runMapper.update(wxRun);
            return Result.success("微信步数更新成功");
        } catch (Exception e) {
            log.info("程序异常 --> {}", e.getMessage());
            return Result.fail("微信步数更新失败");
        }
    }

    @Override
    public Result findRun(String date) {
        Integer userId = SecurityUtil.getUserId();
        try {
            Map<String, List<WxRun>> map = new HashMap<>(4);
            //从今天起第一周  获取今天是星期几 <---> 周一
            int week1 = DateUtil.getWeekOfDate(date);
            //周一
            String date1 = DateUtil.getWeekBeforeDate(date, week1-1);
            List<WxRun> runs1 = runMapper.findByTime(date, date1, userId);
            map.put("week1", runs1);
            //获取第二周 周天 <---> 周一
            String date2 = DateUtil.getWeekBeforeDate(date1, 1);
            String week2 = DateUtil.getWeekBeforeDate(date2, 6);
            List<WxRun> runs2 = runMapper.findByTime(date2, week2, userId);
            map.put("week2", runs2);
            //第三周 周天 --- 周一
            String date3 = DateUtil.getWeekBeforeDate(week2, 1);
            String week3 = DateUtil.getWeekBeforeDate(date3, 6);
            List<WxRun> runs3 = runMapper.findByTime(date3, week3, userId);
            map.put("week3", runs3);
            //第四周 周天 --- 周一
            String date4 = DateUtil.getWeekBeforeDate(date3, 1);
            String week5 = DateUtil.getWeekBeforeDate(date4, 6);
            List<WxRun> runs4 = runMapper.findByTime(date4, week5, userId);
            map.put("week4", runs4);
            return Result.success("微信步数查询成功", map);
        } catch (Exception e) {
            log.info("程序异常 --> {}", e.getMessage());
            return Result.fail("微信步数查询失败");
        }
    }

    @Override
    public Result findByDate(String stampToDate) {
        try {
            WxRun run = runMapper.findByDate(stampToDate);
            return Result.success("微信步数查询成功", run);
        } catch (Exception e) {
            log.info("程序异常 --> {}", e.getMessage());
            return Result.fail("微信步数查询失败");
        }
    }

    @Override
    public Result getRunInfo(RunVo runVo) {
        try {
            List<WxRun> runs = runMapper.findByTime(runVo.getBeginTime(), runVo.getEndTime(), runVo.getUserId());
            return Result.success("微信步数查询成功", runs);
        } catch (Exception e) {
            log.info("程序异常 --> {}", e.getMessage());
            return Result.fail("微信步数查询失败");
        }
    }
}
