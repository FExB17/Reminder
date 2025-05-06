package org.FEB17.ui;

import org.FEB17.models.Reminder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ReminderListPanel extends JPanel {

    private final JPanel listContainer;
    private final List<ReminderBoxPanel> reminderBoxes;
    private final ReminderFormPanel formPanel;


    public ReminderListPanel(ReminderFormPanel formPanel) {
        this.formPanel = formPanel;
        this.setLayout(new BorderLayout());
        listContainer = new JPanel();
        // im konstruktor nochmal listContainer, weil BoxLayout speziell ist und separat erst auch den Container braucht auf, dass es abgestimmt werden soll
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(listContainer);
        this.add(scrollPane);

        reminderBoxes = new ArrayList<>();
    }

    public void addReminder(Reminder reminder) {
        ReminderBoxPanel box = new ReminderBoxPanel(reminder,formPanel);
        reminderBoxes.add(box);
        listContainer.add(box);
        listContainer.revalidate();
        listContainer.repaint();
    }


}
