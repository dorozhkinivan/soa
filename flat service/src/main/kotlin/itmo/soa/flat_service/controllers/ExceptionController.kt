package itmo.soa.flat_service.controllers

import itmo.soa.flat_service.dto.ErrorDto
import itmo.soa.flat_service.exceptions.NotFoundException
import itmo.soa.flat_service.exceptions.ValidationException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
@RestController
class ExceptionController {
    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundException(){}

    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequestException(exception: ValidationException) = ErrorDto(
        msg = exception.message,
        fields = exception.fields,
    )

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badMethodArgumentTypeException(exception: MethodArgumentTypeMismatchException) = ErrorDto(
        msg = "Invalid type of parameter",
        fields = listOf(exception.name),
    )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException) = ErrorDto(
        msg = "Invalid parameter values",
        fields = ex.bindingResult.fieldErrors.map { it.field },
    )

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun internalError(){}
}