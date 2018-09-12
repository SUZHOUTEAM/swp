package com.mlsc.common.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 邮件发送工具类
 */
public class MailConfig {
    private static Logger logger = Logger.getLogger(MailConfig.class);

    private static final String PROPERTIES_DEFAULT = "mailConfig.properties";
    public static String host;
    public static Integer port;
    public static String userName;
    public static String passWord;
    public static String emailForm;
    public static String timeout;
    public static String personal;
    public static Properties properties;

    static {
        init();
    }

    /**
     * 初始化
     */
    private static void init() {
        properties = new Properties();
        try {
            InputStream inputStream = MailConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_DEFAULT);
            properties.load(inputStream);
            inputStream.close();
            host = properties.getProperty("mailHost");
            port = Integer.parseInt(properties.getProperty("mailPort"));
            userName = properties.getProperty("mailUsername");
            passWord = properties.getProperty("mailPassword");
            emailForm = properties.getProperty("mailFrom");
            timeout = properties.getProperty("mailTimeout");
            personal = "test";
        } catch (IOException e) {
            logger.error("MailConfig initial error", e);
        }
    }
}
