package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import com.itheima.util.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/23/20:29
 * @Description:
 */
@RestController
@RequestMapping("/package")
public class PackageController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private PackageService packageService;

    /**
     * 下载图片到七牛云,将地址 图片名 返回给前端回显和存到数据库,将文件名保存到redis,后面定期删除
     * @param imgFile
     * @return
     */
    @PostMapping("upload.do")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        //原文件名
        String originalFilename = imgFile.getOriginalFilename();
        //后缀名
        String extensionName = originalFilename.substring(originalFilename.lastIndexOf("."));
        //产生唯一的文件名
        UUID uuid = UUID.randomUUID();
        //产生新的名字
        String fileName = uuid + extensionName;
        try {
            //上传到七牛
            QiNiuUtil.uploadViaByte(imgFile.getBytes(), fileName);
            //保存到redis
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            //将文件传回前端
            Map<String,String> hm = new HashMap();
            //传域名
            hm.put("domain",QiNiuUtil.DOMAIN);
            //传文件名
            hm.put("imgName",fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, hm);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }
    @PostMapping("add.do")
    public Result add(@RequestBody Package pkg, Integer[] checkgroupIds){
        packageService.add(pkg,checkgroupIds);
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, pkg.getImg());
        return new Result(true, MessageConstant.ADD_PACKAGE_SUCCESS);
    }


}
