package base;

import base.config.JedisHelper;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class RedisAvoidPenetrateDemo {

    private Connection conn;
    private Jedis jedis;

    private void init(){
        String mySQLDriver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/redisDemo";
        try{
            Class.forName(mySQLDriver);
            conn = DriverManager.getConnection(url, "root", "123456");
            jedis = JedisHelper.getJedis();
        }catch (Exception e){
            System.out.println("Init error");
        }
    }

    public RedisAvoidPenetrateDemo(){
        init();
    }

    public Student getStudentByID(int id){
        //拼装键
        String key = "Stu" + Integer.valueOf(id).toString();
        Student stu = new Student();
        //如果存在于Redis,先从Redis里获取
        if(jedis.exists(key)){
            System.out.println("ID:" + key + " exists in Redis.");
            List<String> list = jedis.lrange(key,0,2);
            stu.setAge(id);
            stu.setName(list.get(0));
            stu.setAge(Integer.valueOf(list.get(1)));
            stu.setScore((Float.valueOf(list.get(2))));
            return stu;
        } else{ //没在Redis找到，就到MySQL去找
            System.out.println("ID:" + key + " does not exist in Redis.");
            PreparedStatement ps = null;
            try{
                ps = conn.prepareStatement("select * from student where id = ?");
                ps.setInt(1,id);
                ResultSet rs = ps.executeQuery();
                //如果找到，返回数据，否则返回null
                if(rs.next()){
                    System.out.println("ID:" + key + " exists in MySQL.");
                    stu.setId(id);
                    String name = rs.getString("name");
                    stu.setName(name);
                    int age = rs.getInt("age");
                    stu.setAge(age);
                    float score = rs.getFloat("score");
                    stu.setScore(score);
                    //放入Redis
                    jedis.rpush(key,name,Integer.valueOf(age).toString(),Float.valueOf(score).toString());
                    return stu;
                }else{
                    System.out.println("ID:" + key + " does not exist in MySQL.");
                    jedis.rpush(key,"null","0","0");
                    return null;
                }
            } catch (Exception e ){
                e.printStackTrace();
            }
            finally{
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        RedisAvoidPenetrateDemo demo = new RedisAvoidPenetrateDemo();
        //通过for循环，多次获取不存在的Student数据
        for(int i=0;i<3;i++){
            Student stu = demo.getStudentByID(10);
            if(stu != null) {
                System.out.println(stu.getName());
            }
        }
    }
}

