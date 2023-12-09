package base;

import java.sql.*;

public class MySQLDemo {

    public static void main(String[] args) {
        //MySQL的连接参数
        String mySQLDriver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/redisDemo";
        String user = "root";
        String pwd = "123456";

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //加载驱动
            Class.forName(mySQLDriver);
            //创建同数据库的连接
            conn = DriverManager.getConnection(url, user, pwd);
            ps = conn.prepareStatement("select * from student");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // 输出各字段的数据
                System.out.print("id: " + rs.getInt("id") + ",");
                System.out.print("name: " + rs.getString("name") + ",");
                System.out.print("age: " + rs.getInt("age") + ",");
                System.out.print("score: " + rs.getFloat("score"));
                System.out.print("\n");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

