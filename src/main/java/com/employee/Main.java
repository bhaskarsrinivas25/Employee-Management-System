package com.employee;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final EmployeeDao employeeDao = new EmployeeDao();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            employeeDao.createTableIfNotExists();
            boolean running = true;

            while (running) {
                printMenu();
                System.out.print("Choose an option: ");
                int choice = readInt();

                switch (choice) {
                    case 1 -> addEmployee();
                    case 2 -> showAllEmployees();
                    case 3 -> findEmployeeById();
                    case 4 -> updateEmployee();
                    case 5 -> deleteEmployee();
                    case 6 -> showReport();
                    case 7 -> {
                        running = false;
                        System.out.println("Goodbye!");
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    private static void printMenu() {
        System.out.println("\n===== Employee Management System =====");
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. Find Employee by ID");
        System.out.println("4. Update Employee");
        System.out.println("5. Delete Employee");
        System.out.println("6. Show Report");
        System.out.println("7. Exit");
    }

    private static void addEmployee() throws Exception {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Department: ");
        String department = scanner.nextLine();
        System.out.print("Salary: ");
        double salary = readDouble();

        Employee employee = new Employee(name, email, department, salary);
        validateEmployee(employee);
        employeeDao.addEmployee(employee);
        System.out.println("Employee added successfully.");
    }

    private static void showAllEmployees() throws Exception {
        List<Employee> employees = employeeDao.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("\nID | Name | Email | Department | Salary");
        System.out.println("---------------------------------------------");
        for (Employee employee : employees) {
            System.out.printf("%d | %s | %s | %s | %.2f%n",
                    employee.getId(),
                    employee.getName(),
                    employee.getEmail(),
                    employee.getDepartment(),
                    employee.getSalary());
        }
    }

    private static void findEmployeeById() throws Exception {
        System.out.print("Employee ID: ");
        int id = readInt();
        Employee employee = employeeDao.getEmployeeById(id);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }
        System.out.println(employee);
    }

    private static void updateEmployee() throws Exception {
        System.out.print("Employee ID: ");
        int id = readInt();
        Employee existingEmployee = employeeDao.getEmployeeById(id);
        if (existingEmployee == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.print("New Name (leave blank to keep current): ");
        String name = scanner.nextLine();
        if (!name.isBlank()) {
            existingEmployee.setName(name);
        }

        System.out.print("New Email (leave blank to keep current): ");
        String email = scanner.nextLine();
        if (!email.isBlank()) {
            existingEmployee.setEmail(email);
        }

        System.out.print("New Department (leave blank to keep current): ");
        String department = scanner.nextLine();
        if (!department.isBlank()) {
            existingEmployee.setDepartment(department);
        }

        System.out.print("New Salary (0 to keep current): ");
        double salary = readDouble();
        if (salary > 0) {
            existingEmployee.setSalary(salary);
        }

        validateEmployee(existingEmployee);
        employeeDao.updateEmployee(existingEmployee);
        System.out.println("Employee updated successfully.");
    }

    private static void deleteEmployee() throws Exception {
        System.out.print("Employee ID: ");
        int id = readInt();
        employeeDao.deleteEmployee(id);
        System.out.println("Employee deleted successfully.");
    }

    private static void showReport() throws Exception {
        System.out.println(employeeDao.getReport());
    }

    private static void validateEmployee(Employee employee) {
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required.");
        }
        if (!employee.getName().matches("[A-Za-z ]{2,50}")) {
            throw new IllegalArgumentException("Name must contain only letters and spaces.");
        }
        if (employee.getEmail() == null || !employee.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$")) {
            throw new IllegalArgumentException("Email must be valid.");
        }
        if (employee.getDepartment() == null || employee.getDepartment().trim().isEmpty()) {
            throw new IllegalArgumentException("Department is required.");
        }
        if (employee.getSalary() <= 0) {
            throw new IllegalArgumentException("Salary must be greater than zero.");
        }
    }

    private static int readInt() {
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Please enter a valid integer.");
        }
    }

    private static double readDouble() {
        String input = scanner.nextLine();
        try {
            return Double.parseDouble(input.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Please enter a valid number.");
        }
    }
}
