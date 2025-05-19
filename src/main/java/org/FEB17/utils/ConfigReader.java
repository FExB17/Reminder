package org.FEB17.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigReader {

    private static final Logger logger  = Logger.getLogger(ConfigReader.class.getName());
    private static final Properties properties = new Properties();



    static{
        try {
            FileReader file = new FileReader("src/main/resources/config.properties");
            properties.load(file);
            logger.info("config file loaded");
        } catch (IOException e) {
           logger.warning("could not load config.properties: " + e.getMessage());
        }
    }


    public String getProperty(String key){
        return properties.getProperty(key);
    }


}
