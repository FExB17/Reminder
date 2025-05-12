package org.FEB17.manager;

import org.FEB17.mail.MailData;
import org.FEB17.models.Reminder;
import org.FEB17.models.Status;
import org.FEB17.persistence.ReminderStorage;
import org.FEB17.scheduler.MailScheduler;

import java.util.*;

public class ReminderManager {

    private Map<UUID, Reminder> remindersMap = new HashMap<>();

    public Reminder createReminder(MailData data, int interval) {
        Reminder reminder = new Reminder(data, interval);
        remindersMap.put(reminder.getId(), reminder);
        ReminderStorage.saveReminders(remindersMap.values());

        if (reminder.getStatus() == Status.ACTIVE) {
            MailScheduler.startScheduledMailing(
                    reminder.getId(),
                    reminder.getInterval(),
                    () -> data
            );
        }
        return reminder;
    }

    public void addReminder(Reminder reminder){
        remindersMap.put(reminder.getId(), reminder);

    }

    public void deleteReminder(UUID id){
        MailScheduler.stop(id);
        remindersMap.remove(id);
        ReminderStorage.saveReminders(remindersMap.values());
    }

    public Collection<Reminder> getAllReminder(){
        return remindersMap.values();
    }

    public Reminder getReminder(UUID id){
       return remindersMap.get(id);
    }

    public void stopReminder(UUID id){
        Reminder r = remindersMap.get(id);
        if(r != null){
            MailScheduler.stop(id);
            r.setStatus(Status.STOPPED);
            ReminderStorage.saveReminders(remindersMap.values());
        }
    }

    public void startReminder(UUID id){
        Reminder r = remindersMap.get(id);
        if(r != null){
            MailScheduler.startScheduledMailing(id, r.getInterval(), () -> new MailData(
                    r.getRecipient(),
                    r.getSubject(),
                    r.getBody(),
                    r.getInterval()
            ));
            r.setStatus(Status.ACTIVE);
            ReminderStorage.saveReminders(remindersMap.values());
        }
    }

    public void loadFromDisk (){
        List<Reminder>reminders = ReminderStorage.loadReminders();
        for (Reminder reminder : reminders) {
            this.remindersMap.put(reminder.getId(),reminder);
        }
    }

    public void stopAllReminders(){
        MailScheduler.stopAll(); //TODO lieber stopAll() hier oder stop() im for loop?
        for (Reminder reminder : remindersMap.values()) {
            reminder.setStatus(Status.STOPPED);
        }
        ReminderStorage.saveReminders(remindersMap.values());
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
}
