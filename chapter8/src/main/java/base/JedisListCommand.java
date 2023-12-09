package base;

import base.config.JedisHelper;
import redis.clients.jedis.Jedis;

public class JedisListCommand {
    public static void main(String[] args) {
        //连接到Redis服务器
        Jedis jedis = JedisHelper.getJedis();
        //Student的规范：键是ID,值是Name,Age,Score
        jedis.rpush("001", "Peter");
        jedis.rpush("001", "15");
        jedis.rpush("001", "100");
        if(jedis.exists("001")){
            //从缓存中取，请注意是索引号是从0开始
            System.out.println(jedis.lindex("001",0)); //输出Peter
            System.out.println(jedis.lindex("001",1)); //输出15
            System.out.println(jedis.lindex("001",2)); //输出100
        }else{
            System.out.println("Not Exist in Cache, get from DB.");
        }
    }
}

