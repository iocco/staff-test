package com.example.stafftest.controller;

import com.example.stafftest.component.DataSetComponent;
import com.example.stafftest.data.Employee;
import com.example.stafftest.data.StaffUser;
import com.example.stafftest.data.SummaryStatistics;
import com.example.stafftest.exception.DuplicateResourceException;
import com.example.stafftest.exception.ResourceNotFoundException;
import com.example.stafftest.factory.SummaryStatisticsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class StaffTestController {

    @Autowired
    private DataSetComponent dataSetComponent;

    @PostMapping("/user")
    public StaffUser login(@RequestBody StaffUser staffUser) {
        String token = getJWTToken(staffUser.getUsername());
        staffUser.setToken(token);
        return staffUser;
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee newEmployee) {
        try {
            dataSetComponent.addEmployee(newEmployee);
            return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/employees/{name}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable String name) {
        try {
            dataSetComponent.deleteEmployee(name);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/contractSalaries")
    public ResponseEntity<SummaryStatistics> getContractSalaries() {
        List<Employee> employees = dataSetComponent.getOnContractEmployees();
        return ResponseEntity.ok(SummaryStatisticsFactory.calculateSummaryStatistics(employees));
    }

    @GetMapping("/contractSalariesDepartment")
    public ResponseEntity<Map<String, SummaryStatistics>> getContractSalariesByDepartment(){
        List<Employee> employees = dataSetComponent.getOnContractEmployees();
        Map<String, SummaryStatistics> stats = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment))
                .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, l -> SummaryStatisticsFactory.calculateSummaryStatistics(l.getValue())));
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/contractSalariesSubDepartment")
    public ResponseEntity<Map<String, Map<String, SummaryStatistics>>> getContractSalariesBySubDepartment(){
        List<Employee> employees = dataSetComponent.getOnContractEmployees();
        Map<String, Map<String, SummaryStatistics>> stats = employees.stream().collect(
                        Collectors.groupingBy(Employee::getDepartment, Collectors.groupingBy(Employee::getSub_department)))
                .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                        m -> m.getValue().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                                l -> SummaryStatisticsFactory.calculateSummaryStatistics(l.getValue())))));
        return ResponseEntity.ok(stats);
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("staffTest")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
