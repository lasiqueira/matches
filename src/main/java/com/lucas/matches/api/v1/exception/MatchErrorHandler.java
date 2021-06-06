package com.lucas.matches.api.v1.exception;

import com.lucas.matches.service.exception.InvalidLicenseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@ControllerAdvice
public class MatchErrorHandler {
    @ExceptionHandler(ResponseStatusException.class) public ResponseEntity<APIError> apiException(final ResponseStatusException e) {
        return error(e, e.getStatus());
    }

    @ExceptionHandler({InterruptedException.class, IOException.class}) public ResponseEntity<APIError> internalServerError(final Exception e) {
        return error(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, InvalidLicenseException.class}) public ResponseEntity<APIError> requiredOrInvalidParamException(final Exception e) {
        return error(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity <APIError> error(final Exception exception, final HttpStatus httpStatus) {
        final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ResponseEntity < > (new APIError(message), httpStatus);
    }
}
