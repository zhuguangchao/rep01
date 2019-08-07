package com.itheima.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.dao.PackageDao;
import com.itheima.service.ReportService;
import com.itheima.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/08/04/20:56
 * @Description:
 */
@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PackageDao packageDao;

    /**
     * 获取预约数据统计
     * @return
     */
    @Override
    public Map<String, Object> getBusinessReportData() {
        //reportDate 统计的日期,今天
        String reportDate = DateUtils.parseDate2String(new Date(), "yyyy-MM-dd");
        //本周一
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday(),"yyyy-MM-dd");
        //本周日
        String thisWeekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek(),"yyyy-MM-dd");
        //本月一号
        String firstDayOfThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDayOfThisMonth(), "yyyy-MM-dd");
        //本月最后一号
        String lastDayOfThisMonth = DateUtils.parseDate2String(DateUtils.getLastDayOfThisMonth(), "yyyy-MM-dd");
        //todayNewMember :本日新增会员数
        Integer todayNewMember = memberDao.findMemberCountByDate(reportDate);
        //totalMember :总会员数
        Integer totalMember = memberDao.findMemberTotalCount();
        //thisWeekNewMember :本周新增会员数
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisWeekMonday);
        //thisMonthNewMember :本月新增会员数
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDayOfThisMonth);
        //todayOrderNumber :今日预约数
        Integer todayOrderNumber = orderDao.findOrderCountByDate(reportDate);
        //todayVisitsNumber :今日到诊数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(reportDate);
        //thisWeekOrderNumber :本周预约数
        Integer thisWeekOrderNumber = orderDao.findOrderCountAfterDate(thisWeekMonday);
        //thisWeekVisitsNumber :本周到诊数
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(thisWeekMonday);
        //thisMonthOrderNumber :本月预约数
        Integer thisMonthOrderNumber = orderDao.findOrderCountAfterDate(firstDayOfThisMonth);
        //thisMonthVisitsNumber :本月到诊数
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDayOfThisMonth);
        //hotPackage :热门套餐
        List<Map<String,Object>> hotPackage = packageDao.findHotPackage();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("reportDate",reportDate);
        resultMap.put("todayNewMember",todayNewMember);
        resultMap.put("totalMember",totalMember);
        resultMap.put("thisWeekNewMember",thisWeekNewMember);
        resultMap.put("thisMonthNewMember",thisMonthNewMember);
        resultMap.put("todayOrderNumber",todayOrderNumber);
        resultMap.put("todayVisitsNumber",todayVisitsNumber);
        resultMap.put("thisWeekOrderNumber",thisWeekOrderNumber);
        resultMap.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        resultMap.put("thisMonthOrderNumber",thisMonthOrderNumber);
        resultMap.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        resultMap.put("hotPackage",hotPackage);
        return resultMap;
    }
}
