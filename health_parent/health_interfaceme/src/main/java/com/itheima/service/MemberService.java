package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/29/8:57
 * @Description:
 */
public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);

    Map<String, Object> getMemberReport();




}
