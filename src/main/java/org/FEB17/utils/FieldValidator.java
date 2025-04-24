package org.FEB17.utils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.*;
import javax.swing.text.JTextComponent;

public class FieldValidator {
    public static boolean validateField(JTextComponent field, JLabel errorLabel){
        if (field.getText().isBlank()){
            errorLabel.setText("* Required");
            return false;
        }else{
            errorLabel.setText("");
        }
        return true;
    }

    public static boolean isValidEmail(String email){
        //ein regex für E-mail formate (schwache version)
        //return email != null && email.matches("^[\\\\w\\\\.-]+@[\\\\w\\\\.-]+\\\\.[a-zA-Z]{2,}$");
        try {
            InternetAddress address = new InternetAddress(email);
            address.validate(); // wirft eine exception, wenn die Adresse ungültig ist, deshalb keine weiteren statements
            return true;
        } catch (AddressException e) {
            return false;
        }

    }
}
