package org.FEB17.ui;

import org.FEB17.mail.MailData;
import org.FEB17.models.Reminder;
import org.FEB17.models.Status;
import org.FEB17.scheduler.MailScheduler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * schnittstelle zwischen daten und gui
 * Behälter für die Reminder
 */
public class ReminderBoxPanel extends JPanel {

    private final Reminder reminder;
    private final JButton deleteBtn;
    private final JButton actionBtn;
    private final JTextArea infoArea;

    public ReminderBoxPanel (Reminder reminder, ReminderFormPanel formPanel){
        this.reminder = reminder;
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100)); // feste Höhe, volle Breite

        infoArea = new JTextArea();
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
        infoArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                formPanel.fillForm(reminder);
            }
        });

        // auf dem button steht stop wenn es aktiv ist
        actionBtn = new JButton(reminder.getStatus() == Status.ACTIVE ? "Stop" : "Start");
        deleteBtn = new JButton("Delete");

        actionBtn.addActionListener(e -> {
            if(reminder.getStatus() == Status.ACTIVE){
                MailScheduler.stop(reminder.getId());
                reminder.setStatus(Status.STOPPED);
                actionBtn.setText("Start");
                updateDisplay();
            }
            else{
                MailScheduler.startScheduledMailing(
                        reminder.getId(),
                        reminder.getInterval(),
                        () -> new MailData(reminder.getRecipient(), reminder.getSubject(),reminder.getBody(), reminder.getInterval()));
                reminder.setStatus(Status.ACTIVE);
                actionBtn.setText("Stop");
                updateDisplay();
            }
        });

        deleteBtn.addActionListener(e -> {
            MailScheduler.stop(reminder.getId());

            Container parent = this.getParent();
            if (parent != null) {
                parent.remove(this);
                parent.revalidate();
                parent.repaint();
            }

        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(actionBtn);
        btnPanel.add(deleteBtn);
        this.add(btnPanel,BorderLayout.SOUTH);

    }
    public Reminder getReminder(){
        return reminder;
    }
    private void updateDisplay() {
        infoArea.setText(
                "To: " + reminder.getRecipient() + "\n" +
                        "Subject: " + reminder.getSubject() + "\n" +
                        "Interval: " + reminder.getInterval() + " min\n" +
                        "Status: " + reminder.getStatus()
        );
    }

}
