package com.viktorkuts.apigateway.utils;

import com.viktorkuts.apigateway.utils.exceptions.InUseException;
import com.viktorkuts.apigateway.utils.exceptions.InvalidPostalCodeException;
import com.viktorkuts.apigateway.utils.exceptions.InvalidTimeAllocationException;
import com.viktorkuts.apigateway.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    private HttpErrorInfo createHttpErrorInfo(HttpStatus status, WebRequest request, Exception ex) {
        final String path = request.getDescription(false);
        final String message = ex.getMessage();
        log.debug("[HttpErrorInfo] {}: {} ({})", status, message, path);
        return new HttpErrorInfo(status, path, ex.getMessage());
    }
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidPostalCodeException.class)
    public HttpErrorInfo handleInvalidPostalCodeException(InvalidPostalCodeException ex, WebRequest request) {
        return createHttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY, request, ex);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTimeAllocationException.class)
    public HttpErrorInfo handleInvalidTimeAllocationException(InvalidTimeAllocationException ex, WebRequest request){
        return createHttpErrorInfo(HttpStatus.BAD_REQUEST, request, ex);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public HttpErrorInfo handleNotFoundException(NotFoundException ex, WebRequest request) {
        return createHttpErrorInfo(HttpStatus.NOT_FOUND, request, ex);
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InUseException.class)
    public HttpErrorInfo handleInUseException(InUseException ex, WebRequest request) {
        return createHttpErrorInfo(HttpStatus.CONFLICT, request, ex);
    }
}
