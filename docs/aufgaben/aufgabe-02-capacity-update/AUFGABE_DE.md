# Aufgabe 02: Kapazität eines Studiengangs ändern

## Zeitrahmen

45–60 Minuten, ohne anschließendes Code Review.

## Fachlicher Kontext

Das Zulassungsbüro muss nach einer Anpassung der Studienplanung die Zahl der verfügbaren Plätze eines Studiengangs ändern. Die Abfrage aus Aufgabe 01 ist bereits abgenommen. Diese Aufgabe ergänzt im selben Projekt einen schreibenden Anwendungsfall, ohne die bestehende Abfrage zu verändern.

## User Story

> Als Mitarbeiterin oder Mitarbeiter des Zulassungsbüros möchte ich die Kapazität eines vorhandenen Studiengangs ändern, damit die Abfrage sofort die aktuelle Zahl der Studienplätze liefert.

## Ausgangssituation

Bereitgestellt sind:

- die abgenommene Funktionalität und alle Tests aus Aufgabe 01
- die Entity `StudyProgram` und das `StudyProgramRepository`
- drei H2-Testdatensätze
- Spring Validation, Spring Data JPA, MockMvc und die benötigten Testabhängigkeiten
- der bestehende 404-Fehlervertrag

DTOs, Controller-Methode, Service-Implementierung, Entity-Änderungsmethode und fachliche Tests für die Kapazitätsänderung werden absichtlich nicht vorgegeben. Sie gehören zur Lösung der lernenden Person.

## REST-Schnittstelle

```http
PUT /api/v1/study-programs/{id}/capacity
Content-Type: application/json
```

### Erfolgsfall

```http
PUT /api/v1/study-programs/1/capacity
```

```json
{
  "capacity": 150
}
```

Status: `200 OK`

```json
{
  "id": 1,
  "capacity": 150
}
```

Ein anschließendes `GET /api/v1/study-programs/1` muss `capacity` mit dem Wert `150` liefern. Alle anderen Felder bleiben unverändert.

### Wertebereich

Die zulässige Kapazität ist das geschlossene Intervall `1..1000`:

- `1` und `1000` sind gültig.
- `0`, negative Zahlen und `1001` sind ungültig.
- Ein fehlendes Feld `capacity` sowie `null` sind ebenfalls ungültig.

Eine ungültige Anfrage liefert `400 Bad Request`:

```json
{
  "status": 400,
  "code": "INVALID_CAPACITY",
  "message": "Capacity must be between 1 and 1000"
}
```

`code` und `message` müssen exakt übereinstimmen. Bei einem Validierungsfehler darf die gespeicherte Kapazität nicht verändert werden.

### Studiengang nicht gefunden

Für eine unbekannte ID, zum Beispiel `999`, gilt weiterhin der 404-Vertrag aus Aufgabe 01:

```json
{
  "code": "STUDY_PROGRAM_NOT_FOUND",
  "message": "Study program not found: 999"
}
```

Die Fehlerantwort darf weiterhin das bestehende Feld `status` enthalten.

## Akzeptanzkriterien

1. HTTP-Methode und Pfad lauten exakt `PUT /api/v1/study-programs/{id}/capacity`.
2. Request und Response verwenden eigene unveränderliche DTOs; eine JPA-Entity wird weder als Request noch als Response verwendet.
3. Die erfolgreiche Antwort enthält nur `id` und die aktualisierte `capacity` und liefert Status `200`.
4. Der erlaubte Bereich ist `1..1000`; beide Grenzwerte können erfolgreich gespeichert werden.
5. `0`, negative Zahlen, `1001`, ein fehlender Wert und `null` liefern den vereinbarten 400-Fehler.
6. Eine ungültige Anfrage verändert die Datenbank nicht.
7. Eine unbekannte ID liefert weiterhin den vereinbarten 404-Fehler.
8. Der Aufrufweg ist Controller → Service → Repository; der Controller greift nicht direkt auf das Repository zu.
9. Die Grenze der schreibenden Transaktion liegt im Service; der Controller verwendet kein `@Transactional`.
10. Die Entity ändert die Kapazität über eine fachlich benannte Methode und erhält keinen öffentlichen allgemeinen `setCapacity`-Setter.
11. Die Änderung wird persistiert und ist über den GET-Endpunkt aus Aufgabe 01 sichtbar.
12. Abhängigkeiten werden per Konstruktor injiziert; neue Abhängigkeiten sind `final`.
13. Das PUT-Verhalten ist idempotent: Wiederholtes Setzen derselben Kapazität erzeugt kein anderes fachliches Ergebnis.
14. Alle Tests aus Aufgabe 01 bleiben grün.
15. Neue Service-, Controller- und echte Datenbank-Integrationstests decken Erfolgs- und wichtige Fehlerpfade ab.

