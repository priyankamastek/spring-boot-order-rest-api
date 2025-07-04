package com.training.spring.web.custom.exceptions;

/**
 * This is custom exception class.
 * Extending your exception from java.lang.Exception class, it serves as checked exception
 * Checked exceptions can either be caught with try-catch block
 *     or
 * they can be thrown from the methods using throws keyword.
 * Checked Exceptions will force the caller to handle the exception.
 */
public class ResourceNotFoundException extends Exception{

    public ResourceNotFoundException(String message){
        super(message);
    }
}
