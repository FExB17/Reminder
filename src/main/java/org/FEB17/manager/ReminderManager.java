package org.FEB17.manager;

import org.FEB17.models.Reminder;
import org.FEB17.models.Status;
import org.FEB17.persistence.ReminderStorage;

import java.util.*;

public class ReminderManager {

    private Map<UUID, Reminder> remindersMap = new HashMap<>();


    public void addReminder(Reminder reminder){
        remindersMap.put(reminder.getId(), reminder);

    }

    public void removeReminder(UUID id){
        remindersMap.remove(id);
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
            r.setStatus(Status.STOPPED);
        }
    }

    public void startReminder(UUID id){
        Reminder r = remindersMap.get(id);
        if(r != null){
            r.setStatus(Status.ACTIVE);
        }
    }

    public void loadFromDisk (){
        List<Reminder>reminders = ReminderStorage.loadReminders();
        for (Reminder reminder : reminders) {
            this.remindersMap.put(reminder.getId(),reminder);
        }
    }
}
