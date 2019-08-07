package com.itheima.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MenuDao;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/08/03/15:26
 * @Description:
 */
@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;
    @Override
    public LinkedHashSet<Menu> getMenuByRoleId(int id) {
        LinkedHashSet<Menu> menuone = menuDao.getMenuByRoleId(id,1);
        LinkedHashSet<Menu> menutwo = menuDao.getMenuByRoleId(id,2);
        for (Menu menuo : menuone) {
            for (Menu menut : menutwo) {
                if (menut.getParentMenuId().equals(menuo.getId())){
                    List<Menu> children = menuo.getChildren();
                    children.add(menut);
                }
            }
        }
        return menuone;
    }
}
