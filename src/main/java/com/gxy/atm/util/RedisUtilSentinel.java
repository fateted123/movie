package com.gxy.atm.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RedisUtilSentinel {

    private static final JedisSentinelPool pool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMinIdle(5);

        Set<String> sentinels = new HashSet<>(Arrays.asList(
                "182.61.34.250:26379",
                "182.61.34.250:26380",
                "182.61.34.250:26381"
        ));

        pool = new JedisSentinelPool("mymaster", sentinels, jedisPoolConfig);
    }

    public static void main(String[] args) throws Exception {
        String key1 = "key2";
        try (Jedis jedis = pool.getResource()) {
            jedis.set(key1, "111");
            System.out.println(jedis.get(key1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
