# Employees-project-assignment
Spring boot app that handles employees' project assignments

# HR System API

Welcome to the HR System API documentation. This API provides endpoints to manage employees, projects, and their assignments, catering to different user roles.

## Table of Contents

- [Endpoints](##endpoints)
- [Authorization and Security](##authorization-and-security)
- [Getting Started](##getting-started)
  - [Launching the Application](###launching-the-application)
  - [Executing Tests](###executing-tests)
- [Dependencies and Java Version](##dependencies-and-java-version)
- [Entity Constraint Validation](##entity-constraint-validation)

## Endpoints

### Employee Controller

#### Filter Employees

- `GET /employees/list`: Filter employees by partial name, role, and project.

- `GET /employees/{id}`: Get employee details by ID.

- `POST /employees`: Create a new employee.

- `POST /employees/{id}/evaluate`: Evaluate an employee's score.

### Assignment Controller

#### Assignments

- `POST /assignments/assign/{employeeId}`: Assign projects to an employee.

- `POST /assignments/unassign/{employeeId}`: Unassign projects from an employee.

### Project Controller

#### Projects

- `POST /projects/create`: Create a new project.

- `GET /projects/list`: Get a list of all projects.

## Authorization and Security

Authorization and access control are enforced based on user roles:

- **DEVELOPER**: Read-only access to their own profile and assigned projects. No access to other resources.
- **PROJECT-MANAGER**: Read/write access to all projects and employees/projects assignments. Read-only access to employees (score and salary hidden).
- **HR**: Read/write access to all employees, including score and salary. No access to projects.

Endpoints are exposed according to these roles to ensure appropriate data protection.

## Getting Started

### Launching the Application

To set up and run the HR System API on a local environment (Linux):

1. Clone this repository.
2. Navigate to the project directory.
3. Install required dependencies. For example, using Maven:
   ```bash
   mvn install
   ```
4. Launch the application:
    ```bash
    mvn spring-boot:run
    ```

5. Access the Swagger UI at http://localhost:8080/swagger-ui/index.html to explore and interact with the API.

### Executing Tests
Unit tests play a vital role in maintaining the quality of the application. Extensive tests ensure that the API functions reliably. Coverage percentage is expected to be high, as tests are comprehensive and cover various scenarios.

We use mocking frameworks like Mockito to create test doubles for dependencies, enabling isolated testing of components.

To execute tests and calculate coverage (Linux):

1. Run tests:

    ```bash
    mvn test
    ```

2. Coverage reports can be found in the `target/site/jacoco` directory.

## Dependencies and Java Version

This project utilizes the following:

- Java 17
- Spring Boot 3

Dependencies such as Spring Security, Spring Data JPA, and more are managed through the `pom.xml` file.

## Entity Constraint Validation

Entity constraints are validated using the Bean Validation API. Annotations like **@NotNull**, **@Email**, **@Min**, **@Max** are used to ensure data integrity and validity.

