package com.example.backstage.service.implement;

import com.example.backstage.constant.MessageConstant;
import com.example.backstage.entity.Goods;
import com.example.backstage.entity.EasyUser;
import com.example.backstage.entity.SysUser;
import com.example.backstage.mapper.GoodsMapper;
import com.example.backstage.mapper.SysUserMapper;
import com.example.backstage.result.PageResult;
import com.example.backstage.result.QueryInfo;
import com.example.backstage.result.Result;
import com.example.backstage.service.GoodsService;
import com.example.backstage.util.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author 阿杰
 * @create 2021-04-01 11:15
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private SysUserMapper userMapper;
    @Value("${qiniu.imageDomain}")
    private String imageDomain;
    @Override
    public Result findPage(QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<Goods> page = goodsMapper.findPage(queryInfo.getQueryString());
        if (page == null) {
            return new Result(false, MessageConstant.GOODS_SELECT_FAIL);
        }
        long total = page.getTotal();
        List<Goods> result = page.getResult();
        handlerImage(result);
        return new Result(true, MessageConstant.GOODS_SELECT_SUCCESS, new PageResult(result, total));
    }

    @Override
    public Result add(Goods goods) {
        Goods good = goodsMapper.findByName(goods.getName());
        if (good != null) {
            return new Result(false, MessageConstant.GOODS_EXIST);
        }
        int i = goodsMapper.insert(goods);
        if (i <= 0) {
            return new Result(false, MessageConstant.ADD_GOODS_FAIL);
        }
        return new Result(true, MessageConstant.ADD_GOODS_SUCCESS);
    }

    @Override
    public Result delete(Integer id) {
        if (id == null) {
            return new Result(false, MessageConstant.CHOOSE_GOODS);
        }
        int i = goodsMapper.delete(id);
        if (i <= 0) {
            return new Result(false, MessageConstant.DELETE_GOODS_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_GOODS_SUCCESS);
    }

    @Override
    public Result edit(Goods goods) {
        int update = goodsMapper.update(goods);
        if (update <= 0) {
            return new Result(false, MessageConstant.UPDATE_GOODS_FAIL);
        }
        return new Result(true, MessageConstant.UPDATE_GOODS_SUCCESS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result batchImport(List<Goods> list, Integer userId) {
        EasyUser easyUser = userMapper.findById(userId);
        if (easyUser == null) {
            return new Result(false, MessageConstant.ADMIN_NOT_LOGIN);
        }
        int i = 0,j = 0;
        try {
            for (Goods goods : list) {
                Goods byName = goodsMapper.findByName(goods.getName());
                if (byName != null) {
                    goods.setId(byName.getId());
                    goods.setRemark(byName.getRemark() + "； 管理员：" + easyUser.getNickName() + "于" + DateUtil.getNowDateTime() + " --> 更新了商品:" + goods.getName() + "；目前数量：" + goods.getNumber());
                    i += goodsMapper.update(goods);
                } else {
                    goods.setCreateTime(new Date());
                    goods.setCreateUserId(userId);
                    goods.setRemark("管理员：" + easyUser.getNickName() + "于" + DateUtil.getNowDateTime() + "将商品：" + goods.getName() + "加入库存，数量：" + goods.getNumber());
                    j += goodsMapper.insert(goods);
                }
            }
        } catch (Exception e) {
            return new Result(false, MessageConstant.BATCH_IMPORT_FAIL);
        }
        if (i <= 0 && j <= 0) {
            return new Result(false, MessageConstant.BATCH_IMPORT_FAIL);
        }
        return new Result(true, MessageConstant.BATCH_IMPORT_SUCCESS);
    }

    @Override
    public List<Goods> findAll() {
        List<Goods> goods = goodsMapper.findAll();
        handlerImage(goods);
        return goods;
    }

    /**
     * 因为图片有存在自己的文件服务器的,也有在互联网上的,
     * 在互联网上的,不处理,在自己服务器上的加上自己的域名
     * @param good
     */
    public void handlerImage(Goods good){
        String avatar = good.getImageUrl();
        if (avatar != null && !"".equals(avatar)){
            if (!avatar.startsWith("http")){
                avatar=imageDomain+avatar;
                good.setImageUrl(avatar);
            }
        }
    }
    public void handlerImage(List<Goods> goods){
        for (Goods good : goods) {
            handlerImage(good);
        }
    }
}
