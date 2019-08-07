package com.itheima.service;

import com.itheima.pojo.Package;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/23/20:32
 * @Description:
 */
public interface PackageService {

    void add(Package pkg, Integer[] checkgroupIds);

    List<Package> getPackage();

    Package getPackageDetail(int id);

    Package findById(int id);

    List<Map<String, Object>> getPackageReport();
}
