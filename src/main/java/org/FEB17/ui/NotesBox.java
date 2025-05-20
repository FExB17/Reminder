package org.FEB17.ui;

import javax.swing.*;
import java.awt.*;

public class NotesBox extends JPanel {

    public NotesBox() {


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton actionBtn = new JButton("start/stop");
        buttonPanel.add(actionBtn);
        JButton deleteBtn = new JButton("delete");
        buttonPanel.add(deleteBtn);
        this.add(buttonPanel);
    }
}
