package base;

import base.config.JedisHelper;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.util.List;

class Student{
    private int id;
    private String name;
    private int age;
    private float score;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public float getScore() {
        return score;
    }
}

public class MySQLRedisDemo {

    private Connection conn;
    private Jedis jedis;

    private void init(){
        String mySQLDriver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/redisDemo";
        try{
            Class.forName(mySQLDriver);
            conn = DriverManager.getConnection(url, "root", "123456");
            jedis = JedisHelper.getJedis();
        }catch (Exception e){
            System.out.println("Init error");
        }
    }

    public MySQLRedisDemo(){
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
                    return null;
                }
            } catch (Exception e ){
                e.printStackTrace();
            }
            finally{
                //省略关闭ps和conn的代码
            }
        }
        return null;
    }

    public static void main(String[] args) {
        MySQLRedisDemo demo = new MySQLRedisDemo();
        //通过for循环，两次次获取Student数据
        for(int i=0;i<2;i++){
            System.out.println("The " +i+" times of getting base.Student.");
            Student stu = demo.getStudentByID(1);
            if(stu != null){
                // 输出各字段的数据
                System.out.print("id: " + stu.getId() + ",");
                System.out.print("name: " + stu.getName() + ",");
                System.out.print("age: " + stu.getAge() + ",");
                System.out.println("score: " + stu.getScore());
            }
        }
    }
}

