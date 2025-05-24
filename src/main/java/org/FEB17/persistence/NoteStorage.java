package org.FEB17.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.FEB17.models.Note;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class NoteStorage {
    private static final Path path = Paths.get(System.getProperty("user.home"),"reminderApp","notes.json");
    private static final Logger logger = Logger.getLogger(NoteStorage.class.getName());

    public static void saveNotes(Collection<Note> notes){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(notes);

        try {
            Files.createDirectories(path.getParent());
            Files.write(path,json.getBytes());
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    public static List<Note> loadNotes(){
        List<Note> notes = new ArrayList<>();
        Gson gson = new Gson();
        try{
            String json =Files.readString(path);
            Type typeToken = new TypeToken<List<Note>>(){}.getType();
            notes = gson.fromJson(json, typeToken);
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
        return notes;
    }

}
