package org.FEB17.ui;

import org.FEB17.controller.ReminderController;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReminderListPanel extends JPanel {

    private final JPanel listContainer;
    private final List<ReminderBoxPanel> reminderBoxes;
    final JButton sortBtn;


    public ReminderListPanel() {
        this.setLayout(new BorderLayout());

        JPanel TopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sortBtn = new JButton("Sort ↑"); // ↓
        sortBtn.setFocusable(false);
        TopPanel.add(sortBtn);
        this.add(TopPanel, BorderLayout.NORTH);

        // Container für die Reminder-Boxen da wir ScrollPane verwenden
        // scrollPane und komplexe Layouts sind nicht kompatibel
        // deshalb lieber einen Container verwenden
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
    public void removeBox(ReminderBoxPanel panel) {
        listContainer.remove(panel);
        listContainer.revalidate();
        listContainer.repaint();
    }
}
