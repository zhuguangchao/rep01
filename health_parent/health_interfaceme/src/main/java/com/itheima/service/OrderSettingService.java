package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/25/9:52
 * @Description:
 */
public interface OrderSettingService {

    void doImport(ArrayList<OrderSetting> al);

    List<OrderSetting> getOrderSettingByMonth(String month);

    void editNumberByDate(String orderDate, int number) throws ParseException;
}
