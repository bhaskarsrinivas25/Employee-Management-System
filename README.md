# Employee Management System

A RESTful employee management API migrated from Core Java/JDBC to Spring Boot, Spring Data JPA, Hibernate, MySQL, and Jakarta Validation.

## Features
- Create, read, update, and delete employees
- Search by name and filter by department
- Unique email and request validation
- Salary and department reporting
- JSON error responses with meaningful HTTP status codes

## Tech Stack
- Java 21
- Spring Boot 3.3
- Spring Web, Spring Data JPA, Hibernate
- MySQL and Maven
- Jakarta Validation

## Architecture
The application uses `controller`, `service`, `repository`, `entity`, `dto`, and `exception` layers under `com.employee`. Spring Data JPA replaces the former manual JDBC DAO while the original JDBC classes remain available as migration references.

## MySQL Setup
Create the database before starting the application:

```sql
CREATE DATABASE employee_db;
```

Configure credentials with environment variables:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your-password"
$env:DB_URL="jdbc:mysql://localhost:3306/employee_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
```

Hibernate creates or updates the `employees` table using `spring.jpa.hibernate.ddl-auto=update`.

## Run
```bash
mvn spring-boot:run
```

The API is available at `http://localhost:8080`.

## REST API
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/employees` | Create employee |
| GET | `/api/employees` | List employees |
| GET | `/api/employees/{id}` | Find employee |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |
| GET | `/api/employees/search?name=Ann` | Search by name |
| GET | `/api/employees/department/Engineering` | Filter by department |
| GET | `/api/employees/report` | Total and salary summary |
| GET | `/api/employees/report/by-department` | Department counts |

## Example Requests
```bash
curl -X POST http://localhost:8080/api/employees -H "Content-Type: application/json" -d "{\"name\":\"Alice Smith\",\"email\":\"alice@example.com\",\"department\":\"Engineering\",\"salary\":85000}"
curl http://localhost:8080/api/employees
curl http://localhost:8080/api/employees/1
curl "http://localhost:8080/api/employees/search?name=Alice"
curl http://localhost:8080/api/employees/department/Engineering
curl http://localhost:8080/api/employees/report
curl http://localhost:8080/api/employees/report/by-department
curl -X PUT http://localhost:8080/api/employees/1 -H "Content-Type: application/json" -d "{\"name\":\"Alice Jones\",\"email\":\"alice.jones@example.com\",\"department\":\"Engineering\",\"salary\":90000}"
curl -X DELETE http://localhost:8080/api/employees/1
```

## Project Structure
```text
src/main/java/com/employee/
├── EmployeeManagementApplication.java
├── controller/EmployeeController.java
├── service/EmployeeService.java, EmployeeServiceImpl.java
├── repository/EmployeeRepository.java
├── entity/Employee.java
├── dto/EmployeeDTO.java, ReportDTO.java
└── exception/ErrorResponse.java, GlobalExceptionHandler.java, ResourceNotFoundException.java
```
