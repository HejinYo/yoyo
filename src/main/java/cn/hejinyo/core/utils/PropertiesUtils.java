package cn.hejinyo.core.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PropertiesUtils {
    private static final String DEFAULT_CONFIG_FILE = "properties/application.properties";
    private static Map<String, Properties> loaderMap = new HashMap<String, Properties>();
    private static ConcurrentMap<String, String> configMap = new ConcurrentHashMap<String, String>();
    private static Properties prop = null;

    /**
     * 获得value值
     *
     * @param key
     * @param isCache
     * @return
     */
    public static String getStringByKey(String key, Boolean isCache) {
        return getStringByKey(key, DEFAULT_CONFIG_FILE, isCache);
    }

    /**
     * 获得value值
     *
     * @param key
     * @return
     */
    public static String getStringByKey(String key) {
        return getStringByKey(key, DEFAULT_CONFIG_FILE, true);
    }

    /**
     * 设置值
     *
     * @param key
     * @param value
     */
    public static void setStringByKey(String key, String value) {
        try {
            prop = getPropFromProperties(DEFAULT_CONFIG_FILE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        key = key.trim();
        prop.setProperty(key, value);
    }

    /**
     * 获得Properties对象
     *
     * @return
     */
    public static Properties getProperties() {
        try {
            return getPropFromProperties(DEFAULT_CONFIG_FILE);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringByKey(String key, String propName, Boolean isCache) {
        try {
            prop = getPropFromProperties(propName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        key = key.trim();
        if (isCache) {
            if (!configMap.containsKey(key)) {
                if (prop.getProperty(key) != null) {
                    configMap.put(key, prop.getProperty(key));
                }
            }
            return configMap.get(key);
        } else {
            return prop.getProperty(key);
        }
    }

    public static Properties getPropFromProperties(String fileName) throws Exception {
        Properties prop = loaderMap.get(fileName);
        if (prop != null) {
            return prop;
        }
        InputStream ins = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(ins));
        prop = new Properties();
        prop.load(br);
        loaderMap.put(fileName, prop);
        ins.close();
        return prop;
    }

}
