package com.java.sale.controller;

import com.java.sale.common.Result;
import com.java.sale.domain.User;
import com.java.sale.redis.GoodsKey;
import com.java.sale.redis.RedisService;
import com.java.sale.service.GoodsService;
import com.java.sale.vo.GoodsDetailVo;
import com.java.sale.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品控制器
 * @author 曾伟
 * @date 2019/11/16 11:00
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisService redisService;
    //Thymeleaf的渲染工具。
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 跳转商品列表，并携带商品数据
     *
     * @param model
     * @param user
     * @return
     * 在5000*10的情况下
     * 未使用redis之前的qps是1200左右
     * 使用redis之后是2880左右。系统的负载也降低了
     *
     *
     */
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String toLogin(HttpServletRequest request, HttpServletResponse response, Model model, User user) {
        model.addAttribute("user", user);
        //从缓存中取
        String html = (String) redisService.get(GoodsKey.getGoodsList, "");
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        //手动渲染
        //查询商品列表
        List<GoodsVo> goodsVoList = goodsService.goodsVoList();
        model.addAttribute("goodslist", goodsVoList);
//        return "goods_list";
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;


    }

    /**
     * 模板引擎
     * @param request
     * @param response
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String toDetail( HttpServletRequest request, HttpServletResponse response,Model model, User user, @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", user);
        //取缓存
        String html = (String) redisService.get(GoodsKey.getGoodsDetail, "" + goodsId);
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        //手动渲染
        GoodsVo goodsVo = goodsService.goodsVoById(goodsId);
        model.addAttribute("goods", goodsVo);

        long startTime = goodsVo.getStartDate().getTime();
        long endTime = goodsVo.getEndDate().getTime();
        long currentTime = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if (startTime > currentTime) {//秒杀还未开始
            miaoshaStatus = 0;
            remainSeconds = (int) (startTime - currentTime) / 1000;
        } else if (endTime < currentTime) {//秒杀已结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {//秒杀正在进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", context);
        if (!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsDetail,""+goodsId,html);
        }
        return html;
    }

    /**
     *前后端分离
     * @param request
     * @param response
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value="/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(User user, @PathVariable("goodsId")long goodsId) {
        GoodsVo goods = goodsService.goodsVoById(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < startAt ) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setMiaoshaStatus(miaoshaStatus);
        return Result.success(vo);
    }

}
