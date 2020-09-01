package com.zhjin.utils;

import com.zhjin.bean.User;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class TestUtil {

    @Test
    public void test() {
        String sql = "update t_test set name = ? where id = ?";
        int result = JdbcUtils.update(sql, "zhjin", 1);
        System.out.println(result);
    }

    @Test
    public void test_query() {
        // String sql = "select id, name as uname from t_test where id > ?";
        String sql = "select id, name as uname from t_test";
        List<User> list = JdbcUtils.queryForList(User.class, sql);
        for (User user : list) {
            System.out.println(user);
        }
    }

    @Test
    public void test_query_bean() {
        String sql = "select id, name as uname from t_test where id = ?";
        User user = JdbcUtils.queryForBean(User.class, sql, 1);
        System.out.println(user);
    }

    @Test
    public void test_query_one_value() {
        // String sql = "select count(*) from t_test where id > ?";
        String sql = "select name from t_test where id = ?";
        Object obj = JdbcUtils.queryForOneValue(sql, 3);
        System.out.println(obj);
    }

    @Test
    public void test_query_map() {
        // String sql = "select count(*) from t_test where id > ?";
        String sql = "select *,name as '姓名' from t_test where id > ?";
        List<Map<String, Object>> list = JdbcUtils.queryForListMap(sql, 1);
        System.out.println(list);
    }

}