package org.FEB17.controller;

import org.FEB17.mail.MailData;
import org.FEB17.manager.ReminderManager;
import org.FEB17.models.Reminder;
import org.FEB17.models.Status;
import org.FEB17.ui.ReminderBoxPanel;
import org.FEB17.ui.ReminderFormPanel;
import org.FEB17.ui.ReminderListPanel;

import java.util.*;

/**
 * Die Klasse `ReminderController` verwaltet die Logik für Erinnerungen und deren Darstellung.
 * Sie verbindet die Geschäftslogik (`ReminderManager`) mit der Benutzeroberfläche (`ReminderListPanel` und `ReminderFormPanel`).
 */
public class ReminderController {
    private final ReminderManager manager;
    private final ReminderListPanel listPanel;
    private final ReminderFormPanel formPanel;


public ReminderController(ReminderManager manager, ReminderListPanel listPanel, ReminderFormPanel formPanel) {
    this.manager = manager;
    this.listPanel = listPanel;
    this.formPanel = formPanel;
    this.listPanel.setController(this);
    this.formPanel.setController(this);

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
        listPanel.updateAllToStopped();
    }
    
    public void deleteReminder(UUID id){
    ReminderBoxPanel panel = listPanel.getReminderBox(id);
    manager.deleteReminder(id);
        if (panel != null) {
            listPanel.removeBox(panel);
        }
    }

    // Methode zum Sortieren der Erinnerungen ohne erneutes Senden der E-Mails, weil renderSortedReminders zwar sortiert aber auch sendet
    public void sortViewByCreatedAt(boolean ascending){
        List<Reminder> sorted = manager.getSortedByCreatedAt(ascending);
        listPanel.render(sorted, this);

    }


    public void renderSortedReminders(boolean ascending){
    List<Reminder> sorted = manager.getSortedByCreatedAt(ascending);
    listPanel.render(sorted,this);
    sorted.forEach(manager::startSchedulerIfActive);
    }

}
