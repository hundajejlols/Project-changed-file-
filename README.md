# Projekt 2 — Bezpieczne praktyki koderskie w systemach komputerowych

Projekt zrealizowany w ramach przedmiotu Bezpieczeństwo Systemów i Sieci Komputerowych.

Aplikacja została zbudowana w architekturze Secure-by-Design przy użyciu frameworka Spring Boot oraz systemu budowania Gradle. Głównym celem projektu było wyeliminowanie przechowywania jakichkolwiek sekretów oraz haseł jawnym tekstem (plain-text) w kodzie źródłowym i plikach konfiguracyjnych.

## 🚀 Technologie
* Język: Java 21
* Framework: Spring Boot 4.0.6
* Zarządzanie zależnościami: Gradle
* Baza danych: H2 Database (tryb plikowy)
* Kryptografia: Jasypt (szyfrowanie konfiguracji), Spring Security BCrypt (haszowanie haseł)

---

## 📂 Lokalizacja Rozwiązania (Wymóg Instrukcji)

Zgodnie z wymaganiami kryterium oceny, poniżej znajduje się precyzyjna lokalizacja plików realizujących poszczególne zadania projektowe:

### 🔐 Zadanie A — Bezpieczne credentiale bazy danych (Enkrypcja)
Dane logowania do bazy danych (login i hasło) zostały zaszyfrowane algorytmem kryptograficznym, dzięki czemu mogą być bezpiecznie commitowane do publicznego repozytorium. Klucz główny (master-password) jest wstrzykiwany dopiero w momencie uruchomienia.
* Plik konfiguracyjny z szyfrowaniem: src/main/resources/application.properties

### 👤 Zadanie B — Bezpieczne tworzenie kont (Haszowanie)
Rejestracja użytkowników odbywa się za pomocą bezpiecznego haszowania haseł z automatycznym generowaniem soli kryptograficznej (standard BCrypt).
* Klasa konfiguracyjna Security (Bean BCrypt): src/main/java/pl/SecurityConfig.java
* Logika biznesowa rejestracji (Haszowanie przed zapisem): src/main/java/pl/UserService.java
* Encja użytkownika (Model bazy danych): src/main/java/pl/User.java
* Warstwa dostępu do danych (JPA Repository): src/main/java/pl/UserRepository.java
* Punkt wejścia API (Endpoint rejestracji POST): src/main/java/pl/UserController.java

### 🧪 Sekcja Testowa — Automatyczna Weryfikacja Poprawności
Aplikacja zawiera wymagany zestaw 5 przechodzących testów automatycznych (jednostkowych i integracyjnych), badających poprawność haszowania, unikalność soli oraz bezpieczeństwo właściwości systemowych.
* Klasa testowa: src/test/java/pl/BezpieczenstwoSieciApplicationTests.java

---

## ⚙️ Instrukcja Uruchomienia

### 1. Uruchomienie aplikacji (Przekazywanie credentiali sposobem trzecim)
Aplikacja podczas startu wymaga podania klucza głównego (master-password) jako argumentu startowego, w celu odszyfrowania danych logowania do bazy danych na żywo w pamięci RAM.

Z poziomu terminala:
./gradlew bootRun --args='--jasypt.encryptor.password=MojeMasterHaslo2026'

Z poziomu IntelliJ IDEA:
Dodaj poniższy parametr do konfiguracji uruchomieniowej w polu VM options (Modify options -> Add VM options):
-Djasypt.encryptor.password=MojeMasterHaslo2026

### 2. Uruchomienie testów automatycznych (Kryterium: Min. 5 testów)
Wszystkie 5 testów integracyjnych konfiguruje kontekst bazy danych i bezpieczeństwa automatycznie. Możesz je uruchomić komendą:
./gradlew test

---

## 🛠️ Architektura Bezpieczeństwa — Co zrobiono inaczej?
1. Jasypt zamiast surowych plików .env: Standardowe podejście rynkowe oparte na plikach .env niesie za sobą ryzyko przypadkowego wypchnięcia haseł na platformę GitHub. Dzięki Jasypt właściwości w pliku application.properties są zaszyfrowane w formie ENC(...), co pozwala na bezpieczne przechowywanie konfiguracji w publicznym repozytorium.
2. BCrypt zamiast MD5/SHA-256: Tradycyjne funkcje skrótu (SHA-256/MD5) są podatne na szybkie łamanie metodą ataku słownikowego z użyciem nowoczesnych procesorów GPU. BCrypt wprowadza losową sól dla każdego rekordu oraz spowolnienie procesorowe (work factor), gwarantując pełną odporność na ataki typu Rainbow Tables.
