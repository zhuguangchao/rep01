package com.itheima.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/25/9:52
 * @Description:
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    /**
     *批量导入预约设置
     * @param al
     */
    @Override
    @Transactional
    public void doImport(ArrayList<OrderSetting> al) {
        if (null != al){
            for (OrderSetting os : al) {
                //查询是否设置了预约数量
                int count = orderSettingDao.findCountByOrderDate(os.getOrderDate());
                if (count > 0) {
                    //设置了预约数量就更新
                    orderSettingDao.editNumberByOrderDate(os);
                }else{
                    //没有设置就增加
                    orderSettingDao.add(os);
                }
            }
        }
    }

    /**
     * 获取当月预约信息
     * @param month
     * @return
     */
    @Override
    public List<OrderSetting> getOrderSettingByMonth(String month) {
        String startDate = month + "-01";
        String endDate = month + "-31";
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(startDate,endDate);
        return list;
    }

    /**
     * 设置预约人数
     * @param orderDate
     * @param number
     * @throws ParseException
     */
    @Override
    @Transactional
    public void editNumberByDate(String orderDate, int number) throws ParseException {
        //System.out.println(orderDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        OrderSetting os = new OrderSetting(sdf.parse(orderDate), number);
        //查询是否设置了预约数量
        int count = orderSettingDao.findCountByOrderDate(os.getOrderDate());
        if (count > 0) {
            //设置了预约数量就更新
            orderSettingDao.editNumberByOrderDate(os);
        }else{
            //没有设置就增加
            orderSettingDao.add(os);
        }
    }
}
