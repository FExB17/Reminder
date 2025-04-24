package org.FEB17.scheduler;

import org.FEB17.mail.MailData;
import org.FEB17.mail.MailSender;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class MailScheduler {
    private static final Logger logger = Logger.getLogger(MailScheduler.class.getName());

    // läuft parallel und im Hintergrund
    static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    // sendet die Nachricht in einem angegebenen Intervall
    public static void startScheduledMailing(long intervalMinutes, Supplier<MailData> dataSupplier) {
        Runnable command = () -> {
            MailData data = dataSupplier.get();
            //TODO result sollte von der MailGuiApp benutzt werden weil sonst auch bei fehlern continuous reminder started steht
            String result = MailSender.sendMail(data.to, data.subject, data.body);
        };
        if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()){
            scheduledExecutorService = Executors.newScheduledThreadPool(1);
        }
        scheduledExecutorService.scheduleAtFixedRate(command, 0, intervalMinutes, TimeUnit.MINUTES);
        logger.fine("Reminder started with intervall: "+ intervalMinutes + " minutes");
    }

    public static void stop() {
        if (scheduledExecutorService != null ) {
            scheduledExecutorService.shutdown();
            System.out.println("Scheduler stopped");
        }
    }
}
