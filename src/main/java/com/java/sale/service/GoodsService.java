package com.java.sale.service;

import com.java.sale.dao.mapper.GoodsDao;
import com.java.sale.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品服务
 * @author 曾伟
 * @date 2019/11/21 20:00
 */
@Service
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    /**
     * 列表
     * @return
     */
    public List<GoodsVo> goodsVoList(){
        return  goodsDao.getGoodsVoList();
    }

    public GoodsVo goodsVoById(long goodsId) {
        return goodsDao.getGoodsVoById(goodsId);
    }
}
