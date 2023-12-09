package base;

import base.config.JedisHelper;
import redis.clients.jedis.Jedis;

public class JedisOtherCommand {
    public static void main(String[] args) {
        //连接到Redis服务器
        Jedis jedis = JedisHelper.getJedis();
        //设置若干键
        jedis.set("name", "Peter");
        jedis.set("age", "18");
        jedis.set("salary", "10000");
        System.out.println(jedis.exists("name"));//输出true
        System.out.println(jedis.exists("notExistKey"));//输出false
        //如下语句输出[name, salary, age]
        System.out.println(jedis.keys("*a*"));
        System.out.println(jedis.del("name")); //输出1
        //如下语句输出[age, salary]
        System.out.println(jedis.keys("*a*"));
    }
}

