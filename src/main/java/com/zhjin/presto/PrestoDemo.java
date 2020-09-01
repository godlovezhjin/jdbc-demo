package com.zhjin.presto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * presto jdbc
 */
public class PrestoDemo {

    private static final String driver = "com.facebook.presto.jdbc.PrestoDriver";
    private static final String url = "jdbc:presto://hmnode-2:8080/hive/tmp_bm";
    private static final String sql = "SELECT nschoolid, count(*) cut FROM cdc_bs_roster GROUP BY nschoolid";

    public static void main(String[] args) throws SQLException {
        long start = System.currentTimeMillis();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "root", null);

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            List<Map<String, Object>> result = new ArrayList<>();
            Map<String, Object> map = null;
            while (rs.next()) {
                map = new HashMap<>();
                map.put("nschoolid", rs.getString("nschoolid"));
                map.put("cut", rs.getInt("cut"));
                result.add(map);
            }
            System.out.println("========> result：" + result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }

        long end = System.currentTimeMillis();
        System.out.println("========> time：" + (end - start));
    }
}
