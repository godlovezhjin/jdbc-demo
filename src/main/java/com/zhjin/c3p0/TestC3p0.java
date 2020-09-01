package com.zhjin.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;

public class TestC3p0 {

    public static void main(String[] args) throws Exception {

        //1.先建立连接池
        ComboPooledDataSource ds = new ComboPooledDataSource();

        //2.配置--基本属性
        ds.setDriverClass("com.mysql.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/zj_test");
        ds.setUser("root");
        ds.setPassword("root");

        //3.配置--可选属性
        ds.setInitialPoolSize(100);//初始化连接数
        ds.setMaxPoolSize(1000);//最多连接数
        ds.setMinPoolSize(50);//空闲时最小连接数
        ds.setAcquireIncrement(50);//每次增加的连接数

        //4.获取连接
        Connection conn = ds.getConnection();
        System.out.println("-----conn:----" + conn);

        //5.JDBC操作......
    }

}
