# Employee Management System

This project is a CRUD-based employee management system built with Core Java, JDBC, and MySQL.

## Features
- Add employee
- View all employees
- Find an employee by ID
- Update employee details
- Delete an employee
- Generate a department-wise salary report

## Prerequisites
- Java 17+
- MySQL Server
- Maven
- VS Code with Java Extension Pack

## MySQL Setup
1. Start MySQL and create a database named `employee_db`.
2. Update your MySQL username/password in [src/main/java/com/employee/DatabaseConnection.java](src/main/java/com/employee/DatabaseConnection.java).
3. The application will create the `employees` table automatically.

## Run in VS Code
1. Open the folder in VS Code.
2. Install the Java Extension Pack.
3. Open the terminal and run:
   ```bash
   mvn compile exec:java -Dexec.mainClass=com.employee.Main
   ```

## Notes
If `mvn` is not found, install Maven and add it to your PATH.
