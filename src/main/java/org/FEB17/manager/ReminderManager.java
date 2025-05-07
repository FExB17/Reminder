package org.FEB17.manager;

import org.FEB17.models.Reminder;
import org.FEB17.models.Status;

import java.util.*;

public class ReminderManager {
    private Map<UUID, Reminder> remindersMap = new HashMap<>();

    List<Reminder> reminderList = new ArrayList<>();


    public void addReminder(Reminder reminder){
        reminderList.add(reminder);
        remindersMap.put(reminder.getId(), reminder);
    }
    public void removeReminder(Reminder reminder){
        reminderList.remove(reminder);
    }

    public List<Reminder> getAllReminder(){
        return reminderList;
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

}
