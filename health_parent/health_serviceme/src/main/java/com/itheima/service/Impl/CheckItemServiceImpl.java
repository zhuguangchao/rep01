package com.itheima.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.HealthException;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/20/15:57
 * @Description:
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 添加检查项
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 查询检查项
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //紧接着的查询会被分页
        Page<CheckItem> page = checkItemDao.findPage(queryPageBean.getQueryString());
        return new PageResult<CheckItem>(page.getTotal(), page.getResult());
    }

    /**
     * 删除检查项
     * @param id
     */
    @Override
    public void deleteById(int id) {
        int count = checkItemDao.findCountById(id);
        if (count > 0){
            throw new HealthException(MessageConstant.DELETE_CHECKITEM_FAIL_USED);
        }
        checkItemDao.deleteById(id);
    }

    /**
     * 编辑检查项之前的回显
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(int id) {
        CheckItem checkItem = checkItemDao.findById(id);
        return checkItem;
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> list = checkItemDao.findAll();
        return list;
    }
}
