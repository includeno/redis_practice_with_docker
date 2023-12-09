package base;

public class RedisPenetrateDemo {
    public static void main(String[] args) {
        MySQLRedisDemo demo = new MySQLRedisDemo();
        //用for循环模拟多次请求
        for(int cnt = 0;cnt<=100;cnt++){
            System.out.println("The " +cnt+" times of getting base.Student.");
            //每次请求的数据都不存在于Redis里
            demo.getStudentByID(10);
        }
    }
}

