package org.FEB17.ui;

import org.FEB17.mail.MailData;
import org.FEB17.manager.ReminderManager;
import org.FEB17.models.Reminder;
import org.FEB17.models.Status;
import org.FEB17.scheduler.MailScheduler;
import org.FEB17.utils.FieldValidator;
import org.FEB17.utils.Gui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;


public class MailGuiApp {

    private static final ReminderManager reminderManager = new ReminderManager();

    public static void start() {
        JFrame frame = new JFrame("Reminder");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        // Rechtes Panel (Reminder-Liste)
        ReminderListPanel listPanel = new ReminderListPanel();

        // Linkes Panel (Eingabeform) mit Callback
        ReminderFormPanel formPanel = new ReminderFormPanel(mailData -> {
            // Reminder erzeugen
            Reminder reminder = new Reminder(
                    mailData.to,
                    mailData.subject,
                    5, // Placeholder → später durch formPanel.getInterval()
                    Status.ACTIVE
            );

            // Speichern + anzeigen
            reminderManager.addReminder(reminder);
            listPanel.addReminder(reminder);

            // Starten
            MailScheduler.startScheduledMailing(reminder.getInterval(), () -> mailData);
        });

        // Split Layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, listPanel);
        splitPane.setDividerLocation(300); // Linke Seite: 300px
        frame.add(splitPane);

        frame.setVisible(true);
    }
}
