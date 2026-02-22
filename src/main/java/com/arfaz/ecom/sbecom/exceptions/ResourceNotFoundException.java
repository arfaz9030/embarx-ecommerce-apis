package com.arfaz.ecom.sbecom.exceptions;

/**
 * Custom Exception to handle scenarios where a requested resource
 * (like Customer, Product, Order, etc.) is not found in the database.
 * This class extends RuntimeException because:
 * - It represents a business logic exception.
 * - It is an unchecked exception.
 * - Spring Boot handles RuntimeExceptions well with @ControllerAdvice.
 */
public class ResourceNotFoundException extends RuntimeException {

    // Name of the resource (e.g., Customer, Product)
    private String resourceName;

    // Field name used to search (e.g., id, email)
    private String field;

    // Field value when the search parameter is String type
    private String fieldName;

    // Field value when the search parameter is Long type
    private Long fieldId;

    // Default constructor (optional but useful for framework compatibility)
    public ResourceNotFoundException() {
    }

    /**
     * Constructor used when resource is searched using String field.
     * Example: email, username
     */
    public ResourceNotFoundException(String resourceName,
                                     String field, String fieldName) {
        // Calls parent RuntimeException constructor with formatted message
        super(String.format("%s not found with %s: %s",
                resourceName, field, fieldName));

        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }

    /**
     * Constructor used when resource is searched using Long type.
     * Example: id
     */
    public ResourceNotFoundException(String resourceName,
                                     String field, Long fieldId) {

        // %d is used for numeric formatting
        super(String.format("%s not found with %s: %d",
                resourceName, field, fieldId));

        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }
}

