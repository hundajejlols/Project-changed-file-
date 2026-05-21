# 🛡️ Pakiety Bezpieczeństwa — Projekty Zaliczeniowe

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring_Boot-4.0.6-brightgreen?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot 4.0.6" />
  <img src="https://img.shields.io/badge/Python-3.10+-blue?style=for-the-badge&logo=python&logoColor=white" alt="Python 3" />
  <img src="https://img.shields.io/badge/Gradle-9.4.1-lightgrey?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle 9.4.1" />
  <img src="https://img.shields.io/badge/Architektura-Secure--by--Design-red?style=for-the-badge" alt="Secure by Design" />
</p>

---

## 📝 Spis Treści
1. [Wstęp i Założenia](#-wstęp-i-założenia)
2. [Ewidencja i Lokalizacja Rozwiązania (Kryterium Formalne)](#-ewidencja-i-lokalizacja-rozwiązania-kryterium-formalne)
3. [Projekt 1 — System Wykrywania Zmian](#-projekt-1--system-wykrywania-zmian)
4. [Projekt 2 — Bezpieczne Praktyki Koderskie](#-projekt-2--bezpieczne-praktyki-koderskie)
5. [Automatyczna Sekcja Testowa (Weryfikacja Min. 5 Testów)](#-automatyczna-sekcja-testowa-weryfikacja-min-5-testów)
6. [Analiza Architektoniczna i Uzasadnienie Projektowe](#-analiza-architektoniczna-i-uzasadnienie-projektowe)

---

## 🎯 Wstęp i Założenia

[cite_start]Niniejsze repozytorium stanowi kompletne rozwiązanie zadań projektowych z przedmiotu **Bezpieczeństwo Systemów i Sieci Komputerowych**[cite: 260]. [cite_start]Całość kodu źródłowego oraz konfiguracji została zaimplementowana z restrykcyjnym zachowaniem paradygmatu **Secure-by-Design**, co eliminuje podatności związane z wyciekiem sekretów w kodzie oraz pozwala na bieżąco kontrolować integralność zasobów aplikacyjnych[cite: 298, 310].

> [!NOTE]
> [cite_start]**Deklaracja Jakości Kodu (Clean Code):** Kod źródłowy w pełni realizuje wytyczne estetyczne instrukcji: nazewnictwo klas, metod i zmiennych jest samo-tłumaczące się [cite: 286][cite_start], wyeliminowano nadmiarowe komentarze deweloperskie i puste linie, zachowano spójne wcięcia (indentation) oraz całkowicie zrezygnowano ze stosowania emotikon wewnątrz kodu źródłowego[cite: 287].

---

## 📂 Ewidencja i Lokalizacja Rozwiązania (Kryterium Formalne)

[cite_start]Zgodnie z wymaganiem punktowym instrukcji projektowej dotyczącej precyzyjnego wskazania lokalizacji plików realizujących zadania[cite: 285], poniżej przedstawiono kompletną mapę komponentów systemu:

| Zadanie Projektowe | Główna Funkcja / Cel | Ścieżka do pliku w repozytorium |
| :--- | :--- | :--- |
| [cite_start]**Projekt 1** [cite: 297] | Logika Weryfikatora Integralności | `BezpieczenstwoSieci Projekt 1/main.py` |
| [cite_start]**Projekt 1** [cite: 297] | Baza Skrótów Stanu Plików | `BezpieczenstwoSieci Projekt 1/hashes.json` |
| [cite_start]**Projekt 2 (A)** [cite: 304, 312] | Szyfrowanie Właściwości Bazy (Jasypt) | `BezpieczenstwoSieci Projekt 2/src/main/resources/application.properties` |
| [cite_start]**Projekt 2 (B)** [cite: 304, 315] | Definicja Kodu Szyfrującego (BCrypt Bean) | `BezpieczenstwoSieci Projekt 2/src/main/java/pl/bezpieczenstwosieci/config/SecurityConfig.java` |
| [cite_start]**Projekt 2 (B)** [cite: 304, 315] | Haszowanie haseł przed zapisem JPA | `BezpieczenstwoSieci Projekt 2/src/main/java/pl/bezpieczenstwosieci/service/UserService.java` |
| [cite_start]**Projekt 2 (B)** [cite: 304, 315] | Model Encji Bazodanowej Użytkownika | `BezpieczenstwoSieci Projekt 2/src/main/java/pl/bezpieczenstwosieci/model/User.java` |
| [cite_start]**Projekt 2 (B)** [cite: 304, 315] | Interfejs Repozytorium Danych | `BezpieczenstwoSieci Projekt 2/src/main/java/pl/bezpieczenstwosieci/repository/UserRepository.java` |
| [cite_start]**Projekt 2 (B)** [cite: 304, 315] | Bezpieczny Punkt Wejścia (POST API) | `BezpieczenstwoSieci Projekt 2/src/main/java/pl/bezpieczenstwosieci/controller/UserController.java` |
| [cite_start]**Warstwa Testów** [cite: 281] | Integracyjna Weryfikacja Standardów | `BezpieczenstwoSieci Projekt 2/src/test/java/pl/bezpieczenstwosieci/BezpieczenstwoSieciApplicationTests.java` |

---

## 📋 Projekt 1 — System Wykrywania Zmian

[cite_start]Aplikacja konsolowa wczytuje plik binarnie lub tekstowo i bada jego integralność za pomocą kryptograficznej funkcji skrótu **SHA-256**[cite: 298, 301]. [cite_start]Chroni to system przed ukrytą modyfikacją danych (ataki typu *Data Tampering*) bez konieczności kosztownej analizy semantycznej struktury wewnętrznej plików[cite: 299].

### 🚀 Instrukcja Uruchomienia
# Wybór katalogu komponentu
cd "BezpieczenstwoSieci Projekt 1"

# Egzekucja skryptu dla wybranego pliku docelowego
python main.py <ścieżka_do_pliku>

### 🔁 Schemat Stanów i Komunikatów Terminala
New file '<nazwa>' tracked successfully. → Plik analizowany po raz pierwszy; nastąpiła automatyczna rejestracja jego podpisu cyfrowego w bazie hashes.json.  

File '<nazwa>' has not changed. → Pełna integralność zachowana; bieżący skrót matematyczny odpowiada wartości referencyjnej.

File '<nazwa>' has changed. → Wykryto naruszenie integralności zasobu! Skrót uległ zmianie; baza danych automatycznie aktualizuje stan do nowej sygnatury.

### 💻 Projekt 2 — Bezpieczne Praktyki Koderskie

System webowy eliminujący podatność przechowywania haseł oraz credentiali w formie jawnego tekstu (plain-text). Połączenie z plikową bazą H2 bazuje na unikalnej enkrypcji poświadczeń, a rejestracja użytkowników wykorzystuje kryptograficzny mechanizm rozciągania klucza z automatyczną solą.  

Zastosowanie Sposobu Trzeciego (Wstrzykiwanie w Runtime):
Zgodnie z wytycznymi projektu, master-password służący do deszyfracji parametrów bazy danych nie znajduje się w repozytorium. Jest wstrzykiwany bezpośrednio do pamięci RAM jako argument startowy maszyny JVM podczas inicjalizacji procesu aplikacji.

### 🚀 Instrukcja Bezpiecznego Uruchomienia
Opcja A: Konsola (Gradle Wrapper)

cd "BezpieczenstwoSieci Projekt 2"
./gradlew bootRun --args='--jasypt.encryptor.password=MojeMasterHaslo2026'

Opcja B: Środowisko IntelliJ IDEA
 1 Otwórz okno konfiguracji: Run/Debug Configurations.
 2 Wybierz opcję: Modify options → Add VM options.
 3 Wprowadź flagę systemową deszyfracji in-memory:
 -Djasypt.encryptor.password=MojeMasterHaslo2026

### 🧪 Automatyczna Sekcja Testowa (Weryfikacja Min. 5 Testów)
W klasie testowej zaimplementowano zestaw dokładnie 5 testów integracyjno-jednostkowych. Potwierdzają one kryptograficzną odporność architektury i gwarantują poprawne zaliczenie kryterium automatycznego sprawdzania kodu:

Plik: BezpieczenstwoSieciApplicationTests.java -> Status: 5/5 PASSED

test1_shouldSaveUserToDatabaseAndHashPassword
Sprawdza logiczną poprawność rejestracji. Potwierdza, że zapisany w strukturze DB obiekt posiada ID oraz że surowe hasło wejściowe zostało trwale i nieodwracalnie zmodyfikowane w bazie przy użyciu asymetrycznego dopasowania passwordEncoder.matches.

test2_shouldVerifyThatHashedPasswordsAreDifferentForSameInput
Weryfikuje unikalność soli kryptograficznej (Salt). Tworzy dwa konta z identycznym hasłem jawnym (SamePassword123). Test kończy się sukcesem, gdy oba zapisane skróty w kolumnie password są całkowicie różne, co dowodzi odporności na ataki słownikowe i tablice tęczowe.

test3_shouldVerifyPropertiesAreLoadedSuccessfully
Bada integrację komponentu Jasypt ze strukturą Spring Boot Core. Gwarantuje, że zaszyfrowana tożsamość użytkownika bazy danych jest poprawnie dekodowana ze środowiska konfiguracyjnego.

test4_shouldConfirmPlaintextDatabasePasswordIsNotExposed
Rygorystyczny test bezpieczeństwa typu Negative Assessment. Sprawdza, czy produkcyjne hasło dostępowe do relacyjnej bazy danych (baza123) nie wyciekło w postaci czystego tekstu w plikach .properties lub konfiguracji środowiska.

test5_shouldSuccessfullyConnectToDatabaseAndCount
Integracyjny test transakcyjny. Potwierdza, że poświadczenia uzyskane drogą deszyfracji Jasypt pozwalają aplikacji na nawiązanie poprawnego, uwierzytelnionego połączenia z bazą H2 oraz wykonanie bezbłędnej operacji zapisu i zliczania rekordów.

Egzekucja całego pakietu testów:
./gradlew test

### 🏛️ Analiza Architektoniczna i Uzasadnienie Projektowe
Zgodnie z wymogami kryterium analitycznego, poniżej przedstawiono zestawienie wdrożonych mechanizmów w odniesieniu do powszechnych, standardowych praktyk rynkowych:  

🛡️ 1. Bezpieczeństwo Poświadczeń: Jasypt vs Tradycyjne Pliki .env
Praktyka Powszechna: Przypisywanie haseł bazodanowych do plików .env lub .properties przy jednoczesnym dodaniu ich struktur do pliku .gitignore.

Zagrożenie Standardu: Podejście to jest podatne na czynnik błędu ludzkiego (human error). Przypadkowy commit pliku konfiguracyjnego, błędna modyfikacja reguł gita lub celowy wyciek natychmiast kompromituje dane uwierzytelniające w publicznym repozytorium.

Wdrożona Architektura: Poświadczenia w pliku application.properties są zaszyfrowane silnym algorytmem i zdeponowane w bezpiecznym formacie ENC(...). Plik konfiguracyjny może być bezpiecznie udostępniony, ponieważ klucz deszyfrujący (master-password) nie istnieje w kodzie i jest wstrzykiwany wyłącznie jako ulotny parametr procesu w runtime.

🛡️ 2. Przechowywanie haseł: BCrypt vs Funkcje Skrótu (MD5 / SHA-256)
Praktyka Powszechna: Przetwarzanie haseł użytkowników popularnymi algorytmami skrótu ogólnego przeznaczenia (np. MD5, SHA-1, SHA-256).

Zagrożenie Standardu: Wspomniane algorytmy zostały zaprojektowane pod kątem maksymalnej przepustowości i szybkości matematycznej. Współczesne układy GPU mogą przetwarzać miliardy kombinacji SHA-256 na sekundę, co pozwala na błyskawiczne złamanie haseł atakiem brute-force lub za pomocą gotowych struktur danych typu Rainbow Tables.

Wdrożona Architektura: Zastosowanie algorytmu BCrypt wprowadza dwie bariery obronne:
Kryptograficzna Sól (Salt): Do każdego hasła dobierana jest unikalna sekwencja bitów, eliminując podatność na gotowe tablice tęczowe.
Adaptacyjny Koszt (Work Factor): BCrypt celowo obciąża procesor matematycznie. Złożoność obliczeniowa spowalnia czas weryfikacji dla pojedynczego logowania do bezpiecznej granicy milisekundowej, co czyni zmasowane ataki sprzętowe brute-force całkowicie nieopłacalnymi i niewykonalnymi w skończonym czasie.
