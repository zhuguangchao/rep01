package com.itheima.controller;

import com.itheima.entity.Result;
import com.itheima.exception.HealthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/21/11:11
 * @Description:
 */
@ControllerAdvice//切面
public class GlobalExceptionHandler {
    // 记录日志, logback.xml
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HealthException.class)
    @ResponseBody
    public Result handlerHealthException(HealthException e){
        log.error("验证失败",e);
        return new Result(false,e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result handlerRuntimeException(RuntimeException e){
        log.error("RuntimeException",e);
        //e.printStackTrace();// outputstream findBug
        return new Result(false,e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handlerException(Exception e){
        log.error("出错了",e);
        return new Result(false,"出错了，请联系管理员");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result handlerAccessDeniedException(AccessDeniedException e){
        log.error("权限不足",e);
        return new Result(false,"权限不足");
    }
}
