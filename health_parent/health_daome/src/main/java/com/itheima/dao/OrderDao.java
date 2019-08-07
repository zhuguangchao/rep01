package com.itheima.dao;

import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/27/16:21
 * @Description:
 */
public interface OrderDao {
    public void add(Order order);
    public List<Order> findByCondition(Order order);
    public Map findById4Detail(Integer id);
    public Integer findOrderCountByDate(String date);
    public Integer findOrderCountAfterDate(String date);
    public Integer findVisitsCountByDate(String date);
    public Integer findVisitsCountAfterDate(String date);
    public List<Map> findHotPackage();

    /**
     * 查询预约成功信息
     * @param id
     * @return
     */
    Map<String,Object> findById(int id);
}
