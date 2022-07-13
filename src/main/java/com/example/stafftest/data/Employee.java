package com.example.stafftest.data;

import lombok.Data;
import lombok.NonNull;

@Data
public class Employee {
    @NonNull
    private final String name;
    @NonNull
    private final double salary;
    @NonNull
    private final String currency;
    @NonNull
    private final String department;
    @NonNull
    private final String sub_department;
    @NonNull
    private final boolean on_contract;
}
