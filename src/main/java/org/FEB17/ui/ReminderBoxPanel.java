package org.FEB17.ui;

import org.FEB17.controller.ReminderController;
import org.FEB17.models.Reminder;
import org.FEB17.models.Status;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//  furkanervan@hotmail.de
public class ReminderBoxPanel extends JPanel {

    private final Reminder reminder;
    private final JLabel statusLabel;

    private final JButton actionBtn;
    private final JTextArea infoArea;

    public ReminderBoxPanel (Reminder reminder, final ReminderController controller) {
        final JButton deleteBtn;
        this.reminder = reminder;
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100)); // feste Höhe, volle Breite

        statusLabel = new JLabel(reminder.getStatus().name());
        this.add(statusLabel, BorderLayout.NORTH);

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
                controller.fillForm(reminder.getId());
            }
        });

        // auf dem button steht stop wenn es aktiv ist
        actionBtn = new JButton(reminder.getStatus() == Status.ACTIVE ? "Stop" : "Start");
        deleteBtn = new JButton("Delete");

        actionBtn.addActionListener(e -> controller.toggleReminder(reminder.getId()));

        deleteBtn.addActionListener(e -> {
            controller.deleteReminder(reminder.getId());

            // damit sich die Box nicht von sich aus löscht sondern vom parent
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
    // später benötigt, falls der manager ausgeweitet wird
    private void updateDisplay() {
        infoArea.setText(
                "To: " + reminder.getRecipient() + "\n" +
                        "Subject: " + reminder.getSubject() + "\n" +
                        "Interval: " + reminder.getInterval() + " min\n" +
                        "Status: " + reminder.getStatus()
        );
    }
    public void updateToActive() {
        statusLabel.setText("Active");
        actionBtn.setText("Stop");
    }

    public void updateToStopped() {
        statusLabel.setText("Stopped");
        actionBtn.setText("Start");
    }

}
