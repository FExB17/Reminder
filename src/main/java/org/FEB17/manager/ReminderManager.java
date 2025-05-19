package org.FEB17.manager;

import org.FEB17.mail.MailData;
import org.FEB17.models.Reminder;
import org.FEB17.models.Status;
import org.FEB17.persistence.ReminderStorage;
import org.FEB17.scheduler.MailScheduler;

import java.util.*;
/**
 * Die Klasse `ReminderManager` verwaltet Erinnerungen und deren Status.
 * Sie bietet Methoden zum Erstellen, Löschen, Starten und Stoppen von Erinnerungen
 * sowie zum Laden und Speichern von Erinnerungen.
 * Derzeit ist die Klasse nicht thread-sicher.
 */
public class ReminderManager {

    private Map<UUID, Reminder> reminders = new HashMap<>();

    public Reminder createReminder(MailData data, int interval) {
        Reminder reminder = new Reminder(data, interval);
        reminders.put(reminder.getId(), reminder);
        ReminderStorage.saveReminders(reminders.values());

        if (reminder.getStatus() == Status.ACTIVE) {
            MailScheduler.startScheduledMailing(
                    reminder.getId(),
                    reminder.getInterval(),
                    () -> data
            );
        }
        return reminder;
    }

    public void deleteReminder(UUID id){
        MailScheduler.stop(id);
        reminders.remove(id);
        ReminderStorage.saveReminders(reminders.values());
    }

    public Collection<Reminder> getAllReminder(){
        return reminders.values();
    }

    public Reminder getReminder(UUID id){
       return reminders.get(id);
    }

    public void stopReminder(UUID id){
        Reminder r = reminders.get(id);
        if(r != null){
            MailScheduler.stop(id);
            r.setStatus(Status.STOPPED);
            ReminderStorage.saveReminders(reminders.values());
        }
    }

    public void startReminder(UUID id){
        Reminder r = reminders.get(id);
        if(r != null){
            MailScheduler.startScheduledMailing(id, r.getInterval(), () -> new MailData(
                    r.getRecipient(),
                    r.getSubject(),
                    r.getBody(),
                    r.getInterval()
            ));
            r.setStatus(Status.ACTIVE);
            ReminderStorage.saveReminders(reminders.values());
        }
    }

    public void loadFromDisk (){
        List<Reminder>reminders = ReminderStorage.loadReminders();
        for (Reminder reminder : reminders) {
            this.reminders.put(reminder.getId(),reminder);
        }
    }

    public void stopAllReminders(){
        MailScheduler.stopAll();
        for (Reminder reminder : reminders.values()) {
            reminder.setStatus(Status.STOPPED);
        }
        ReminderStorage.saveReminders(reminders.values());
    }

    public void startSchedulerIfActive(Reminder reminder){
        if (reminder.getStatus() == Status.ACTIVE) {

            MailScheduler.startScheduledMailing(
                    reminder.getId(),
                    reminder.getInterval(),
                    () -> new MailData(
                            reminder.getRecipient(),
                            reminder.getSubject(),
                            reminder.getBody(),
                            reminder.getInterval()
                    )
            );
        }
    }

    public List<Reminder> getSortedByCreatedAt(boolean ascending){
        Comparator<Reminder> comparator = Comparator.comparingLong(Reminder::getCreatedAt);
        if (!ascending){
            comparator = comparator.reversed();
        }
        return reminders.values().stream().
                sorted(comparator)
                .toList();
    }


}
