package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.stereotype.Controller;
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
 * @Date: 2019/07/21/20:01
 * @Description:
 */
@RequestMapping("/group")
@RestController
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PostMapping("add.do")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        checkGroupService.add(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 分页查询检查组
     * @param queryPageBean
     * @return
     */
    @PostMapping("findPage.do")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    /**
     * 根据id查询检查组
     * @param id
     * @return
     */
    @GetMapping("findById.do")
    public Result findById(int id){
        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    /**
     * 根据id查询检查组的所有检查项id
     * @param id
     * @return
     */
    @GetMapping("findCheckItemIdsByCheckGroupId.do")
    public Result findCheckItemIdsByCheckGroupId(int id){
        List<Integer> checkitemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkitemIds);
    }
    @PostMapping("update.do")
    public Result update(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        checkGroupService.update(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
    @GetMapping("findAll.do")
    public Result findAll(){
        List<CheckGroup> list = checkGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }
}
