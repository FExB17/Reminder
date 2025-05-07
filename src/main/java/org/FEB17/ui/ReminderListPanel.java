package org.FEB17.ui;

import org.FEB17.controller.ReminderController;
import org.FEB17.models.Reminder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReminderListPanel extends JPanel {

    private final JPanel listContainer;
    private final List<ReminderBoxPanel> reminderBoxes;
    private ReminderController controller;

    public ReminderListPanel() {
        this.setLayout(new BorderLayout());

        // Container für die Reminder-Boxen
        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        reminderBoxes = new ArrayList<>();

        // Scrollbar hinzufügen
        JScrollPane scrollPane = new JScrollPane(listContainer);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // Wird verwendet, wenn Reminder vom Controller direkt übergeben wird
    public void addReminderBox(ReminderBoxPanel panel) {
        reminderBoxes.add(panel);
        listContainer.add(panel);
        listContainer.revalidate();
        listContainer.repaint();
    }

    // Falls Reminder-Objekt übergeben wird (nicht Pflicht, aber praktisch)
    public void addReminder(Reminder reminder) {
        if (controller == null) {
            throw new IllegalStateException("Controller must be set before adding a reminder.");
        }

        ReminderBoxPanel box = new ReminderBoxPanel(reminder, controller);
        addReminderBox(box);
    }

    public void setController(ReminderController controller) {
        this.controller = controller;
    }
}
