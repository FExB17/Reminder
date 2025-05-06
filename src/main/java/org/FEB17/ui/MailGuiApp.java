package org.FEB17.ui;

import org.FEB17.manager.ReminderManager;
import org.FEB17.models.Reminder;
import org.FEB17.models.Status;
import org.FEB17.scheduler.MailScheduler;

import javax.swing.*;

public class MailGuiApp {

    private static final ReminderManager reminderManager = new ReminderManager();

    public static void start() {
        JFrame frame = new JFrame("Reminder");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        //TODO muss noch verstanden werden
        // Holder für listPanel (Zirkelschluss lösen)
        final ReminderListPanel[] listPanelHolder = new ReminderListPanel[1];

        // Eingabeformular mit Callback
        ReminderFormPanel formPanel = new ReminderFormPanel(mailData -> {
            Reminder reminder = new Reminder(
                    mailData.to(),
                    mailData.subject(),
                    mailData.body(),
                    mailData.interval(),
                    Status.ACTIVE
            );

            reminderManager.addReminder(reminder);
            listPanelHolder[0].addReminder(reminder);
            MailScheduler.startScheduledMailing(reminder.getId(), reminder.getInterval(), () -> mailData);
        },
                // methoden referenz -> wird ausgeführt wenn der Button gedrückt wird
                // der normale aufruf würde sofort ausgeführt werden
                //TODO wieso ist hier ein callback
                MailScheduler::stopAll
        );

        ReminderListPanel listPanel = new ReminderListPanel(formPanel);
        listPanelHolder[0] = listPanel;

        // Layout zusammenbauen
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, listPanel);
        splitPane.setDividerLocation(300);
        frame.add(splitPane);

        frame.setVisible(true);
    }
}
