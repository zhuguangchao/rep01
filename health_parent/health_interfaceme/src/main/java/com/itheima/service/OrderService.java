package com.itheima.service;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/27/16:20
 * @Description:
 */
public interface OrderService {
    int addOrder(Map<String, String> orderInfo);

    Map<String, Object> findById(int id);
}
