package com.itheima.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/31/18:27
 * @Description:
 */
@Controller
@RequestMapping("/my")
public class MyController {
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('add')")
    public String add(){
        System.out.println("打印add...");
        return null;
    }
    @RequestMapping("/delete")
    @PreAuthorize("hasRole('delete')")
    public String delete(){
        System.out.println("delete...");
        return null;
    }

}
