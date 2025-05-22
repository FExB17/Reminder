package org.FEB17.ui;

import org.FEB17.controller.ReminderController;
import org.FEB17.mail.MailData;
import org.FEB17.models.Reminder;
import org.FEB17.utils.FieldValidator;
import org.FEB17.utils.Gui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;
/**
 * Das Panel zur Eingabe und Verwaltung von Erinnerungs-E-Mails.
 * Stellt ein Formular bereit, um Empfänger, Betreff, Nachricht und Intervall für Erinnerungen einzugeben.
 * Bietet Schaltflächen zum Senden und Stoppen aller Erinnerungen.
 */
public class ReminderFormPanel extends JPanel {

    private final JTextField mailTo;
    private final JTextField subject;
    private final JTextArea messageArea;
    private final JSpinner intervalSpinner;
    private final JLabel errorRecipient, errorSubject, errorMessage;
    private final JLabel statusLabel;
    private ReminderController controller;

/**
 * Erstellt ein neues ReminderFormPanel.
 * Initialisiert das Formular zur Eingabe und Verwaltung von Erinnerungs-E-Mails.
 * @param controllerSupplier Supplier, der einen ReminderController liefert
 */
    public ReminderFormPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Recipient
        mailTo = new JTextField();
        JLabel labelRecipient = new JLabel("Recipient");
        errorRecipient = Gui.createDefaultErrorLabel();
        mailTo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        this.add(labelRecipient);
        this.add(mailTo);
        this.add(errorRecipient);

        // Subject
        subject = new JTextField();
        JLabel labelSubject = new JLabel("Subject");
        errorSubject = Gui.createDefaultErrorLabel();
        subject.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        this.add(labelSubject);
        this.add(subject);
        this.add(errorSubject);

        // Message
        messageArea = new JTextArea();
        JLabel labelMessage = new JLabel("Message");
        errorMessage = Gui.createDefaultErrorLabel();
        JScrollPane scrollPane = new JScrollPane(messageArea);
        this.add(labelMessage);
        this.add(scrollPane);
        this.add(errorMessage);

        // Interval
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 1440, 1);
        intervalSpinner = new JSpinner(model);
        intervalSpinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        this.add(new JLabel("Interval (minutes)"));
        this.add(intervalSpinner);

        // Buttons
        JButton sendBtn = new JButton("Send");
        JButton stopAllBtn = new JButton("Stop All");
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
        btnPanel.add(sendBtn);
        btnPanel.add(stopAllBtn);
        this.add(btnPanel);

        // Statusanzeige
        statusLabel = new JLabel();
        this.add(statusLabel);


        sendBtn.addActionListener(e -> {
            boolean valid =
            FieldValidator.validateEmail(mailTo, errorRecipient) &
            FieldValidator.validateField(subject, errorSubject) &
            FieldValidator.validateField(messageArea, errorMessage);

            MailData data = new MailData(
                    mailTo.getText(),
                    subject.getText(),
                    messageArea.getText(),
                    getInterval()
            );
            if (valid) {
                controller.createReminder(data, getInterval());
                statusLabel.setText("Reminder created. Every " + getInterval() + " minutes.");
            }
        });


        stopAllBtn.addActionListener(e -> {
            controller.stopAllReminders();
            statusLabel.setText("All reminders stopped.");
        });
    }

    /**
     * Returns the intervall chosen in the form.
     * @return the interval in minutes from the form
     */
    public int getInterval() {
        return (int) intervalSpinner.getValue();
    }

    /**
     * Fills the form with the data from the given reminder.
     * @param reminder The reminder to fill the form with.
     */
    public void fillForm(Reminder reminder) {
        mailTo.setText(reminder.getRecipient());
        subject.setText(reminder.getSubject());
        messageArea.setText(reminder.getBody());
        intervalSpinner.setValue(reminder.getInterval());
    }
    public void setController(ReminderController controller) {
        this.controller = controller;
    }
}