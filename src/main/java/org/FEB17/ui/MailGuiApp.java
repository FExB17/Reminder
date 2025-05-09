package org.FEB17.ui;

import org.FEB17.controller.ReminderController;
import org.FEB17.manager.ReminderManager;
import org.FEB17.models.Reminder;

import javax.swing.*;

public class MailGuiApp {

    public static void start() {
        JFrame frame = new JFrame("Reminder");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        ReminderController[] holder = new ReminderController[1];
        ReminderManager manager = new ReminderManager();
        ReminderFormPanel formPanel = new ReminderFormPanel(() -> holder[0]);
        ReminderListPanel listPanel = new ReminderListPanel();
        holder[0] = new ReminderController(manager, listPanel, formPanel);


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, listPanel);
        splitPane.setDividerLocation(300);
        frame.add(splitPane);

        frame.setVisible(true);
    }
}
