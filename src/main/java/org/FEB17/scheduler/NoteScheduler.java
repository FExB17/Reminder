package org.FEB17.scheduler;

import org.FEB17.models.Note;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class NoteScheduler {


    private final static Map<UUID, ScheduledExecutorService> schedulers = new HashMap<>();
    private final static Logger logger = Logger.getLogger(NoteScheduler.class.getName());

    public static void scheduleNote(Note note) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        long interval = note.getInterval();
        Runnable command = () -> showLinuxNotification(note);
        scheduledExecutorService.scheduleAtFixedRate(command,0, interval, TimeUnit.MINUTES);
        schedulers.put(note.getId(),scheduledExecutorService);
        logger.info("Scheduler " + note.getId() + " has been started");
    }

    private static void showLinuxNotification(Note note) {
        int maxRetries = 3;
        int retryDelayMs = 1000;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                new ProcessBuilder("notify-send", note.getContent()).start();
                return;
            } catch (IOException e) {
                logger.warning("Fehler beim Senden der Benachrichtigung (Versuch " + attempt + "): " + e.getMessage());
                if (attempt == maxRetries) {
                    logger.severe("Alle Versuche fehlgeschlagen.");
                    JOptionPane.showMessageDialog(null, note.getContent(), "Erinnerung", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    try {
                        Thread.sleep(retryDelayMs);
                    } catch (InterruptedException ignored) {}
                }
            }
        }
    }

    public static void stopScheduler(UUID id) {
        if (schedulers.get(id) != null && !schedulers.get(id).isShutdown()){
            schedulers.remove(id).shutdown();
            logger.info("Scheduler " + id + " has been shut down");
        }
    }

    public static void startScheduler(Note note) {
        scheduleNote(note);
    }
}
