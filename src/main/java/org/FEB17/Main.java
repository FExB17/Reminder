package org.FEB17;

import org.FEB17.ui.MailGuiApp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

public class Main {
    // TODO mail darf nicht in Spam-Order
    public static void main(String[] args) {
        try{
            LogManager.getLogManager().readConfiguration(new FileInputStream("src/main/resources/logging.properties"));
        }catch (IOException e){
            System.err.println("could not load logging.properties: " + e.getMessage());
        }

        MailGuiApp.start();


    }
}






