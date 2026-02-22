package com.arfaz.ecom.sbecom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

//Advanced version of ControllerAdvice to manage REST API
@RestControllerAdvice
public class MyGlobalExceptions {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        Map<String, String> errorsRes = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errorsRes.put(fieldName, errorMessage);
        });
//        return errorsRes; we are returning resmessage without proper status code
        return new ResponseEntity<>(errorsRes,  HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<String> myResourceNotFound(ResourceNotFoundException e) {
        String errorMessage = e.getMessage(); // inbuit method of RuntimeException
        // where ResourceNotFoundException extends RuntimeException and passes message super()
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = APIException.class)
    public ResponseEntity<String> myAPIException(APIException e) {
        String errorMessage = e.getMessage(); // inbuit method of RuntimeException
        // where APIException extends RuntimeException and passes message super()
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
