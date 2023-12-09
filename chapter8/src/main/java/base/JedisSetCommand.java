package base;

import base.config.JedisHelper;
import redis.clients.jedis.Jedis;

public class JedisSetCommand {
    public static void main(String[] args) {
        //连接到Redis服务器
        Jedis jedis = JedisHelper.getJedis();
        //用sadd方法向集合里添加元素
        jedis.sadd("bonusID","1","2","3","1");
        //如下返回[1, 2, 3]
        System.out.println(jedis.smembers("bonusID"));

    }
}

