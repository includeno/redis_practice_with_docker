package base;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
public class JedisPoolDemo {
    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10); //最大连接数是10
        config.setMaxIdle(5);   //最大空闲连接是5
        config.setMaxWaitMillis(100); //客户端最长等待时间是0.1秒
        config.setBlockWhenExhausted(false);//连接耗尽不阻塞
        //创建连接池
        JedisPool pool = new JedisPool(config,"127.0.0.1",6379,null,"redis123456");
        //从连接池里得到连接
        Jedis jedis = pool.getResource();
        jedis.set("testPool","OK");
        System.out.println(jedis.get("testPool"));
        pool.close(); //用完后释放连接池
    }
}

