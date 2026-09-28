# Shrimad Bhagwat Geeta - Spring Boot MVC

Spring Boot MVC + Thymeleaf conversion of the original Shrimad Bhagwat Geeta application.

## Stack

- Java 17+
- Spring Boot 3.5.5
- Spring MVC
- Thymeleaf
- Jackson
- Bootstrap 3

## Run

```powershell
mvn clean spring-boot:run
```

Open:

```text
http://localhost:8081/
```

## MVC routes

```text
GET /
GET /home
GET /chapter/{chapterNo}
GET /chapter/{chapterNo}/verse/{verseNo}
GET /word-meaning/{wordId}
```

## Important implementation detail

The JSON data is application data and is loaded from:

```text
src/main/resources/data/json/
```

The service converts JSON into `List<Map<String,Object>>` before exposing it to Thymeleaf. This avoids SpringEL/ObjectNode indexing errors such as:

```text
Indexing into type 'com.fasterxml.jackson.databind.node.ObjectNode' is not supported
```

Templates therefore use normal property access:

```html
${c.id}
${c.chapterTitle}
${chapter.verses}
${verse.verseHeader}
```

The original AngularJS `#/chapter/...` and `#/word-meaning/...` links are converted to server-side MVC URLs.

## Audio

The original application contains a large MP3 collection. The lightweight ZIP does not duplicate the ~300 MB audio directory.

Copy the original audio directory to:

```text
src/main/resources/static/audio/
```

Example:

```text
src/main/resources/static/audio/001_001.mp3
```

The verse pages already point to `/audio/...`.
