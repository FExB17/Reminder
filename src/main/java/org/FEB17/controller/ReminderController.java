package org.FEB17.controller;

import org.FEB17.mail.MailData;
import org.FEB17.manager.ReminderManager;
import org.FEB17.models.Reminder;
import org.FEB17.models.Status;
import org.FEB17.ui.ReminderBoxPanel;
import org.FEB17.ui.ReminderFormPanel;
import org.FEB17.ui.ReminderListPanel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReminderController {
    private ReminderManager reminderManager;
    private ReminderListPanel reminderListPanel;
    private Map<UUID, ReminderBoxPanel> reminderViews;
    private ReminderFormPanel formPanel;

public ReminderController(ReminderManager manager, ReminderListPanel listPanel, ReminderFormPanel formPanel) {
    this.reminderManager = manager;
    this.reminderListPanel = listPanel;
    this.formPanel = formPanel;
    this.reminderViews = new HashMap<>();
}

 public void  toggleReminder(UUID id){

//Reminder vom Manager holt
Reminder reminder = reminderManager.getReminder(id);
if(reminder == null) return;


if(reminder.getStatus() == Status.ACTIVE ){
    reminderManager.stopReminder(id);
    updateViewToStopped(id);
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
     reminderManager.addReminder(reminder);

     ReminderBoxPanel panel = new ReminderBoxPanel(reminder, this); // ggf. anpassen
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
        // 1. ReminderManager: Reminder stoppen
        for (Reminder reminder : reminderManager.getAllReminder()) {
            reminder.setStatus(Status.STOPPED);
        }

        // 2. GUI: Alle ReminderBoxPanels aktualisieren
        for (UUID id : reminderViews.keySet()) {
            updateViewToStopped(id);
        }
    }


}
