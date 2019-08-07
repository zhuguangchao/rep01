package com.itheima.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.PackageDao;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/23/20:31
 * @Description:
 */
@Service(interfaceClass = PackageService.class)
public class PackageServiceImpl implements PackageService {
    @Autowired
    private PackageDao packageDao;

    /**
     * 添加套餐
     * @param pkg
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void add(Package pkg, Integer[] checkgroupIds) {
        packageDao.add(pkg);
        Integer pkgId = pkg.getId();
        for (Integer checkgroupId : checkgroupIds) {
            packageDao.addPackageAndCheckGroup(pkgId,checkgroupId);
        }
    }

    /**
     * 获取全部套餐信息
     * @return
     */
    @Override
    public List<Package> getPackage() {
        return packageDao.getPackage();
    }

    /**
     * 查询套餐详情(包括检查组,检查项)
     * @param id
     * @return
     */
    @Override
    public Package getPackageDetail(int id) {
        return packageDao.getPackageDetail(id);
    }

    /**
     * 查询预约页面套餐信息
     * @param id
     * @return
     */
    @Override
    public Package findById(int id) {
        return packageDao.findById(id);
    }

    /**
     * 获取套餐预约数据
     * @return
     */
    @Override
    public List<Map<String, Object>> getPackageReport() {
        return packageDao.getPackageReport();
    }
}
