package org.FEB17.utils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Hilfsklasse zur Validierung von Eingabefeldern in Swing.
 * Zeigt eine Fehlermeldung im zugehörigen JLabel an, wenn das Feld leer ist.
 * Die Meldung verschwindet automatisch, sobald das Feld gültig wird.
 */
public class FieldValidator {

    public static boolean isValidEmail(JTextComponent field) {
    if (field.getText().isBlank()){
        return false;
    }
    try {
        InternetAddress emailAddr = new InternetAddress(field.getText());
        emailAddr.validate();
        return true;
    } catch (AddressException ex) {
        return false;
    }
}

    // die validierung und das aktualisierung des Labels kann ausgelagert werden
    public static boolean validateField(JTextComponent field, JLabel errorLabel){
        Runnable validate = () -> {
            errorLabel.setText(field.getText().isBlank() ? "* Required" : "");
        };
            runListeners(validate, field);
            return !field.getText().isBlank();
    }

    // die validierung und das aktualisierung des Labels kann ausgelagert werden
    public static boolean validateEmail(JTextComponent field, JLabel errorLabel) {
        Runnable validate = () -> {
            String text = field.getText();
            if (text.isBlank()) {
                errorLabel.setText("* Required");
                return;
            }
            try{
                InternetAddress emailAddr = new InternetAddress(text);
                // überprüft die adresse nicht zuverlässig
                emailAddr.validate();
                errorLabel.setText("");
            } catch (AddressException ex) {
                errorLabel.setText("* Invalid E-mail");
            }
        };
        runListeners(validate, field);
        return isValidEmail(field);
    }

    public static void runListeners(Runnable validate, JTextComponent field) {
        field.addFocusListener(new FocusAdapter(){
            @Override
            public void focusLost(FocusEvent e) {
                validate.run();
            }
        });

        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validate.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validate.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validate.run();
            }
        });
        validate.run();
    }
}
