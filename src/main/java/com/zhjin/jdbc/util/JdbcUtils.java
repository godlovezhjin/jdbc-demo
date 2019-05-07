package com.zhjin.jdbc.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtils {

    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/zj_test";
    private static String username = "root";
    private static String password = "root";

    //获取连接
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    //获取连接--C3P0
    public static Connection getConnectionByC3p0() {
        Connection conn = null;
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();
            conn = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    //释放连接
    public static void free(Connection conn, Statement st, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //释放连接
    public static void free(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //释放连接
    public static void free(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //释放连接
    public static void free(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //增删改方法
    public static int update(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement prst = null;
        try {
            conn = getConnection();
            prst = conn.prepareStatement(sql);
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    prst.setObject(i + 1, args[i]);
                }
            }
            return prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            free(conn, prst, null);
        }
        return 0;
    }

    //查询方法--返回集合
    public static <T> List<T> queryForList(Class<T> clazz, String sql, Object... args) {
        Connection conn = null;
        PreparedStatement prst = null;
        ResultSet rs = null;
        List<T> resultList = new ArrayList<T>();
        try {
            conn = getConnection();
            prst = conn.prepareStatement(sql);
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    prst.setObject(i + 1, args[i]);
                }
            }
            rs = prst.executeQuery();
            //将结果集封装到JavaBean中
            ResultSetMetaData metaData = rs.getMetaData(); //元数据
            int count = metaData.getColumnCount(); //列数
            while (rs.next()) {
                //反射创建对象
                T bean = clazz.newInstance();
                //将查询结果封装给对象
                for (int i = 0; i < count; i++) {
                    String fieldName = metaData.getColumnLabel(i + 1);//别名
                    Object columnValue = rs.getObject(i + 1);//查询数据
                    //将查询结果封装给bean
                    Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);//默认不能设置,取消安全检查
                    field.set(bean, columnValue);
                }
                //将对象添加到集合
                resultList.add(bean);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            free(conn, prst, rs);
        }
        return null;
    }

    //查询方法--返回单个对象
    public static <T> T queryForBean(Class<T> clazz, String sql, Object... args) {
        return queryForList(clazz, sql, args).get(0);
    }

    //查询方法--返回单个值(count/max/min)
    public static Object queryForOneValue(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement prst = null;
        ResultSet rs = null;
        Object resultValue = null;
        try {
            conn = getConnection();
            prst = conn.prepareStatement(sql);
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    prst.setObject(i + 1, args[i]);
                }
            }
            rs = prst.executeQuery();
            while (rs.next()) {
                resultValue = rs.getObject(1);
            }
            return resultValue;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            free(conn, prst, rs);
        }
        return null;
    }

    //查询方法--返回List<Map>
    public static List<Map<String, Object>> queryForListMap(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement prst = null;
        ResultSet rs = null;
        Map<String, Object> resultMap = new HashMap();
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        try {
            conn = getConnection();
            prst = conn.prepareStatement(sql);
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    prst.setObject(i + 1, args[i]);
                }
            }
            rs = prst.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData(); //元数据
            int count = metaData.getColumnCount(); //列数
            while (rs.next()) {
                for (int i = 0; i < count; i++) {
                    String key = metaData.getColumnLabel(i + 1);
                    Object value = rs.getObject(i + 1);
                    resultMap.put(key, value);
                }
                resultList.add(resultMap);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            free(conn, prst, rs);
        }
        return null;
    }

}
