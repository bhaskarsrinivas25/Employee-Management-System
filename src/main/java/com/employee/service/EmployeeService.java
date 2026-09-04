package com.employee.service;

import com.employee.entity.Employee;
import java.util.List;

public interface EmployeeService {

    /**
     * Create a new employee
     */
    Employee addEmployee(Employee employee);

    /**
     * Get all employees
     */
    List<Employee> getAllEmployees();

    /**
     * Get employee by ID
     */
    Employee getEmployeeById(Long id);

    /**
     * Update employee
     */
    Employee updateEmployee(Long id, Employee employee);

    /**
     * Delete employee
     */
    void deleteEmployee(Long id);

    /**
     * Find employees by name (search)
     */
    List<Employee> searchEmployeesByName(String name);

    /**
     * Get all employees by department
     */
    List<Employee> getEmployeesByDepartment(String department);

    /**
     * Get total number of employees
     */
    long getTotalEmployees();

    /**
     * Get average salary
     */
    Double getAverageSalary();

    /**
     * Get highest salary
     */
    Double getHighestSalary();

    /**
     * Get lowest salary
     */
    Double getLowestSalary();

    /**
     * Get employee count by department
     */
    List<Object[]> getEmployeeCountByDepartment();

}
