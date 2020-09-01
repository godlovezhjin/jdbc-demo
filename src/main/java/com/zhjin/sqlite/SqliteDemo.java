package com.zhjin.sqlite;

import java.sql.*;
import java.util.*;

/**
 * sqlite 是一个嵌入式数据库,可以通过jdbc方式访问,无需启动服务,数据存储在本地文件系统中
 */
public class SqliteDemo {

    private static Connection conn;
    private static final String create_table_sql = "CREATE TABLE IF NOT EXISTS t_user (id INTEGER, NAME TEXT, PRIMARY KEY(id))";
    private static final String insert_sql = "INSERT INTO t_user VALUES(?, ?)";
    private static final String query_sql = "SELECT * FROM t_user";

    public static void main(String[] args) throws SQLException {
        getConnection();

        // boolean create_table_res = createTable();
        // System.out.println("====> create_table_res：" + create_table_res);

        // boolean insert_res = insertData();
        // System.out.println("====> insert_res：" + insert_res);

        List<Map<String, Object>> query_res = queryData();
        System.out.println("====> query_res：" + query_res);

        closeConn();
    }

    public static void getConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:data/my-sqlite.db");
            System.out.println("======> conn：" + conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConn() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean createTable() {
        try (Statement statement = conn.createStatement()) {
            return statement.execute(create_table_sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insertData() {
        try (PreparedStatement prst = conn.prepareStatement(insert_sql)) {
            for (int i = 1; i < 5; i++) {
                prst.setInt(1, 1000 + i);
                prst.setString(2, "zhjin" + i);
                prst.addBatch();
            }
            //执行批处理
            int[] res = prst.executeBatch();
            return !Arrays.asList(res).contains(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Map<String, Object>> queryData() {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try (Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery(query_sql);

            Map<String, Object> map = null;
            while (rs.next()) {
                map = new HashMap();
                map.put("id", rs.getInt("id"));
                map.put("name", rs.getString("name"));
                result.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
