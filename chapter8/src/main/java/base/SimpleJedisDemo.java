package base;

import base.config.JedisHelper;
import redis.clients.jedis.Jedis;
public class SimpleJedisDemo {
    public static void main(String[] args) {
        //连接到Redis服务器
        Jedis jedis = JedisHelper.getJedis();
        //测试连接
        System.out.println(jedis.ping());//输出PONG
        //存取字符串类型的数据
        //输出OK
        System.out.println(jedis.set("name", "Peter"));
        System.out.println(jedis.get("name"));//输出Peter
        System.out.println(jedis.get("notExistKey"));//输出null
    }
}

