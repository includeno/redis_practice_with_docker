package base;

import base.config.JedisHelper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class JedisTransactionDemo {
    public static void main(String[] args) {
        //连接到Redis服务器
        Jedis jedis = JedisHelper.getJedis();
        //开启Redis事务
        Transaction t1 = jedis.multi();
        t1.set("UnSet1", "1");
        t1.set("UnSet2", "2");
        t1.discard(); //回滚事务
        System.out.println(jedis.keys("*"));//看不到UnSet1和UnSet2
        //再开启一个事务
        jedis.set("flag","true");
        jedis.watch("flag");
        Transaction t2 = jedis.multi();
        t2.set("key1", "1");
        t2.set("key2", "2");
        t2.exec();
        System.out.println(jedis.keys("*"));//能看到key1和key2
        jedis.unwatch();
    }
}

