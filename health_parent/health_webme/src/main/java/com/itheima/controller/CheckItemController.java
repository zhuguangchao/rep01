package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.exception.HealthException;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/20/15:54
 * @Description:
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    /**
     * 添加检查项
     * @param checkItem
     * @return
     */
    @PostMapping("add.do")
    public Result add(@RequestBody CheckItem checkItem){
        checkItemService.add(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 查找检查项
     * @param queryPageBean
     * @return
     */
    @PostMapping("findPage.do")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS,pageResult);
    }

    /**
     * 删除检查项
     * @param id
     * @return
     */
    @PostMapping("deleteById.do")
    public Result deleteById(int id)  throws HealthException {
        checkItemService.deleteById(id);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 修改回显
     * @param id
     * @return
     */
    @GetMapping("findById.do")
    public Result findById(int id){
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS,checkItem);
    }

    /**
     * 编辑检查项
     * @param checkItem
     * @return
     */
    @PostMapping("update.do")
    public Result update(@RequestBody CheckItem checkItem){
        checkItemService.update(checkItem);
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    /**
     * 查询所有检查项
     * @return
     */
    @PostMapping("findAll.do")
    public Result findAll(){
        List<CheckItem> list = checkItemService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
    }

}
