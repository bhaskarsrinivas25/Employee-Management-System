package com.employee.service;

import com.employee.entity.Employee;
import com.employee.exception.ResourceNotFoundException;
import com.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void addEmployeeSavesWhenEmailIsUnique() {
        Employee employee = employee("alice@example.com");
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());
        when(employeeRepository.save(employee)).thenReturn(employee);

        assertSame(employee, employeeService.addEmployee(employee));
        verify(employeeRepository).save(employee);
    }

    @Test
    void addEmployeeRejectsDuplicateEmail() {
        Employee employee = employee("alice@example.com");
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> employeeService.addEmployee(employee));

        assertEquals("Email already exists", exception.getMessage());
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void getEmployeeByIdThrowsWhenMissing() {
        when(employeeRepository.findById(7L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(7L));
    }

    @Test
    void updateEmployeeChangesProvidedValuesAndRejectsExistingEmail() {
        Employee existing = new Employee(1L, "Alice Smith", "alice@example.com", "Engineering", 85000.0);
        Employee update = new Employee(null, "Alice Jones", "new@example.com", "Product", 90000.0);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(employeeRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());
        when(employeeRepository.save(existing)).thenReturn(existing);

        Employee result = employeeService.updateEmployee(1L, update);

        assertSame(existing, result);
        assertEquals("Alice Jones", existing.getName());
        assertEquals("new@example.com", existing.getEmail());
        assertEquals("Product", existing.getDepartment());
        assertEquals(90000.0, existing.getSalary());
    }

    @Test
    void deleteEmployeeDeletesExistingEmployee() {
        Employee employee = employee("alice@example.com");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository).delete(employee);
    }

    @Test
    void delegatesSearchDepartmentAndReportsToRepository() {
        List<Employee> employees = List.of(employee("alice@example.com"));
        List<Object[]> departmentCounts = java.util.Collections.singletonList(new Object[]{"Engineering", 1L});
        when(employeeRepository.findByNameContainingIgnoreCase("ali")).thenReturn(employees);
        when(employeeRepository.findByDepartment("Engineering")).thenReturn(employees);
        when(employeeRepository.getTotalEmployees()).thenReturn(1L);
        when(employeeRepository.getAverageSalary()).thenReturn(85000.0);
        when(employeeRepository.getHighestSalary()).thenReturn(85000.0);
        when(employeeRepository.getLowestSalary()).thenReturn(85000.0);
        when(employeeRepository.getEmployeeCountByDepartment()).thenReturn(departmentCounts);

        assertEquals(employees, employeeService.searchEmployeesByName("ali"));
        assertEquals(employees, employeeService.getEmployeesByDepartment("Engineering"));
        assertEquals(1L, employeeService.getTotalEmployees());
        assertEquals(85000.0, employeeService.getAverageSalary());
        assertEquals(85000.0, employeeService.getHighestSalary());
        assertEquals(85000.0, employeeService.getLowestSalary());
        assertEquals(departmentCounts, employeeService.getEmployeeCountByDepartment());
    }

    private Employee employee(String email) {
        return new Employee(1L, "Alice Smith", email, "Engineering", 85000.0);
    }
}
