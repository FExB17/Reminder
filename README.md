# Reminder-App (Java Swing)

Dies ist eine einfache Desktop-Anwendung zum Erstellen und Verwalten von E-Mail-Remindern.  
Die App basiert auf Java und verwendet Java Swing für die grafische Benutzeroberfläche.

## Funktionen

- Erstellen von Erinnerungen mit Empfänger, Betreff, Nachricht und Intervall
- Automatischer E-Mail-Versand im gewählten Intervall
- Übersicht aller aktiven Reminder in der rechten Spalte
- Reminder einzeln starten und stoppen
- Reminder löschen
- Alle Reminder gleichzeitig stoppen
- Automatisches Speichern beim Beenden und Laden beim Start
- Sortierte Anzeige nach Erstellungsdatum
- Zuletzt verwendete Sortierreihenfolge wird gespeichert
- Zusätzlicher Reiter für Desktop-Benachrichtigungen

## Aufbau

Das Projekt folgt dem MVC-Prinzip (Model-View-Controller) und ist übersichtlich in Packages gegliedert:

- `controller/`: Koordiniert die Anwendung und verbindet UI und Logik
- `desktop/`: Systemnahe Komponenten wie Desktop-Benachrichtigungen
- `mail/`: E-Mail-Versand über SMTP
- `manager/`: Verwaltung und Status der Reminder
- `models/`: Reminder-Datenmodelle
- `persistence/`: Speicherung und Laden der Daten (JSON mit Gson)
- `scheduler/`: Intervallsteuerung mit Timer
- `ui/`: Java Swing GUI (Formulare, Listen, Eingabefelder)
- `utils/`: Hilfsfunktionen und Konfigurationslogik

Zusätzliche Konfigurationsdateien befinden sich in `resources/`:

- `config.properties` – SMTP-Zugangsdaten
- `logging.properties` – Java-Logging-Konfiguration
- `settings.properties` – sonstige Anwendungseinstellungen

## Benutzung

1. Projekt in einer Java-IDE (z. B. IntelliJ IDEA) öffnen
2. `config.properties` mit Absenderdaten (Gmail) anpassen
3. Hauptklasse `MailGuiApp` starten
4. Reminder über das Formular anlegen
5. Beim Beenden werden alle Reminder automatisch gespeichert

## Voraussetzungen

- Java 17 oder neuer
- Internetverbindung (für den E-Mail-Versand)
- Gmail-Konto für SMTP-Versand (`config.properties`)

## Speicherort

Reminder werden lokal als JSON-Datei gespeichert unter:

```
~/.reminder-app/reminders.json
```

## Konfiguration

Die Anwendung verwendet eine `config.properties`-Datei mit folgenden Feldern:

```properties
mail.user=example@gmail.com  
mail.password=deinPasswort  
mail.smtp=smtp.gmail.com
```

## ToDo / Ideen

- Filterfunktion (z. B. nur aktive Reminder anzeigen)
- Tray-Icon und Hintergrundbetrieb
- Android-Version (zukünftig geplant)

## Autor

Projekt von **Furkan Ervan**  
Erstellt zum Lernen von Java, GUI-Entwicklung und strukturiertem Softwaredesign.
