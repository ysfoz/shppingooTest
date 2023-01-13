package org.Shoppingoo.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationReader {

    private static Properties properties;

    static {
        try {

            String path = "configurations.properties";
            FileInputStream fis = new FileInputStream(path);
            properties = new Properties();
            properties.load(fis);
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {

        return properties.getProperty(key);
    }
}
