package itmo.soa.flat_service.controllers

import itmo.soa.flat_service.dto.ErrorDto
import itmo.soa.flat_service.exceptions.NotFoundException
import itmo.soa.flat_service.exceptions.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice
@RestController
class ExceptionController {
    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundException(){}

    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun notFoundException(exception: ValidationException) = ErrorDto(
        msg = exception.message,
        fields = exception.fields,
    )

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun internalError(){}
}