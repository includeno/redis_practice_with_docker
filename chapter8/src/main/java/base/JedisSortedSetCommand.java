package base;

import base.config.JedisHelper;
import redis.clients.jedis.Jedis;

public class JedisSortedSetCommand {
    public static void main(String[] args) {
        //连接到Redis服务器
        Jedis jedis = JedisHelper.getJedis();
        //用zadd方法向有序集合里添加元素
        jedis.zadd("emps",1.0,"Peter");
        jedis.zadd("emps",2.0,"Tom");
        jedis.zadd("emps",3.0,"John");
        jedis.zadd("emps",4.0,"Mike");
        //如下输出[Peter, Tom, John]
        System.out.println(jedis.zrange("emps",(long)0.5,(long)2.5));
        //如下输出[[Peter,1.0], [Tom,2.0], [John,3.0]]
        System.out.println(jedis.zrangeWithScores("emps",(long)0.5,(long)2.5));
    }
}

