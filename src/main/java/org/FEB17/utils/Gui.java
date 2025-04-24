package org.FEB17.utils;

import javax.swing.*;
import java.awt.*;

public class Gui {
    public static JLabel createDefaultErrorLabel(){
        JLabel label = new JLabel("");
        label.setFont(new Font("Arial", Font.PLAIN, 10));
        label.setForeground(Color.RED);
        return label;
    }
}
