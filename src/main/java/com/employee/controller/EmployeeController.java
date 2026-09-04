package com.employee.controller;

import com.employee.dto.EmployeeDTO;
import com.employee.dto.ReportDTO;
import com.employee.entity.Employee;
import com.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * POST /api/employees - Create a new employee
     */
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertDTOToEntity(employeeDTO);
        Employee savedEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(convertEntityToDTO(savedEmployee), HttpStatus.CREATED);
    }

    /**
     * GET /api/employees - Get all employees
     */
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(employeeDTOs, HttpStatus.OK);
    }

    /**
     * GET /api/employees/{id} - Get employee by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(convertEntityToDTO(employee), HttpStatus.OK);
    }

    /**
     * PUT /api/employees/{id} - Update employee
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertDTOToEntity(employeeDTO);
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return new ResponseEntity<>(convertEntityToDTO(updatedEmployee), HttpStatus.OK);
    }

    /**
     * DELETE /api/employees/{id} - Delete employee
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }

    /**
     * GET /api/employees/search - Search employees by name
     */
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDTO>> searchEmployees(@RequestParam String name) {
        List<Employee> employees = employeeService.searchEmployeesByName(name);
        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(employeeDTOs, HttpStatus.OK);
    }

    /**
     * GET /api/employees/department/{department} - Get employees by department
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartment(@PathVariable String department) {
        List<Employee> employees = employeeService.getEmployeesByDepartment(department);
        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(employeeDTOs, HttpStatus.OK);
    }

    /**
     * GET /api/employees/report - Get salary report
     */
    @GetMapping("/report")
    public ResponseEntity<ReportDTO> getReport() {
        long totalEmployees = employeeService.getTotalEmployees();
        Double averageSalary = employeeService.getAverageSalary();
        Double highestSalary = employeeService.getHighestSalary();
        Double lowestSalary = employeeService.getLowestSalary();

        ReportDTO report = new ReportDTO(totalEmployees, averageSalary, highestSalary, lowestSalary);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    /**
     * GET /api/employees/report/by-department - Get employee count by department
     */
    @GetMapping("/report/by-department")
    public ResponseEntity<List<Map<String, Object>>> getEmployeeCountByDepartment() {
        List<Object[]> results = employeeService.getEmployeeCountByDepartment();
        List<Map<String, Object>> reportList = results.stream()
                .map(row -> {
                    Map<String, Object> map = new java.util.HashMap<>();
                    map.put("department", row[0]);
                    map.put("count", row[1]);
                    return map;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(reportList, HttpStatus.OK);
    }

    // Utility methods for DTO conversion
    private EmployeeDTO convertEntityToDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary()
        );
    }

    private Employee convertDTOToEntity(EmployeeDTO employeeDTO) {
        return new Employee(
                employeeDTO.getId(),
                employeeDTO.getName(),
                employeeDTO.getEmail(),
                employeeDTO.getDepartment(),
                employeeDTO.getSalary()
        );
    }

}
