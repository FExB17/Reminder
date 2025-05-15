# Reminder-App (Java Swing)

Dies ist eine einfache Desktop-Anwendung zum Erstellen und Verwalten von E-Mail-Remindern.  
Die App basiert auf Java und nutzt Java Swing für die Benutzeroberfläche.

## Funktionen

- Erinnerungen mit Empfänger, Betreff, Nachricht und Intervall erstellen
- Automatischer E-Mail-Versand im gewählten Intervall
- Übersicht aller Reminder in der rechten Spalte
- Start/Stopp von einzelnen Remindern
- Reminder löschen
- Alle Reminder stoppen
- Reminder werden beim Beenden gespeichert und beim Start wieder geladen
- Reminder werden sortiert angezeigt (nach Erstellungsdatum)
- der letzte Sortierzustand wird gespeichert

## Aufbau

Das Projekt ist nach dem MVC-Prinzip (Model-View-Controller) aufgebaut:

- **model/**: Reminder-Daten, Status, Manager
- **ui/**: Swing-Benutzeroberfläche (Formulare, Boxen, Listen)
- **controller/**: Verbindung zwischen Logik und GUI
- **scheduler/**: E-Mail-Versand mit Timer
- **persistence/**: Reminder in JSON-Datei speichern und laden

## Benutzung

1. Projekt mit einer Java-IDE wie IntelliJ öffnen
2. config.properties anpassen (E-Mail-Daten)
2. Main-Klasse starten: `MailGuiApp`
3. Reminder mit Formular anlegen
4. Anwendung schließen → Reminder werden automatisch gespeichert

## Voraussetzungen

- Java 17 oder neuer
- Internetverbindung (für den E-Mail-Versand)
- Absender-Maildaten (nur gmail) in der Konfigurationsdatei (`config.properties`)

## Speicherort

Reminder werden als JSON-Datei im Benutzerverzeichnis gespeichert unter:
```
~/.reminder-app/reminders.json
```

## Konfiguration

Die Anwendung verwendet eine `config.properties`-Datei, z. B:

```properties
mail.user=example@gmail.com
mail.password=deinPasswort
mail.smtp=smtp.gmail.com
```

## ToDo / Ideen
- zusätzlicher Reiter für Benachrichtigungen auf dem Display
- Filterfunktion (z. B. nur aktive Reminder anzeigen)
- Tray-Icon und Hintergrundmodus
- Android-Version (Irgendwann)

## Autor

Projekt von Furkan Ervan 
Erstellt zum Lernen von Java, GUI-Entwicklung und sauberem Softwaredesign.
