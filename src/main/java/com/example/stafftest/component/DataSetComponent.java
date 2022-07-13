package com.example.stafftest.component;

import com.example.stafftest.data.Employee;
import com.example.stafftest.exception.DuplicateResourceException;
import com.example.stafftest.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DataSetComponent {

    private final List<Employee> employees;

    public DataSetComponent() {
        this.employees = new ArrayList<>();
        this.employees.add(new Employee("Abhishek", 145000, "USD", "Engineering", "Platform", false));
        this.employees.add(new Employee("Anurag", 90000, "USD", "Banking", "Loan", true));
        this.employees.add(new Employee("Himani", 240000, "USD", "Engineering", "Platform", false));
        this.employees.add(new Employee("Yatendra", 30, "USD", "Operations", "CustomerOnboarding", false));
        this.employees.add(new Employee("Ragini", 30, "USD", "Engineering", "Platform", false));
        this.employees.add(new Employee("Nikhil", 110000, "USD", "Engineering", "Platform", true));
        this.employees.add(new Employee("Guljit", 30, "USD", "Administration", "Agriculture", false));
        this.employees.add(new Employee("Himanshu", 70000, "EUR", "Operations", "CustomerOnboarding", false));
        this.employees.add(new Employee("Anupam", 20000000, "INR", "Engineering", "Platform", false));
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        if(!getEmployeeByName(employee.getName()).isPresent()) {
            employees.add(employee);
        } else {
            throw new DuplicateResourceException(employee.getName());
        }
    }

    public void deleteEmployee(String name){
        Employee employee = getEmployeeByName(name).orElseThrow(() -> new ResourceNotFoundException(name));
        employees.remove(employee);
    }

    public Optional<Employee> getEmployeeByName(String name) {
        return employees.stream().filter(x -> x.getName().equals(name)).findFirst();
    }

    public List<Employee> getOnContractEmployees() {
        return employees.stream().filter(x -> x.isOn_contract()).collect(Collectors.toList());
    }
}
