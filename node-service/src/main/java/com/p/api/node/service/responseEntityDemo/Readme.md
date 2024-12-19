# please create a controller class to demonstrate use of various methods of org.springframework.http.ResponseEntity

Here is a demonstration of a Spring Boot controller showcasing various methods of
`org.springframework.http.ResponseEntity`:

### Controller Class: `ResponseEntityDemoController`

```java
package com.example.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/response-demo")
public class ResponseEntityDemoController {

    // Example 1: Returning a plain OK response
    @GetMapping("/ok")
    public ResponseEntity<String> getOkResponse() {
        return ResponseEntity.ok("This is an OK response");
    }

    // Example 2: Returning a response with a custom status and body
    @GetMapping("/custom-status")
    public ResponseEntity<String> getCustomStatus() {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Request was accepted for processing");
    }

    // Example 3: Adding headers to the response
    @GetMapping("/headers")
    public ResponseEntity<String> getResponseWithHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "CustomHeaderValue");
        return ResponseEntity.ok()
                .headers(headers)
                .body("Response with custom headers");
    }

    // Example 4: Returning a response with no content
    @DeleteMapping("/no-content")
    public ResponseEntity<Void> deleteResource() {
        return ResponseEntity.noContent().build();
    }

    // Example 5: Using created() to return a location header for a newly created resource
    @PostMapping("/created")
    public ResponseEntity<String> createResource() {
        URI location = URI.create("/response-demo/resource/123");
        return ResponseEntity.created(location).body("Resource created with ID 123");
    }

    // Example 6: Returning a bad request response
    @GetMapping("/bad-request")
    public ResponseEntity<String> getBadRequest() {
        return ResponseEntity.badRequest().body("Invalid request");
    }

    // Example 7: Building a response with custom headers and status
    @GetMapping("/custom-response")
    public ResponseEntity<Map<String, String>> getCustomResponse() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "ExampleValue");

        Map<String, String> body = new HashMap<>();
        body.put("message", "Custom response body");
        body.put("status", "success");

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(body);
    }

    // Example 8: Handling a NOT FOUND response
    @GetMapping("/not-found")
    public ResponseEntity<String> getNotFoundResponse() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
    }
}
```

### Key Methods Used

1. **`ResponseEntity.ok()`**: Sends an HTTP 200 OK response with a body.
2. **`ResponseEntity.status(HttpStatus)`**: Sets a custom HTTP status code.
3. **`ResponseEntity.headers(HttpHeaders)`**: Adds custom headers to the response.
4. **`ResponseEntity.noContent()`**: Sends an HTTP 204 No Content response.
5. **`ResponseEntity.created(URI)`**: Sends an HTTP 201 Created response with a `Location` header.
6. **`ResponseEntity.badRequest()`**: Sends an HTTP 400 Bad Request response.
7. **`ResponseEntity.build()`**: Builds a response without a body.
8. **Custom Headers and Body**: Combines headers, body, and status.

### How to Test

Each method corresponds to a different endpoint. You can test them using `curl`, Postman, or a browser. For example:

- **GET `/response-demo/ok`** → Returns a 200 OK with a plain message.
- **GET `/response-demo/headers`** → Returns a 200 OK with custom headers.
- **POST `/response-demo/created`** → Returns a 201 Created with a `Location` header.

Let me know if you'd like to expand on any part!