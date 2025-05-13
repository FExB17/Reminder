package org.FEB17.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.FEB17.models.Reminder;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class ReminderStorage {

    private static final Path path = Paths.get(System.getProperty("user.home"), "reminders.json");
    static Logger logger = Logger.getLogger(ReminderStorage.class.getName());

    public static void saveReminders(Collection<Reminder> reminders) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(reminders);
        try{
           Files.write(path, json.getBytes());
       }catch(IOException e){
           logger.severe(e.getMessage());
        }
    }

    public static List<Reminder> loadReminders() {
        List<Reminder> list = new ArrayList<>();
        if (Files.exists(path)) {
            try {
                String json = Files.readString(path);
                Gson gson = new Gson();
                Type typeToken = new TypeToken<List<Reminder>>() {}.getType();
                list = gson.fromJson(json, typeToken);
            } catch (IOException e) {
                logger.severe(e.getMessage());
            }
        }
        return list;
    }

}
