package base;

import base.config.JedisHelper;
import redis.clients.jedis.Jedis;
public class RedisMemDemo {
    public static void main(String[] args) {
        Jedis jedis = JedisHelper.getJedis();
         System.out.println(Runtime.getRuntime().freeMemory()/1024/1024 + "M");
        //通过for循环，频繁向Redis里缓存数据
        for(int i=0;i<500000;i++){
            //省略从MySQL得到对象的代码
            //拼装键
            String key = "Stu" + Integer.valueOf(i).toString();
            jedis.rpush(key,"name" + Integer.valueOf(i).toString() ,"18","100");
        }
        System.out.println(Runtime.getRuntime().freeMemory()/1024/1024 + "M");
    }
}

