package com.itheima.dao;

import com.itheima.pojo.Package;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/23/20:32
 * @Description:
 */
public interface PackageDao {

    void add(Package pkg);

    void addPackageAndCheckGroup(@Param("pkgId") Integer pkgId, @Param("checkgroupId") Integer checkgroupId);

    List<Package> getPackage();

    Package getPackageDetail(int id);

    Package findById(int id);

    List<Map<String, Object>> getPackageReport();

    List<Map<String, Object>> findHotPackage();
}
