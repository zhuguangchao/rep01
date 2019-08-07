package com.itheima.service;

import com.itheima.pojo.Menu;

import java.util.LinkedHashSet;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/08/03/15:26
 * @Description:
 */
public interface MenuService {
    LinkedHashSet<Menu> getMenuByRoleId(int id);
}
