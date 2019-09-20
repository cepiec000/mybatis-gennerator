package com.seven.gen.config;


import org.apache.ibatis.io.Resources;

import java.io.InputStream;
import java.util.Properties;

public class GenConfig {
    private static Properties pro = new Properties();

    static {
        try {
            InputStream reader = Resources.getResourceAsStream("application.properties");
            pro.load(reader);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAuthor() {
        return pro.getProperty("gen.author");
    }

    public static String getBeanSuffix() {
        return pro.getProperty("gen.bean.suffix");
    }

    public static String getPath() {
        return pro.getProperty("gen.path");
    }

    public static String getPackageBase() {
        return pro.getProperty("gen.package");
    }
    public static String getCorePackageBase(){
        return pro.getProperty("gen.core.package");
    }
}
