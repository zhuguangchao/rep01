package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/27/15:53
 * @Description:
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String,String> orderInfo){
        Jedis jedis = jedisPool.getResource();
        //1.先验证验证码:
        //    1.1从redis取出验证码如果没有提示重新发,
        String telephone = orderInfo.get("telephone");
        String key = RedisMessageConstant.SENDTYPE_ORDER+"_"+telephone;
        String codeInRedis = jedis.get(key);
        if (null == codeInRedis){
            return new Result(false, MessageConstant.SEND_VALIDATECODE);
        }
        //    1.2验证前端传过来的验证码与redis中是否相同
        if (!codeInRedis.equals(orderInfo.get("validateCode"))){
            //不同
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //2.设置预约的类型为微信预约
        orderInfo.put("orderType", Order.ORDERTYPE_WEIXIN);
        //3.调用业务层存入数据
        int orderId = orderService.addOrder(orderInfo);
        //4.返回订单的编号给前端
        return new Result(true, MessageConstant.ORDER_SUCCESS, orderId);
    }
    @GetMapping("/findById")
    public Result findById(int id){
        Map<String,Object> orderInfo = orderService.findById(id);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,orderInfo);
    }
}
