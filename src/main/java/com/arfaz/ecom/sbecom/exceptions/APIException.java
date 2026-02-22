package com.arfaz.ecom.sbecom.exceptions;

/**
 * Custom Exception class for handling generic API-related errors.
 *
 * This exception extends RuntimeException because:
 * - It represents business logic failures.
 * - It is an unchecked exception.
 * - It works well with Spring Boot's @ControllerAdvice for global handling.
 */
public class APIException extends RuntimeException {

    /**
     * serialVersionUID is used during serialization.
     * It ensures that a loaded class corresponds exactly
     * to a serialized object.
     * Although not mandatory for REST APIs,
     * it is considered a good practice.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * Allows creation of exception without custom message.
     * Rarely used, but kept for flexibility and framework compatibility.
     */
    public APIException() {
    }

    /**
     * Constructor that accepts a custom error message.
     *message - detailed error message describing the API failure.
     *  The super(message) call passes the message to the parent
     * RuntimeException class, so it can be retrieved later using getMessage().
     */
    public APIException(String message)
    {
        super(message);
    }
}
