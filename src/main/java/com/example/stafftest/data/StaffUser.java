package com.example.stafftest.data;

import lombok.Data;
import lombok.NonNull;

@Data
public class StaffUser {
    @NonNull
    private final String username;
    @NonNull
    private final String password;
    private String token;
}
