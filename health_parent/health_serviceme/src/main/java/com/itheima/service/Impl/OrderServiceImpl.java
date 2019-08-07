package com.itheima.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.exception.HealthException;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import com.itheima.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/27/16:20
 * @Description:
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    @Transactional
    public int addOrder(Map<String, String> orderInfo) {
        String orderDate = orderInfo.get("orderDate");
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
        //1.查询dao是否可以预约:
            //1.1查询t_ordersetting的reservations为null不可预约,报错
        if (null == orderSetting) {
            throw new HealthException(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
            //1.2查询t_ordersetting的numberd小于reservations时不能预约,已满
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            throw new HealthException(MessageConstant.ORDER_FULL);
        }
        //2.查询是否为会员通过电话号码,
        String telephone = orderInfo.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        if (null == member) {
            //2.2不是,插入t_number取出id
            member = new Member();
            member.setName(orderInfo.get("name"));
            member.setSex(orderInfo.get("sex"));
            member.setPhoneNumber(telephone);
            member.setIdCard(orderInfo.get("idCard"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }
            // 2.1是,取出id
        Integer memberId = member.getId();
        //3.是否重复预约,通过会员编号,日期查询存在则报错,不存在则插入t_order
        Order order = new Order();
        order.setMemberId(memberId);
        try {
            order.setOrderDate(DateUtils.parseString2Date(orderDate));
        } catch (Exception e) {
            e.printStackTrace();
            throw new HealthException(MessageConstant.ORDER_FAIL);
        }
        List<Order> orders =  orderDao.findByCondition(order);
        if (null != orders && orders.size() > 0){
            throw new HealthException(MessageConstant.HAS_ORDERED);
        }
        order.setOrderStatus(order.ORDERSTATUS_NO);
        order.setOrderType(order.ORDERTYPE_WEIXIN);
        order.setPackageId(Integer.valueOf(orderInfo.get("packageId")));
        orderDao.add(order);
        //4.预约成功修改已预约人数加1
        orderSettingDao.editReservationsByOrderDate(1,orderDate);
        return order.getId();
    }

    @Override
    public Map<String, Object> findById(int id) {
        return orderDao.findById(id);
    }
}
