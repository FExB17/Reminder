package org.FEB17.ui;

import org.FEB17.controller.ReminderController;
import org.FEB17.manager.ReminderManager;

import javax.swing.*;

public class MailGuiApp {

    public static void start() {
        JFrame frame = new JFrame("Reminder");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        // Reminder-Logik-Komponente
        ReminderManager reminderManager = new ReminderManager();

        // GUI-Komponenten erzeugen (ohne Callbacks!)
        ReminderFormPanel formPanel = new ReminderFormPanel();
        ReminderListPanel listPanel = new ReminderListPanel();

        // Controller erzeugen (zentrale Steuerung der App)
        ReminderController controller = new ReminderController(reminderManager, listPanel, formPanel);

        // Controller an die GUI-Komponenten übergeben
        formPanel.setController(controller);
        listPanel.setController(controller);

        // Layout zusammenbauen
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, listPanel);
        splitPane.setDividerLocation(300);
        frame.add(splitPane);

        frame.setVisible(true);
    }
}
