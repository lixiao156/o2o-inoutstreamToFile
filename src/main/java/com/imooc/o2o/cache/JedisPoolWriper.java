package com.imooc.o2o.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 线线程池
 *
 * 强指定redis的JedisPool接口构造函数
 *
 *
 * @author lixw
 * @date created in 15:36 2019/1/31
 */
public class JedisPoolWriper {

    private JedisPool jedisPool;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 构造函数
     *
     * @param host
     * @param port
     */
    public JedisPoolWriper(final JedisPoolConfig poolConfig, final String host,
                           final int port) {
        try {
            jedisPool = new JedisPool(poolConfig, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
