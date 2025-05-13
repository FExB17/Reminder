package org.FEB17.controller;

import org.FEB17.mail.MailData;
import org.FEB17.manager.ReminderManager;
import org.FEB17.models.Reminder;
import org.FEB17.models.Status;
import org.FEB17.ui.ReminderBoxPanel;
import org.FEB17.ui.ReminderFormPanel;
import org.FEB17.ui.ReminderListPanel;

import java.util.*;

public class ReminderController {
    private final ReminderManager manager;
    private final ReminderListPanel listPanel;
    private final ReminderFormPanel formPanel;

public ReminderController(ReminderManager manager, ReminderListPanel listPanel, ReminderFormPanel formPanel) {
    this.manager = manager;
    this.listPanel = listPanel;
    this.formPanel = formPanel;
}

    public void toggleReminder(UUID id) {
        Reminder reminder = manager.getReminder(id);
        if (reminder == null) return;

        if (reminder.getStatus() == Status.ACTIVE) {
            manager.stopReminder(id);
            updateViewToStopped(id);
        } else {
            manager.startReminder(id);
            updateViewToActive(id);
        }
    }

    public void updateViewToActive(UUID id){
    ReminderBoxPanel panel = listPanel.getReminderBox(id);
    if(panel != null){
        panel.updateToActive();
    }
 }

    public void updateViewToStopped(UUID id) {
        ReminderBoxPanel panel = listPanel.getReminderBox(id);
        if (panel != null) {
            panel.updateToStopped();
        }
    }

    // erstellt die reminder und den scheduler
    public void createReminder(MailData mailData, int interval){
        Reminder reminder = manager.createReminder(mailData, interval);
        ReminderBoxPanel panel = new ReminderBoxPanel(reminder, this);
        listPanel.addReminderBox(panel);
    }

    public void fillForm(UUID id) {
        Reminder reminder = manager.getReminder(id);
        if (reminder != null) {
            formPanel.fillForm(reminder);
        }
    }
    
    public void stopAllReminders() {
        manager.stopAllReminders();
        listPanel.getAllReminderBoxPanels().forEach(ReminderBoxPanel::updateToStopped);
    }
    
    public void deleteReminder(UUID id){
    ReminderBoxPanel panel = listPanel.getReminderBox(id);
    manager.deleteReminder(id);
        if (panel != null) {
            listPanel.removeBox(panel);
        }
    }

    // ladet alle reminder und startet die aktiven scheduler
    public void renderAllReminders() {
        // Methodenreferenz Klasse::Methode nutzbar bei functional interfaces
        List<Reminder> sortedReminders = manager.getAllReminder().stream()
                        .sorted(Comparator.comparingLong(Reminder :: getCreatedAt))
                                .toList();
        sortedReminders.forEach(reminder ->{
            ReminderBoxPanel reminderBoxPanel = new ReminderBoxPanel(reminder, this);
            listPanel.addReminderBox(reminderBoxPanel);
            manager.startSchedulerIfActive(reminder);
        });
    }

}
