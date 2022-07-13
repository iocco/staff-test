package com.example.stafftest;

import com.example.stafftest.controller.StaffTestController;
import com.example.stafftest.data.Employee;
import com.example.stafftest.data.SummaryStatistics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StaffTestApplicationTests {

    @Autowired
    private StaffTestController controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void createEmployeeTest() {
        ResponseEntity<Employee> response = controller.addEmployee(new Employee("New", 1000, "USD", "dep", "sub", false));
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals("New", response.getBody().getName());
    }

    @Test
    public void createEmployeeConflictTest() {
        ResponseEntity<Employee> response = controller.addEmployee(new Employee("Abhishek", 1000, "USD", "dep", "sub", false));
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Assertions.assertFalse(response.hasBody());
    }

    @Test
    public void deleteEmployeeTest() {
        ResponseEntity<Employee> response = controller.deleteEmployee("Himani");
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertFalse(response.hasBody());
    }

    @Test
    public void deleteEmployeeNotFoundTest() {
        ResponseEntity<Employee> response = controller.deleteEmployee("Other");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getSummaryStatsTest() {
        ResponseEntity<SummaryStatistics> summary = controller.getContractSalaries();
        Assertions.assertEquals(HttpStatus.OK, summary.getStatusCode());
        Assertions.assertEquals(100000, summary.getBody().getMean());
        Assertions.assertEquals(90000, summary.getBody().getMin());
        Assertions.assertEquals(110000, summary.getBody().getMax());
    }

    @Test
    public void getSummaryStatsByDepartment() {
        ResponseEntity<Map<String, SummaryStatistics>> summary = controller.getContractSalariesByDepartment();
        Assertions.assertEquals(HttpStatus.OK, summary.getStatusCode());
        Assertions.assertEquals(90000, summary.getBody().get("Banking").getMean());
        Assertions.assertEquals(110000, summary.getBody().get("Engineering").getMean());
    }

    @Test
    public void getSummaryStatsBySubDepartment() {
        ResponseEntity<Map<String, Map<String, SummaryStatistics>>> summary = controller.getContractSalariesBySubDepartment();
        Assertions.assertEquals(HttpStatus.OK, summary.getStatusCode());
        Assertions.assertEquals(90000, summary.getBody().get("Banking").get("Loan").getMean());
        Assertions.assertEquals(110000, summary.getBody().get("Engineering").get("Platform").getMean());
    }

}
