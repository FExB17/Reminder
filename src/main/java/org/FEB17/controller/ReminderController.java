package org.FEB17.controller;

import org.FEB17.mail.MailData;
import org.FEB17.manager.ReminderManager;
import org.FEB17.models.Reminder;
import org.FEB17.models.Status;
import org.FEB17.scheduler.MailScheduler;
import org.FEB17.ui.ReminderBoxPanel;
import org.FEB17.ui.ReminderFormPanel;
import org.FEB17.ui.ReminderListPanel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReminderController {
    private final ReminderManager reminderManager;
    private final ReminderListPanel reminderListPanel;
    private final Map<UUID, ReminderBoxPanel> reminderViews;
    private final ReminderFormPanel formPanel;

public ReminderController(ReminderManager manager, ReminderListPanel listPanel, ReminderFormPanel formPanel) {
    this.reminderManager = manager;
    this.reminderListPanel = listPanel;
    this.formPanel = formPanel;
    this.reminderViews = new HashMap<>();
}

    public void toggleReminder(UUID id) {
        Reminder reminder = reminderManager.getReminder(id);
        if (reminder == null) return;

        if (reminder.getStatus() == Status.ACTIVE) {
            reminderManager.stopReminder(id);
            MailScheduler.stop(id);
            updateViewToStopped(id);
        } else {
            reminderManager.startReminder(id);
            MailScheduler.startScheduledMailing(id, reminder.getInterval(), () -> new MailData(
                    reminder.getRecipient(),
                    reminder.getSubject(),
                    reminder.getBody(),
                    reminder.getInterval()
            ));
            updateViewToActive(id);
        }
    }

 public void updateViewToActive(UUID id){
    ReminderBoxPanel panel = reminderViews.get(id);
    if(panel != null){
        panel.updateToActive();
    }
 }

    public void updateViewToStopped(UUID id) {
        ReminderBoxPanel panel = reminderViews.get(id);
        if (panel != null) {
            panel.updateToStopped();
        }
    }

 public void createReminder(MailData mailData, int interval){
     Reminder reminder = new Reminder(mailData, interval);
     reminder.setStatus(Status.ACTIVE);
     reminderManager.addReminder(reminder);


     ReminderBoxPanel panel = new ReminderBoxPanel(reminder, this);
     reminderListPanel.addReminderBox(panel);
     reminderViews.put(reminder.getId(), panel);
     MailScheduler.startScheduledMailing(
             reminder.getId(),
             reminder.getInterval(),
             () -> mailData
     );
 }

    public void fillForm(UUID id) {
        Reminder reminder = reminderManager.getReminder(id);
        if (reminder != null) {
            formPanel.fillForm(reminder);
        }
    }
    public void stopAllReminders() {
        for (Reminder reminder : reminderManager.getAllReminder()) {
            reminder.setStatus(Status.STOPPED);
            MailScheduler.stop(reminder.getId());
        }

        for (UUID id : reminderViews.keySet()) {
            updateViewToStopped(id);
        }
    }
    public void deleteReminder(UUID id){
    reminderManager.removeReminder(id);
    MailScheduler.stop(id);
    ReminderBoxPanel panel = reminderViews.remove(id);
        if (panel != null) {
            reminderListPanel.removeBox(panel);
        }
    }


}
