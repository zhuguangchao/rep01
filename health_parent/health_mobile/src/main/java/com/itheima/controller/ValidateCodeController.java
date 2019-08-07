package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.util.SMSUtils;
import com.itheima.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/27/14:51
 * @Description:
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;
    @PostMapping("/send4Order")
    public Result send4Order(String telephone){
        String key = RedisMessageConstant.SENDTYPE_ORDER+"_"+telephone;
        return sendValidateCode(telephone, key);
    }
    @PostMapping("/send4Login")
    public Result send4Login(String telephone){
        String key = RedisMessageConstant.SENDTYPE_LOGIN+"_"+telephone;
        return sendValidateCode(telephone, key);
    }
    private Result sendValidateCode(String telephone,String key){
        Jedis jedis = jedisPool.getResource();
        //1.验证码是否发送成功,提示注意查询
        if (null != jedis.get(key)){
            //发送过提示查收
            return new Result(false, MessageConstant.SENT_VALIDATECODE);
        }
        //2.没有发送过就生成验证码重新发送
        Integer code = ValidateCodeUtils.generateValidateCode(6);
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code+"");
            //3.把验证码存入redis中(提交预约时验证是否重复发送)
            jedis.setex(key, 5*60, code + "" );
            //4.存入redis的验证码需要设置有效期(过期了从redis中删除),放置验证码重复使用
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS );
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
    }
}
