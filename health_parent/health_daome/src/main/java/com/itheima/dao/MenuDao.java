package com.itheima.dao;

import com.itheima.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashSet;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/08/03/15:27
 * @Description:
 */
public interface MenuDao {
    LinkedHashSet<Menu> getMenuByRoleId(@Param("id") int id, @Param("level") int level);


}
