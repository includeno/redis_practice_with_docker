package base;

import base.config.JedisHelper;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Random;

public class ExpireRandomDemo {


    public static void main(String[] args) {
        Jedis jedis = JedisHelper.getJedis();
        //清空缓存
        jedis.flushAll();
        Random rand = new Random();
        for(int i=0;i<5;i++)        {
            int fixedExpiredTime = 5;
            int randNum = rand.nextInt(6);
            String key = "Stu" + Integer.valueOf(i).toString();
            jedis.set(key,key);
            jedis.expire(key,fixedExpiredTime+randNum);
        }
        //sleep 6秒，模拟超时，并间隔一段时间
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i=0;i<5;i++)        {
            String key = "Stu" + Integer.valueOf(i).toString();
            if(jedis.exists(key)){
                System.out.println(key + " exists.");
            } else {
                System.out.println(key + " does not exist.");
            }
        }
    }
}

