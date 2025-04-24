package org.FEB17.utils;

public class MailConfigService {
    ConfigReader configReader = new ConfigReader();

    public String getSubject(){
        return configReader.getProperty("subject");

    }
    public String getBody(){
        return configReader.getProperty("body");
    }



}
