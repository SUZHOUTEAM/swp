package com.mlsc.waste.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyUtil extends PropertyPlaceholderConfigurer {
    public static final String IM_APP_ENV = "IM_APP_ENV";
    public static final String IM_APP_ENV_TEST = "IM_APP_ENV_TEST";
    public static final String IM_APP_ENV_ONLINE = "IM_APP_ENV_ONLINE";
    public static final String IM_ENV_TEST_KEY = "test";
    public static final String IM_ENV_ONLINE_KEY = "online";

    private static Logger logger = Logger.getLogger(PropertyUtil.class);
    private static Map<String, String> keyValueMap ;
    private static Map<String, String> valueKeyMap ;
    private static JSONObject imEnvJson;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        try {
            super.processProperties(beanFactoryToProcess, props);
            keyValueMap = new HashMap<String, String>(props.size());
            valueKeyMap = new HashMap<String, String>(props.size());
            for (Object key : props.keySet()) {
                String keyStr = key.toString();
                String value = props.getProperty(keyStr);
                keyValueMap.put(keyStr, value);
                valueKeyMap.put(value, keyStr);
            }
            if (keyValueMap.containsKey(IM_APP_ENV)) {
                if (keyValueMap.get(IM_APP_ENV).toLowerCase().startsWith(IM_ENV_TEST_KEY)) {
                    imEnvJson = JSONObject.parseObject(keyValueMap.get(IM_APP_ENV_TEST));
                }
                if (keyValueMap.get(IM_APP_ENV).toLowerCase().startsWith(IM_ENV_ONLINE_KEY)) {
                    imEnvJson = JSONObject.parseObject(keyValueMap.get(IM_APP_ENV_ONLINE));
                }
            }
        } catch (BeansException e) {
            logger.error("processProperties",e);
        }
    }
    public static String getValue(String key) {
        return keyValueMap.get(key);
    }

    public static void updateData(String key, String value) {
        keyValueMap.put(key, value);
        valueKeyMap.put(value, key);
    }

    public static String getKey(String value) {
        return valueKeyMap.get(value);
    }

    public static JSONObject getImEnv() {
        return imEnvJson;
    }
}
