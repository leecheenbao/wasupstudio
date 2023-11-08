package com.wasupstudio.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertieUtils {

    public static Properties getProperties(String url){
        Properties properties = new Properties();
        try {
            // 使用 ClassLoader 加載 application.properties
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream(url);
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    public static String getProperties(String url, String propertiesName){
        Properties properties = new Properties();
        String verificationCode = "";
        try {
            // 使用 ClassLoader 加載 application.properties
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream(url);
            properties.load(input);
            verificationCode = properties.getProperty(propertiesName);
            System.out.println("Verification Code: " + verificationCode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return verificationCode;
    }

}
