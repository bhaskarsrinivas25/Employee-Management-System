package com.employee.repository;

import com.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find employee by email
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Find all employees by department
     */
    List<Employee> findByDepartment(String department);

    /**
     * Find employees by name containing (case-insensitive)
     */
    List<Employee> findByNameContainingIgnoreCase(String name);

    /**
     * Get total number of employees
     */
    @Query("SELECT COUNT(e) FROM Employee e")
    long getTotalEmployees();

    /**
     * Get average salary of all employees
     */
    @Query("SELECT AVG(e.salary) FROM Employee e")
    Double getAverageSalary();

    /**
     * Get highest salary
     */
    @Query("SELECT MAX(e.salary) FROM Employee e")
    Double getHighestSalary();

    /**
     * Get lowest salary
     */
    @Query("SELECT MIN(e.salary) FROM Employee e")
    Double getLowestSalary();

    /**
     * Get employee count by department
     */
    @Query("SELECT e.department, COUNT(e) FROM Employee e GROUP BY e.department")
    List<Object[]> getEmployeeCountByDepartment();

}
