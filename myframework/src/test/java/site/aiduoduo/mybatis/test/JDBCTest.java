package site.aiduoduo.mybatis.test;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCTest {
    @Test
    public void test() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        // 注意:使用JDBC规范，采用都是 java.sql包下的内容

        try {
            // 1 注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 2 获得连接
            String url = "jdbc:mysql://rm-bp186l387tovm7nm1ao.mysql.rds.aliyuncs.com:3306/yth";
            conn = DriverManager.getConnection(url, "xiaoy1995", "Xiaoy9502");
            // 3 获取sql语句
            String sql = "select * from user where name = ?";
            // 4 获取预处理 statement
            stmt = conn.prepareStatement(sql);
            // 5 设置参数，序号从1开始
            stmt.setString(1, "刘备");
            // 6 执行SQL语句
            rs = stmt.executeQuery();
            // 7 处理结果集
            while (rs.next()) {
                // 获得一行数据
                System.out.println(rs.getString("name") + ", " + rs.getString("gender") + "," + rs.getString("phone")
                    + "," + rs.getString("address"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 8释放资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
