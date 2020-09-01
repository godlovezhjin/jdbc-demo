package com.zhjin.dbcp;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

public class TestDbcpProperties {

    public static void main(String[] args) throws Exception {
        //1.读取配置文件的信息，把它封装到Properties的map对象中
        Properties pro = new Properties();
        pro.load(ClassLoader.getSystemResourceAsStream("dbcp.properties"));

        //2.建立数据库连接池
        DataSource ds = BasicDataSourceFactory.createDataSource(pro);

        //3.获取连接
        for (int i = 0; i < 50; i++) {
            Connection conn = ds.getConnection();
            System.out.println("-----第" + (i + 1) + "个连接:-----" + conn);
            Thread.sleep(500);
            conn.close(); //将连接归还连接池
        }

        //4.JDBC操作......
    }

}
