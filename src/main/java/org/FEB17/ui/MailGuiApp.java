package org.FEB17.ui;

import org.FEB17.controller.NotesController;
import org.FEB17.controller.ReminderController;
import org.FEB17.manager.NotesManager;
import org.FEB17.manager.ReminderManager;
import org.FEB17.persistence.NoteStorage;
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

        ReminderManager manager = new ReminderManager();
        NotesManager notesManager = new NotesManager();
        manager.loadFromDisk();
        ReminderFormPanel formPanel = new ReminderFormPanel();
        ReminderListPanel listPanel = new ReminderListPanel();
        ReminderController reminderController = new ReminderController(manager, listPanel, formPanel);
        reminderController.renderSortedReminders(Boolean.parseBoolean(SettingsAccess.getProperty("isAscending")));


        JSplitPane splitPaneEmail = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, listPanel);
        splitPaneEmail.setDividerLocation(300);

        NotesForm notesForm = new NotesForm();
        NotesPanel notesPanel = new NotesPanel();
        notesManager.loadFromDisk();
        NotesController notesController = new NotesController(notesManager, notesForm, notesPanel);
        notesController.renderAllNotes();

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
                NoteStorage.saveNotes(notesManager.getAllNotes());
                frame.dispose();
                System.exit(0);
            }
        });


    }
}