## Java-Schwerpunkte

- Unterschied zwischen `int` und `Integer` und die Darstellung von `null` in einem Request
- Java `record` oder eine gleichwertige unveränderliche Klasse als Request-/Response-DTO
- Bean Validation mit `@NotNull`, `@Min` und `@Max`
- Kapselung durch eine fachliche Entity-Methode statt eines allgemeinen Setters
- Transaktionsgrenzen mit `@Transactional` und JPA Dirty Checking
- Idempotenz von PUT
- Grenzwertanalyse mit gültigen Grenzen und Werten knapp außerhalb

## Testanforderungen

Mindestens abzudecken sind:

- Service-Unit-Tests
  - eine vorhandene ID wird aktualisiert und die neue Kapazität zurückgegeben
  - eine unbekannte ID erzeugt die bestehende `StudyProgramNotFoundException`
  - Repository-Interaktionen und die Zustandsänderung der Entity werden geprüft
- MockMvc-Controller-Tests
  - eine gültige Anfrage liefert `200` und das exakte JSON
  - `0`, `1001` und `null` liefern `400` mit dem exakten Fehlervertrag
  - eine unbekannte ID liefert den bestehenden 404-Vertrag
- Spring-Boot-Integrationstests
  - Service und Repository werden nicht gemockt
  - nach PUT wird mit GET geprüft, dass die echte H2-Datenbank aktualisiert wurde
  - mindestens ein gültiger Grenzwert wird geprüft
  - nach einer ungültigen Anfrage bleibt der Datenbankwert unverändert

Tests müssen voneinander isoliert sein. Eine Testtransaktion mit Rollback oder ein Zurücksetzen der Daten vor jedem Test ist möglich; die Ausführungsreihenfolge darf keine Voraussetzung sein.

## Nicht im Umfang

- Änderung von Kennung, Name, Hochschule oder Aktivstatus
- Anlegen, Löschen oder Suchen von Studiengängen
- Massenänderungen von Kapazitäten
- Optimistic Locking, Behandlung paralleler Konflikte oder Audit-Logging
- Spring Security, externe Datenbank, Flyway, Docker oder Kafka
- vorgezogene Implementierung ab Aufgabe 03
- ein neuer Fehlervertrag für syntaktisch ungültiges JSON oder nichtnumerische JSON-Werte

## Abgabe-Checkliste

- [ ] `mvnw.cmd clean test` ist vollständig grün
- [ ] PUT-Pfad, Status und JSON-Verträge stimmen exakt
- [ ] DTOs sind unveränderlich und keine Entity wird direkt exponiert
- [ ] `1`, `1000`, `0`, `1001` und `null` sind gezielt getestet
- [ ] Die schreibende Transaktion liegt im Service
- [ ] Die Entity hat keinen neuen öffentlichen `setCapacity`-Setter
- [ ] GET liefert nach PUT die neue Kapazität
- [ ] Eine ungültige Anfrage verändert die Datenbank nicht
- [ ] Funktionalität und Tests aus Aufgabe 01 bleiben unverändert funktionsfähig
- [ ] Keine spätere Aufgabe wurde vorweggenommen

Nach Fertigstellung den aktuellen Branch committen und „交卷“ schreiben.
