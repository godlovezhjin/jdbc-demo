package com.zhjin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {

    public static String getProperty(String property, String key) {
        Properties properties = new Properties();
        ClassLoader classLoader = PropertyUtils.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(property);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}
