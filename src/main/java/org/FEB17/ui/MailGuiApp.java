package org.FEB17.ui;

import org.FEB17.controller.ReminderController;
import org.FEB17.manager.ReminderManager;
import org.FEB17.persistence.ReminderStorage;
import org.FEB17.utils.SettingsAccess;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MailGuiApp {

    public static void start() {
        JFrame frame = new JFrame("Reminder");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        /**
         * Ein Array als Holder für den ReminderController, um den Zugriff innerhalb von Lambda-Ausdrücken zu ermöglichen.
         * Da lokale Variablen in Lambdas final oder effektiv final sein müssen, wird ein Array mit einem Element verwendet,
         * um den Controller nachträglich zu setzen und darauf zugreifen zu können.
         */
        ReminderController[] holder = new ReminderController[1];
        ReminderManager manager = new ReminderManager();
        manager.loadFromDisk();
        ReminderFormPanel formPanel = new ReminderFormPanel(() -> holder[0]);
        ReminderListPanel listPanel = new ReminderListPanel();
        holder[0] = new ReminderController(manager, listPanel, formPanel);
        holder[0].renderSortedReminders(Boolean.parseBoolean(SettingsAccess.getProperty("isAscending")));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, listPanel);
        splitPane.setDividerLocation(300);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("E-mail Reminder", splitPane);
        tabbedPane.addTab("Desktop Reminder", new JPanel());
        frame.add(tabbedPane);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                ReminderStorage.saveReminders(manager.getAllReminder());
                frame.dispose();
                System.exit(0);
            }
        });


    }
}
