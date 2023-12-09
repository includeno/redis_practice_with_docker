package base;

import base.config.JedisHelper;
import redis.clients.jedis.Jedis;
        import redis.clients.jedis.Pipeline;

public class PipelineDemo {
    public static void main(String[] args){

        Jedis jedis = JedisHelper.getJedis();
        long startTime = System.currentTimeMillis();
        for (int cnt = 0; cnt < 10000; cnt++) {
            jedis.set("key" + cnt, String.valueOf(cnt));
            jedis.get("key" + cnt);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time cost by Non Pipeline:" + (endTime - startTime));

        startTime = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();
        for (int cnt = 0; cnt < 10000; cnt++) {
            pipeline.set("key" + cnt, String.valueOf(cnt));
            pipeline.get("key" + cnt);
        }
        pipeline.sync();
        endTime = System.currentTimeMillis();
        System.out.println("Time cost by Non Pipeline:" + (endTime - startTime));
    }
}
