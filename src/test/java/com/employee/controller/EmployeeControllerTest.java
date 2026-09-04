package com.employee.controller;

import com.employee.dto.EmployeeDTO;
import com.employee.entity.Employee;
import com.employee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        employee = new Employee(1L, "Alice Smith", "alice@example.com", "Engineering", 85000.0);
        employeeDTO = new EmployeeDTO(1L, "Alice Smith", "alice@example.com", "Engineering", 85000.0);
    }

    @Test
    void createEmployeeReturnsCreatedEmployee() {
        when(employeeService.addEmployee(any(Employee.class))).thenReturn(employee);

        var response = employeeController.createEmployee(employeeDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employee.getEmail(), response.getBody().getEmail());
    }

    @Test
    void getAllEmployeesMapsEntitiesToDtos() {
        when(employeeService.getAllEmployees()).thenReturn(List.of(employee));

        var response = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(employee.getName(), response.getBody().get(0).getName());
    }

    @Test
    void getEmployeeByIdReturnsEmployee() {
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        var response = employeeController.getEmployeeById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void updateEmployeeReturnsUpdatedEmployee() {
        when(employeeService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(employee);

        var response = employeeController.updateEmployee(1L, employeeDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Engineering", response.getBody().getDepartment());
    }

    @Test
    void deleteEmployeeReturnsSuccessMessage() {
        var response = employeeController.deleteEmployee(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee deleted successfully", response.getBody());
        verify(employeeService).deleteEmployee(1L);
    }

    @Test
    void searchAndDepartmentEndpointsReturnResults() {
        when(employeeService.searchEmployeesByName("Alice")).thenReturn(List.of(employee));
        when(employeeService.getEmployeesByDepartment("Engineering")).thenReturn(List.of(employee));

        assertEquals(1, employeeController.searchEmployees("Alice").getBody().size());
        assertEquals(1, employeeController.getEmployeesByDepartment("Engineering").getBody().size());
    }

    @Test
    void reportEndpointsReturnServiceValues() {
        when(employeeService.getTotalEmployees()).thenReturn(1L);
        when(employeeService.getAverageSalary()).thenReturn(85000.0);
        when(employeeService.getHighestSalary()).thenReturn(85000.0);
        when(employeeService.getLowestSalary()).thenReturn(85000.0);
        when(employeeService.getEmployeeCountByDepartment()).thenReturn(
            java.util.Collections.singletonList(new Object[]{"Engineering", 1L}));

        var report = employeeController.getReport();
        var byDepartment = employeeController.getEmployeeCountByDepartment();

        assertEquals(1L, report.getBody().getTotalEmployees());
        assertEquals(85000.0, report.getBody().getAverageSalary());
        assertEquals("Engineering", byDepartment.getBody().get(0).get("department"));
        assertEquals(1L, byDepartment.getBody().get(0).get("count"));
    }
}
