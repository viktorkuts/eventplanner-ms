package com.viktorkuts.customers.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    private HttpErrorInfo createHttpErrorInfo(HttpStatus status, WebRequest request, Exception ex) {
        final String path = request.getDescription(false);
        final String message = ex.getMessage();
        log.debug("[HttpErrorInfo] {}: {} ({})", httpStatus, message, path);
        return new HttpErrorInfo(status, path, ex.getMessage());
    }
}
