package com.itheima.service;

import com.itheima.pojo.User;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/31/21:12
 * @Description:
 */
public interface UserService {
    User findByUsername(String username);
}
