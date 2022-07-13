package com.example.stafftest.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String name) {
        super("Employee with name: " + name + " does not exist");
    }
}
