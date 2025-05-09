package org.FEB17.ui;

import org.FEB17.controller.ReminderController;
import org.FEB17.mail.MailData;
import org.FEB17.models.Reminder;
import org.FEB17.utils.FieldValidator;
import org.FEB17.utils.Gui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class ReminderFormPanel extends JPanel {

    private final JTextField mailTo;
    private final JTextField subject;
    private final JTextArea messageArea;
    private final JSpinner intervalSpinner;
    private final JLabel errorRecipient, errorSubject, errorMessage;
    private final JLabel statusLabel;

    public ReminderFormPanel(Supplier<ReminderController> controllerSupplier) {
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
            boolean valid = true;
            if (!FieldValidator.validateField(mailTo, errorRecipient)) valid = false;
            if (!FieldValidator.validateField(subject, errorSubject)) valid = false;
            if (!FieldValidator.validateField(messageArea, errorMessage)) valid = false;
            if (!FieldValidator.isValidEmail(mailTo.getText())) {
                errorRecipient.setText("Invalid e-mail address format.");
                valid = false;
            }

            if (!valid) return;

            MailData data = new MailData(
                    mailTo.getText(),
                    subject.getText(),
                    messageArea.getText(),
                    getInterval()
            );

            controllerSupplier.get().createReminder(data, getInterval());
            statusLabel.setText("Reminder created. Every " + getInterval() + " minutes.");
        });


        stopAllBtn.addActionListener(e -> {
            controllerSupplier.get().stopAllReminders();
            statusLabel.setText("All reminders stopped.");
        });
    }

    public int getInterval() {
        return (int) intervalSpinner.getValue();
    }

    public void fillForm(Reminder reminder) {
        mailTo.setText(reminder.getRecipient());
        subject.setText(reminder.getSubject());
        messageArea.setText(reminder.getBody());
        intervalSpinner.setValue(reminder.getInterval());
    }
}