package com.itheima.job;

import com.itheima.constant.RedisConstant;
import com.itheima.util.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/24/21:04
 * @Description:
 */
@Component("cleanImgJob")
public class CleanImgJob {
    @Autowired
    private JedisPool jedisPool;
    public void doJob(){
        Jedis jedis = jedisPool.getResource();
        //获取要删除的图片的名称
        Set<String> needToDelete = jedis.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //七牛云上删除图片
        String[] needToDeleteArr = needToDelete.toArray(new String[]{});
        QiNiuUtil.removeFiles(needToDeleteArr);
        //删除redis中多余的图片名称
        //jedis.srem(RedisConstant.SETMEAL_PIC_RESOURCES, needToDeleteArr);
        jedis.del(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);
    }
}
