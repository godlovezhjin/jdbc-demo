package com.zhjin.dbcp;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDbcp {

    public static void main(String[] args) throws SQLException {

        //1.先建立连接池
        BasicDataSource ds = new BasicDataSource();

        //2.配置--必要属性
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/zj_test");
        ds.setUsername("root");
        ds.setPassword("root");

        //3.配置---非必要属性
        ds.setInitialSize(10); //初始化的连接数(0)
        ds.setMaxActive(20); //最大连接数(0)
        ds.setMinIdle(5); //空闲时最小连接数(0)
        ds.setMaxWait(1000); //最长等待时间(-L)

        //4.获取连接
        Connection conn = ds.getConnection();
        System.out.println("-----conn:----" + conn);

        //5.JDBC操作......
    }

}
