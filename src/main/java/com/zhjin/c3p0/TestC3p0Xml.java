package com.zhjin.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class TestC3p0Xml {

    public static void main(String[] args) throws SQLException {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        Connection conn = ds.getConnection();
        System.out.println("最大连接数：" + ds.getMaxPoolSize());
        System.out.println("连接：" + conn);
    }

}
