package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.pojo.Package;
import com.itheima.service.OrderSettingService;
import com.itheima.service.PackageService;
import com.itheima.util.POIUtils;
import com.itheima.util.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 下载模板
     * @param excelFile
     * @return
     */
    @PostMapping("/upload")
    public Result update(@RequestParam("excelFile") MultipartFile excelFile){
        //解析excel
        try {
            //strings代表没一个excel文件,String[]代表每一行
            List<String[]> strings = POIUtils.readExcel(excelFile);
            //将List<String[]>转成List<ordersetting>
            OrderSetting os = null;
            ArrayList<OrderSetting> al = new ArrayList();
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            for (String[] string : strings) {
                //每一行存到一个os对象
                os = new OrderSetting(sdf.parse(string[0]),Integer.valueOf(string[1]));
                al.add(os);
            }
            orderSettingService.doImport(al);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
    }

    /**
     * 按月查询预约人数
     * @param month
     * @return
     */
    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month){
        List<OrderSetting> list = orderSettingService.getOrderSettingByMonth(month);
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,list);
    }

    /**
     * 设置总预约人数
     * @param orderDate
     * @param number
     * @return
     */
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(String orderDate,int number){
        try {
            orderSettingService.editNumberByDate(orderDate,number);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }


}
