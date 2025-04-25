package org.FEB17.ui;

import org.FEB17.models.Reminder;

import javax.swing.*;
import java.awt.*;

public class ReminderBoxPanel extends JPanel {

    private final Reminder reminder;

    public ReminderBoxPanel (Reminder reminder){
        this.reminder = reminder;
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100)); // feste Höhe, volle Breite

        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setBackground(null);
        infoArea.setFont(new Font("Arial", Font.PLAIN,12));
        infoArea.setText(
                "To: " + reminder.getRecipient() + "\n" +
                        "Subject: " + reminder.getSubject() + "\n" +
                        "Interval: " + reminder.getInterval() + " min\n" +
                        "Status: " + reminder.getStatus()
        );

        this.add(infoArea, BorderLayout.CENTER);

        JButton stopBtn = new JButton("Stop");
        JButton deleteBtn = new JButton("Delete");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(stopBtn);
        btnPanel.add(deleteBtn);
        this.add(btnPanel,BorderLayout.SOUTH);
    }
    public Reminder getReminder(){
        return reminder;
    }

}
