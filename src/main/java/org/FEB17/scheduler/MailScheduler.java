package org.FEB17.scheduler;

import org.FEB17.mail.MailData;
import org.FEB17.mail.MailSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class MailScheduler {
    private static final Logger logger = Logger.getLogger(MailScheduler.class.getName());
    private static final Map<UUID,ScheduledExecutorService> schedulerMap = new HashMap<>();

    public static void startScheduledMailing(UUID id, long intervalMinutes, Supplier<MailData> dataSupplier) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable command = () -> {
            MailData data = dataSupplier.get();

            //TODO muss ich noch verstehen
            String result = MailSender.sendMail(data.to(), data.subject(), data.body());
        };

        scheduler.scheduleAtFixedRate(command, 0, intervalMinutes, TimeUnit.MINUTES);
        schedulerMap.put(id,scheduler);

        logger.fine("Reminder started with intervall: "+ intervalMinutes + " minutes");
    }

    public static void stop(UUID id) {
        ScheduledExecutorService scheduler = schedulerMap.get(id);
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            schedulerMap.remove(id);
            System.out.println("Scheduler stopped");
        }
    }
    public static void stopAll() {
        for (UUID id : new ArrayList<>(schedulerMap.keySet())) {
            stop(id);
        }
    }

}
