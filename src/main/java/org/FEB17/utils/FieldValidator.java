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



    /**
     * Installiert eine einfache Pflichtfeld-Validierung auf einem Eingabefeld.
     * Sobald das Feld leer ist, wird eine Meldung angezeigt. Bei gültiger Eingabe verschwindet sie.
     *
     * @param field das Eingabefeld (z. B. JTextField)
     * @param errorLabel das JLabel für Fehlermeldungen
     */
    public static void install(JTextComponent field, JLabel errorLabel){
        Runnable validate = () -> {
            errorLabel.setText(field.getText().isBlank() ? "* Required" : "");
        };
            runListeners(validate, field);
    }

    public static void installEmailValidation(JTextComponent field, JLabel errorLabel) {
        //ein regex für E-mail formate (schwache version)
        //return email != null && email.matches("^[\\\\w\\\\.-]+@[\\\\w\\\\.-]+\\\\.[a-zA-Z]{2,}$");
        Runnable validate = () -> {
            String text = field.getText();
            if (text.isEmpty() || text.isBlank()) {
                errorLabel.setText("* Required");
                return;
            }
            try{
                InternetAddress emailAddr = new InternetAddress(text);
                emailAddr.validate();
                errorLabel.setText("");
            } catch (AddressException ex) {
                errorLabel.setText("* Invalid E-mail");
            }
        };
        runListeners(validate, field);
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
