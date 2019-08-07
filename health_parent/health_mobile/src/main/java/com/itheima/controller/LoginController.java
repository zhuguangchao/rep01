package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.itheima.util.SMSUtils;
import com.itheima.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/29/8:51
 * @Description:
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/check")
    public Result check(@RequestBody Map<String,String> loginInfo, HttpServletResponse res){
        Jedis jedis = jedisPool.getResource();
        String telephone = loginInfo.get("telephone");
        String key = RedisMessageConstant.SENDTYPE_LOGIN+"_"+telephone;
        //1.验证码是否发送成功,提示注意查询
        String codeInRedis = jedis.get(key);
        if (null == codeInRedis){
            return new Result(false, MessageConstant.SEND_VALIDATECODE);
        }
        //    1.2验证前端传过来的验证码与redis中是否相同
        if (!codeInRedis.equals(loginInfo.get("validateCode"))){
            //不同
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //3.2 先判断是否为会员 通过手机号码查询
        Member member = memberService.findByTelephone(telephone);
        if(null == member) {
            //- 不存在，调用Dao插入新会员t_member，获取ID
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            memberService.add(member);
        }
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        cookie.setMaxAge(30 * 24 * 60 * 60);
        cookie.setPath("/");
        res.addCookie(cookie);
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
