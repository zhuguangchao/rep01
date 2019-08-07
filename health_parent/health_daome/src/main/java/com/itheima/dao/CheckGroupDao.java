package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/21/21:13
 * @Description:
 */
public interface CheckGroupDao {

    void add(CheckGroup checkGroup);

    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId,@Param("checkItemId") Integer checkItemId);

    Page<CheckGroup> findPage(String queryString);

    CheckGroup findById(int id);

    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    void update(CheckGroup checkGroup);

    void deleteByCheckGroupId(Integer id);

    List<CheckGroup> findAll();
}
