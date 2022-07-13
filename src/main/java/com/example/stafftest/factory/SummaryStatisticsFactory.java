package com.example.stafftest.factory;

import com.example.stafftest.data.Employee;
import com.example.stafftest.data.SummaryStatistics;

import java.util.List;

public class SummaryStatisticsFactory {

    public static SummaryStatistics calculateSummaryStatistics(List<Employee> employees) {
        double sum = 0;
        double min = 0;
        double max = 0;
        if(employees.isEmpty()) {
            return new SummaryStatistics(0, 0, 0);
        }

        double firstValue = employees.get(0).getSalary();
        sum = firstValue;
        max = firstValue;
        min = firstValue;

        for(int i=1; i<employees.size(); i++) {
            double value = employees.get(i).getSalary();
            sum += value;
            max = (value > max)? value : max;
            min = (value < min)? value : min;
        }
        return new SummaryStatistics(sum/employees.size(), max, min);
    }
}
