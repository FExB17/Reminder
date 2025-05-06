package org.FEB17.ui;

import org.FEB17.mail.MailData;
import org.FEB17.models.Reminder;
import org.FEB17.utils.FieldValidator;
import org.FEB17.utils.Gui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ReminderFormPanel extends JPanel {

    private final JTextField mailTo;
    private final JTextField subject;
    private final JTextArea messageArea;
    private final JSpinner intervalSpinner;
    private final JLabel errorRecipient, errorSubject, errorMessage;
    private final JLabel statusLabel;

    public ReminderFormPanel (Consumer<MailData> onReminderCreated){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        //Recipient
        mailTo = new JTextField();
        JLabel labelRecipient = new JLabel("Recipient");
        errorRecipient = Gui.createDefaultErrorLabel();
        mailTo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        this.add(labelRecipient);
        this.add(mailTo);
        this.add(errorRecipient);


        //Subject
        subject  = new JTextField();
        JLabel labelSubject = new JLabel("Subject");
        errorSubject = Gui.createDefaultErrorLabel();
        subject.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));
        this.add(labelSubject);
        this.add(subject);
        this.add(errorSubject);

        //Message
        messageArea = new JTextArea();
        JLabel labelMessage = new JLabel("Message");
        errorMessage = Gui.createDefaultErrorLabel();
        JScrollPane scrollPane = new JScrollPane(messageArea);
        this.add(labelMessage);
        this.add(scrollPane);
        this.add(errorMessage);

        //Interval
        SpinnerNumberModel model = new SpinnerNumberModel(1,1,1440,1);
        intervalSpinner = new JSpinner(model);
        intervalSpinner.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));
        this.add(new JLabel("Interval (minutes)"));
        this.add(intervalSpinner);

        //Buttons
        JButton sendBtn = new JButton("Send");
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(sendBtn);
        this.add(btnPanel);

        // Statusanzeige
        statusLabel = new JLabel();
        this.add(statusLabel);

        sendBtn.addActionListener(e -> {
            boolean valid = true;
            valid &= (!FieldValidator.validateField(mailTo,errorRecipient));
            valid &= (!FieldValidator.validateField(subject,errorSubject));
            valid &= (!FieldValidator.validateField(messageArea,errorMessage));
            if (!FieldValidator.isValidEmail(mailTo.getText())){
                errorRecipient.setText("Invalid e-mail address format.");
                valid = false;
            }
            if (!valid) return;

            MailData data = new MailData(
                    mailTo.getText(),
                    subject.getText(),
                    messageArea.getText(),
                    // nicht direkt intervalSpinner weil der ein Objekt zurück gibt und kein int wie in MailData verlangt deshalb die hilfsmethode getInterval()
                    getInterval()
            );

            onReminderCreated.accept(data);
            statusLabel.setText("Reminder created. Every " + intervalSpinner.getValue() + "minutes");
        });

    }
    public int getInterval(){
        return (int) intervalSpinner.getValue();
    }
    public void fillForm(Reminder reminder){
        mailTo.setText(reminder.getRecipient());
        subject.setText(reminder.getSubject());
        messageArea.setText(reminder.getBody());
        intervalSpinner.setValue(reminder.getInterval());
    }
}