package org.FEB17.ui;

import javax.swing.*;
import java.awt.*;

public class NotesForm extends JPanel {


    public NotesForm() {
        this.setLayout(new BorderLayout());

        JLabel textAreaLabel = new JLabel("Note");
        this.add(textAreaLabel, BorderLayout.NORTH);

        JTextArea textField = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textField);
        this.add(scrollPane,BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.Y_AXIS));

        SpinnerNumberModel model = new SpinnerNumberModel(30, 30, null, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        bottomPanel.add(spinner);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton startBtn = new JButton("Start");
        buttonPanel.add(startBtn);
        JButton saveBtn = new JButton("Save");
        buttonPanel.add(saveBtn);
        bottomPanel.add(buttonPanel);
        this.add(bottomPanel, BorderLayout.SOUTH);



    }
}
