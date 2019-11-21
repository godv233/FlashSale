package com.java.sale.dao.mapper;

import com.java.sale.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * @author 曾伟
 * @date 2019/11/21 20:01
 */
@Mapper
public interface GoodsDao {
    @Select("select g.*,mg.stock_count ,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id=g.id")
    List<GoodsVo> getGoodsVoList();
    @Select("select g.*,mg.stock_count ,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id=g.id where g.id=#{goodsId}")
    GoodsVo getGoodsVoById( @Param("goodsId") long goodsId);
}
