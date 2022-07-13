package com.example.stafftest.component;

import com.example.stafftest.data.Employee;
import com.example.stafftest.exception.DuplicateResourceException;
import com.example.stafftest.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class DataSetComponentTest {

    private DataSetComponent dataSetComponent;

    @BeforeEach
    public void init() {
        dataSetComponent = new DataSetComponent();
    }

    @Test
    public void getAllTest() {
        List<Employee> employees = dataSetComponent.getEmployees();
        Assertions.assertEquals(9, employees.size());
    }

    @Test
    public void addEmployeeTest() {
        dataSetComponent.addEmployee(new Employee("New", 1000, "usd", "dep", "sub", true));
        Assertions.assertEquals(10, dataSetComponent.getEmployees().size());
    }

    @Test
    public void addEmployeeConflictTest() {
        Assertions.assertThrows(DuplicateResourceException.class, () ->
                dataSetComponent.addEmployee(new Employee("Abhishek", 1000, "usd", "dep", "sub", true)));
        Assertions.assertEquals(9, dataSetComponent.getEmployees().size());
    }

    @Test
    public void deleteEmployeeTest() {
        dataSetComponent.deleteEmployee("Abhishek");
        Assertions.assertEquals(8, dataSetComponent.getEmployees().size());
    }

    @Test
    public void deleteEmployeeNotFoundTest() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> dataSetComponent.deleteEmployee("Other"));
        Assertions.assertEquals(9, dataSetComponent.getEmployees().size());
    }

    @Test
    public void findEmployeeTest() {
        Optional<Employee> employee = dataSetComponent.getEmployeeByName("Abhishek");
        Assertions.assertTrue(employee.isPresent());
        Assertions.assertEquals("Abhishek", employee.get().getName());
    }

    @Test
    public void findEmployeeNullTest() {
        Optional<Employee> employee = dataSetComponent.getEmployeeByName("Other");
        Assertions.assertFalse(employee.isPresent());
    }

    @Test
    public void filterOnContractEmployees() {
        List<Employee> employees = dataSetComponent.getOnContractEmployees();
        Assertions.assertEquals(2, employees.size());
        Assertions.assertEquals("Anurag", employees.get(0).getName());
        Assertions.assertEquals("Nikhil", employees.get(1).getName());
    }

}
