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
        //Создаем объект свойст
        properties = new Properties();
        File file = new File(path);
        //Загружаем свойства из файла
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getSettings(String key) {
        //Получаем в переменную значение конкретного свойства
        return properties.getProperty(key);
    }

    Boolean getSettingsBool(String key) {
        //Получаем в переменную значение конкретного свойства
        return properties.getProperty(key).equals("1");
    }

    Float getSettingsFloat(String key) {
        //Получаем в переменную значение конкретного свойства
        return Float.valueOf(properties.getProperty(key));
    }

    Integer getSettingsInt(String key) {
        //Получаем в переменную значение конкретного свойства
        return Integer.parseInt(properties.getProperty(key));
    }

    void setProperties(String key, String value) {
        //Устанавливаем значение свойста
        properties.setProperty(key, value);
        //Сохраняем свойства в файл.
        try {
            properties.store(new FileOutputStream(new File(path)), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
