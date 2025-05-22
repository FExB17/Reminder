package org.FEB17.ui;

import org.FEB17.controller.NotesController;
import org.FEB17.controller.ReminderController;
import org.FEB17.manager.NotesManager;
import org.FEB17.manager.ReminderManager;
import org.FEB17.persistence.ReminderStorage;
import org.FEB17.utils.SettingsAccess;


import javax.swing.*;
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
        ReminderManager manager = new ReminderManager();
        manager.loadFromDisk();
        ReminderFormPanel formPanel = new ReminderFormPanel();
        ReminderListPanel listPanel = new ReminderListPanel();
        ReminderController reminderController = new ReminderController(manager, listPanel, formPanel);
        reminderController.renderSortedReminders(Boolean.parseBoolean(SettingsAccess.getProperty("isAscending")));

        JSplitPane splitPaneEmail = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, listPanel);
        splitPaneEmail.setDividerLocation(300);

        NotesManager notesManager = new NotesManager();
        NotesForm notesForm = new NotesForm();
        NotesPanel notesPanel = new NotesPanel();
        NotesController notesController = new NotesController(notesManager, notesForm, notesPanel);

        JSplitPane splitPaneDesktop = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, notesForm, notesPanel);
        splitPaneDesktop.setDividerLocation(300);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Desktop Reminder", splitPaneDesktop);
        tabbedPane.addTab("E-mail Reminder", splitPaneEmail);

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
