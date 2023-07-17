package com.lucas.matches.api.v1.exception

import com.lucas.matches.service.exception.InvalidLicenseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException
import java.io.IOException

@ControllerAdvice
class MatchErrorHandler {
    @ExceptionHandler(ResponseStatusException::class)
    fun apiException(e: ResponseStatusException): ResponseEntity<APIError> {
        return error(e, HttpStatus.resolve(e.statusCode.value())!!)
    }

    @ExceptionHandler(InterruptedException::class, IOException::class)
    fun internalServerError(e: Exception): ResponseEntity<APIError> {
        return error(e, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(MissingServletRequestParameterException::class, InvalidLicenseException::class)
    fun requiredOrInvalidParamException(e: Exception): ResponseEntity<APIError> {
        return error(e, HttpStatus.BAD_REQUEST)
    }

    private fun error(exception: Exception, httpStatus: HttpStatus): ResponseEntity<APIError> {
        return ResponseEntity(APIError(exception.message!!), httpStatus)
    }
}