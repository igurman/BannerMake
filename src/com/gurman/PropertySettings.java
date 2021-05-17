package com.gurman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

class PropertySettings {

    private Properties properties;
    private String path;

    PropertySettings(String prop_path) {
        path = prop_path;
        properties = new Properties();
        File file = new File(path);
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getSettings(String key) {
        return properties.getProperty(key);
    }

    Boolean getSettingsBool(String key) {
        return properties.getProperty(key).equals("1");
    }

    Float getSettingsFloat(String key) {
        return Float.valueOf(properties.getProperty(key));
    }

    Integer getSettingsInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    void setProperties(String key, String value) {
        properties.setProperty(key, value);
        try {
            properties.store(new FileOutputStream(new File(path)), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
