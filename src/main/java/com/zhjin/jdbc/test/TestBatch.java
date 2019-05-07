package com.zhjin.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestBatch {

    public static void main(String[] args) throws Exception {

        String url = "jdbc:mysql://localhost:3306/zj_test?rewriteBatchedStatements=true";
        String username = "root";
        String password = "root";

        Connection conn = null;
        PreparedStatement prst = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            prst = conn.prepareStatement("insert into t_test values (?, ?)");
            for (int i = 10; i < 12; i++) {
                prst.setInt(1, i);
                prst.setString(2, String.valueOf(i));
                prst.addBatch(); //添加到批处理中
            }
            prst.executeBatch();//执行批处理
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            prst.close();
        }
    }

}
