package com.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    public void createTableIfNotExists() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS employees (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                email VARCHAR(100) NOT NULL UNIQUE,
                department VARCHAR(100) NOT NULL,
                salary DOUBLE NOT NULL
            )
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    public void addEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO employees (name, email, department, salary) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setString(3, employee.getDepartment());
            preparedStatement.setDouble(4, employee.getSalary());
            preparedStatement.executeUpdate();
        }
    }

    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setDepartment(resultSet.getString("department"));
                employee.setSalary(resultSet.getDouble("salary"));
                employees.add(employee);
            }
        }
        return employees;
    }

    public Employee getEmployeeById(int id) throws SQLException {
        String sql = "SELECT * FROM employees WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setId(resultSet.getInt("id"));
                    employee.setName(resultSet.getString("name"));
                    employee.setEmail(resultSet.getString("email"));
                    employee.setDepartment(resultSet.getString("department"));
                    employee.setSalary(resultSet.getDouble("salary"));
                    return employee;
                }
            }
        }
        return null;
    }

    public void updateEmployee(Employee employee) throws SQLException {
        String sql = "UPDATE employees SET name = ?, email = ?, department = ?, salary = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setString(3, employee.getDepartment());
            preparedStatement.setDouble(4, employee.getSalary());
            preparedStatement.setInt(5, employee.getId());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public String getReport() throws SQLException {
        String sql = "SELECT department, COUNT(*) AS total, ROUND(AVG(salary), 2) AS avg_salary FROM employees GROUP BY department";

        StringBuilder report = new StringBuilder();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            report.append("Employee Report\n");
            report.append("----------------\n");
            while (resultSet.next()) {
                report.append(String.format("Department: %-15s | Employees: %-3d | Avg Salary: %.2f%n",
                        resultSet.getString("department"),
                        resultSet.getInt("total"),
                        resultSet.getDouble("avg_salary")));
            }
        }
        return report.toString();
    }
}
