package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.pojo.User;
import com.itheima.service.MenuService;
import com.itheima.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/08/03/15:23
 * @Description:
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Reference
    private MenuService menuService;
    @Reference
    private UserService userService;
    @GetMapping("/getMenuByRoleId")
    public Result getMenuByRoleId(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        LinkedHashSet<Menu> menus = menuService.getMenuByRoleId(user.getId());
        return new Result(true, MessageConstant.GET_MENU_SUCCESS, menus);
    }
}
