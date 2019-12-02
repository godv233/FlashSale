package com.java.sale.dao.mapper;

import com.java.sale.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 *  商品的数据库相关
 * @author 曾伟
 * @date 2019/11/21 20:01
 */
@Mapper
public interface GoodsDao {
    /**
     * 商品列表
     * @return
     */
    @Select("select g.*,mg.stock_count ,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id=g.id")
    List<GoodsVo> getGoodsVoList();

    /**
     * id得到商品
     * @param goodsId
     * @return
     */
    @Select("select g.*,mg.stock_count ,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id=g.id where g.id=#{goodsId}")
    GoodsVo getGoodsVoById( @Param("goodsId") long goodsId);

    /**
     * 减库存
     * @param id
     * @return
     */
    @Update("update miaosha_goods set stock_count=stock_count-1 where goods_id=#{id} and stock_count>0")
    int reduceStock(long id);
}
