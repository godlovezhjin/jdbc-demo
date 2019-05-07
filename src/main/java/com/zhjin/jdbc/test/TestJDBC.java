package com.zhjin.jdbc.test;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestJDBC {

    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/zj_test";
    private String username = "root";
    private String password = "root";

    //1.注册驱动----高版本不需要手动注册,DriverManager的静态代码块中已经注册
    @Test
    public void testDriver() throws ClassNotFoundException {
        //--方式一：
        //Driver driver1 = new com.mysql.jdbc.Driver();

        //--方式二：
        //DriverManager.registerDriver(new com.mysql.jdbc.Driver());

        //--方式三：
        Class.forName(driver);
    }

    //2.创建连接
    @Test
    public void testConnection() throws SQLException {
        //--方式一：
        Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/zj_test?user=root&password=root");
        System.out.println("---conn1:---" + conn1);

        //--方式二：
        Connection conn2 = DriverManager.getConnection(url, username, password);
        System.out.println("---conn2:---" + conn2);
    }

    @Test
    public void testJdbc_Statement() throws Exception {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            //1.创建连接
            conn = DriverManager.getConnection(url, username, password);
            //2.创建Statement对象:
            st = conn.createStatement();
            //3.执行SQL语句：
            rs = st.executeQuery("SELECT * FROM t_test");
            //***ResultSet---API
            System.out.println("----元数据对象：----" + rs.getMetaData());
            System.out.println("----列数：----" + rs.getMetaData().getColumnCount());
            System.out.println("----列名(不支持别名,从1开始)：----" + rs.getMetaData().getColumnName(2));
            System.out.println("----别名(默认为列名,从1开始)：----" + rs.getMetaData().getColumnLabel(1));

            //4.处理结果
            Map<String, Object> resultMap = null;
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            while (rs.next()) {
                resultMap = new HashMap();
                resultMap.put("id", rs.getInt("id"));
                resultMap.put("name", rs.getString("name"));
                resultList.add(resultMap);
            }
            System.out.println(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.关闭资源
            rs.close();
            st.close();
            conn.close();
        }
    }

    @Test
    public void testJdbc_PreparedStatement() throws Exception {
        Connection conn = null;
        PreparedStatement prst = null;
        ResultSet rs = null;
        try {
            //1.创建连接
            conn = DriverManager.getConnection(url, username, password);
            //2.创建PreparedStatement对象:
            prst = conn.prepareStatement("SELECT * FROM t_test WHERE id > ?");
            prst.setInt(1, 2);
            //3.执行SQL语句：
            rs = prst.executeQuery();
            //4.处理结果
            Map<String, Object> resultMap = null;
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            while (rs.next()) {
                resultMap = new HashMap();
                resultMap.put("id", rs.getString("id"));
                resultMap.put("name", rs.getString("name"));
                resultList.add(resultMap);
            }
            System.out.println(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.关闭资源
            rs.close();
            prst.close();
            conn.close();
        }
    }

}
