package org.FEB17.scheduler;

import org.FEB17.models.Note;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class NoteScheduler {


    private static Map<UUID, ScheduledExecutorService> schedulers = new HashMap<>();
    private static Logger logger = Logger.getLogger(NoteScheduler.class.getName());

    public static void scheduleNote(Note note) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        long interval = note.getInterval();
        Runnable command = () -> showLinuxNotification(note);
        scheduledExecutorService.scheduleAtFixedRate(command,0, interval, TimeUnit.MINUTES);
        schedulers.put(note.getId(),scheduledExecutorService);
    }

    private static void showLinuxNotification(Note note) {
        try {
            new ProcessBuilder("notify-send",note.getContent()).start();
        }catch (IOException e){
            logger.severe(e.getMessage());
        }
    }

}
