package com.example.stafftest.exception;

public class DuplicateResourceException extends RuntimeException{

    public DuplicateResourceException(String name) {
        super("There is already a employee with name: " + name);
    }

}
