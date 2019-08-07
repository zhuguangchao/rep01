package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/20/16:05
 * @Description:
 */
public interface CheckItemDao {
    /**
     * 添加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询检查项
     * @param queryString
     * @return
     */
    Page<CheckItem> findPage(String queryString);

    /**
     * 查询检查项id是否被检查组占用
     * @param id
     * @return
     */
    int findCountById(int id);

    /**
     * 删除检查项
     * @param id
     */
    void deleteById(int id);

    /**
     * 编辑检查项之前的回显
     * @param id
     * @return
     */
    CheckItem findById(int id);

    /**
     * 编辑检查项
     * @param checkItem
     */
    void update(CheckItem checkItem);

    /**
     * 查询所有检查项
     * @return
     */
    List<CheckItem> findAll();
}
