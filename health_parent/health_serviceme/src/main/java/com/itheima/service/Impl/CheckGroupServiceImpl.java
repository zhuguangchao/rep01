package com.itheima.service.Impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/21/20:04
 * @Description:
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        if (checkitemIds != null) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroupId,checkitemId);
            }
        }
    }

    /**
     * 分页查询检查组
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckGroup> page = checkGroupDao.findPage(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(page.getTotal(), page.getResult());
    }

    @Override
    public CheckGroup findById(int id) {
        CheckGroup checkGroup = checkGroupDao.findById(id);
        return checkGroup;
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id) {
        List<Integer> checkitemIds = checkGroupDao.findCheckItemIdsByCheckGroupId(id);
        return checkitemIds;
    }

    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.update(checkGroup);
        checkGroupDao.deleteByCheckGroupId(checkGroup.getId());
        //int i = 10/0;
        for (Integer checkitemId : checkitemIds) {
            checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(),checkitemId);
        }

    }

    @Override
    public List<CheckGroup> findAll() {
        List<CheckGroup> list = checkGroupDao.findAll();
        return list;
    }
}
