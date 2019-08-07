package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.HealthException;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/20/15:56
 * @Description:
 */
public interface CheckItemService {
    /**
     * 添加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    void deleteById(int id) throws HealthException;

    CheckItem findById(int id);

    void update(CheckItem checkItem);

    List<CheckItem> findAll();

}
