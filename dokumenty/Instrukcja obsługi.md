# Ankieter
Aplikacja do przeprowadzania ankiet stworzona w ramach przedmiotu Inżynieria Oprogramownia w semestrze letnim 2021/2022 na Uniwersytecie Jagiellońskim. Działająca wersja online aplikacji dostępna jest [tutaj](https://ankieter-frontend.vercel.app/).

### Autorzy:
- Blagoja Mladenov
- Magdalena Lipka
- Michał Sławek
- Michał Goniakowski
- Seweryn Ziemski

# Instrukcja obsługi

Aplikacja składa się z dwóch części składowych wymagających osobnych kompilacji.

## Backend

### Beta

Wersja beta backendu aplikacji dostępna jest pod linkiem [https://ankiety-io-projekt.herokuapp.com](https://ankiety-io-projekt.herokuapp.com).  Do wykonywania zapytań wymagany jest dedykowany klient typu Postman. 

Backend napisany jest w frameworku Spring Boot i wymaga zainstalowanych narzędzi [Maven](https://maven.apache.org/) oraz [JDK](https://www.oracle.com/java/technologies/downloads/) do kompilacji. Minimalna wymagana wersja to Java 17.

### Instalacja narzędzi na Ubuntu:

```sh
sudo apt-get install oracle-java17-installer oracle-java17-set-default
sudo apt install maven
```

Należy upewnić się, że Java działa poprawnie oraz wszystkie zmienne środowiskowe są poprawnie przypisane, a narzędzia należą do ścieżki systemu. [Poprawna instalacja środowiska Javy zależy od używanego systemu i indywidualnych konfiguracji.](https://www.baeldung.com/install-maven-on-windows-linux-mac)

### Ustawienia bazy danych

Aplikacja używa bazy danych [Postgresql](https://www.postgresql.org.pl/). Połączenie z bazą danych jest konfigurowane w pliku `src/main/resources/application.properties`. W przypadku zmiany danych bazy danych, należy zaktualizować plik o odpowiednie klucze.

### Uruchomienie aplikacji w wersji developerskiej

Przed uruchomeniem aplikacji należy zainstalować wszystkie wymagane biblioteki (wymienione w pliku `pom.xml`). Instalacji najlepiej dokonać komendą:

```sh
mvn dependency:resolve
```

Po zainstalowaniu bibliotek aplikacja jest gotowa do uruchomienia.

```sh
mvn spring-boot:run
```

Aplikacja domyślnie uruchamia serwer HTTP na porcie `8080`.

### Kompilacja aplikacji do wersji produkcyjnej

Przed kompilacją należy upewnić się, że wszyskie biblioteki są zainstalowane. Służy do tego komenda

```sh
mvn dependency:resolve
```

Jeśli wszystkie biblioteki są poprawnie zainstalowane, można kompilować aplikację komendą:

```sh
mvn package
```

Kompilacja stworzy folder `target`, w którym znajdują się wszystkie skompilowane pliki aplikacji. Aby uruchomić skompilowaną aplikację należy użyć komendy:

```sh
java -jar target/ankieter-0.0.1-SNAPSHOT.jar
```

Aplikacja domyślnie uruchamia serwer HTTP na porcie `8080`.

## Frontend

### Beta
Wersja beta frontend aplikacji dostępna jest pod linkiem [https://ankieter-frontend.vercel.app/](https://ankieter-frontend.vercel.app/). Aplikację należy uruchomić w przeglądarce internetowej.

Frontend napisany jest we frameworku [Next.js](https://nextjs.org/) i wymaga zainstalowanego menedżera paczek [NPM](https://www.npmjs.com/) oraz środowiska [Node.js](https://www.npmjs.com/). Wymagane wersje to Node.js 17 oraz NPM 8.

### Instalacja narzędzi na ubuntu

```sh
sudo apt-get install -y nodejs
```

Należy upewnić się, że narzędzia zostały popwrawnie zainstalowane wpisując komendy:

```sh
node -v
npm -v
```

### Ustalenie połączenia z backendem

W pliku `next.config.js` należy podać adres serwera backendu. W przypadku równoczesnego uruchamiia developerskiej wersji backendu jest to `http://localhost:8080`.

### Uruchomienie aplikacji w wersji developerskiej

Należy zainstalować wszystkie biblioteki komendą:

```sh
npm install
```

Po zainstalowaniu bibliotek aplikacja jest dostępna do uruchomienia komendą:

```sh
npm run dev
```

Wersja developersja aplikacji posiada wbudowany hot reload, dzięki czzemu zmiany w kodzie są natychmiast odzwierciedlone w działającej aplikacji. Aplikacja domyślnie uruchamia serwer WWW na porcie `3000`.

### Kompilacja aplikacji do wersji produkcyjnej

Przed kompilacją należy upewnić się, że wszystkie biblioteki są zainstalowane.

```sh
npm install
```

Jeśli wszystkie bibioleki są poprawnie zainstalowane, aplikacja jest gotowa do kompilacji komendą

```sh
npm run build
```

Kompilacja stworzy folder `.next`, w którym znajdują się wszystkie pliki aplikacji. Aby uruchomić skompilowaną aplikację, należy użyć komendy:

```sh
npm run start
```

Aplikacja domyślnie uruchamia serwer WWW na porcie `3000`.
