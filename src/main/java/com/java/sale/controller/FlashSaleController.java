package com.java.sale.controller;

import com.java.sale.common.CodeMsg;
import com.java.sale.common.Result;
import com.java.sale.domain.FlashSaleOrder;
import com.java.sale.domain.OrderInfo;
import com.java.sale.domain.User;
import com.java.sale.rabbitmq.MqSender;
import com.java.sale.rabbitmq.SaleMessage;
import com.java.sale.redis.GoodsKey;
import com.java.sale.redis.MiaoshaKey;
import com.java.sale.redis.RedisService;
import com.java.sale.service.FlashSaleService;
import com.java.sale.service.GoodsService;
import com.java.sale.service.OrderService;
import com.java.sale.utils.UUIDUtils;
import com.java.sale.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

/**
 * 秒杀的控制器
 *
 * @author 曾伟
 * @date 2019/11/21 21:58
 */
@Controller
@RequestMapping("/miaosha")
public class FlashSaleController implements InitializingBean {
    @Autowired
    private RedisService redisService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private FlashSaleService flashSaleService;
    @Autowired
    private MqSender sender;

    /**
     * 页面静态化
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/{path}/do_miaosha")
    @ResponseBody
    public Result<Integer> sale(Model model, User user, @RequestParam("goodsId") long goodsId,
                                @PathVariable("path") String path) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
//        //秒杀的主要逻辑
//        //判断商品库存
//        GoodsVo goodsVo = goodsService.goodsVoById(goodsId);
//        if (goodsVo!=null){
//            int stock=goodsVo.getStockCount();
//            if (stock<=0){//库存不足，秒杀失败
//                return Result.error(CodeMsg.MIAO_SHA_OVER);
//            }
//        }
//        //判断是否已经秒杀到了
//        FlashSaleOrder order = orderService.orderByUserIdGoodsId(user.getId(), goodsId);
//        if (order!=null){//不能重复秒杀
//            return Result.error(CodeMsg.REPEATE_MIAOSHA);
//        }
//        //下单，需要事务控制，写在一个service里，使用事务控制。
//        //减库存
//        //下订单
//        //写入秒杀订单
//        OrderInfo orderInfo= flashSaleService.miaosha(user,goodsVo);
//        return Result.success(orderInfo);
        //验证path
        boolean check = flashSaleService.check(path, user.getId(), goodsId);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }


        //接口优化
        //1.预减库存
        long decrease = redisService.decrease(GoodsKey.getGoodsStock, "" + goodsId);
        if (decrease <= 0) {
            return Result.error(CodeMsg.MIAOSHA_FAIL);
        }
        //判断是否秒杀过了
        FlashSaleOrder order = orderService.orderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {//不能重复秒杀
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //入队
        SaleMessage message = new SaleMessage();
        message.setGoodsId(goodsId);
        message.setUser(user);
        sender.miaoshaMessage(message);
        return Result.success(0);
    }

    /**
     * 系统初始化,加载缓存
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.goodsVoList();
        if (list != null && !list.isEmpty()) {//加入缓存
            for (GoodsVo goods : list) {
                redisService.set(GoodsKey.getGoodsStock, "" + goods.getId(), goods.getStockCount());
            }
        }
    }

    /**
     * 查询秒杀结果：
     * 成功：orderId
     * 失败：-1
     * 还在处理中：0
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @GetMapping("/result")
    @ResponseBody
    public Result<Long> miaoshaResult(Model model, User user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = flashSaleService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);

    }

    /**
     * 得到path
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @GetMapping("/path")
    @ResponseBody
    public Result<String> getMiaoshaPath(Model model, User user, @RequestParam("goodsId") long goodsId,
                                         @RequestParam(value="verifyCode", defaultValue="0")int verifyCode) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        boolean check = flashSaleService.checkVerifyCode(user, goodsId, verifyCode);
        if(!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        String str = flashSaleService.createPath(user.getId(), goodsId);
        return Result.success(str);
    }


    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaVerifyCod(HttpServletResponse response, User user,
                                              @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        try {
            BufferedImage image = flashSaleService.createVerifyCode(user, goodsId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.MIAOSHA_FAIL);
        }
    }
}
