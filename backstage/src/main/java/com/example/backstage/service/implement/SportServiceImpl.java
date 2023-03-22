package com.example.backstage.service.implement;

import com.example.backstage.constant.MessageConstant;
import com.example.backstage.entity.Sport;
import com.example.backstage.mapper.SportMapper;
import com.example.backstage.result.PageResult;
import com.example.backstage.result.QueryInfo;
import com.example.backstage.result.Result;
import com.example.backstage.service.SportService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 阿杰
 * @create 2021-04-01 11:15
 */
@Service
public class SportServiceImpl implements SportService {

    @Autowired
    private SportMapper sportMapper;

    @Override
    public Result findPage(QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<Sport> page = sportMapper.findPage(queryInfo.getQueryString());
        if (page == null) {
            return new Result(false, MessageConstant.SPORT_SELECT_FAIL);
        }
        long total = page.getTotal();
        List<Sport> result = page.getResult();
        return new Result(true, MessageConstant.SPORT_SELECT_SUCCESS, new PageResult(result, total));
    }

    @Override
    public Result add(Sport sport) {
        Sport sportByName = sportMapper.findByName(sport.getName());
        if (sportByName != null) {
            return new Result(false, MessageConstant.SPORT_EXIST);
        }
        int i = sportMapper.insert(sport);
        if (i <= 0) {
            return new Result(false, MessageConstant.ADD_SPORT_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SPORT_SUCCESS);
    }

    @Override
    public Result delete(Integer id) {
        if (id == null) {
            return new Result(false, MessageConstant.CHOOSE_SPORT);
        }
        int i = sportMapper.delete(id);
        if (i <= 0) {
            return new Result(false, MessageConstant.DELETE_SPORT_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_SPORT_SUCCESS);
    }

    @Override
    public Result edit(Sport Sport) {
        int update = sportMapper.update(Sport);
        if (update <= 0) {
            return new Result(false, MessageConstant.UPDATE_SPORT_FAIL);
        }
        return new Result(true, MessageConstant.UPDATE_SPORT_SUCCESS);
    }
}
