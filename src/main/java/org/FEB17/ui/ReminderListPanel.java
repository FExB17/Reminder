package org.FEB17.ui;

import org.FEB17.controller.ReminderController;
import org.FEB17.models.Reminder;
import org.FEB17.utils.SettingsAccess;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.UUID;

/**
 * `ReminderListPanel` ist ein UI-Panel, das eine Liste von Erinnerungsboxen anzeigt.
 * Es bietet Funktionen zum Hinzufügen, Entfernen und Sortieren von Erinnerungsboxen.
 */
public class ReminderListPanel extends JPanel {

    private final JPanel listContainer;
    final JButton sortBtn;
    private ReminderController controller;
    /**
     * `ascending` gibt an, ob die Liste aufsteigend oder absteigend sortiert ist.
     * wird gelesen aus den Einstellungen
     */
    private boolean ascending = Boolean.parseBoolean(SettingsAccess.getProperty("isAscending"));

    public ReminderListPanel() {
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sortBtn = new JButton();
        sortBtn.setText(ascending ? "Sort ↑" : "Sort ↓");
        sortBtn.setFocusable(false);
        topPanel.add(sortBtn);
        this.add(topPanel, BorderLayout.NORTH);

/**
 *          Container für die Reminder-Boxen da wir ScrollPane verwenden
 *          scrollPane und komplexe Layouts sind nicht kompatibel
 *          deshalb lieber einen Container verwenden
 */
        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));

        // Scrollbar hinzufügen
        JScrollPane scrollPane = new JScrollPane(listContainer);
        this.add(scrollPane, BorderLayout.CENTER);


        sortBtn.addActionListener(e ->{
            ascending = !ascending;
            sortBtn.setText(ascending ? "Sort ↑" : "Sort ↓");
            controller.sortViewByCreatedAt(ascending);
            SettingsAccess.setProperty("isAscending", String.valueOf(ascending));
        });
    }

    public void addReminderBox(ReminderBoxPanel box) {
        listContainer.add(box);
        listContainer.revalidate();
        listContainer.repaint();
    }

    public void removeBox(ReminderBoxPanel panel) {
        listContainer.remove(panel);
        listContainer.revalidate();
        listContainer.repaint();
    }

    public void render (List<Reminder> reminders, ReminderController controller){
        listContainer.removeAll();
        for (Reminder reminder : reminders){
            ReminderBoxPanel box = new ReminderBoxPanel(reminder, controller);
            listContainer.add(box);
        }
        listContainer.revalidate();
        listContainer.repaint();

        // Scrollen zum Anfang der Liste
    SwingUtilities.invokeLater(() -> {
        JScrollBar verticalBar = ((JScrollPane) this.getComponent(1)).getVerticalScrollBar();
        verticalBar.setValue(0);
    });
}

    public void setController(ReminderController controller){
        this.controller = controller;
}

    public ReminderBoxPanel getReminderBox(UUID id){
        for (Component component : listContainer.getComponents()){
            if (component instanceof ReminderBoxPanel box){
                if (box.getReminder().getId().equals(id)){
                    return box;
                }
            }
        }
        return null;
}

}
