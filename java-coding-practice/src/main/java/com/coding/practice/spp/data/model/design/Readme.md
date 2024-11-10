# SPP Application: From Thoughts to Reality

## Expected Functionalities

### Journey: First User Onboarding in the System

*To be defined (TBD)*

- Include steps for collecting initial user data and setting up a user profile.
- Define the flow for welcoming new users and introducing them to application features.

### Journey: Signup

*To be defined (TBD)*

- Outline the form fields and validation rules for user registration.
- Include email/phone verification as optional steps.
- Optionally, support for third-party authentication (e.g., Google, Facebook, etc.).

### Journey: Login for Authenticated Users, Redirection to Login Page for Unauthenticated Users

1. **First Release**:
    - Basic implementation using a hardcoded user for testing.

2. **Second Release**:
    - Transition to in-memory users stored within the application.

3. **Third Release**:
    - Implement authentication using a database to persist user credentials.

4. **Fourth Release**:
    - Add JWT token-based authentication to improve security and enable stateless API interactions.
    - Include token expiration and refresh mechanisms.

### Journey: Authorization

1. **First Release**: Implement basic role-based access control:
    - **`ApplicationUser`**:
        - Can create nodes.
        - Can relate nodes.

    - **`ApplicationAdmin`**:
        - Can configure existing node types.
        - Can create new node types.
        - Can create new relationships between nodes.
        - Can configure existing relationships.

2. Gradual Enhancements:
    - Introduce more roles as the system evolves.
    - Support dynamic role assignments and role-based API restrictions.

### Journey: Creating a Basic Node

Nodes can later be converted into specific types like **Topic**, **Task**, **Tag**, **Course**, **CourseItem**, etc.,
and vice versa.

#### **Implementation Thoughts**:

- Users should be able to create a basic node with a non-empty title.
- Nodes will initially be standalone, with no relationships, but users should be able to link them later.
- Allow users to define attributes for nodes with string values.
- Add search and filter functionalities to manage nodes effectively.
- Ensure all changes are logged for audit purposes.

---

## Expected Non-Functional Requirements (NFRs)

### NFR: Maintain Code Coverage >60%

- Maintain a consistent code coverage threshold of at least 60% during testing.

### NFR: Tag Creation for Each Deployment

- Automatically generate a unique tag before every new deployment to ensure traceability.

### NFR: Application Segregation into Microservices

- Gradually decompose the application into microservices, ensuring each service has a clear purpose and well-defined
  boundaries.

### NFR: Use of a Common Library

- Create and use a shared library for utility methods, common interfaces, and reusable components.

### NFR: Use of Automation Tools

- Implement tools for unit testing, builds, and deployment.
- Initially, set up these tools locally for each developer.
- Later, configure GitHub Actions or equivalent CI/CD pipelines for automation.

### NFR: API Documentation with Swagger

- Gradually document REST APIs using Swagger for better clarity and ease of use.
- Ensure each endpoint has comprehensive details, including request/response schemas and example values.

### NFR: Uniform Response Object Structure

- Establish a consistent structure for API responses, with fields for status, data, and error details.

### NFR: Validations in APIs and Frontend

- Implement strict input validations in APIs and the frontend to prevent errors and ensure data integrity.

### NFR: Detailed Javadoc

- Provide meaningful Javadoc for all methods, classes, and modules to improve code maintainability.

### NFR: Logging and Monitoring

- Implement centralized logging and monitoring to track application health and user activity.
- Use tools like ELK Stack, Prometheus, or Grafana for monitoring.

### NFR: Security Standards

- Ensure all sensitive data is encrypted in transit and at rest.
- Regularly conduct security audits and patch vulnerabilities.

### NFR: Scalability and Performance

- Design the system to handle a growing number of users and data efficiently.
- Optimize database queries, API performance, and resource utilization.
