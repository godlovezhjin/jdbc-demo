package com.zhjin.kylin;

import java.sql.*;

/**
 * kylin jdbc
 */
public class KylinDemo {

    private static final String driver = "org.apache.kylin.jdbc.Driver";
    private static final String url = "jdbc:kylin://hadoop102:7070/my_project";
    private static final String username = "ADMIN";
    private static final String password = "KYLIN";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //注册驱动
        Class.forName(driver);

        //获取连接
        Connection conn = DriverManager.getConnection(url, username, password);

        //发送sql
        String sql = "SELECT sum(sal) FROM emp GROUP BY deptno";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        //解析结果
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1));
        }

        //关闭资源
        resultSet.close();
        statement.close();
        conn.close();
    }
}
