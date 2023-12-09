package base.config;

import redis.clients.jedis.*;

public class JedisHelper {
    private static final JedisPool pool = new JedisPool("127.0.0.1", 6379, null, "redis123456");

    //https://redis.io/docs/connect/clients/java/
    public static Jedis getJedis() {
        return pool.getResource();
    }

    private JedisHelper() {
    }
}
