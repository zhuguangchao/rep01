package com.itheima.job;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/24/10:28
 * @Description:
 */

public class MyJob {
    public void abc(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date())+": 你好世界");
    }
    public static void main(String[] args) {
        ApplicationContext pc = new ClassPathXmlApplicationContext("classpath:spring-job.xml");
    }
}
