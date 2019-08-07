package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import com.itheima.util.QiNiuUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/26/10:08
 * @Description:
 */
@RestController
@RequestMapping("/package")
public class PackageController {
    @Reference
    private PackageService packageService;

    /**
     * 获取套餐信息
     * @return
     */
    @PostMapping("/getPackage")
    public Result getPackage(){
        List<Package> list = packageService.getPackage();
        /*for (Package pkg : list) {
            pkg.setImg(QiNiuUtil.DOMAIN+"/"+pkg.getImg());
        }*/
        list.forEach( pkg -> {
            pkg.setImg(QiNiuUtil.DOMAIN+"/"+pkg.getImg());
        });
        return new Result(true, MessageConstant.GET_PACKAGE_COUNT_REPORT_SUCCESS,list);
    }

    /**
     * 查询套餐详情(包括检查组,检查项)
     * @param id
     * @return
     */
    @GetMapping("/getPackageDetail")
    public Result getPackageDetail(int id){
        Package pkg = packageService.getPackageDetail(id);
        pkg.setImg( QiNiuUtil.DOMAIN + "/" + pkg.getImg());
        return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS,pkg);
    }

    /**
     * 查询预约页面套餐信息
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        Package pkg = packageService.findById(id);
        pkg.setImg(QiNiuUtil.DOMAIN+"/"+pkg.getImg());
        return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS,pkg);
    }
}
