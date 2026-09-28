# Vocab Khajana - Spring Boot MVC

Converted from the cleaned JSP application to Spring Boot MVC + Thymeleaf.

## Stack
- Spring Boot 3.5.6
- Java 21
- Spring MVC
- Thymeleaf
- Lombok 1.18.48
- Maven

## Original JSP routes converted
- `index.jsp` -> `/`
- `getVocabs.jsp` -> `/getVocabs` JSON API
- `viewVocabs.jsp` -> `/viewVocabs`
- `viewVocabsSimple.jsp` -> `/viewVocabsSimple`
- `viewVocabsWithDivJquery.jsp` -> `/viewVocabsWithDivJquery`
- `targetWifeRepairingReturning.jsp` -> `/targetWifeRepairingReturning`
- `WEB-INF/views/login.jsp` -> `/login`
- `welcome.jsp` -> `/welcome`
- `list-todos.jsp` -> `/list-todos`

The old JSP scriptlets and `web.xml`/`spring-servlet.xml` are removed.

## Run
```bash
mvn clean test
mvn spring-boot:run
```

Open:
http://localhost:8085

JSON:
http://localhost:8085/getVocabs?offset=0&count=20
