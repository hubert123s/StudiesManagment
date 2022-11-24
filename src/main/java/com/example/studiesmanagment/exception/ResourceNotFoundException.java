package com.example.studiesmanagment.exception;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(Long id) {
        super("Not found Object with id:"+id);
    }
}
