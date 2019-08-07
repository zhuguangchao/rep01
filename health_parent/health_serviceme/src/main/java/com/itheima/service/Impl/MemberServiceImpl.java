package com.itheima.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/29/8:57
 * @Description:
 */
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;
    /**
     * 通过手机号码查询会员是否存在
     * @param telephone
     * @return
     */
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    /**
     * 添加会员
     * @param member
     */
    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    @Override
    public Map<String, Object> getMemberReport() {
        //回去日历子类对象 默认当前系统时间
        Calendar calendar = Calendar.getInstance();
        //1.得到去年
        calendar.add(calendar.YEAR,-1);
        //2.遍历十二个月得到每个月数据
        List<String> months = new ArrayList();
        List<Integer> memberCount = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String month = "";
        for (int i = 0; i < 12; i++) {
            calendar.add(calendar.MONTH, 1);
            month = sdf.format(calendar.getTime());
            months.add(month);
            memberCount.add(memberDao.findMemberCountBeforeDate(month + "-31"));
        }
        Map<String,Object> hm = new HashMap();
        hm.put("months",months);
        hm.put("memberCount",memberCount);
        return hm;
    }




}
