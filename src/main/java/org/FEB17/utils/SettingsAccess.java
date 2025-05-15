package org.FEB17.utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class SettingsAccess {
    private final static Properties properties = new Properties();
    private final static Logger logger = Logger.getLogger(SettingsAccess.class.getName());

    static {
        try{
            FileReader file = new FileReader("src/main/resources/settings.properties");
            properties.load(file);
            logger.info("Loaded settings.properties");
        }catch (IOException e){
            logger.warning(e.getMessage()+ " - settings.properties not found");
        }
    }

    public static void setProperty(String key, String value){
        properties.setProperty(key, value);
        try{
            FileWriter file = new FileWriter("src/main/resources/settings.properties");
            properties.store(file,"Update settings");
            logger.info("Setting property " + key + " to " + value);
        }catch (IOException e){
            logger.warning(e.getMessage()+ " - settings.properties not found");
        }
    }

    public static String getProperty(String entry){
        return properties.getProperty(entry);
    }
}
