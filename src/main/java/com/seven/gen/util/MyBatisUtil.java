package com.seven.gen.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

public class MyBatisUtil {
    private static final String configFile = "mybatis-config.xml";

    /**
     * 创建连接
     */
    public static SqlSession getSession() {
        SqlSession session = null;
        try {
            ClassLoader classLoader = MyBatisUtil.class.getClassLoader();
            Reader reader = Resources.getResourceAsReader(configFile);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
            session = factory.openSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }

    public static void closeSession(SqlSession session) {
        session.close();
    }

    public static void main(String[] args) {
        SqlSession session = MyBatisUtil.getSession();
        System.out.println(session);
        session.close();
    }
}
