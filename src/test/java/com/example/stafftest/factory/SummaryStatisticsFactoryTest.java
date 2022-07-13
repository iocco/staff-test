package com.example.stafftest.factory;

import com.example.stafftest.data.Employee;
import com.example.stafftest.data.SummaryStatistics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class SummaryStatisticsFactoryTest {

    @Test
    public void summaryEmptyTest(){
        SummaryStatistics summary = SummaryStatisticsFactory.calculateSummaryStatistics(Collections.emptyList());

        Assertions.assertEquals(0, summary.getMean());
        Assertions.assertEquals(0, summary.getMax());
        Assertions.assertEquals(0, summary.getMin());
    }

    @Test
    public void summaryOneItemTest(){
        SummaryStatistics summary = SummaryStatisticsFactory.calculateSummaryStatistics(List.of(
                new Employee("1", 1.0, "usd", "dep", "sub", true)));

        Assertions.assertEquals(1.0, summary.getMean());
        Assertions.assertEquals(1.0, summary.getMax());
        Assertions.assertEquals(1.0, summary.getMin());
    }

    @Test
    public void summaryMultipleItemsTest(){
        SummaryStatistics summary = SummaryStatisticsFactory.calculateSummaryStatistics(List.of(
                new Employee("1", 1.0, "usd", "dep", "sub", true),
                new Employee("1", 2.0, "usd", "dep", "sub", true)));

        Assertions.assertEquals(1.5, summary.getMean());
        Assertions.assertEquals(2.0, summary.getMax());
        Assertions.assertEquals(1.0, summary.getMin());
    }

}
