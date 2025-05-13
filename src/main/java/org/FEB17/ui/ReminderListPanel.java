package org.FEB17.ui;

import org.FEB17.controller.ReminderController;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * `ReminderListPanel` ist ein UI-Panel, das eine Liste von Erinnerungsboxen anzeigt.
 * Es bietet Funktionen zum Hinzufügen, Entfernen und Sortieren von Erinnerungsboxen.
 */
public class ReminderListPanel extends JPanel {

    private final JPanel listContainer;
    private final List<ReminderBoxPanel> reminderBoxes;
    final JButton sortBtn;
    private ReminderController controller;
    private boolean isAscending = true;


    public ReminderListPanel() {
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sortBtn = new JButton("Sort ↑"); // ↓
        sortBtn.setFocusable(false);
        topPanel.add(sortBtn);
        this.add(topPanel, BorderLayout.NORTH);

        // Container für die Reminder-Boxen da wir ScrollPane verwenden
        // scrollPane und komplexe Layouts sind nicht kompatibel
        // deshalb lieber einen Container verwenden
        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        reminderBoxes = new ArrayList<>();

        // Scrollbar hinzufügen
        JScrollPane scrollPane = new JScrollPane(listContainer);
        this.add(scrollPane, BorderLayout.CENTER);

        sortBtn.addActionListener(e ->{
            // reminder aus manager holen
            // lesen in welche richtung sortiert werden muss
            // aus der setting.properties file
            // sortieren
            // reminder in den listContainer hinzufügen
            // beim erstellen sind sie ohnehin neu alt nach neu
            // neuen reminder sollen standardmäßig oben sein
        });
    }


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
