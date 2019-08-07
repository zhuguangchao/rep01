package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/25/9:53
 * @Description:
 */
public interface OrderSettingDao {
    int findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting os);

    void add(OrderSetting os);

    List<OrderSetting> getOrderSettingByMonth(@Param("startDate") String startDate, @Param("endDate") String endDate);

    OrderSetting findByOrderDate(String orderDate);

    void editReservationsByOrderDate(@Param("i") int i, @Param("orderDate") String orderDate);

}
