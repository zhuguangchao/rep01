package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/08/01/14:52
 * @Description:
 */
@RequestMapping("/user")
@RestController
public class UserController {
    @Reference
    private UserService userService;
    @GetMapping("/getLoginUsername")
    public Result getLoginUsername(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        username = userDetails.getUsername();
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
    }
}
