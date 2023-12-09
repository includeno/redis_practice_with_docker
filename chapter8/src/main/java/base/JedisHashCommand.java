package base;

import base.config.JedisHelper;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class JedisHashCommand {
    public static void main(String[] args) {
        //连接到Redis服务器
        Jedis jedis = JedisHelper.getJedis();
        Map<String, String> empHM = new HashMap<String,String>();
        empHM.put("Name","Mike");
        empHM.put("Team","CloudTeam");
        empHM.put("Salary","15000");
        empHM.put("Level","Senior");

        //用HashMap的形式直接插入Hash数据
        jedis.hset("Emp001", empHM);
        //用键值对的方式插入Hash数据
        jedis.hset("Emp002","Name","John");
        jedis.hset("Emp002","Team","HR");
        jedis.hset("Emp002","Salary","15000");
        jedis.hset("Emp002","Level","Senior");

        System.out.println(jedis.hget("Emp001","Name"));//Mike
        System.out.println(jedis.hget("Emp002","Team"));//HR
    }
}

