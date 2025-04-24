package org.FEB17.manager;

import org.FEB17.models.Reminder;

import java.util.ArrayList;
import java.util.List;

public class ReminderManager {

    List<Reminder> reminderList = new ArrayList<>();


    public void addReminder(Reminder reminder){
        reminderList.add(reminder);
    }
    public void removeReminder(Reminder reminder){
        reminderList.remove(reminder);
    }

    public List<Reminder> getAllReminder(){
        return reminderList;
    }

}
