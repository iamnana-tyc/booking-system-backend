package com.iamnana.booking.exception;


import com.iamnana.booking.dto.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
        String message = ex.getMessage();
        APIResponse response = new APIResponse(message, false);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> handleAPIException(APIException ex){
        String message = ex.getMessage();
        APIResponse response = new APIResponse(message, false);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
