package com.springjpa.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.Date;

@ControllerAdvice
@ResponseStatus
public class GlobalExceptionHandler {

    //Catch no data found requests
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorResponse message = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorResponse>(message, HttpStatus.NOT_FOUND);
    }

    //Catch request Params that are bad
    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(TypeMismatchException ex, WebRequest request){
        ErrorResponse message = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                //ex.getMessage(),
                "invalid request parameter",
                request.getDescription(false));

        return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
    }


    //Catch everything else bad
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorResponse message = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorResponse>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}