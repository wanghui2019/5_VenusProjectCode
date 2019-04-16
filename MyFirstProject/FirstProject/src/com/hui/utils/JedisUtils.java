package com.hui.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {
    //创建redis数据库链接池
    private static JedisPoolConfig config;
    private static JedisPool pool;

    static {
        config=new JedisPoolConfig();
        config.setMaxTotal(30);
        config.setMaxIdle(2);

        pool=new JedisPool(config,"192.168.31.101",6379);
    }

    //获取链接的方法
    public static Jedis getJedis(){
        return pool.getResource();
    }

    //释放资源
    public static void closeJedis(Jedis j){
        j.close();;
    }
}
