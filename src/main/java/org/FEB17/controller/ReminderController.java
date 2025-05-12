package org.FEB17.controller;

import org.FEB17.mail.MailData;
import org.FEB17.manager.ReminderManager;
import org.FEB17.models.Reminder;
import org.FEB17.models.Status;
import org.FEB17.scheduler.MailScheduler;
import org.FEB17.ui.ReminderBoxPanel;
import org.FEB17.ui.ReminderFormPanel;
import org.FEB17.ui.ReminderListPanel;

import java.util.*;

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
            updateViewToStopped(id);
        } else {
            reminderManager.startReminder(id);
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

    // erstellt die reminder und den scheduler
    public void createReminder(MailData mailData, int interval){
        Reminder reminder = reminderManager.createReminder(mailData, interval);
        ReminderBoxPanel panel = new ReminderBoxPanel(reminder, this);
        reminderListPanel.addReminderBox(panel);
        reminderViews.put(reminder.getId(), panel);
    }

    public void fillForm(UUID id) {
        Reminder reminder = reminderManager.getReminder(id);
        if (reminder != null) {
            formPanel.fillForm(reminder);
        }
    }
    
    public void stopAllReminders() {
        reminderManager.stopAllReminders();
        for (UUID id : reminderViews.keySet()) {
            updateViewToStopped(id);
        }
    }
    
    public void deleteReminder(UUID id){
    ReminderBoxPanel panel = reminderViews.remove(id);
        if (panel != null) {
            reminderListPanel.removeBox(panel);
        }
    }

    // ladet alle reminder und startet die aktiven scheduler
    public void renderAllReminders() {
        List<Reminder> sortedReminders = reminderManager.getAllReminder().stream()
                        .sorted(Comparator.comparingLong(Reminder :: getCreatedAt))
                                .toList();
        sortedReminders.forEach(reminder ->{
            ReminderBoxPanel reminderBoxPanel = new ReminderBoxPanel(reminder, this);
            reminderListPanel.addReminderBox(reminderBoxPanel);
            reminderViews.put(reminder.getId(), reminderBoxPanel);
            reminderManager.startSchedulerIfActive(reminder);
        });
    }

}
