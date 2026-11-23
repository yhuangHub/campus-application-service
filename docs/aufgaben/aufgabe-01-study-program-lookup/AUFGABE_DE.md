# Aufgabe 01: Studiengang nach ID abfragen

## Zeitrahmen

30–40 Minuten, ohne anschließendes Code Review.

## Fachlicher Kontext

Eine Bewerberin oder ein Bewerber möchte vor der Bewerbung die Details eines Studiengangs prüfen. Drei fiktive Studiengänge sind bereits in der H2-Datenbank vorhanden. Eine REST-Schnittstelle existiert noch nicht.

## User Story

> Als Bewerberin oder Bewerber möchte ich einen Studiengang anhand seiner ID abrufen, damit ich Kennung, Name, Hochschule, Kapazität und Aktivstatus prüfen kann.

## Ausgangssituation

Bereitgestellt sind:

- Java 21 und Spring Boot
- die JPA-Entity `StudyProgram`
- das `StudyProgramRepository`
- drei H2-Testdatensätze
- OpenAPI/Swagger-Abhängigkeiten
- ein grundlegender Context- und Seed-Data-Test

DTO, Service, Controller, API-Fehlerbehandlung und fachliche Tests sind absichtlich nicht implementiert.

## REST-Schnittstelle

```http
GET /api/v1/study-programs/{id}
```

### Erfolgsfall

```http
GET /api/v1/study-programs/1
```

```json
{
  "id": 1,
  "code": "CS-BER-01",
  "name": "Computer Science",
  "university": "Campus University Berlin",
  "capacity": 120,
  "active": true
}
```

Status: `200 OK`

### Nicht gefunden

```http
GET /api/v1/study-programs/999
```

```json
{
  "code": "STUDY_PROGRAM_NOT_FOUND",
  "message": "Study program not found: 999"
}
```

Status: `404 Not Found`

Die Fehlerantwort darf zusätzlich `timestamp` und `path` enthalten. `code` und `message` müssen jedoch exakt dem Vertrag entsprechen.

## Akzeptanzkriterien

1. HTTP-Methode und Pfad entsprechen der Aufgabenstellung.
2. ID `1` liefert `200` und alle sechs vereinbarten Felder.
3. Die Antwort verwendet ein eigenes DTO und serialisiert keine JPA-Entity direkt.
4. Die Daten werden über das Repository aus der echten H2-Datenbank gelesen und nicht fest codiert.
5. Eine unbekannte ID liefert `404`.
6. Der Fehlercode lautet `STUDY_PROGRAM_NOT_FOUND`.
7. Die Fehlermeldung lautet `Study program not found: {id}`.
8. Der Aufrufweg ist Controller → Service → Repository.
9. Abhängigkeiten werden per Konstruktor injiziert.
10. `Optional.get()` wird nicht direkt aufgerufen.
11. Service-Unit-Tests decken gefunden und nicht gefunden ab.
12. Controller-Tests decken `200` und `404` einschließlich Response-Inhalt ab.
13. Mindestens ein Integrationstest prüft den echten Datenbankzugriff ohne gemockten Service.
14. Der vorhandene Basistest bleibt grün.

## Java-Schwerpunkte

- Verantwortlichkeiten von Klassen und Objekten
- Konstruktorinjektion und `final`-Abhängigkeiten
- `Optional` und `orElseThrow`
- eigene Runtime Exception
- Mapping von Entity zu DTO
- Eignung eines Java Records als Response-DTO
- Unterschied zwischen `==` und `equals()`

## Testanforderungen

Empfohlen sind mindestens:

- `StudyProgramServiceTest`
  - ID 1 wird gefunden und vollständig gemappt
  - ID 999 erzeugt den richtigen Exception-Typ und die richtige Meldung
- `StudyProgramControllerTest`
  - erfolgreiche 200-Antwort
  - 404-Antwort mit Fehlercode und Meldung
- `StudyProgramLookupIntegrationTest`
  - echter Spring Context, MockMvc und H2
  - weder Service noch Repository mocken

Testnamen sollen Verhalten und erwartetes Ergebnis ausdrücken.

## Nicht im Umfang

- POST, PUT oder DELETE
- Suche oder Pagination
- Spring Security
- Flyway, PostgreSQL oder Docker
- Änderungen an vorhandenen Fachdaten
- vorgezogene Implementierung späterer Aufgaben

## Abgabe-Checkliste

- [ ] `mvnw.cmd clean test` ist vollständig grün
- [ ] Keine Entity wird als API-Antwort zurückgegeben
- [ ] Keine Feldinjektion
- [ ] Keine fest codierte Erfolgsantwort
- [ ] Fehlercode und Meldung entsprechen exakt dem Vertrag
- [ ] Erfolgs- und Fehlerpfad sind getestet
- [ ] Keine Funktion einer späteren Aufgabe wurde vorweggenommen

Nach Fertigstellung den aktuellen Branch pushen und „交卷“ schreiben.

