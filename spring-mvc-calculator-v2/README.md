# Spring MVC Scientific Calculator V2

A scientific calculator built with Java 17, Spring Boot, Spring MVC, Thymeleaf, and a JSON REST API. No database is required.

## Features
- Basic operations: add, subtract, multiply, divide, modulo, power
- Scientific operations: square root, sin, cos, tan, log10, natural log, absolute value, factorial
- Trigonometric functions use degrees
- Browser-local calculation history (localStorage; last 50 entries)
- Browser-local light/dark theme preference
- Responsive calculator UI and keyboard support
- REST API request validation and centralized JSON exception responses
- JUnit 5 service tests

## Requirements
- JDK 17+
- Maven 3.6+

## Run
```bash
mvn spring-boot:run
```
Open http://localhost:8080/

## Tests
```bash
mvn test
```

## Build executable JAR
```bash
mvn clean package
java -jar target/spring-mvc-calculator-v2-2.0.0.jar
```

## REST API examples

### Binary calculation
`POST /api/calculator/binary`
```json
{
  "first": 12,
  "second": 3,
  "operation": "divide"
}
```
Response:
```json
{
  "first": 12.0,
  "second": 3.0,
  "operation": "divide",
  "result": 4.0
}
```

Supported binary operations: `add`, `subtract`, `multiply`, `divide`, `modulo`, `power`.

### Unary/scientific calculation
`POST /api/calculator/unary`
```json
{
  "value": 90,
  "operation": "sin"
}
```
Response:
```json
{
  "value": 90.0,
  "operation": "sin",
  "result": 1.0
}
```
Supported unary operations: `sqrt`, `sin`, `cos`, `tan`, `log`, `ln`, `abs`, `factorial`.

### Error response
Invalid requests return HTTP 400 with a JSON body:
```json
{
  "timestamp": "2026-01-01T10:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Division by zero is not allowed"
}
```

## Storage and privacy
History and theme are stored in the browser's `localStorage`, not on the server. Clearing browser site data removes them. Do not enter sensitive data into history.

## Project structure
```text
src/main/java/com/example/calculator/
  controller/     MVC page controller and REST controller
  dto/            API request/response models
  exception/      calculation exception and global handler
  service/        calculator business logic
  CalculatorApplication.java
src/main/resources/templates/calculator.html
src/test/java/com/example/calculator/service/CalculatorServiceTest.java
```
