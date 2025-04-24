package org.FEB17.ui;

import org.FEB17.mail.MailData;
import org.FEB17.scheduler.MailScheduler;
import org.FEB17.utils.FieldValidator;
import org.FEB17.utils.Gui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class MailGuiApp {

    public static void start() {
        JFrame frame = createFrame();
        JPanel panel = createForm(frame);

        frame.add(panel);
        frame.setVisible(true);
    }
//TODO createForm muss aufgeteilt werden
    private static JPanel createForm(JFrame frame) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField mailTo = new JTextField();
        JLabel labelRecipient = new JLabel("Recipient");
        JLabel errorRecipient = Gui.createDefaultErrorLabel();
        mailTo.setMaximumSize(new Dimension((Integer.MAX_VALUE), 30));
        panel.add(labelRecipient);
        panel.add(mailTo);
        panel.add(errorRecipient);


        JTextField subject = new JTextField();
        JLabel labelSubject = new JLabel("Subject");
        JLabel errorSubject = Gui.createDefaultErrorLabel();
        subject.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(labelSubject);
        panel.add(subject);
        panel.add(errorSubject);


        JTextArea messageArea = new JTextArea();
        JLabel labelMessage = new JLabel("Message");
        JLabel errorMessage = Gui.createDefaultErrorLabel();


        JScrollPane scrollPane = new JScrollPane(messageArea);
        panel.add(labelMessage);
        panel.add(scrollPane);
        panel.add(errorMessage);


        SpinnerNumberModel model = new SpinnerNumberModel(1,1,1440,1);
        JSpinner intervallSpinner = new JSpinner(model);
        intervallSpinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(new JLabel("Interval (minutes)"));
        panel.add(intervallSpinner);


        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton startBtn = new JButton("Start");
        JButton stopBtn = new JButton("Stop");
        btnPanel.add(startBtn);
        btnPanel.add(stopBtn);
        panel.add(btnPanel);

        final JLabel statusLabel = new JLabel();
        panel.add(statusLabel);


        startBtn.addActionListener(e -> {

            boolean valid = true;
            if (!FieldValidator.validateField(mailTo, errorRecipient)) valid = false;
            if (!FieldValidator.validateField(subject, errorSubject)) valid = false;
            if (!FieldValidator.validateField(messageArea, errorMessage)) valid = false;
            if (!valid) {
                return;
            }

            long interval = (int) intervallSpinner.getValue();
            Supplier<MailData> supplier = () -> {
                return new MailData(mailTo.getText(), subject.getText(), messageArea.getText());
            };
            if (!FieldValidator.isValidEmail(mailTo.getText())){
                errorRecipient.setText("Invalid e-mail address format. Please enter a valid email address.");
                return;
            }
            MailScheduler.startScheduledMailing(interval, supplier);
            statusLabel.setText("Continuous reminder started. Mails will be sent every "  + interval +  " minutes.");

        });

        stopBtn.addActionListener(e -> {
            MailScheduler.stop();
            statusLabel.setText("Reminder stopped");
        });

        return panel;
    }

    private static JFrame createFrame() {
        JFrame frame = new JFrame("Reminder");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        return frame;
    }
}
