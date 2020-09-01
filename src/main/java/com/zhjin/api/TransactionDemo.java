package com.zhjin.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * 默认的一次连接Connection(一个会话)
 * 默认的连接的属性,事务是自动提交,每执行一个Sql,都自动提交
 *
 * 在命令行：
 * 1、一次会话设置为手动提交：set autocommit=false;
 * 2、当次打开事务：start transaction;
 *
 * JDBC：
 * 事务的属性设置只一个连接对象的属性修改位置，Connection对象
 * Connection对象
 * 1) void setAutoCommit(boolean autoCommit)--如果为true就是自动提交,为false就是手动提交
 * 事务开始之前设置为false,事务结束之后还原为true,不影响其他的sql
 * 2)commit()
 * 3)rollback()
 *
 * 注意：
 * 1)一个事务的所有sql语句，必须在同一个connection对象中执行
 * 2)在关闭连接(连接池技术关闭连接其实只是把连接放回池中)之前,一定要还原Connection对象的setAutoCommit(true)
 */
public class TransactionDemo {

    public static void main(String[] args) throws Exception {

        String url = "jdbc:mysql://localhost:3306/zj_test";
        String username = "root";
        String password = "root";

        Connection conn = null;
        PreparedStatement prst1 = null;
        PreparedStatement prst2 = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false); //从该句代码后,手动提交或回滚事务

            String sql1 = "UPDATE t_test SET name = 'zhangsan' WHERE id = 2";
            String sql2 = "UPDATE t_test SET name = 'zhang3' WHERE id = 2";
            prst1 = conn.prepareStatement(sql1);
            prst2 = conn.prepareStatement(sql2);
            int r1 = prst1.executeUpdate();
            System.out.println("---r1---" + r1);
            int r2 = prst2.executeUpdate();
            System.out.println("---r2---" + r2);
            if (r1 > 0 && r2 > 0) {
                conn.commit();
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true); //从这句代码后,恢复自动提交
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                prst1.close();
                prst2.close();
                conn.close();
            }
        }
    }

}
