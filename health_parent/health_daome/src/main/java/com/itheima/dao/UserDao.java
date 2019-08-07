package com.itheima.dao;

import com.itheima.pojo.User;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/31/21:13
 * @Description:
 */
public interface UserDao {
    User findByUsername(String username);
}
